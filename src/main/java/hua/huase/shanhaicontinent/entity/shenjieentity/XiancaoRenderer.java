package hua.huase.shanhaicontinent.entity.shenjieentity;

import com.mojang.blaze3d.vertex.PoseStack;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapability;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class XiancaoRenderer extends MobRenderer<XiancaoEntity, CustomModel<XiancaoEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("shanhaicontinent:textures/entity/texture.png");

    public XiancaoRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomModel<>(context.bakeLayer(CustomModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    protected void scale(XiancaoEntity entity, PoseStack poseStack, float partialTicks) {
        // 获取nianxian值（假设通过MonsterAttributeCapability获取）
        int nianxian = entity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY)
                .map(MonsterAttributeCapability::getNianxian)
                .orElse(0);
        // 根据年限计算缩放倍数
        float scaleMultiplier = 1.0f;
        if (nianxian > 10000000) {
            scaleMultiplier = 6.0f;
        } else if (nianxian > 1000000) {
            scaleMultiplier = 5.0f;
        } else if (nianxian > 100000) {
            scaleMultiplier = 4.5f;
        } else if (nianxian > 10000) {
            scaleMultiplier = 4.0f;
        } else if (nianxian > 1000) {
            scaleMultiplier = 3.0f;
        } else if (nianxian > 100) {
            scaleMultiplier = 2.0f;
        }
        poseStack.scale(scaleMultiplier, scaleMultiplier, scaleMultiplier);
    }

    @Override
    public ResourceLocation getTextureLocation(XiancaoEntity entity) {
        return TEXTURE;
    }
}