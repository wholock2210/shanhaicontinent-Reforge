package hua.huase.shanhaicontinent.util.hungufenjie;

import hua.huase.shanhaicontinent.capability.itemattribute.ItemAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.item.BoneItem;
import hua.huase.shanhaicontinent.item.shenjie.wupin.Hungufen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public class EnhanceSoulBonePacket {
    private final int containerId;
    private final int slot1;
    private final int slot2;

    public EnhanceSoulBonePacket(int containerId, int slot1, int slot2) {
        this.containerId = containerId;
        this.slot1 = slot1;
        this.slot2 = slot2;
    }

    public static void encode(EnhanceSoulBonePacket msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.containerId);
        buffer.writeInt(msg.slot1);
        buffer.writeInt(msg.slot2);
    }

    public static EnhanceSoulBonePacket decode(FriendlyByteBuf buffer) {
        return new EnhanceSoulBonePacket(buffer.readInt(), buffer.readInt(), buffer.readInt());
    }

    public static void handle(EnhanceSoulBonePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            try {
                // 1. 获取原始物品（不复制，直接获取引用）
                ItemStack originalHunGu = player.containerMenu.getSlot(msg.slot1).getItem();
                ItemStack jingHua = player.containerMenu.getSlot(msg.slot2).getItem();

                // 2. 验证物品类型
                if (!(originalHunGu.getItem() instanceof BoneItem) || !(jingHua.getItem() instanceof Hungufen)) {
                    player.sendSystemMessage(Component.translatable("message.shanhaicontinent.hun_gu_qiang_hua_fail")
                            .withStyle(ChatFormatting.RED));
                    return;
                }

                // 3. 检查强化次数
                CompoundTag originalTag = originalHunGu.getOrCreateTag();
                int currentCount = originalTag.getInt(BoneItem.ENHANCE_COUNT_KEY);
                if (currentCount >= BoneItem.MAX_ENHANCE_COUNT) {
                    player.sendSystemMessage(Component.translatable("message.shanhaicontinent.hun_gu_max_enhanced")
                            .withStyle(ChatFormatting.RED));
                    return;
                }

                // 4. 创建新的魂骨实例（关键修复点）
                ItemStack enhancedHunGu = new ItemStack(originalHunGu.getItem(), 1);

                // 5. 复制并修改NBT
                CompoundTag enhancedTag = originalHunGu.getTag() == null ? new CompoundTag() : originalHunGu.getTag().copy();
                enhancedTag.putInt(BoneItem.ENHANCE_COUNT_KEY, currentCount + 1);

                // 6. 合并属性
                CompoundTag attrs = originalHunGu.getOrCreateTagElement("shanhaiitematuble").copy();
                CompoundTag jingHuaAttrs = jingHua.getOrCreateTagElement("shanhaiitematuble");

                for (String key : jingHuaAttrs.getAllKeys()) {
                    if (jingHuaAttrs.contains(key, Tag.TAG_FLOAT)) {
                        float current = attrs.contains(key) ? attrs.getFloat(key) : 0;
                        attrs.putFloat(key, current + jingHuaAttrs.getFloat(key));
                    } else if (jingHuaAttrs.contains(key, Tag.TAG_INT)) {
                        float current = attrs.contains(key) ? attrs.getFloat(key) : 0;
                        attrs.putFloat(key, current + jingHuaAttrs.getInt(key));
                    }
                }

                enhancedTag.put("shanhaiitematuble", attrs);
                enhancedHunGu.setTag(enhancedTag);

                // 7. 重新初始化能力提供器（关键修复点）
                ICapabilityProvider provider = ((BoneItem)enhancedHunGu.getItem()).initCapabilities(enhancedHunGu, enhancedTag);
                enhancedHunGu.getCapability(ItemAttributeCapabilityProvider.CAPABILITY)
                        .ifPresent(cap -> cap.deserializeNBT(attrs));

                // 8. 更新物品槽位
                player.containerMenu.getSlot(msg.slot1).set(ItemStack.EMPTY);
                player.containerMenu.getSlot(msg.slot2).set(ItemStack.EMPTY);

                int outputSlot = 3;
                player.containerMenu.getSlot(outputSlot).set(enhancedHunGu);

                // 9. 同步到客户端（三重保障）
                player.containerMenu.broadcastChanges();
                player.connection.send(new ClientboundContainerSetSlotPacket(
                        player.containerMenu.containerId,
                        player.containerMenu.incrementStateId(),
                        outputSlot,
                        enhancedHunGu
                ));

                // 10. 强制刷新描述（关键修复点）
                Minecraft.getInstance().execute(() -> {
                    if (player.containerMenu != null) {
                        player.containerMenu.slotsChanged(player.getInventory());
                    }
                });

                player.sendSystemMessage(Component.translatable("message.shanhaicontinent.hun_gu_qiang_hua_success"));

            } catch (Exception e) {
                player.sendSystemMessage(Component.translatable("message.shanhaicontinent.hun_gu_qiang_hua_fail")
                        .withStyle(ChatFormatting.RED));
                e.printStackTrace();
            }
        });
        ctx.get().setPacketHandled(true);
    }

}