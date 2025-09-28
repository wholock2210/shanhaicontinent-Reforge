package hua.huase.shanhaicontinent.event.client;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.item.shenjie.wupin.ReincarnationCapsuleItem;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ReincarnationEventHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ReincarnationCapsuleItem.checkReincarnationProgress(event.player);
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            ReincarnationCapsuleItem.cancelReincarnation(player);
        }
    }
}
