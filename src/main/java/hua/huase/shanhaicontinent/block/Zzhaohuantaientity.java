package hua.huase.shanhaicontinent.block;

import hua.huase.shanhaicontinent.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Zzhaohuantaientity extends BlockEntity {
    private ItemStack lastUsedItem = ItemStack.EMPTY;
    private boolean hasSpawned;

    private ItemStackHandler inventory = new ItemStackHandler(1){


        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }
    };

    private long lastSummonTime = 0;
    private static final long COOLDOWN_TICKS = 48000;

    public boolean isOnCooldown() {
        return this.level != null &&
                this.level.getGameTime() - lastSummonTime < COOLDOWN_TICKS;
    }

    public long getRemainingCooldown() {
        if (this.level == null) return 0;
        return Math.max(0, COOLDOWN_TICKS - (this.level.getGameTime() - lastSummonTime));
    }

    public void setLastSummonTime(long time) {
        this.lastSummonTime = time;
        this.setChanged();
    }

    public Zzhaohuantaientity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ZHAOHUANTAI.get(), pos, state);
    }

    public void clearInventory() {
        this.hasSpawned = false;
        if (this.inventory.getSlots() > 0) {
            this.lastUsedItem = this.inventory.getStackInSlot(0).copy();
        }
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            this.inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
        setChanged();
    }
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("inventory")) {
            this.inventory.deserializeNBT(pTag.getCompound("inventory"));
        } else {
            this.inventory.deserializeNBT(pTag);
        }
        if (pTag.contains("LastSummonTime")) {
            this.lastSummonTime = pTag.getLong("LastSummonTime");
        }
    }

    public String getFormattedCooldown() {
        if (level == null) return "0:00";

        long remainingTicks = getRemainingCooldown();
        if (remainingTicks <= 0) return "0:00";

        long seconds = (remainingTicks % 1200) / 20;
        long minutes = remainingTicks / 1200;
        return String.format("%d:%02d", minutes, seconds);
    }

    private CompoundTag writeItems(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.put("inventory", this.inventory.serializeNBT());
        return compoundTag;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        this.writeItems(pTag);
        pTag.putLong("LastSummonTime", this.lastSummonTime);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.writeItems(new CompoundTag());
    }

    public boolean addItem(ItemStack itemStack) {
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (stack.isEmpty()) {
                this.inventory.setStackInSlot(i, itemStack.split(1));
                setChanged();
                return true;
            }
        }
        return false;
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    public Vec2 getItemOffest(int i) {
        float x = 0.2f;
        float y = 0.2f;
        Vec2[] offest = new Vec2[]{
                new Vec2(x, y),new Vec2(-x, y),
                new Vec2(x, -y),new Vec2(-x, -y)
        };
        return offest[i];
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    public ItemStack getLastUsedItem() {
        return this.lastUsedItem.copy();
    }

    public void markSpawned() {
        this.hasSpawned = true;
    }
    public boolean shouldSpawnThisTick() {
        return !this.hasSpawned && this.getLastUsedItem() != null;
    }
}
