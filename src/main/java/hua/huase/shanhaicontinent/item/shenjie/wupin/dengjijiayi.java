package hua.huase.shanhaicontinent.item.shenjie.wupin;

import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.util.ShuXingSet;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class dengjijiayi extends Item {
    public dengjijiayi(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            float currentDengji = PlayerAttrubuteAPI.getDengji(pPlayer);
            ShuXingSet.setDengji(pPlayer,(int) (currentDengji + 1));
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.literal("右键使用测试丹药，直接增加一级").withStyle(ChatFormatting.DARK_RED));
        list.add(Component.literal("注意！玩家的其他属性不提升！").withStyle(ChatFormatting.DARK_RED));
        list.add(Component.literal("注意！使用该丹药提升的等级不可完成成就！").withStyle(ChatFormatting.DARK_RED));
    }
}