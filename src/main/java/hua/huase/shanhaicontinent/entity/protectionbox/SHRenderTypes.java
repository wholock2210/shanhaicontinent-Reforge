package hua.huase.shanhaicontinent.entity.protectionbox;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;


import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SHRenderTypes extends RenderType {

    private static final ResourceLocation textureLoc = new ResourceLocation(SHMainBus.MOD_ID, "textures/model/protectionbox.png");
    public SHRenderTypes(String name, VertexFormat vertexFormat, VertexFormat.Mode mode, int bufferSize, boolean crumbling, boolean sort, Runnable setup, Runnable clear) {
        super(name, vertexFormat, mode, bufferSize, crumbling, sort, setup, clear);
    }

    public static final RenderType PROTECTION_BOX = create("protection_box", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true,
            CompositeState.builder().setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
                    .setTextureState(new TextureStateShard(textureLoc, false, false))
                    .setTexturingState(new ProtectionBoxTexturingStateShard())
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL)
                    .setLightmapState(LIGHTMAP).setOverlayState(OVERLAY)
                    .createCompositeState(false));

    @OnlyIn(Dist.CLIENT)
    public static final class ProtectionBoxTexturingStateShard extends TexturingStateShard {
        public ProtectionBoxTexturingStateShard() {
            super("protection_offset_texturing", () -> {
                float tick = (float) (Minecraft.getInstance().cameraEntity != null ? Minecraft.getInstance().cameraEntity.tickCount : 0) + Minecraft.getInstance().getPartialTick();
                RenderSystem.setTextureMatrix((new Matrix4f()).translation((-tick * 0.06F) % 1.0F, (-tick * 0.035F) % 1.0F, 0.0F).scale(0.5F));
            }, RenderSystem::resetTextureMatrix);
        }
    }
}
