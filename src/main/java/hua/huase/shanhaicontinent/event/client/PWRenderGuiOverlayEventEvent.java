package hua.huase.shanhaicontinent.event.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapability;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.render.SHRenderApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import static hua.huase.shanhaicontinent.SHMainBus.HUNHUAN;
import static net.minecraftforge.client.gui.overlay.VanillaGuiOverlay.*;

@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PWRenderGuiOverlayEventEvent {


    public static final ResourceLocation overlaydown = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/shuchai/overlaydown.png");
    public static final ResourceLocation healthoverlayup = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/shuchai/healthoverlayup.png");
    public static final ResourceLocation jingshenlioverlayup = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/shuchai/jingshenlioverlayup.png");
    public static final ResourceLocation jinyanoverlayup = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/shuchai/jinyanoverlayup.png");
    public static final ResourceLocation foodoverlayup = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/shuchai/foodoverlayup.png");

    @SubscribeEvent(priority= EventPriority.LOWEST)
    public static void onRenderGuiOverlayEvent(RenderGuiOverlayEvent.Pre event){
        NamedGuiOverlay overlay = event.getOverlay();
        if(overlay.id().getPath() == PLAYER_HEALTH.id().getPath()){
            event.setCanceled(true);
            renderPlayerHealth(event);
        }
        if(overlay.id().getPath() == FOOD_LEVEL.id().getPath()){
            event.setCanceled(true);
        }
        if(overlay.id().getPath() == ARMOR_LEVEL.id().getPath()){
//            event.setCanceled(true);
            PoseStack poseStack = event.getGuiGraphics().pose();
            poseStack.translate(0,-20.0f,0.0f);

        }
        if(overlay.id().getPath() == AIR_LEVEL.id().getPath()){
//            event.setCanceled(true);
            PoseStack poseStack = event.getGuiGraphics().pose();
            poseStack.translate(0,-20.0f,0.0f);

        }


    }

    private static void renderPlayerHealth(RenderGuiOverlayEvent.Pre event) {

        LocalPlayer player = Minecraft.getInstance().player;
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {



            int width = event.getWindow().getGuiScaledWidth();
            int height = event.getWindow().getGuiScaledHeight();
            GuiGraphics guiGraphics = event.getGuiGraphics();



            PoseStack pose1 = event.getGuiGraphics().pose();
            Matrix4f matrix4f =event.getGuiGraphics().pose().last().pose();

//            SHRenderApi.renderStart(pose1);
//            RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f,0.0f);

            pose1.pushPose();
            matrix4f.scale(0.5f,0.5F,1f);


            guiGraphics.blit(overlaydown, width/2-91, height-48, 0, 0, 85, 8, 85, 8);
            guiGraphics.blit(healthoverlayup, width/2-90, height-47, (float) 0, (float) 0, Math.min(83,(int) (83*(player.getHealth()/player.getMaxHealth()))), 6, Math.min(83,(int) (83*(player.getHealth()/player.getMaxHealth()))), 6);
            Minecraft.getInstance().font.drawInBatch(Component.translatable("生命",(int)player.getHealth(),(int)player.getMaxHealth()), width-90*2+2, height*2-47*2+2, 0, false, matrix4f, guiGraphics.bufferSource(), Font.DisplayMode.SEE_THROUGH, 100, 000000);



            guiGraphics.blit(overlaydown, width/2-91, height-38, 0, 0, 85, 8, 85, 8);
            guiGraphics.blit(jingshenlioverlayup, width/2-90, height-37, 0, 0, Math.min(83,(int) (83*(PlayerAttrubuteAPI.getJingshenli(player)/PlayerAttrubuteAPI.getMaxjingshenli(player)))), 6, Math.min(83,(int) (83*(capability.getJingshenli()/capability.getMaxjingshenli()))), 6);
            Minecraft.getInstance().font.drawInBatch(Component.translatable("精神力",(int) PlayerAttrubuteAPI.getJingshenli(player),(int)PlayerAttrubuteAPI.getMaxjingshenli(player)), width-90*2+2, height*2-37*2+2, 0, false, matrix4f, guiGraphics.bufferSource(), Font.DisplayMode.SEE_THROUGH, 100, 000000);



            guiGraphics.blit(overlaydown, width/2+7, height-48, 0, 0, 85, 8, 85, 8);
            guiGraphics.blit(foodoverlayup, width/2+8, height-47, 0, 0, Math.min(83,(int) (83*(player.getFoodData().getFoodLevel()/20f))), 6, Math.min(83,(int) (83*(player.getFoodData().getFoodLevel()/20f))), 6);
            Minecraft.getInstance().font.drawInBatch(Component.translatable("体力",player.getFoodData().getFoodLevel(),20), width+8*2+2, height*2-47*2+2, 0, false, matrix4f, guiGraphics.bufferSource(), Font.DisplayMode.SEE_THROUGH, 100, 000000);


            guiGraphics.blit(overlaydown, width/2+7, height-38, 0, 0, 85, 8, 85, 8);
            guiGraphics.blit(jinyanoverlayup, width/2+8, height-37, 0, 0, Math.min(83,(int) (83*capability.getJingyan()/capability.getMaxjingyan())), 6, Math.min(83,(int) (83*capability.getJingyan()/capability.getMaxjingyan())), 6);
            Minecraft.getInstance().font.drawInBatch(Component.translatable("经验",(int)capability.getJingyan(),(int)capability.getMaxjingyan()), width+8*2+2, height*2-37*2+2, 0, false, matrix4f, guiGraphics.bufferSource(), Font.DisplayMode.SEE_THROUGH, 100, 000000);

            matrix4f.scale(2f,2F,1f);
//            SHRenderApi.renderEnd(pose1);
            pose1.popPose();
        });
    }


    @SubscribeEvent(priority= EventPriority.HIGHEST)
    public static void onRenderGuiOverlayEvent(RenderGuiOverlayEvent.Post event){

        NamedGuiOverlay overlay = event.getOverlay();
        if(overlay.id().getPath() == ARMOR_LEVEL.id().getPath()){
//            event.setCanceled(true);
            PoseStack poseStack = event.getGuiGraphics().pose();
            poseStack.translate(0,20.0f,0.0f);

        }
        if(overlay.id().getPath() == AIR_LEVEL.id().getPath()){
//            event.setCanceled(true);
            PoseStack poseStack = event.getGuiGraphics().pose();
            poseStack.translate(0,20.0f,0.0f);

        }

    }


}
