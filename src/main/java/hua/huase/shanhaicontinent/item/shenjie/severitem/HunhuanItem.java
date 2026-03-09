package hua.huase.shanhaicontinent.item.shenjie.severitem;

import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.entity.hunhuan.HunhuanEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HunhuanItem extends Item {
    public HunhuanItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        // 客户端直接返回
        if (level.isClientSide) {
            return InteractionResultHolder.pass(itemStack);
        }

        // 检查物品是否有有效的属性NBT
        if (!hasValidAttributes(itemStack)) {
            player.displayClientMessage(Component.literal("这个魂环不包含有效属性，无法释放").withStyle(ChatFormatting.RED), true);
            return InteractionResultHolder.fail(itemStack);
        }

        // 从物品NBT读取怪物数据
        CompoundTag monsterData = itemStack.getTag().getCompound("monster_data");

        // 生成魂环实体
        HunhuanEntity hunhuanEntity = new HunhuanEntity(EntityInit.HUNHUAN.get(), level);
        hunhuanEntity.setPos(player.getX(), player.getY() + 1, player.getZ());

        // 继承属性到实体
        hunhuanEntity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            cap.deserializeNBT(monsterData);
        });

        level.addFreshEntity(hunhuanEntity);

        // 消耗物品
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResultHolder.success(itemStack);
    }

    // 检查物品是否有有效属性
    private boolean hasValidAttributes(ItemStack stack) {
        if (stack == null || !stack.hasTag()) return false;
        return stack.getTag().contains("monster_data");
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        if (stack.hasTag() && stack.getTag().contains("monster_data")) {
            CompoundTag monsterData = stack.getTag().getCompound("monster_data");
            // 获取年限值
            if (monsterData.contains("nianxian")) {
                int nianxian = monsterData.getInt("nianxian");
                // 添加年限到物品提示
                tooltip.add(Component.literal("当前魂环年限: " + nianxian + "年")
                        .withStyle(ChatFormatting.GRAY));
            }
        }
    }
}