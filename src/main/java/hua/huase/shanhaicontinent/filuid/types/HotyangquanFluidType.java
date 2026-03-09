package hua.huase.shanhaicontinent.filuid.types;

import org.joml.Vector3f;

import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.Camera;

import java.util.function.Consumer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.shaders.FogShape;
import org.joml.Vector4f;

public class HotyangquanFluidType extends FluidType {
    public HotyangquanFluidType() {
        super(FluidType.Properties.create().canSwim(false).canDrown(false).pathType(BlockPathTypes.LAVA).adjacentPathType(null).motionScale(0.007D).canConvertToSource(true).lightLevel(15).rarity(Rarity.EPIC).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));
    }

    // 深红色调参数 (R:0.7, G:0.0, B:0.1, A:0.9)
    private static final Vector4f DEEP_RED_TINT = new Vector4f(0.7f, 0.0f, 0.1f, 0.9f);

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_TEXTURE = new ResourceLocation("shanhaicontinent:block/hotyangquan_overlay");
            private static final ResourceLocation FLOWING_TEXTURE = new ResourceLocation("shanhaicontinent:block/hotyangquan_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_TEXTURE;
            }

            @Override
            public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                float pulse = 0.1f * (float)Math.sin(level.getGameTime() * 0.05f) + 0.8f;
                return new Vector3f(
                        pulse,       // R 动态波动
                        0.05f,       // G 微量绿色（避免过于单调）
                        0.05f        // B 微量蓝色（增加层次感）
                );
            }

            @Override
            public int getTintColor() {
                return ((int)(DEEP_RED_TINT.w() * 255) << 24) |  // Alpha通道
                        ((int)(DEEP_RED_TINT.x() * 255) << 16) |  // Red通道
                        ((int)(DEEP_RED_TINT.y() * 255) << 8)  |   // Green通道
                        (int)(DEEP_RED_TINT.z() * 255);           // Blue通道
            }

            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                Entity entity = camera.getEntity();
                Level world = entity.level();
                RenderSystem.setShaderFogShape(FogShape.SPHERE);
                RenderSystem.setShaderFogStart(1f);
                RenderSystem.setShaderFogEnd(Math.min(48f, renderDistance));
            }
        });
    }
}
