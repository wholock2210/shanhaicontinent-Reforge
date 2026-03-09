package hua.huase.shanhaicontinent.event.client;

import com.mojang.blaze3d.vertex.*;
import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import static net.minecraftforge.client.gui.overlay.VanillaGuiOverlay.*;

@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PWRenderGuiOverlayEventEvent {

    public static final ResourceLocation jingshenlibeijing = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/balzhebu/jingshenlibeijing.png");
    public static final ResourceLocation jingshenli = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/balzhebu/jingshenli.png");
    public static final ResourceLocation health = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/balzhebu/health.png");
    public static final ResourceLocation health_kong = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/balzhebu/health_kong.png");
    public static final ResourceLocation food_kong = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/balzhebu/fill_food_kong.png");
    public static final ResourceLocation food = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/balzhebu/fill_food.png");
    public static final ResourceLocation exp_kong = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/balzhebu/fill_exp_kong.png");
    public static final ResourceLocation exp = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/balzhebu/fill_exp.png");
    public static final ResourceLocation exp_bar_kong = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/balzhebu/exp_bar_kong.png");
    public static final ResourceLocation exp_bar = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/balzhebu/exp_bar.png");

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
            int w = event.getWindow().getGuiScaledWidth();
            int h = event.getWindow().getGuiScaledHeight();

            Level world = null;
            double x = 0;
            double y = 0;
            double z = 0;
            Player entity = Minecraft.getInstance().player;
            if (entity != null) {
                world = entity.level();
                x = entity.getX();
                y = entity.getY();
                z = entity.getZ();
            }

            GuiGraphics guiGraphics = event.getGuiGraphics();



            PoseStack pose1 = event.getGuiGraphics().pose();
            Matrix4f matrix4f =event.getGuiGraphics().pose().last().pose();

//            SHRenderApi.renderStart(pose1);
//            RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f,0.0f);

            pose1.pushPose();
            matrix4f.scale(0.5f,0.5F,1f);
            int centerX = width/2 - 12; // 原图片X位置（保留作为文字位置参考）
            int centerY = height - 48 - 2; // 原图片Y位置（保留作为文字位置参考）
            PoseStack textPose = new PoseStack();
            textPose.pushPose();
            float textScale = 0.7f;
            int level = capability.getDengji();
            String levelText = String.valueOf(level);
            int textWidth = (int)(Minecraft.getInstance().font.width(levelText) * textScale);
            int textX = centerX + 10 - textWidth / 2; // 原centerX + 8调整为centerX + 10，使文字居中
            int textY = centerY + 10 - (int)(4 * textScale); // 调整Y位置使文字垂直居中
            textPose.translate(centerX + 10, centerY + 10, 0);
            textPose.scale(textScale, textScale, 1f);
            textPose.translate(-(centerX + 10), -(centerY + 10), 0);
            Minecraft.getInstance().font.drawInBatch(
                    Component.literal(levelText)
                            .withStyle(style -> style
                                    .withColor(0xFFD700)
                                    .withBold(true)
                            ),
                    textX, textY,
                    0xFFD700,
                    true,
                    textPose.last().pose(),
                    guiGraphics.bufferSource(),
                    Font.DisplayMode.NORMAL,
                    0,
                    15728880
            );
            textPose.popPose();

            guiGraphics.blit(health_kong,43, 23, 0, 0, 49, 6, 49, 6);
            guiGraphics.blit(health, 43, 23,0,0, Math.min(49,(int) (49*(player.getHealth()/player.getMaxHealth()))), 6, Math.min(49,(int) (49*(player.getHealth()/player.getMaxHealth()))), 6);
            Minecraft.getInstance().font.drawInBatch(Component.literal((int)player.getHealth() + "/" + (int)player.getMaxHealth()),90, 48, -1, false, matrix4f, guiGraphics.bufferSource(), Font.DisplayMode.SEE_THROUGH, 100, 000000);

            guiGraphics.blit(jingshenlibeijing, 44, 31, 0, 0, 91, 7, 91, 7);
            guiGraphics.blit(jingshenli, 44, 31, 0, 0, Math.min(91,(int) (91*(PlayerAttrubuteAPI.getJingshenli(player)/PlayerAttrubuteAPI.getMaxjingshenli(player)))), 7, Math.min(91,(int) (91*(capability.getJingshenli()/capability.getMaxjingshenli()))), 7);
            Minecraft.getInstance().font.drawInBatch(Component.translatable("精神力",(int) PlayerAttrubuteAPI.getJingshenli(player),(int)PlayerAttrubuteAPI.getMaxjingshenli(player)), 108, 65, 0, false, matrix4f, guiGraphics.bufferSource(), Font.DisplayMode.SEE_THROUGH, 100, 000000);

            guiGraphics.blit(food_kong, 102, 23, 0, 0, 39, 6, 39, 6);
            guiGraphics.blit(food, 102, 23, 0, 0, Math.min(39,(int) (39*(player.getFoodData().getFoodLevel()/20f))), 6, Math.min(39,(int) (39*(player.getFoodData().getFoodLevel()/20f))), 6);
            Minecraft.getInstance().font.drawInBatch(Component.translatable("体力",player.getFoodData().getFoodLevel(),20), 214, 48, 0, false, matrix4f, guiGraphics.bufferSource(), Font.DisplayMode.SEE_THROUGH, 100, 000000);

            guiGraphics.blit(exp_kong,  w / 2 + -64, h - 26, 0, 0, 130, 3, 130, 3);
            guiGraphics.blit(exp, w / 2 + -64, h - 26, 0, 0, Math.min(130,(int) (130*capability.getJingyan()/capability.getMaxjingyan())), 3, Math.min(130,(int) (130*capability.getJingyan()/capability.getMaxjingyan())), 3);

            int expBarX = w / 2 - 64;
            int expBarY = h - 26;
            int expTextX = expBarX + 50- Minecraft.getInstance().font.width("经验") / 2; // 水平居中
            int expTextY = expBarY + 0;
            guiGraphics.blit(exp_kong, expBarX, expBarY, 0, 0, 130, 3, 130, 3);
            guiGraphics.blit(exp, expBarX, expBarY, 0, 0,
                    Math.min(130, (int)(130*capability.getJingyan()/capability.getMaxjingyan())),
                    3, 130, 3);
            pose1.pushPose();
            pose1.scale(0.5f, 0.5f, 1f);
            Minecraft.getInstance().font.drawInBatch(Component.translatable("经验", (int)capability.getJingyan(), (int)capability.getMaxjingyan()), expTextX * 2, expTextY * 2, -1, false, pose1.last().pose(), guiGraphics.bufferSource(), Font.DisplayMode.SEE_THROUGH, 100, 000000);
            pose1.popPose();

            matrix4f.scale(2f,2F,1f);
//            SHRenderApi.renderEnd(pose1);
            pose1.popPose();
        });
    }

    private static void renderCustomExperienceBar(GuiGraphics guiGraphics, Player player, int width, int height) {
        PoseStack poseStack = guiGraphics.pose();
        int barX = 26;
        int barY = 47;
        int barWidth = 91;
        int barHeight = 5;
        int playerLevel = player.experienceLevel;
        float experienceProgress = player.experienceProgress;
        guiGraphics.blit(
                exp_bar_kong,
                barX, barY,
                0, 0,
                barWidth, barHeight,
                barWidth, barHeight
        );
        int filledWidth = (int)(barWidth * experienceProgress);
        guiGraphics.blit(exp_bar, barX, barY, 0, 0, filledWidth, barHeight, barWidth, barHeight);
        poseStack.pushPose();
        float scale = 0.7f;
        poseStack.scale(scale, scale, 1f);
        String levelText = String.valueOf(playerLevel);
        int textWidth = (int)(Minecraft.getInstance().font.width(levelText) * scale);
        int textX = (int)((barX + (barWidth - textWidth)/2) / scale);
        int textY = (int)((barY - 6) / scale);
        guiGraphics.drawString(Minecraft.getInstance().font, levelText, textX, textY, 0x80FF20, false);
        poseStack.popPose();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderGuiPre(RenderGuiOverlayEvent.Pre event) {

        if (!event.getOverlay().id().getPath().equals("experience_bar")) {
            return;
        }
        event.setCanceled(true);
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        renderCustomExperienceBar(event.getGuiGraphics(), player, event.getWindow().getGuiScaledWidth(), event.getWindow().getGuiScaledHeight());
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