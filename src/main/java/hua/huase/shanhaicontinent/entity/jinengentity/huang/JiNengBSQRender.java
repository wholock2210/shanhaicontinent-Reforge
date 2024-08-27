package hua.huase.shanhaicontinent.entity.jinengentity.huang;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.render.SHRenderApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class JiNengBSQRender extends EntityRenderer<JiNengBSQEntity> {

    public static final ResourceLocation JIAN = new ResourceLocation(SHMainBus.MOD_ID, "textures/jineng/jian.png");
    public JiNengBSQRender(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(JiNengBSQEntity entity, float v, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        renderEntity(entity,v,partialTick,poseStack,multiBufferSource,i);
        super.render(entity,v,partialTick,poseStack,multiBufferSource,i);
    }

    private void renderEntity(JiNengBSQEntity entity, float v, float partialTick, PoseStack pPoseStack, MultiBufferSource multiBufferSource, int i) {



        SHRenderApi.renderStart(JIAN,pPoseStack);

        RenderSystem.setShaderColor(1.0F, 0.0F, 0.0F, 0.5F);
        Matrix4f matrix4f = pPoseStack.last().pose();
        LocalPlayer player = Minecraft.getInstance().player;
        matrix4f.translate(0,10,0);
        pPoseStack.scale(3,3,3);
        Quaternionf quaternionf = Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation();
        pPoseStack.mulPose(quaternionf.rotateXYZ((float) Math.PI, (float) Math.PI, (float) Math.PI));
        matrix4f.rotate((float) Math.PI,1,0,0);
//        poseStack.mulPose(new Quaternionf(-quaternionf.x(),-quaternionf.y(),-quaternionf.z(),-quaternionf.w()));


        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, (float)0-6, (float)0-6, 0.1f).uv(0, 0).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0-6, (float)0+6, 0.1f).uv(0, 1).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0+6, (float)0+6, 0.1f).uv(1, 1).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0+6, (float)0-6, 0.1f).uv(1, 0).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0+6, (float)0-6, 0.1f).uv(1, 0).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0+6, (float)0+6, 0.1f).uv(1, 1).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0-6, (float)0+6, 0.1f).uv(0, 1).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0-6, (float)0-6, 0.1f).uv(0, 0).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        SHRenderApi.renderEnd(pPoseStack);

    }


    @Override
    public ResourceLocation getTextureLocation(JiNengBSQEntity p_114482_) {
        return null;
    }
}
