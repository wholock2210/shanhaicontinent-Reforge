package hua.huase.shanhaicontinent.entity.shenjieentity.severentity.evileyetyrant;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;

public class EvileyetyrantRenderer extends HumanoidMobRenderer<EvileyetyrantEntity, HumanoidModel<EvileyetyrantEntity>> {
    public EvileyetyrantRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<EvileyetyrantEntity>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(EvileyetyrantEntity entity) {
        return new ResourceLocation("shanhaicontinent:textures/entity/shiwan.png");
    }
}
