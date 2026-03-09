package hua.huase.shanhaicontinent.entity.jinengitem;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import hua.huase.shanhaicontinent.item.jineng.Jineng;
import hua.huase.shanhaicontinent.render.SHRenderApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

import static hua.huase.shanhaicontinent.SHMainBus.HUNHUAN;

public class JinengItemRender extends EntityRenderer<JinengItemEntity> {
    public JinengItemRender(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(JinengItemEntity entity, float v, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        render(entity,partialTick,poseStack,multiBufferSource,i);
    }

    private void render(JinengItemEntity entity, float partialTick, PoseStack pPoseStack, MultiBufferSource multiBufferSource, int i) {




        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = entity.getItem();

        Jineng item = (Jineng) itemStack.getItem();
        int nianxian = item.getNianxian(null, itemStack);

        renderHunhuan(entity,partialTick,pPoseStack,nianxian);

        pPoseStack.pushPose();
        pPoseStack.translate(0.0f, 0.3f, 0.0f);
        pPoseStack.last().pose().rotate((float)Math.PI*0.5f, 1.0F, 0.0F, 0.0F);
        pPoseStack.last().pose().rotate((float)Math.PI*0.01f*(entity.level().getGameTime()+partialTick), 0.0F, 0.0F, 1.0F);
//        pPoseStack.last().pose().rotate((float)Math.PI*0.5f, 1.0F, 0.0F, 1.0F);
        pPoseStack.scale(0.5f, 0.5f, 0.5f);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, i,
                OverlayTexture.NO_OVERLAY, pPoseStack, multiBufferSource, entity.level(), 1);
        pPoseStack.popPose();



    }


    public static void renderHunhuan(Entity entity, float partialTick, PoseStack poseStack, int nianxian){

        SHRenderApi.renderStart(HUNHUAN,poseStack);
        Matrix4f matrix4f = poseStack.last().pose();
        matrix4f.rotate((float)Math.PI*0.01f*(entity.level().getGameTime()+partialTick), 0.0F, 1.0F, 0.0F);
//            matrix4f.rotate((float)Math.PI*0.5f, 1.0F, 0.0F, 0.0F);
//            渲染属性
        renderHunhuanColor(matrix4f,nianxian,(entity.level().getGameTime()+partialTick));
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, (float)0-0.6f, 0.1f, (float)0-0.6f).uv(0, 0).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0-0.6f, 0.1f, (float)0+0.6f).uv(0, 1).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0+0.6f, 0.1f, (float)0+0.6f).uv(1, 1).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0+0.6f, 0.1f, (float)0-0.6f).uv(1, 0).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0+0.6f, 0.1f, (float)0-0.6f).uv(1, 0).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0+0.6f, 0.1f, (float)0+0.6f).uv(1, 1).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0-0.6f, 0.1f, (float)0+0.6f).uv(0, 1).endVertex();
        bufferbuilder.vertex(matrix4f, (float)0-0.6f, 0.1f, (float)0-0.6f).uv(0, 0).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        SHRenderApi.renderEnd(poseStack);
    }


    private static void renderHunhuanColor(Matrix4f matrix4f, int nianxian, float partialTick) {
        if (nianxian>=10000000) {
            RenderSystem.setShaderColor(0.0f, 0.0f, 1.0f, 1.0f);
        }else if(nianxian>=1000000){
            RenderSystem.setShaderColor(1.0f, 0.6f, 0.1f,0.8f);
        }else if(nianxian>=100000){
            RenderSystem.setShaderColor(1.0f, 0, 0,0.6f);
        }else if(nianxian>=10000){
            RenderSystem.setShaderColor(0, 0f, 0,0.6f);
        }else if(nianxian>=1000){
            RenderSystem.setShaderColor(1.0f, 0f, 1.0f,0.4f);
        }else if(nianxian>=100){
            RenderSystem.setShaderColor(1.0f, 1.0f, 0,0.4f);
        }else if(nianxian>=1){
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f,0.8f);
        }
    }


    @Override
    public ResourceLocation getTextureLocation(JinengItemEntity p_114482_) {
        return null;
    }
}
