package hua.huase.shanhaicontinent.item.jineng.huang;

import hua.huase.shanhaicontinent.entity.jinengentity.huang.JiNengKPBSEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import hua.huase.shanhaicontinent.item.jineng.JinengBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Jineng_Huang_1 extends JinengBase{
    public Jineng_Huang_1(Properties properties) {
        super(properties);
    }


    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(!this.isBelongToPlayer(player,itemstack))return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        int nianxian = this.getNianxian(player, itemstack);
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, (int) (80-Math.log10(nianxian)*10));
        if (!level.isClientSide) {

                JiNengKPBSEntity entity = new JiNengKPBSEntity(EntityInit.jinengkpbs.get(), level);
                entity.setOwner(player);
                entity.setPos(player.getX(),player.getY()+1,player.getZ());
                entity.setItem(itemstack);
                entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 10F, 0.0F);
                level.addFreshEntity(entity);
        }

        double f = -Math.sin(player.getYRot() * 0.017453292F) * Math.cos(player.getXRot() * 0.017453292F);
        double f1 = -Math.sin((player.getXRot()) * 0.017453292F);
        double f2 = Math.cos(player.getYRot() * 0.017453292F) * Math.cos(player.getXRot() * 0.017453292F);
        if(player.isShiftKeyDown()){
            player.setPos(player.xo+10.0*f, player.yo+10.0*f1, player.zo+10.0*f2);
        }
        player.lerpMotion(f*3,f1*3,f2*3);

        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.NEUTRAL, 0.5F, 1.0f);

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }


    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("向前位移，对路过的敌人造成伤害").withStyle(ChatFormatting.GREEN));
    }
}
