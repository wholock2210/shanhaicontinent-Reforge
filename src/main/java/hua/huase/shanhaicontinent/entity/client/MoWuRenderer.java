package hua.huase.shanhaicontinent.entity.client;

import hua.huase.shanhaicontinent.entity.shenjieentity.MoWuEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.vertex.PoseStack;

public class MoWuRenderer extends HumanoidMobRenderer<MoWuEntity, HumanoidModel<MoWuEntity>> {

    private static final ResourceLocation ZOMBIE_TEXTURE = new ResourceLocation("shanhaicontinent:textures/entity/zombie.png");

    public MoWuRenderer(EntityRendererProvider.Context context) {
        // 使用僵尸模型的 ModelLayer 而不是玩家模型的 ModelLayer
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.5f);
        // 添加盔甲层，使用僵尸模型的盔甲层
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)),
                context.getModelManager()));
    }

    @Override
    protected void scale(MoWuEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(0.9375f, 0.9375f, 0.9375f);
    }

    @Override
    protected boolean isBodyVisible(MoWuEntity entity) {
        return true; // 确保身体可见
    }

    @Override
    public ResourceLocation getTextureLocation(MoWuEntity entity) {
        return ZOMBIE_TEXTURE;
    }
}