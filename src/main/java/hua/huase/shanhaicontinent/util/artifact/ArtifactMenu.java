package hua.huase.shanhaicontinent.util.artifact;

import hua.huase.shanhaicontinent.block.shenjie.blockentity.ArtifactBlockEntity;
import hua.huase.shanhaicontinent.init.ShanhaicontinentModMenus;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.Map;

import static hua.huase.shanhaicontinent.register.ModItems.*;

public class ArtifactMenu extends AbstractContainerMenu {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final Level world;
    public final Player entity;
    public final BlockPos pos;
    public final ArtifactBlockEntity blockEntity;
    private final Map<Integer, Slot> customSlots = new HashMap<>();

    public ArtifactMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public ArtifactMenu(int id, Inventory inv, BlockEntity blockEntity) {
        super(ShanhaicontinentModMenus.ARTIFACT.get(), id);
        this.entity = inv.player;
        this.world = inv.player.level();
        this.blockEntity = (ArtifactBlockEntity) blockEntity;
        this.pos = blockEntity.getBlockPos();

        IItemHandler handler = ((ArtifactBlockEntity) blockEntity).getItemHandler();

        this.customSlots.put(0, this.addSlot(new SlotItemHandler(handler, 0, 49, 8) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return isTokenItem(stack);
            }

            @Override
            public boolean mayPickup(Player player) {
                return !((ArtifactBlockEntity) blockEntity).isCrafting();
            }
        }));

        this.customSlots.put(1, this.addSlot(new SlotItemHandler(handler, 1, 102, 8) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player player) {
                return true;
            }
        }));

        for (int si = 0; si < 3; ++si) {
            for (int sj = 0; sj < 9; ++sj) {
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
            }
        }

        for (int si = 0; si < 9; ++si) {
            this.addSlot(new Slot(inv, si, 8 + si * 18, 142));
        }
    }

    private boolean isTokenItem(ItemStack stack) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return itemId.equals(LINGPAI_HAISHEN.getId()) ||
                itemId.equals(LINGPAI_TIANSHISHEN.getId()) ||
                itemId.equals(LINGPAI_XIULUOSHEN.getId()) ||
                itemId.equals(LINGPAI_LUOCHASHEN.getId());
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index < 2) {
                return ItemStack.EMPTY;
            } else if (isTokenItem(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public int getProgress() {
        return blockEntity.getProgress();
    }

    public boolean isCrafting() {
        return blockEntity.isCrafting();
    }

    public Map<Integer, Slot> get() {
        return customSlots;
    }
}