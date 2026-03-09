package hua.huase.shanhaicontinent.util.gui;

import net.minecraft.server.MinecraftServer;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public class GlobalAnnouncer {
    public static void broadcast(MinecraftServer server, String message) {
        Component msg = Component.literal("[神器强化] ")
                .withStyle(ChatFormatting.GOLD)
                .append(Component.literal(message)
                        .withStyle(ChatFormatting.LIGHT_PURPLE));

        server.getPlayerList().broadcastSystemMessage(msg, false);
    }
}
