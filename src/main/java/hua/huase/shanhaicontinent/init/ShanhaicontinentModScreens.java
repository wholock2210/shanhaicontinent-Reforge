package hua.huase.shanhaicontinent.init;

import hua.huase.shanhaicontinent.util.gui.MianbanScreen;
import hua.huase.shanhaicontinent.util.gui.StrengtheningGUIScreen;
import hua.huase.shanhaicontinent.util.hungufenjie.FenjieGUIScreen;
import hua.huase.shanhaicontinent.util.jinengmianban.JinengmianbanScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ShanhaicontinentModScreens {
    @SubscribeEvent
    public static void clientLoad(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ShanhaicontinentModMenus.MIANBAN.get(), MianbanScreen::new);
            MenuScreens.register(ShanhaicontinentModMenus.JINENGMIANBAN.get(), JinengmianbanScreen::new);
            MenuScreens.register(ShanhaicontinentModMenus.FENJIE_GUI.get(), FenjieGUIScreen::new);
            MenuScreens.register(ShanhaicontinentModMenus.ARTIFACT.get(), hua.huase.shanhaicontinent.util.artifact.ArtifactScreen::new);
            MenuScreens.register(ShanhaicontinentModMenus.STRENGTHENING_GUI.get(), StrengtheningGUIScreen::new);

        });
    }
}