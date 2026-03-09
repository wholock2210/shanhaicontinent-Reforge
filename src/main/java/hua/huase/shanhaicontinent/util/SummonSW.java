package hua.huase.shanhaicontinent.util;

import hua.huase.shanhaicontinent.util.gui.MianbanScreen;
import hua.huase.shanhaicontinent.world.inventory.MianbanMenu;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SummonSW extends MianbanScreen {

    public SummonSW(MianbanMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
    }

    public static void onGenerateButtonClick(Player entity, Level world, int x, int y, int z) {
        if (!world.isClientSide() && world instanceof ServerLevel serverLevel) {

            EditBox nianxianEditBox = (EditBox) guistate.get("text:nianxian");
            int nianxian = getInputOrDefault(nianxianEditBox, 1);

            String nbtData = String.format(
                    "{Tags:[\"nianxian\"],ForgeCaps:{\"shanhaicontinent:monster_attribute\":{\"nianxian\":%d}}}", nianxian);

            double playerX = entity.getX();
            double playerY = entity.getY();
            double playerZ = entity.getZ();

            String summonCommand = String.format("summon minecraft:chicken %.2f %.2f %.2f %s", playerX, playerY, playerZ, nbtData);
            serverLevel.getServer().getCommands().performPrefixedCommand(
                    serverLevel.getServer().createCommandSourceStack(),
                    summonCommand
            );
        }
    }


    private static int getInputOrDefault(EditBox inputBox, int defaultValue) {
        String value = inputBox.getValue();
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}