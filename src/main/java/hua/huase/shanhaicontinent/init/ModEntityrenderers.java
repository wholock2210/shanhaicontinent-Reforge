package hua.huase.shanhaicontinent.init;

import hua.huase.shanhaicontinent.entity.client.LoveRedRenderer;
import hua.huase.shanhaicontinent.entity.client.MoWuRenderer;
import hua.huase.shanhaicontinent.entity.client.QiluotulipRenderer;
import hua.huase.shanhaicontinent.entity.client.TianFaRenderer;
import hua.huase.shanhaicontinent.entity.shenjieentity.XiancaoRenderer;
import hua.huase.shanhaicontinent.entity.shenjieentity.severentity.demonking.DemonKingRenderer;
import hua.huase.shanhaicontinent.entity.shenjieentity.severentity.evileyetyrant.EvileyetyrantEntity;
import hua.huase.shanhaicontinent.entity.shenjieentity.severentity.evileyetyrant.EvileyetyrantRenderer;
import hua.huase.shanhaicontinent.entity.shenjieentity.severentity.frostprison.FrostPrisonRenderer;
import hua.huase.shanhaicontinent.register.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEntityrenderers {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.MOWU.get(), MoWuRenderer::new);
        event.registerEntityRenderer(ModEntities.TIANFA.get(), TianFaRenderer::new);
        event.registerEntityRenderer(ModEntities.CHRYSANTHEMUM_TRUNCATUM.get(), XiancaoRenderer::new);
        event.registerEntityRenderer(ModEntities.LOVERED.get(), LoveRedRenderer::new);
        event.registerEntityRenderer(ModEntities.QILUOTULIP.get(), QiluotulipRenderer::new);
        event.registerEntityRenderer(ModEntities.EVILEYETYRANT.get(), EvileyetyrantRenderer::new);
        event.registerEntityRenderer(ModEntities.DEMON_KING.get(), DemonKingRenderer::new);
        event.registerEntityRenderer(ModEntities.FROST_PRISON.get(), FrostPrisonRenderer::new);
    }
}