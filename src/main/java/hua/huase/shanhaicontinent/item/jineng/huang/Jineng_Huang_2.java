package hua.huase.shanhaicontinent.item.jineng.huang;

import hua.huase.shanhaicontinent.entity.jinengentity.huang.JiNengKPBSEntity;
import hua.huase.shanhaicontinent.entity.jinengentity.huang.JiNengSNSLEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import hua.huase.shanhaicontinent.item.jineng.JinengBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;

import javax.annotation.Nullable;
import java.util.List;

public class Jineng_Huang_2 extends JinengBase{
    public Jineng_Huang_2(Properties properties) {
        super(properties);
    }
    private final TargetingConditions alertableTargeting = TargetingConditions.forNonCombat().range(0.0D).ignoreLineOfSight().ignoreInvisibilityTesting();



    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(!this.isBelongToPlayer(player,itemstack))return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, (int) (80-Math.log10(this.getNianxian(player,itemstack))*10));
        if (!level.isClientSide) {

            JiNengSNSLEntity entity = new JiNengSNSLEntity(EntityInit.jinengsnsl.get(), level);
            entity.setOwner(player);
            entity.setPos(player.getX(),player.getY()+1,player.getZ());
            entity.setItem(itemstack);
            entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 5F, 0.0F);
            entity.isExploade = player.isShiftKeyDown();
            level.addFreshEntity(entity);

        }
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.NEUTRAL, 1.0F, 1.0f);

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }


    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("召唤雷电轰击地面").withStyle(ChatFormatting.GREEN));
        list.add(Component.translatable("蹲下释放可破环地形").withStyle(ChatFormatting.GRAY));

    }
}
