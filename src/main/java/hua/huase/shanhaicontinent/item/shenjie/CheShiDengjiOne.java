package hua.huase.shanhaicontinent.item.shenjie;

import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
public class CheShiDengjiOne extends Item {
    public CheShiDengjiOne(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
                float currentRate = capability.getTupochenggonggailv();
                capability.setTupochenggonggailv(currentRate + 99f);
                player.sendSystemMessage(Component.literal("突破成功率已增加99%").withStyle(ChatFormatting.GREEN));
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
            });
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.literal("右键使用增加99%的突破成功率").withStyle(ChatFormatting.GREEN));
    }
}