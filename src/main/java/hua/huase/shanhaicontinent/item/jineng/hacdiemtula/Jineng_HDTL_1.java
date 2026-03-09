
package hua.huase.shanhaicontinent.item.jineng.hacdiemtula;

import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.entity.jinengentity.hacdiemtula.*;
import hua.huase.shanhaicontinent.entity.jinengentity.huang.JiNengCMJJEntity;
import hua.huase.shanhaicontinent.entity.jinengentity.jinggubang.JiNengFSHYEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import hua.huase.shanhaicontinent.init.ModConfig;
import hua.huase.shanhaicontinent.item.jineng.JinengBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;
import java.util.List;

public class Jineng_HDTL_1 extends JinengBase {
    public Jineng_HDTL_1(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!this.isBelongToPlayer(player, itemstack))
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        int nianxian = this.getNianxian(player, itemstack);
        level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW,
                SoundSource.NEUTRAL, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, (int) (120 - Math.log10(nianxian) * 10));

        boolean canBreakBlocks = ModConfig.canBreakBlocks.get();

        player.getCooldowns().addCooldown(this, 80);

        if (!level.isClientSide) {

            for (int i = 0; i < 5; i++) {

                float startAngle = i * 72F;

                Jineng_HDTL_1_Entity sword = new Jineng_HDTL_1_Entity(
                        EntityInit.jinenghdtl1.get(),
                        level,
                        nianxian,
                        startAngle);

                sword.setOwner(player);

                sword.setPos(player.getX(), player.getY() + 2, player.getZ());

                level.addFreshEntity(sword);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list,
            TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.literal("Triệu hồi 5 đạo kiếm khí tấn công về phía trước").withStyle(ChatFormatting.GREEN));
        list.add(Component.literal("sát thương dựa vào niên hạn hồn hoàn * level").withStyle(ChatFormatting.RED));
    }
}
