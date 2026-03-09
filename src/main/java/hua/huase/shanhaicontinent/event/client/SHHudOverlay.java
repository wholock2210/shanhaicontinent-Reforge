package hua.huase.shanhaicontinent.event.client;

import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraft.network.chat.Component;

public class SHHudOverlay {
    public static final IGuiOverlay HUD = (gui, guiGraphics, partialTick, width, height) -> {

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {

            Font font = Minecraft.getInstance().font;

            int x = width / 2 - 50;
            int y = height - 105;

            int health = (int) player.getHealth();
            int maxHealth = (int) player.getMaxHealth();

            int food = player.getFoodData().getFoodLevel();

            int jingshen = (int) PlayerAttrubuteAPI.getJingshenli(player);
            int maxJingshen = (int) PlayerAttrubuteAPI.getMaxjingshenli(player);

            int exp = (int) capability.getJingyan();
            int maxExp = (int) capability.getMaxjingyan();

            int level = capability.getDengji();

            guiGraphics.drawString(font, "Lv: " + level, x, y, 0xFFD700, true);
            y += 10;

            guiGraphics.drawString(font, "HP: " + health + " / " + maxHealth, x, y, 0xFF5555, false);
            y += 10;

            guiGraphics.drawString(font, Component.translatable("精神力", jingshen, maxJingshen), x, y, 0x55FFFF, false);
            y += 10;

            guiGraphics.drawString(font, Component.translatable("体力", food, 20), x, y, 0x55FF55, false);
            y += 10;

            guiGraphics.drawString(font, Component.translatable("经验", exp, maxExp), x, y, 0xAAAAFF, false);

        });
    };
}
