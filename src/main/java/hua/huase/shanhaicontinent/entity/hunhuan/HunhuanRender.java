package hua.huase.shanhaicontinent.entity.hunhuan;

import com.mojang.blaze3d.vertex.PoseStack;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static hua.huase.shanhaicontinent.event.client.PWRenderLivingEvent.renderHunhuan;

public class HunhuanRender extends EntityRenderer<HunhuanEntity> {
    public HunhuanRender(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(HunhuanEntity entity, float v, float v1, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        entity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            renderHunhuan(entity,v1,poseStack,capability.getNianxian());
        });
    }
    @Override
    public ResourceLocation getTextureLocation(HunhuanEntity p_114482_) {
        return null;
    }
}
