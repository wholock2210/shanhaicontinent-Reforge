package hua.huase.shanhaicontinent.network.client;

import hua.huase.shanhaicontinent.util.gui.StrengtheningGUIMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketStrengthen {
    public CPacketStrengthen() {
    }

    public static void encode(CPacketStrengthen msg, FriendlyByteBuf buffer) {
    }

    public static CPacketStrengthen decode(FriendlyByteBuf buffer) {
        return new CPacketStrengthen();
    }

    public static void handle(CPacketStrengthen msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null && player.containerMenu instanceof StrengtheningGUIMenu) {
                StrengtheningGUIMenu menu = (StrengtheningGUIMenu) player.containerMenu;
                menu.strengthenItem();
            }
        });
        context.setPacketHandled(true);
    }
}