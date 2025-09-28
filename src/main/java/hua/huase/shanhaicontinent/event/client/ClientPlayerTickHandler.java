package hua.huase.shanhaicontinent.event.client;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;

@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, value = Dist.CLIENT)
public class ClientPlayerTickHandler {

    private static int lastHunhuankuaiguan = -2; // 初始值，和能力默认值不同

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.ClientTickEvent.Phase.END) return;

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            int current = capability.getHunhuankuaiguan();

            if (lastHunhuankuaiguan == -1 && current >= 0) {
                // 触发打开动画
                PWRenderPlayerEvent.startOpenAnimation(player);
            }

            lastHunhuankuaiguan = current;
        });
    }
}
