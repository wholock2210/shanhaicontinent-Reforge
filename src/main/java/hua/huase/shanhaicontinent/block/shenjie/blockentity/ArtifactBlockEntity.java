package hua.huase.shanhaicontinent.block.shenjie.blockentity;
import hua.huase.shanhaicontinent.init.ModRecipesInit;
import hua.huase.shanhaicontinent.recipe.ArtifactRecipe;
import hua.huase.shanhaicontinent.register.ModBlockEntities;
import hua.huase.shanhaicontinent.util.artifact.ArtifactMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.core.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public class ArtifactBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<ItemStackHandler> handler = LazyOptional.of(() -> itemHandler);

    private UUID craftingPlayerUUID;

    private int progress = 0;
    private boolean isCrafting = false;
    private static final int TOTAL_CRAFTING_TICKS = 100;

    public ArtifactBlockEntity(@NotNull BlockEntityType<ArtifactBlockEntity> artifactBlockEntityBlockEntityType, BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARTIFACT.get(), pos, state);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if (level != null) {
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return slot != 1; // 只有输出槽不能放置物品
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.putInt("Progress", progress);
        tag.putBoolean("IsCrafting", isCrafting);
        // 新增：保存玩家UUID
        if (craftingPlayerUUID != null) {
            tag.putUUID("CraftingPlayer", craftingPlayerUUID);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        progress = tag.getInt("Progress");
        isCrafting = tag.getBoolean("IsCrafting");
        // 新增：恢复玩家UUID
        if (tag.hasUUID("CraftingPlayer")) {
            craftingPlayerUUID = tag.getUUID("CraftingPlayer");
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        load(tag);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("神器铸造");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ArtifactMenu(id, inventory, this);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return handler.cast();
        }
        return super.getCapability(cap);
    }

    public void tick() {
        if (isCrafting && !level.isClientSide()) {
            progress++;
            if (progress >= TOTAL_CRAFTING_TICKS) {
                finishCrafting();
            }
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public boolean canStartCrafting(Player player) {
        if (isCrafting || level == null) return false;

        ItemStack token = itemHandler.getStackInSlot(0);
        if (token.isEmpty()) return false;

        RecipeManager recipeManager = level.getRecipeManager();
        SimpleContainer container = new SimpleContainer(token);
        Optional<ArtifactRecipe> recipe = recipeManager.getRecipeFor(ModRecipesInit.ARTIFACT_RECIPE.get(), container, level);

        return recipe.isPresent() && recipe.get().hasEnoughMaterials(player);
    }

    public void startCrafting(Player player) {
        this.isCrafting = true;
        this.progress = 0;
        this.craftingPlayerUUID = player.getUUID();
        setChanged();
    }

    private void finishCrafting() {
        if (level == null || level.isClientSide()) return;

        Player player = craftingPlayerUUID != null ?
                level.getPlayerByUUID(craftingPlayerUUID) : null;

        if (player == null) {
            isCrafting = false;
            progress = 0;
            setChanged();
            return;
        }
        ItemStack token = itemHandler.getStackInSlot(0);
        if (token.isEmpty()) {
            isCrafting = false;
            progress = 0;
            setChanged();
            return;
        }
        RecipeManager recipeManager = level.getRecipeManager();
        SimpleContainer container = new SimpleContainer(token);
        Optional<ArtifactRecipe> recipe = recipeManager.getRecipeFor(
                ModRecipesInit.ARTIFACT_RECIPE.get(), container, level);

        if (recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(level.registryAccess());

            CompoundTag nbt = result.getOrCreateTag();
            nbt.putString("sh_playername", "literal{" + player.getName().getString() + "}");
            result.setTag(nbt);
            itemHandler.setStackInSlot(1, result);
        }
        isCrafting = false;
        progress = 0;
        craftingPlayerUUID = null;
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }

    public int getProgress() {
        return progress;
    }

    public boolean isCrafting() {
        return isCrafting;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }
}