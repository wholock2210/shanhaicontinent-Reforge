package hua.huase.shanhaicontinent.block.entityblock.pot;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.shaders.ShadersInt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.joml.Matrix4f;

public class PotBlockEntityRenderer implements BlockEntityRenderer<PotBlockEntity> {
    public PotBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(PotBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = pBlockEntity.getRenderStack();

        pPoseStack.pushPose();
        pPoseStack.translate(0.5f, 1.10f, 0.5f);
        pPoseStack.scale(0.35f, 0.35f, 0.35f);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);
        pPoseStack.popPose();
        rendertext(pBlockEntity,pPartialTick,pPoseStack,pBuffer,pPackedLight,pPackedOverlay);
    }

    private static final ResourceLocation HUNHUAN = new ResourceLocation(SHMainBus.MOD_ID, "textures/picture/particletext.png");
    private void rendertext(PotBlockEntity pBlockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
//        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        RenderSystem.setShaderTexture(0, HUNHUAN);
        RenderSystem.depthMask(Minecraft.useShaderTransparency());
        poseStack.pushPose();

//        RenderSystem.setShaderColor(f, f1, f2, (float)d1);
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShader(ShadersInt::getShmapshaderUnlitShader);
        RenderSystem.polygonOffset(-3.0F, -3.0F);
        RenderSystem.enablePolygonOffset();
        RenderSystem.disableCull();

        Matrix4f matrix4f = poseStack.last().pose();


        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, -6f, 0.1f, -6f).uv(0, 0).endVertex();
        bufferbuilder.vertex(matrix4f, -6f, 0.1f, 6f).uv(0, 1).endVertex();
        bufferbuilder.vertex(matrix4f, 6f, 0.1f, 6f).uv(1, 1).endVertex();
        bufferbuilder.vertex(matrix4f, 6f, 0.1f, -6f).uv(1, 0).endVertex();
        bufferbuilder.vertex(matrix4f, 6f, 0.1f, -6f).uv(1, 0).endVertex();
        bufferbuilder.vertex(matrix4f, 6f, 0.1f, 6f).uv(1, 1).endVertex();
        bufferbuilder.vertex(matrix4f, -6f, 0.1f, 6f).uv(0, 1).endVertex();
        bufferbuilder.vertex(matrix4f, -6f, 0.1f, -6f).uv(0, 0).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());


        RenderSystem.enableCull();
        RenderSystem.polygonOffset(0.0F, 0.0F);
        RenderSystem.disablePolygonOffset();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        poseStack.popPose();
        RenderSystem.disableDepthTest();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.depthMask(true);
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
