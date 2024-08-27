package hua.huase.shanhaicontinent.event.client;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.client.Camera;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PWRenderLevelStageEvent {



    @SubscribeEvent
    public static void renderPlayerEventPost(RenderLevelStageEvent event){
//        Camera camera = event.getCamera();
//        Matrix4f pose = event.getPoseStack().last().pose();
//        double v = (event.getRenderTick() + event.getPartialTick()) * Math.PI / 180f;
//        double x = Math.sin((float) v) * 3f;
//        double y = Math.cos((float) v) * 3f;
//        pose.rotate((float) v,0,-1,0);
//        pose.translate((float) x, (float) 0, (float) y);
    }



}
