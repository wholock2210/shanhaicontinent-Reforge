package hua.huase.shanhaicontinent.item.jineng.jinggubang;

import hua.huase.shanhaicontinent.item.jineng.JinengBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;

import javax.annotation.Nullable;
import java.util.List;

public class Jineng_JGB_2 extends JinengBase{
    public Jineng_JGB_2(Properties properties) {
        super(properties);
    }
    private final TargetingConditions alertableTargeting = TargetingConditions.forNonCombat().range(0.0D).ignoreLineOfSight().ignoreInvisibilityTesting();



    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(!this.isBelongToPlayer(player,itemstack))return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, (int) (400-Math.log10(this.getNianxian(player, itemstack))*40));
        if (!level.isClientSide) {

//            List<LivingEntity> nearbyEntities = level.getNearbyEntities(LivingEntity.class, this.alertableTargeting, player, player.getBoundingBox().inflate(18));
            List<LivingEntity> nearbyEntities = level.getEntities(EntityTypeTest.forClass(LivingEntity.class), player.getBoundingBox().inflate(18,6,18), Entity::isAlive);
            for (LivingEntity nearbyEntity : nearbyEntities) {
                if(nearbyEntity.getId() != player.getId()){

                    nearbyEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 2));
                    nearbyEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 2));
                    nearbyEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 2));
                    nearbyEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 2));
                }
            }


        }

//        player.awardStat(Stats.ITEM_USED.get(this));
//        if (!player.getAbilities().instabuild) {
//            itemstack.shrink(1);
//        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }


    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("对附近生物施加负面效果").withStyle(ChatFormatting.GREEN));

    }
}
