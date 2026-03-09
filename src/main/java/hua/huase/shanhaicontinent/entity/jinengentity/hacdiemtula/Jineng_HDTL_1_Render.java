package hua.huase.shanhaicontinent.entity.jinengentity.hacdiemtula;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import hua.huase.shanhaicontinent.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
public class Jineng_HDTL_1_Render extends EntityRenderer<Jineng_HDTL_1_Entity> {

    private static ItemStack itemStack = new ItemStack(ItemInit.jineng_hdtl_0.get());
    public Jineng_HDTL_1_Render(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(Jineng_HDTL_1_Entity entity, float v, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        renderEntity(entity,v,partialTick,poseStack,multiBufferSource,i);
        super.render(entity,v,partialTick,poseStack,multiBufferSource,i);
    }

    private void renderEntity(Jineng_HDTL_1_Entity entity, float v, float partialTick, PoseStack pPoseStack, MultiBufferSource multiBufferSource, int i) {



        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        pPoseStack.pushPose();
        pPoseStack.translate(0,3,0);
        
        // Tính toán vị trí của mỗi kiếm trong vòng tròn (5 kiếm cách nhau 72 độ)
        int swordIndex = (int)(entity.getId() % 5);
        float angleOffset = swordIndex * 72f; // 360 / 5 = 72 độ
        float radius = 1.5f; // Bán kính vòng tròn
        
        // Tính toán vị trí X, Z cố định (không quay theo thời gian)
        float radians = (float) Math.toRadians(angleOffset);
        float offsetX = (float) Math.sin(radians) * radius;
        float offsetZ = (float) Math.cos(radians) * radius;
        
        // Di chuyển kiếm đến vị trí cố định trong vòng tròn
        pPoseStack.translate(offsetX, 0, offsetZ);
        
        Quaternionf quaternionf = Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation();
        pPoseStack.mulPose(quaternionf.rotateXYZ((float) Math.PI, (float) Math.PI, (float) Math.PI));
        pPoseStack.scale(2.0f,2.0f, 2.0f);
        
        // Tilt blade tip upward by 45 degrees (positive X fixes reversed tip direction).
        pPoseStack.mulPose(Axis.XP.rotationDegrees(45));
        // Xoay kiếm tự quay tại chỗ
        pPoseStack.mulPose(Axis.YP.rotationDegrees(entity.tickCount * 20));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, i,
                OverlayTexture.NO_OVERLAY, pPoseStack, multiBufferSource, entity.level(), 1);
        pPoseStack.popPose();

    } 


    @Override
    public ResourceLocation getTextureLocation(Jineng_HDTL_1_Entity p_114482_) {
        return null;
    }
}
