package hua.huase.shanhaicontinent.item.jineng.huang;

import hua.huase.shanhaicontinent.entity.jinengentity.huang.JiNengBSQEntity;
import hua.huase.shanhaicontinent.entity.jinengentity.huang.JiNengCMJJEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import hua.huase.shanhaicontinent.init.ModConfig;
import hua.huase.shanhaicontinent.init.SHModMobEffectsinit;
import hua.huase.shanhaicontinent.item.jineng.JinengBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Jineng_Huang_7 extends JinengBase {
    public Jineng_Huang_7(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(!this.isBelongToPlayer(player, itemstack)) return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, (int) (60 - Math.log10(this.getNianxian(player, itemstack)) * 5));

        boolean canBreakBlocks = ModConfig.canBreakBlocks.get();

        if (!level.isClientSide) {
            JiNengBSQEntity entity = new JiNengBSQEntity(EntityInit.jinengbsq.get(), level);
            entity.setOwner(player);
            double v = -Math.sin((player.getYRot()) * 0.017453292F) * 12f;
            double v1 = Math.cos((player.getYRot()) * 0.017453292F) * 12f;
            entity.setPos(player.getX() + v, player.getY() + 10, player.getZ() + v1);
            entity.setItem(itemstack);
            entity.shootFromRotation(player, 90, 0, 0.0F, 1F, 0.0F);
            if (canBreakBlocks && player.isShiftKeyDown()) {
                entity.isExploade = true;
            } else {
                entity.isExploade = false;
            }
            level.addFreshEntity(entity);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("对前方单体造成伤害").withStyle(ChatFormatting.GREEN));
    }
}
