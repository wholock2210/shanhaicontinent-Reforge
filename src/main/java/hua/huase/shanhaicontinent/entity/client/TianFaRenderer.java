package hua.huase.shanhaicontinent.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import hua.huase.shanhaicontinent.entity.shenjieentity.TianFaEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class TianFaRenderer extends HumanoidMobRenderer<TianFaEntity, HumanoidModel<TianFaEntity>> {

    private static final ResourceLocation TIANFA_TEXTURE = new ResourceLocation("shanhaicontinent:textures/entity/tianfa.png");

    public TianFaRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.5f);
        // 添加盔甲层，使用僵尸模型的盔甲层
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)),
                context.getModelManager()));
    }

    @Override
    protected void scale(TianFaEntity entity, PoseStack poseStack, float f) {
        // 调整实体的大小
        poseStack.scale(1.5f, 1.5f, 1.5f);
        poseStack.scale(0.9375f, 0.9375f, 0.9375f);
    }

    @Override
    protected boolean isBodyVisible(TianFaEntity entity) {
        return true; // 确保身体可见
    }


    @Override
    public ResourceLocation getTextureLocation(TianFaEntity entity) {
        return TIANFA_TEXTURE;
    }
}