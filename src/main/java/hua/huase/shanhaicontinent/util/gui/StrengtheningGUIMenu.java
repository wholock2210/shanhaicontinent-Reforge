package hua.huase.shanhaicontinent.util.gui;


import hua.huase.shanhaicontinent.init.ShanhaicontinentModMenus;
import hua.huase.shanhaicontinent.item.shenjie.severitem.StrengtheningSucceedItem;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

import static hua.huase.shanhaicontinent.init.AdvenceInit.WeaponStrengthenTrigger;
import static hua.huase.shanhaicontinent.register.ModItems.*;

public class StrengtheningGUIMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
    public final static HashMap<String, Object> guistate = new HashMap<>();
    public final Level world;
    public final Player entity;
    public int x, y, z;
    private ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private IItemHandler internal;
    private final Map<Integer, Slot> customSlots = new HashMap<>();
    private boolean bound = false;
    private Supplier<Boolean> boundItemMatcher = null;
    private Entity boundEntity = null;
    private BlockEntity boundBlockEntity = null;


    int getStrengthenLevel(ItemStack stack) {
        if (stack.isEmpty()) return -1;
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt("UpgradeLevel");
    }

    private void setStrengthenLevel(ItemStack stack, int level) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("UpgradeLevel", level);
        stack.setTag(tag);
    }

    float getSuccessRate(int currentLevel) {
        float successRate = 100f - (currentLevel * 4.38095f);
        return Math.max(successRate, 8f);
    }

    public void strengthenItem() {
        if (this.world.isClientSide()) return;

        Slot inputSlot = this.customSlots.get(0);
        Slot protectSlot = this.customSlots.get(1); // 保护卷槽位
        Slot succeedSlot = this.customSlots.get(2); // 成功率卷轴槽位
        Slot doubleSlot = this.customSlots.get(3);  // 双倍卷轴槽位（新增）
        Slot outputSlot = this.customSlots.get(4);

        if (inputSlot.hasItem()) {
            ItemStack inputStack = inputSlot.getItem().copy();
            boolean isShenqi = shenqilist.stream().anyMatch(reg -> reg.get() == inputStack.getItem());
            if (!isShenqi) return;

            // 检查强化石是否足够
            int currentLevel = getStrengthenLevel(inputStack);
            if (!consumeStrengtheningStonesFromInventory(currentLevel)) {
                entity.sendSystemMessage(Component.literal("强化石不足！").withStyle(ChatFormatting.RED));
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                        SoundEvents.NOTE_BLOCK_HAT.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
                return;
            }

            // 检查是否有保护卷
            boolean hasProtect = protectSlot.hasItem() &&
                    protectSlot.getItem().getItem() == STRENGTHENING_PROTECT.get();

            // 检查是否有双倍卷
            boolean hasDouble = doubleSlot.hasItem() &&
                    doubleSlot.getItem().getItem() == STRENGTHENING_DOUBLE.get();

            // 计算基础成功率（含成功率卷轴加成）
            float successRate = getSuccessRate(currentLevel);
            if (succeedSlot.hasItem() && succeedSlot.getItem().getItem() == STRENGTHENING_SUCCEED.get()) {
                successRate += StrengtheningSucceedItem.getSuccessRate(succeedSlot.getItem());
            }
            successRate = Math.min(successRate, 100.0f);

            boolean success = world.random.nextFloat() * 100 < successRate;

            // 处理强化结果
            int levelChange = 0;

            if (success) {
                levelChange = 1;
                int newLevel = currentLevel + levelChange;

                // 双倍卷生效
                if (hasDouble) {
                    levelChange += 1;
                    entity.sendSystemMessage(Component.literal("双倍卷生效！额外+1等级")
                            .withStyle(ChatFormatting.LIGHT_PURPLE));
                }

                if (entity instanceof ServerPlayer serverPlayer && newLevel >= 10) {
                    String playerName = serverPlayer.getScoreboardName();
                    String itemName = inputStack.getHoverName().getString();

                    MinecraftServer server = serverPlayer.server;
                    GlobalAnnouncer.broadcast(server,
                            playerName + "的§b「" + itemName + "」突破到 +" + newLevel + "！");
                }

                // 触发成就检测
                if (entity instanceof ServerPlayer serverPlayer) {
                    WeaponStrengthenTrigger.trigger(serverPlayer, newLevel);
                }

                setStrengthenLevel(inputStack, currentLevel + levelChange);

                String msg = String.format("强化成功，等级+%d (成功率: %.1f%%)",
                        levelChange, successRate);
                entity.sendSystemMessage(Component.literal(msg).withStyle(ChatFormatting.GREEN));
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                        SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);

                // 成功时消耗保护卷（如果有）
                if (hasProtect) {
                    protectSlot.getItem().shrink(1);
                }
            } else {
                // 失败时：有保护卷则免疫降级，否则降级
                if (hasProtect) {
                    entity.sendSystemMessage(Component.literal("强化失败，但保护卷生效！等级不变")
                            .withStyle(ChatFormatting.BLUE));
                    protectSlot.getItem().shrink(1);

                } else {
                    levelChange = -1;
                    setStrengthenLevel(inputStack, Math.max(0, currentLevel + levelChange));
                    entity.sendSystemMessage(Component.literal("强化失败，等级-1 (成功率: " +
                                    String.format("%.1f", successRate) + "%)")
                            .withStyle(ChatFormatting.RED));
                }
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                        SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            // 消耗所有使用的卷轴（无论成功失败）
            if (succeedSlot.hasItem() && succeedSlot.getItem().getItem() == STRENGTHENING_SUCCEED.get()) {
                succeedSlot.getItem().shrink(1);
            }
            if (hasDouble) {
                doubleSlot.getItem().shrink(1);
            }

            outputSlot.set(inputStack);
            inputSlot.set(ItemStack.EMPTY);
            this.broadcastChanges();
        }
    }

    public StrengtheningGUIMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        super(ShanhaicontinentModMenus.STRENGTHENING_GUI.get(), id);
        this.entity = inv.player;
        this.world = inv.player.level();
        this.internal = new ItemStackHandler(5);
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
                    this.internal = capability;
                    this.bound = true;
                });
            } else if (extraData.readableBytes() > 1) { // bound to entity
                extraData.readByte(); // drop padding
                boundEntity = world.getEntity(extraData.readVarInt());
                if (boundEntity != null)
                    boundEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                        this.internal = capability;
                        this.bound = true;
                    });
            } else { // might be bound to block
                boundBlockEntity = this.world.getBlockEntity(pos);
                if (boundBlockEntity != null)
                    boundBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                        this.internal = capability;
                        this.bound = true;
                    });
            }
        }
        this.customSlots.put(0, this.addSlot(new SlotItemHandler(internal, 0, 8, 9) {
            private final int slot = 0;
            private int x = StrengtheningGUIMenu.this.x;
            private int y = StrengtheningGUIMenu.this.y;
            @Override
            public boolean mayPlace(ItemStack stack) {
                return shenqilist.stream().anyMatch(regObj -> stack.getItem() == regObj.get()); // 仅允许放入神器
            }
        }));
        this.customSlots.put(1, this.addSlot(new SlotItemHandler(internal, 1, 148, 9) {
            private final int slot = 1;
            private int x = StrengtheningGUIMenu.this.x;
            private int y = StrengtheningGUIMenu.this.y;
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() == STRENGTHENING_PROTECT.get(); // 仅允许放入保护卷
            }
        }));
        this.customSlots.put(2, this.addSlot(new SlotItemHandler(internal, 2, 148, 34) {
            private final int slot = 2;
            private int x = StrengtheningGUIMenu.this.x;
            private int y = StrengtheningGUIMenu.this.y;
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() == STRENGTHENING_SUCCEED.get();
            }
        }));
        this.customSlots.put(3, this.addSlot(new SlotItemHandler(internal, 3, 148, 58) {
            private final int slot = 3;
            private int x = StrengtheningGUIMenu.this.x;
            private int y = StrengtheningGUIMenu.this.y;
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() == STRENGTHENING_DOUBLE.get();
            }
        }));
        this.customSlots.put(4, this.addSlot(new SlotItemHandler(internal, 4, 59, 9) {
            private final int slot = 4;
            private int x = StrengtheningGUIMenu.this.x;
            private int y = StrengtheningGUIMenu.this.y;

            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        }));
        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(inv, sj + (si + 1) * 9, 0 + 8 + sj * 18, 0 + 84 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(inv, si, 0 + 8 + si * 18, 0 + 142));
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
            if (index < 5) {
                if (!this.moveItemStackTo(itemstack1, 5, this.slots.size(), true))
                    return ItemStack.EMPTY;
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (!this.moveItemStackTo(itemstack1, 0, 5, false)) {
                if (index < 5 + 27) {
                    if (!this.moveItemStackTo(itemstack1, 5 + 27, this.slots.size(), true))
                        return ItemStack.EMPTY;
                } else {
                    if (!this.moveItemStackTo(itemstack1, 5, 5 + 27, false))
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
    public void removed(Player playerIn) {
        super.removed(playerIn);
        if (!bound && playerIn instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isAlive() || serverPlayer.hasDisconnected()) {
                for (int j = 0; j < internal.getSlots(); ++j) {
                    playerIn.drop(internal.extractItem(j, internal.getStackInSlot(j).getCount(), false), false);
                }
            } else {
                for (int i = 0; i < internal.getSlots(); ++i) {
                    playerIn.getInventory().placeItemBackInInventory(internal.extractItem(i, internal.getStackInSlot(i).getCount(), false));
                }
            }
        }
    }

    // 在StrengtheningGUIMenu类中添加这个方法
    private boolean consumeStrengtheningStonesFromInventory(int targetLevel) {
        int requiredStones = calculateRequiredStones(targetLevel);
        int foundStones = 0;

        // 遍历玩家背包寻找强化石
        for (int i = 0; i < entity.getInventory().getContainerSize(); i++) {
            ItemStack stack = entity.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.getItem() == STRENGTHENING_STONE.get()) {
                foundStones += stack.getCount();
            }
        }

        // 检查数量是否足够
        if (foundStones < requiredStones) {
            return false;
        }

        // 扣除强化石
        int remaining = requiredStones;
        for (int i = 0; i < entity.getInventory().getContainerSize() && remaining > 0; i++) {
            ItemStack stack = entity.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.getItem() == STRENGTHENING_STONE.get()) {
                int remove = Math.min(stack.getCount(), remaining);
                stack.shrink(remove);
                remaining -= remove;
                if (stack.isEmpty()) {
                    entity.getInventory().setItem(i, ItemStack.EMPTY);
                }
            }
        }

        return true;
    }

    int calculateRequiredStones(int targetLevel) {
        // 基础数量
        int base = 10;
        // 最大限制
        int max = 512;

        // 指数增长公式：base * 2^(level/5)
        // 这样设计可以让：
        // - 1级 = 10个
        // - 5级 = 20个
        // - 10级 = 40个
        // - 15级 = 80个
        // - 20级 = 160个
        // - 25级 = 320个
        // - 30级 = 640个（但会被限制在512）

        // 更精确的控制版本：
        if (targetLevel <= 0) return base;
        if (targetLevel <= 20) {
            // 1-20级：从10线性增长到256
            return base + (int)((256 - base) * (targetLevel / 20.0));
        } else if (targetLevel <= 40) {
            // 21-40级：从256线性增长到512
            return 256 + (int)((512 - 256) * ((targetLevel - 20) / 20.0));
        } else {
            // 超过40级保持512
            return max;
        }
    }

    public Map<Integer, Slot> get() {
        return customSlots;
    }
}
