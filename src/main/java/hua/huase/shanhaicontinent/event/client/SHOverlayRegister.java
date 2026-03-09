// package hua.huase.shanhaicontinent.event.client;


// import hua.huase.shanhaicontinent.SHMainBus;
// import net.minecraftforge.api.distmarker.Dist;
// import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
// import net.minecraftforge.fml.common.Mod;
// import net.minecraftforge.eventbus.api.SubscribeEvent;

// @Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
// public class SHOverlayRegister {

//     @SubscribeEvent
//     public static void registerOverlay(RegisterGuiOverlaysEvent event) {

//         event.registerAboveAll(
//                 "sh_player_stats",
//                 SHHudOverlay.HUD
//         );

//     }
// }