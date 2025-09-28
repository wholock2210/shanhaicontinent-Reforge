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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class godeye extends Item {

    public godeye(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        if (!world.isClientSide && world instanceof ServerLevel serverLevel) {
            try {
                String structureName;

                if (world.dimension() == Level.OVERWORLD) {
                    structureName = "shanhaicontinent:dengxiantai";
                } else if (world.dimension().location().equals(new ResourceLocation("shanhaicontinent:shenjieworld"))) {
                    structureName = "shanhaicontinent:gumingjitan";
                } else {
                    return InteractionResultHolder.success(player.getItemInHand(hand));
                }
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
                    player.sendSystemMessage(Component.literal("§e建筑所在坐标:" + "x轴: §a" + x + " §ez轴: §a" + z));
                } else {
                    player.sendSystemMessage(Component.literal("§c未找到建筑所在地(可能不是主世界或神界维度!)."));
                }
                ItemStack itemStack = player.getItemInHand(hand);
                itemStack.shrink(1);
            } catch (Exception e) {
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.literal("§7右键神之眼，它将指引你封神的路"));
        list.add(Component.literal("§7注意！神之眼是一次性消耗品"));
        list.add(Component.literal("§7使用后反馈的坐标根据你所在维度所定"));
        list.add(Component.literal("§7在神界指向的就是古冥祭坛，在主世界指向的就是登神台"));
    }
}