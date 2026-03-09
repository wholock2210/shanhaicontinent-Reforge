package hua.huase.shanhaicontinent.filuid.types;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.Camera;
import java.util.function.Consumer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.shaders.FogShape;
import org.joml.Vector4f;

public class ColdiceFluidType extends FluidType {
    public ColdiceFluidType() {
        super(FluidType.Properties.create().fallDistanceModifier(0F).canExtinguish(true).supportsBoating(true).canHydrate(true).canConvertToSource(true).motionScale(0.007D).lightLevel(15).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_TEXTURE = new ResourceLocation("shanhaicontinent:block/coldice_overlay");
            private static final ResourceLocation FLOWING_TEXTURE = new ResourceLocation("shanhaicontinent:block/coldice_flow");

            // 冰蓝色调色参数 (R:0.4, G:0.6, B:1.0, A:0.8)
            private static final Vector4f ICE_TINT = new Vector4f(0.4f, 0.6f, 1.0f, 0.8f);

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_TEXTURE;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level,
                                                    int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return new Vector3f(0.4f, 0.6f, 1.0f); // 水下雾效颜色
            }

            @Override
            public int getTintColor() {
                // 将灰白贴图染成冰蓝色 (RGBA)
                return (int)(ICE_TINT.x() * 255) << 16 |
                        (int)(ICE_TINT.y() * 255) << 8 |
                        (int)(ICE_TINT.z() * 255) |
                        (int)(ICE_TINT.w() * 255) << 24;
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance,
                                        float partialTick, float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogShape(FogShape.SPHERE);
                RenderSystem.setShaderFogStart(0.25f);
                RenderSystem.setShaderFogEnd(Math.min(32f, renderDistance));
            }
        });
    }
}
