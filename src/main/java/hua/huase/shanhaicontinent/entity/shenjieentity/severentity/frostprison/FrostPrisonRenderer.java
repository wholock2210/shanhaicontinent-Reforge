package hua.huase.shanhaicontinent.entity.shenjieentity.severentity.frostprison;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class FrostPrisonRenderer extends HumanoidMobRenderer<FrostPrisonEntity, HumanoidModel<FrostPrisonEntity>> {
    public FrostPrisonRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<FrostPrisonEntity>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(FrostPrisonEntity entity) {
        return new ResourceLocation("shanhaicontinent:textures/entity/frostprison.png");
    }
}
