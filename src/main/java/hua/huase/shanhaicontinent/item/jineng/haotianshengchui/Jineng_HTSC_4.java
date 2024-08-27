package hua.huase.shanhaicontinent.item.jineng.haotianshengchui;

import hua.huase.shanhaicontinent.entity.jinengentity.haotianchui.Jineng_HTSC_1_Entity;
import hua.huase.shanhaicontinent.entity.jinengentity.haotianchui.Jineng_HTSC_4_Entity;
import hua.huase.shanhaicontinent.entity.jinengentity.huang.JiNengSNSLEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import hua.huase.shanhaicontinent.item.jineng.JinengBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Jineng_HTSC_4 extends JinengBase{
    public Jineng_HTSC_4(Properties properties) {
        super(properties);
    }


    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(!this.isBelongToPlayer(player,itemstack))return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, (int) (200-Math.log10(this.getNianxian(player, itemstack))*16));
        if (!level.isClientSide) {

            Jineng_HTSC_4_Entity entity = new Jineng_HTSC_4_Entity(EntityInit.jinenghtsc4.get(), level);
            entity.setOwner(player);
            entity.setPos(player.getX(),player.getY()+1,player.getZ());
            entity.setItem(itemstack);
            entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3F, 0.0F);
            level.addFreshEntity(entity);

        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }


    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("发出强大的力量风暴，前方的敌人造成伤害").withStyle(ChatFormatting.GREEN));

    }
}
