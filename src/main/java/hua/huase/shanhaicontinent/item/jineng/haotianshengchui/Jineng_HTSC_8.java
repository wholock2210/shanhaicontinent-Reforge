package hua.huase.shanhaicontinent.item.jineng.haotianshengchui;

import hua.huase.shanhaicontinent.entity.jinengentity.huang.JiNengSNSLEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import hua.huase.shanhaicontinent.init.ModConfig;
import hua.huase.shanhaicontinent.item.jineng.JinengBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;

import javax.annotation.Nullable;
import java.util.List;

public class Jineng_HTSC_8 extends JinengBase {
    public Jineng_HTSC_8(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!this.isBelongToPlayer(player, itemstack)) return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, (int) (200 - Math.log10(this.getNianxian(player, itemstack)) * 10));

        boolean canBreakBlocks = ModConfig.canBreakBlocks.get();

        if (!level.isClientSide) {
            List<LivingEntity> nearbyEntities = level.getEntities(EntityTypeTest.forClass(LivingEntity.class), player.getBoundingBox().inflate(40, 40, 40), Entity::isAlive);
            for (LivingEntity nearbyEntity : nearbyEntities) {
                if (nearbyEntity.getId() != player.getId()) {
                    JiNengSNSLEntity entity = new JiNengSNSLEntity(EntityInit.jinengsnsl.get(), level);
                    entity.setOwner(player);
                    entity.setPos(nearbyEntity.xo, nearbyEntity.yo, nearbyEntity.zo);
                    entity.setItem(itemstack);
                    entity.shootFromRotation(player, 0, 0, 0.0F, 1F, 0.0F);

                    if (canBreakBlocks && player.isShiftKeyDown()) {
                        entity.isExploade = true;
                    } else {
                        entity.isExploade = false;
                    }

                    level.addFreshEntity(entity);
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("引动天地之力，降下天雷").withStyle(ChatFormatting.GREEN));
    }
}
