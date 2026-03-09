package hua.huase.shanhaicontinent.item.jineng.jinggubang;

import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.item.jineng.Jineng;
import hua.huase.shanhaicontinent.item.jineng.WuqiBase;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Jineng_JGB_0 extends WuqiBase {
    public Jineng_JGB_0(Tier tier, int i, float v, Properties properties) {
        super(tier, i, v, properties);
    }

    public float getWugong(Player player, ItemStack itemStack, float value, EquipmentSlot offhand) {
        if(offhand == EquipmentSlot.MAINHAND){
            float dengji = PlayerAttrubuteAPI.getDengji(player);
            value += value*0.08f*(dengji+1)/10;
        }

        return value;
    }


    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(!this.isBelongToPlayer(player,itemstack))return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        int nianxian = this.getNianxian(player, itemstack);
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, (int) (40-Math.log10(nianxian*10)));
        if (!level.isClientSide) {
            if(itemstack.getOrCreateTag()!=null){
                byte b= 0;
                CompoundTag wuhunjineng = (CompoundTag) itemstack.getOrCreateTag().get("wuhunjineng"+b);
                while (wuhunjineng !=null){
                    ItemStack jineng =ItemStack.of(wuhunjineng);
                    if((!jineng.isEmpty()) && jineng.getItem() instanceof Jineng){
                        if (!player.getCooldowns().isOnCooldown(jineng.getItem()))
                        {
                            jineng.use(level, player, hand);
                        }
                    }
                    ++b;
                    wuhunjineng = (CompoundTag) itemstack.getOrCreateTag().get("wuhunjineng"+b);
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }


    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);

        list.add(Component.translatable("《万破》：每10级增加8%的物攻").withStyle(ChatFormatting.GREEN));
    }
}