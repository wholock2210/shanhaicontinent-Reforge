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

public class ColdHotEYEBook extends Item {

    public ColdHotEYEBook(Item.Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        if (!world.isClientSide && world instanceof ServerLevel serverLevel) {
            try {
                // 只检测主世界
                if (world.dimension() != Level.OVERWORLD) {
                    player.sendSystemMessage(Component.literal("§c只能在主世界使用!"));
                    return InteractionResultHolder.success(player.getItemInHand(hand));
                }

                String structureName = "shanhaicontinent:coldhoteye";

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
                    player.sendSystemMessage(Component.literal("§c未找到建筑所在地!"));
                }

                ItemStack itemStack = player.getItemInHand(hand);
                itemStack.shrink(1);
            } catch (Exception e) {
                player.sendSystemMessage(Component.literal("§c查找建筑时发生错误!"));
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.literal("§7右键使用这本书使你找寻传说中的至宝圣地 - §1冰§c火§a两仪眼"));
        list.add(Component.literal("§7一次性消耗品"));
        list.add(Component.literal("§7大师级制图师可获得"));
        list.add(Component.literal("§7只能在主世界使用"));
    }
}