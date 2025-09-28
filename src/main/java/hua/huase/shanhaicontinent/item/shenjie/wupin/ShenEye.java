package hua.huase.shanhaicontinent.item.shenjie.wupin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.NotNull;

public class ShenEye extends Item {
    public ShenEye(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        if (!world.isClientSide && world instanceof ServerLevel serverLevel) {
            try {
                // 检查维度是否为 shenjieworld
                if (!world.dimension().location().equals(new ResourceLocation("shanhaicontinent:shenjieworld"))) {
                    player.sendSystemMessage(Component.literal("§c只能在神界维度使用此物品！"));
                    return InteractionResultHolder.success(player.getItemInHand(hand));
                }
                String structureName = "shanhaicontinent:shenqiduanzao";
                Registry<Structure> structureRegistry = serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
                ResourceKey<Structure> structureKey = ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(structureName));
                Holder<Structure> structureHolder = structureRegistry.getHolderOrThrow(structureKey);
                HolderSet<Structure> structureSet = HolderSet.direct(structureHolder);
                BlockPos playerPos = player.blockPosition();
                BlockPos structurePos = serverLevel.getChunkSource().getGenerator()
                        .findNearestMapStructure(serverLevel, structureSet, playerPos, 100, false).getFirst();
                if (structurePos != null) {
                    int x = structurePos.getX();
                    int z = structurePos.getZ();
                    player.sendSystemMessage(Component.literal("§e神铸工坊所在坐标: §aX: " + x + " §eZ: §a" + z));
                } else {
                    player.sendSystemMessage(Component.literal("§c未找到神铸工坊！"));
                }
                ItemStack itemStack = player.getItemInHand(hand);
                itemStack.shrink(1);
            } catch (Exception e) {
                player.sendSystemMessage(Component.literal("§c发生错误，请稍后再试！"));
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
