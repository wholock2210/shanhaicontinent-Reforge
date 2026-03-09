package hua.huase.shanhaicontinent.network.client;

import hua.huase.shanhaicontinent.util.jinengmianban.JinengmianbanMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class CPacketOpenSkillGUI {
    public CPacketOpenSkillGUI() {}

    public CPacketOpenSkillGUI(FriendlyByteBuf buf) {}

    public void encode(FriendlyByteBuf buf) {
        // 空实现，不需要传输数据
    }

    public static void handle(CPacketOpenSkillGUI msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                // 创建包含位置数据的FriendlyByteBuf
                FriendlyByteBuf extraData = new FriendlyByteBuf(Unpooled.buffer());
                extraData.writeBlockPos(player.blockPosition());

                NetworkHooks.openScreen(
                        player,
                        new SimpleMenuProvider(
                                (windowId, playerInventory, playerEntity) ->
                                        new JinengmianbanMenu(windowId, playerInventory, extraData),
                                Component.translatable("skill.gui.title")
                        ),
                        (buf) -> buf.writeBlockPos(player.blockPosition()) // 额外写入位置数据
                );
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public static class Handler {
        public static void onMessage(CPacketOpenSkillGUI msg, Supplier<NetworkEvent.Context> ctx) {
            handle(msg, ctx);
        }
    }
}