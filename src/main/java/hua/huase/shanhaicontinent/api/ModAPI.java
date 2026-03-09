package hua.huase.shanhaicontinent.api;

import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import net.minecraft.world.entity.player.Player;

public class ModAPI {
    public static int getDengji(Player forgePlayer) {
        return forgePlayer.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY)
                .orElseThrow(() -> new IllegalStateException("Capability not found"))
                .getDengji();
    }
    public static int getJingyan(Player forgePlayer) {
        return (int) forgePlayer.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY)
                .orElseThrow(() -> new IllegalStateException("Capability not found"))
                .getJingyan();
    }
    public static int getMaxjingyan(Player forgePlayer) {
        return (int) forgePlayer.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY)
                .orElseThrow(() -> new IllegalStateException("Capability not found"))
                .getMaxjingyan();
    }
    public static String getShenwei(Player forgePlayer) {
        return forgePlayer.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY)
                .orElseThrow(() -> new IllegalStateException("Capability not found"))
                .getShenwei();
    }
}
