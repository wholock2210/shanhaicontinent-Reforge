package hua.huase.shanhaicontinent.util.jinengmianban;


import hua.huase.shanhaicontinent.init.ShanhaicontinentModMenus;
import hua.huase.shanhaicontinent.item.jineng.haotianshengchui.*;
import hua.huase.shanhaicontinent.item.jineng.huang.*;
import hua.huase.shanhaicontinent.item.jineng.jinggubang.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class JinengmianbanMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final Level world;
    public final Player entity;
    public int x, y, z;
    private ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private ItemStackHandler internal;
    private final Map<Integer, Slot> customSlots = new HashMap<>();
    private boolean bound = false;
    private Supplier<Boolean> boundItemMatcher = null;
    private Entity boundEntity = null;
    private BlockEntity boundBlockEntity = null;
    private final boolean isPlayerGui = true;


    public JinengmianbanMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(ShanhaicontinentModMenus.JINENGMIANBAN.get(), id);
        this.entity = inv.player;
        this.world = inv.player.level();

        this.internal = new ItemStackHandler(3) {
            @Override
            public int getSlotLimit(int slot) {
                return 1; // 每个槽位最大堆叠1个
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                saveToPlayerNBT(entity);
            }
        };

        loadFromPlayerNBT(entity);

        BlockPos pos = null;
        if (extraData != null) {
            pos = extraData.readBlockPos();
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
            access = ContainerLevelAccess.create(world, pos);
        }
        if (pos != null) {
            if (extraData.readableBytes() == 1) { // bound to item
                byte hand = extraData.readByte();
                ItemStack itemstack = hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem();
                this.boundItemMatcher = () -> itemstack == (hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem());
                itemstack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                    this.internal = (ItemStackHandler) capability;
                    this.bound = true;
                });
            } else if (extraData.readableBytes() > 1) { // bound to entity
                extraData.readByte(); // drop padding
                boundEntity = world.getEntity(extraData.readVarInt());
                if (boundEntity != null)
                    boundEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                        this.internal = (ItemStackHandler) capability;
                        this.bound = true;
                    });
            } else { // might be bound to block
                boundBlockEntity = this.world.getBlockEntity(pos);
                if (boundBlockEntity != null)
                    boundBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                        this.internal = (ItemStackHandler) capability;
                        this.bound = true;
                    });
            }
        }
        this.customSlots.put(0, this.addSlot(new SlotItemHandler(internal, 0, 11, 21) {
            private final int slot = 0;
            private int x = JinengmianbanMenu.this.x;
            private int y = JinengmianbanMenu.this.y;
            @Override
            public boolean mayPlace(ItemStack stack) {
                return isJinengItem(stack.getItem());
            }

            @Override
            public int getMaxStackSize() {
                return 1; // 最大堆叠数为1
            }

            @Override
            public int getMaxStackSize(ItemStack stack) {
                return 1; // 特定物品的最大堆叠数也为1
            }
        }));
        this.customSlots.put(1, this.addSlot(new SlotItemHandler(internal, 1, 71, 21) {
            private final int slot = 1;
            private int x = JinengmianbanMenu.this.x;
            private int y = JinengmianbanMenu.this.y;

            @Override
            public boolean mayPlace(ItemStack stack) {
                return isJinengItem(stack.getItem());
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        }));
        this.customSlots.put(2, this.addSlot(new SlotItemHandler(internal, 2, 132, 21) {
            private final int slot = 2;
            private int x = JinengmianbanMenu.this.x;
            private int y = JinengmianbanMenu.this.y;
            @Override
            public boolean mayPlace(ItemStack stack) {
                return isJinengItem(stack.getItem());
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        }));
        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 1 + 8 + sj * 18, 0 + 84 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(inv, si, 1 + 8 + si * 18, 0 + 142));
    }

    private boolean isJinengItem(Item item) {
        return item instanceof Jineng_JGB_0 ||
                item instanceof Jineng_JGB_1 ||
                item instanceof Jineng_JGB_2 ||
                item instanceof Jineng_JGB_3 ||
                item instanceof Jineng_JGB_4 ||
                item instanceof Jineng_JGB_5||
                item instanceof Jineng_JGB_6||
                item instanceof Jineng_JGB_7||
                item instanceof Jineng_JGB_8||
                item instanceof Jineng_HTSC_0 ||
                item instanceof Jineng_HTSC_1 ||
                item instanceof Jineng_HTSC_2 ||
                item instanceof Jineng_HTSC_3 ||
                item instanceof Jineng_HTSC_4 ||
                item instanceof Jineng_HTSC_5||
                item instanceof Jineng_HTSC_6||
                item instanceof Jineng_HTSC_7||
                item instanceof Jineng_HTSC_8||
                item instanceof Jineng_Huang_0||
                item instanceof Jineng_Huang_1 ||
                item instanceof Jineng_Huang_2 ||
                item instanceof Jineng_Huang_3 ||
                item instanceof Jineng_Huang_4 ||
                item instanceof Jineng_Huang_6||
                item instanceof Jineng_Huang_7||
                item instanceof Jineng_Huang_8;
    }

    private void saveToPlayerNBT(Player player) {
        if (player instanceof ServerPlayer) {
            CompoundTag playerData = player.getPersistentData();
            CompoundTag itemsTag = ((ItemStackHandler) internal).serializeNBT();
            playerData.put("JinengmianbanItems", itemsTag);
        }
    }

    private void loadFromPlayerNBT(Player player) {
        if (player instanceof ServerPlayer) {
            CompoundTag playerData = player.getPersistentData();
            if (playerData.contains("JinengmianbanItems")) {
                CompoundTag itemsTag = playerData.getCompound("JinengmianbanItems");
                ((ItemStackHandler) internal).deserializeNBT(itemsTag);
            }
        }
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.bound) {
            if (this.boundItemMatcher != null)
                return this.boundItemMatcher.get();
            else if (this.boundBlockEntity != null)
                return AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
            else if (this.boundEntity != null)
                return this.boundEntity.isAlive();
        }
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot) this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 3) {
                if (!this.moveItemStackTo(itemstack1, 3, this.slots.size(), true))
                    return ItemStack.EMPTY;
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (!this.moveItemStackTo(itemstack1, 0, 3, false)) {
                if (index < 3 + 27) {
                    if (!this.moveItemStackTo(itemstack1, 3 + 27, this.slots.size(), true))
                        return ItemStack.EMPTY;
                } else {
                    if (!this.moveItemStackTo(itemstack1, 3, 3 + 27, false))
                        return ItemStack.EMPTY;
                }
                return ItemStack.EMPTY;
            }
            if (itemstack1.getCount() == 0)
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();
            if (itemstack1.getCount() == itemstack.getCount())
                return ItemStack.EMPTY;
            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }

    @Override
    protected boolean moveItemStackTo(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
        boolean flag = false;
        int i = p_38905_;
        if (p_38907_) {
            i = p_38906_ - 1;
        }
        if (p_38904_.isStackable()) {
            while (!p_38904_.isEmpty()) {
                if (p_38907_) {
                    if (i < p_38905_) {
                        break;
                    }
                } else if (i >= p_38906_) {
                    break;
                }
                Slot slot = this.slots.get(i);
                ItemStack itemstack = slot.getItem();
                if (slot.mayPlace(itemstack) && !itemstack.isEmpty() && ItemStack.isSameItemSameTags(p_38904_, itemstack)) {
                    int j = itemstack.getCount() + p_38904_.getCount();
                    int maxSize = Math.min(slot.getMaxStackSize(), p_38904_.getMaxStackSize());
                    if (j <= maxSize) {
                        p_38904_.setCount(0);
                        itemstack.setCount(j);
                        slot.set(itemstack);
                        flag = true;
                    } else if (itemstack.getCount() < maxSize) {
                        p_38904_.shrink(maxSize - itemstack.getCount());
                        itemstack.setCount(maxSize);
                        slot.set(itemstack);
                        flag = true;
                    }
                }
                if (p_38907_) {
                    --i;
                } else {
                    ++i;
                }
            }
        }
        if (!p_38904_.isEmpty()) {
            if (p_38907_) {
                i = p_38906_ - 1;
            } else {
                i = p_38905_;
            }
            while (true) {
                if (p_38907_) {
                    if (i < p_38905_) {
                        break;
                    }
                } else if (i >= p_38906_) {
                    break;
                }
                Slot slot1 = this.slots.get(i);
                ItemStack itemstack1 = slot1.getItem();
                if (itemstack1.isEmpty() && slot1.mayPlace(p_38904_)) {
                    if (p_38904_.getCount() > slot1.getMaxStackSize()) {
                        slot1.setByPlayer(p_38904_.split(slot1.getMaxStackSize()));
                    } else {
                        slot1.setByPlayer(p_38904_.split(p_38904_.getCount()));
                    }
                    slot1.setChanged();
                    flag = true;
                    break;
                }
                if (p_38907_) {
                    --i;
                } else {
                    ++i;
                }
            }
        }
        return flag;
    }

    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
        if (isPlayerGui) {
            return;
        }
        saveToPlayerNBT(playerIn);
        if (!bound && playerIn instanceof ServerPlayer serverPlayer) {
            for (int i = 3; i < internal.getSlots(); ++i) {
                ItemStack stack = internal.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    if (!serverPlayer.isAlive() || serverPlayer.hasDisconnected()) {
                        playerIn.drop(stack.copy(), false);
                    } else {
                        playerIn.getInventory().placeItemBackInInventory(stack.copy());
                    }
                    internal.setStackInSlot(i, ItemStack.EMPTY);
                }
            }
        }
    }

    public Map<Integer, Slot> get() {
        return customSlots;
    }
}
