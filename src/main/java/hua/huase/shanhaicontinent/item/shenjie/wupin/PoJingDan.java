package hua.huase.shanhaicontinent.item.shenjie.wupin;

import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.item.DanYaoItem;
import hua.huase.shanhaicontinent.util.ShuXingSet;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
public class PoJingDan extends Item {
    public PoJingDan(Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(0)
                .saturationMod(0)
                .alwaysEat()
                .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 0), 1.0f)
                .build()
        ));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide() && entity instanceof Player player) {
            int playerLevel = (int) PlayerAttrubuteAPI.getDengji(player);

            if (playerLevel > 50) {
                doublePlayerAttributes(player);
                stack.shrink(1);
                player.sendSystemMessage(Component.literal("Năng lượng của thần dược đã được hấp thụ.").withStyle(ChatFormatting.GREEN));
            } else {
                player.sendSystemMessage(Component.translatable("不能吸收"));
                return stack;
            }
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT; // 使用吃的动画
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // 标准食物食用时间
    }

    private void doublePlayerAttributes(Player player) {
        float currentMaxJingshenli = PlayerAttrubuteAPI.getMaxjingshenli(player);
        ShuXingSet.setMaxjingshenli(player, (int) (currentMaxJingshenli * 1.3));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("破镜丹简介"));
        tooltip.add(Component.translatable("破镜丹简介1"));
        tooltip.add(Component.translatable("破镜丹简介2"));
    }
}