package hua.huase.shanhaicontinent.item.jineng.jinggubang;

import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.entity.jinengentity.jinggubang.JiNengFSHYEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import hua.huase.shanhaicontinent.item.jineng.Jineng;
import hua.huase.shanhaicontinent.item.jineng.JinengBase;
import hua.huase.shanhaicontinent.item.jineng.WuqiAttribute;
import hua.huase.shanhaicontinent.item.jineng.WuqiBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Jineng_JGB_1 extends JinengBase{
    public Jineng_JGB_1(Properties properties) {
        super(properties);
    }


    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(!this.isBelongToPlayer(player,itemstack))return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        int nianxian = this.getNianxian(player, itemstack);
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, (int) (100-Math.log10(nianxian)*10));
//        player.getCooldowns().addCooldown(this, 10);
        if (!level.isClientSide) {
            for (int i = 0; i < 3; i++) {

                JiNengFSHYEntity entity = new JiNengFSHYEntity(EntityInit.jinengfshy.get(), level);
                entity.setOwner(player);
                entity.setPos(player.getX()+(float) (-Math.sin(((i-1)*45+player.getYRot()) * 0.017453292F)*2f),player.getY()+1.8,player.getZ()+(float) (Math.cos(((i-1)*45+player.getYRot()) * 0.017453292F)*2f));
                entity.setItem(itemstack);
                entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 4F, 0.0F);
                entity.isExploade = player.isShiftKeyDown();
                level.addFreshEntity(entity);
            }
//            JiNengFSHYEntity entity = new JiNengFSHYEntity(EntityInit.jinengfshy.get(), level);
//            entity.setOwner(player);
//            entity.setPos(player.getX(),player.getY()+1,player.getZ());
//            entity.setItem(itemstack);
//            entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 4F, 0.0F);
//            level.addFreshEntity(entity);
        }

//        player.awardStat(Stats.ITEM_USED.get(this));
//        if (!player.getAbilities().instabuild) {
//            itemstack.shrink(1);
//        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }


    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("分出三根破天神棍，对前方造成伤害").withStyle(ChatFormatting.GREEN));
        list.add(Component.translatable("蹲下释放可破环地形").withStyle(ChatFormatting.GRAY));
    }
}
