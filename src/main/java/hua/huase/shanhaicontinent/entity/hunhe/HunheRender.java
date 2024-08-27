package hua.huase.shanhaicontinent.entity.hunhe;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import hua.huase.shanhaicontinent.render.SHRenderApi;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import static hua.huase.shanhaicontinent.SHMainBus.HUNHUAN;
import static hua.huase.shanhaicontinent.SHMainBus.TEXT;

public class HunheRender extends EntityRenderer<HunheEntity> {
    public HunheRender(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(HunheEntity entity, float v, float v1, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        renderHunhe(entity,v1,poseStack,entity.getValue());
    }

    private void renderHunhe(HunheEntity entity, float v1, PoseStack poseStack, float value) {


        SHRenderApi.renderStart(TEXT,poseStack);

        Matrix4f matrix4f = poseStack.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_TEX);
        float v = (entity.level().getGameTime() + v1)*0.02f%20/20;
        float size = (float) (0.2f + Math.sqrt(value)*0.05f);
//        renderAttibute(matrix4f,entity.level().getGameTime() + v1,value);
        renderAttibute(matrix4f,entity.getLivetime() + v1,value);

        renderTriangle(bufferbuilder,matrix4f,poseStack,size,size*2,size,v);
        renderTriangle(bufferbuilder,matrix4f,poseStack,-size,size*2,size, v);
        renderTriangle(bufferbuilder,matrix4f,poseStack,-size,size*2,-size, v);
        renderTriangle(bufferbuilder,matrix4f,poseStack,size,size*2,-size, v);
        renderTriangle(bufferbuilder,matrix4f,poseStack,size,-size*2,size, v);
        renderTriangle(bufferbuilder,matrix4f,poseStack,-size,-size*2,size, v);
        renderTriangle(bufferbuilder,matrix4f,poseStack,-size,-size*2,-size, v);
        renderTriangle(bufferbuilder,matrix4f,poseStack,size,-size*2,-size, v);
        BufferUploader.drawWithShader(bufferbuilder.end());
        SHRenderApi.renderEnd(poseStack);
    }

    private void renderAttibute(Matrix4f matrix4f, float v, float value) {
//        RenderSystem.setShaderColor(0.0F, 1.0F, 0.0F, 0.0F);
        matrix4f.translate( 0, 0.8f,0);
        matrix4f.translate( 0, (float) Math.sin(v*0.2f)*0.3f,0);
        matrix4f.rotate((float)Math.PI*v*0.02f, 0.0F, 1.0F, 0.0F);
        renderColor(value);

    }
    /*
        5.301898110478399
        21.207592441913597
        47.71708299430558
        84.83036976765439
        132.54745276195996
        190.8683319772223
*/
    private void renderColor(float value) {
        if(value>=170){
            RenderSystem.setShaderColor(1.0f, 0.1f, 0.1f,0.2f);
        }else if(value>=130){
            RenderSystem.setShaderColor(0.0f, 1.0f, 1.0f,0.2f);
        }else if(value>=80){
            RenderSystem.setShaderColor(1.0f, 0.0f, 1.0f,0.2f);
        }else if(value>=40){
            RenderSystem.setShaderColor(0.2f, 0.2f, 1.0f,0.2f);
        }else if(value>=20){
            RenderSystem.setShaderColor(1.0f, 1.0f, 0.0f,0.2f);
        }else if(value>=0){
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f,0.2f);
        }

    }

    private void renderTriangle(BufferBuilder bufferbuilder, Matrix4f matrix4f, PoseStack poseStack, float x, float y, float z, float v) {

        bufferbuilder.vertex(matrix4f, x, 0, 0).uv(0, v).endVertex();
        bufferbuilder.vertex(matrix4f, 0, y, 0).uv(0, v+0.05f).endVertex();
        bufferbuilder.vertex(matrix4f, 0, 0, z).uv(1, v+0.05f).endVertex();
        bufferbuilder.vertex(matrix4f, 0, 0, z).uv(1, v+0.05f).endVertex();
        bufferbuilder.vertex(matrix4f, 0, y, 0).uv(0, v+0.05f).endVertex();
        bufferbuilder.vertex(matrix4f, x, 0, 0).uv(0, v).endVertex();



    }


    private void renderSquare(BufferBuilder bufferbuilder, Matrix4f matrix4f, PoseStack poseStack, float size) {
//        matrix4f.rotate((float)Math.PI*0.01f*(entity.level().getGameTime()+partialTick), 0.0F, 1.0F, 0.0F);
//        matrix4f.scale(1,2.0f,1);
//        matrix4f.rotate((float)Math.PI*0.25f, 1.0F, 0.0F, 1.0F);
//        matrix4f.rotate((float)Math.PI*0.25f, 1.0F, 0.0F, 0.0F);







//        bufferbuilder.vertex(matrix4f, (float)0-size, size, (float)0-size).uv(0, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0-size, size, (float)0+size).uv(0, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, size, (float)0+size).uv(1, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, size, (float)0-size).uv(1, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, size, (float)0-size).uv(1, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, size, (float)0+size).uv(1, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0-size, size, (float)0+size).uv(0, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0-size, size, (float)0-size).uv(0, 0).endVertex();
//
//        bufferbuilder.vertex(matrix4f, (float)0-size, -size, (float)0-size).uv(0, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0-size, -size, (float)0+size).uv(0, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, -size, (float)0+size).uv(1, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, -size, (float)0-size).uv(1, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, -size, (float)0-size).uv(1, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, -size, (float)0+size).uv(1, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0-size, -size, (float)0+size).uv(0, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0-size, -size, (float)0-size).uv(0, 0).endVertex();
//
//
//        bufferbuilder.vertex(matrix4f, (float)0-size, (float)0-size, size).uv(0, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0-size, (float)0+size, size).uv(0, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, (float)0+size, size).uv(1, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, (float)0-size, size).uv(1, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, (float)0-size, size).uv(1, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, (float)0+size, size).uv(1, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0-size, (float)0+size, size).uv(0, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0-size, (float)0-size, size).uv(0, 0).endVertex();
//
//        bufferbuilder.vertex(matrix4f, (float)0-size, (float)0-size, -size).uv(0, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0-size, (float)0+size, -size).uv(0, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, (float)0+size, -size).uv(1, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, (float)0-size, -size).uv(1, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, (float)0-size, -size).uv(1, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0+size, (float)0+size, -size).uv(1, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0-size, (float)0+size, -size).uv(0, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, (float)0-size, (float)0-size, -size).uv(0, 0).endVertex();
//
//
//        bufferbuilder.vertex(matrix4f, size, (float)0-size, (float)0-size).uv(0, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, size, (float)0-size, (float)0+size).uv(0, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, size, (float)0+size, (float)0+size).uv(1, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, size, (float)0+size, (float)0-size).uv(1, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, size, (float)0+size, (float)0-size).uv(1, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, size, (float)0+size, (float)0+size).uv(1, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, size, (float)0-size, (float)0+size).uv(0, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, size, (float)0-size, (float)0-size).uv(0, 0).endVertex();
//
//        bufferbuilder.vertex(matrix4f, -size, (float)0-size, (float)0-size).uv(0, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, -size, (float)0-size, (float)0+size).uv(0, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, -size, (float)0+size, (float)0+size).uv(1, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, -size, (float)0+size, (float)0-size).uv(1, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, -size, (float)0+size, (float)0-size).uv(1, 0).endVertex();
//        bufferbuilder.vertex(matrix4f, -size, (float)0+size, (float)0+size).uv(1, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, -size, (float)0-size, (float)0+size).uv(0, 1).endVertex();
//        bufferbuilder.vertex(matrix4f, -size, (float)0-size, (float)0-size).uv(0, 0).endVertex();


    }

    @Override
    public ResourceLocation getTextureLocation(HunheEntity p_114482_) {
        return null;
    }
}
