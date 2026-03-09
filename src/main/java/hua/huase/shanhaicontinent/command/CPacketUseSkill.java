package hua.huase.shanhaicontinent.command;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketUseSkill {
    private final int slotIndex;

    public CPacketUseSkill(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public CPacketUseSkill(FriendlyByteBuf buf) {
        this.slotIndex = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(slotIndex);
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public static void handle(CPacketUseSkill msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            CompoundTag persistentData = player.getPersistentData();
            ItemStackHandler handler = new ItemStackHandler(3);
            if (persistentData.contains("JinengmianbanItems")) {
                handler.deserializeNBT(persistentData.getCompound("JinengmianbanItems"));
            }
            ItemStack originalStack = handler.getStackInSlot(msg.getSlotIndex());
            if (originalStack.isEmpty()) return;
            if (player.getCooldowns().isOnCooldown(originalStack.getItem())) {
                player.sendSystemMessage(Component.literal("§c技能冷却中..."));
                return;
            }

            ItemStack skillStack = originalStack.copy();
            CompoundTag tempTag = skillStack.getOrCreateTag().copy();
            tempTag.putString("sh_playername", player.getName().toString());
            skillStack.setTag(tempTag);
            ItemStack originalOffhand = player.getOffhandItem();
            player.setItemInHand(InteractionHand.OFF_HAND, skillStack);

            try {
                int nianxian = getNianxian(player, originalStack);
                int cooldownTicks = (int)(80 - Math.log10(nianxian) * 10);
                player.getCooldowns().addCooldown(originalStack.getItem(), cooldownTicks);
                InteractionResultHolder<ItemStack> result = skillStack.getItem().use(
                        player.level(), player, InteractionHand.OFF_HAND);
                if (!result.getObject().equals(skillStack, false)) {
                    handler.setStackInSlot(msg.getSlotIndex(),
                            result.getObject().isEmpty() ? ItemStack.EMPTY : result.getObject());
                    persistentData.put("JinengmianbanItems", handler.serializeNBT());
                }
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS, 0.5F, 1.0f);
            } finally {
                player.setItemInHand(InteractionHand.OFF_HAND, originalOffhand);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private static int getNianxian(Player player, ItemStack stack) {
        return stack.getOrCreateTag().getInt("nianxian");
    }

}