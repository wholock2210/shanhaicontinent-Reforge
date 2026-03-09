package hua.huase.shanhaicontinent.block.shenjie.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import hua.huase.shanhaicontinent.block.Zzhaohuantaientity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;


public class ZhaohuantaiEnitityrebderer implements BlockEntityRenderer<Zzhaohuantaientity> {
    // 动画参数
    private static final float ROTATION_SPEED = 0.8f; // 旋转速度
    private static final float ITEM_SCALE = 0.6f;    // 物品缩放比例
    private static final float HEIGHT_OFFSET = 3.0f / 16.0f; // 5像素的高度偏移 (0.3125方块单位)

    public ZhaohuantaiEnitityrebderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(Zzhaohuantaientity entity, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemStackHandler inventory = entity.getInventory();
        int posLong = (int) entity.getBlockPos().asLong();
        long gameTime = entity.getLevel() != null ? entity.getLevel().getGameTime() : 0;
        float time = gameTime + partialTick;

        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                poseStack.pushPose();

                // 基础位置 - 方块中心 (0.5, Y偏移, 0.5)
                poseStack.translate(0.5, 0.5 + HEIGHT_OFFSET, 0.5);

                // 旋转动画 (Y轴)
                poseStack.mulPose(Axis.YP.rotationDegrees(time * ROTATION_SPEED + i * 30));

                // 物品缩放
                poseStack.scale(ITEM_SCALE, ITEM_SCALE, ITEM_SCALE);

                // 渲染物品
                if (entity.getLevel() != null) {
                    Minecraft.getInstance().getItemRenderer().renderStatic(
                            stack,
                            ItemDisplayContext.FIXED,
                            LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos().above()),
                            combinedOverlay,
                            poseStack,
                            buffer,
                            entity.getLevel(),
                            posLong + i
                    );
                }

                poseStack.popPose();
            }
        }
    }
}