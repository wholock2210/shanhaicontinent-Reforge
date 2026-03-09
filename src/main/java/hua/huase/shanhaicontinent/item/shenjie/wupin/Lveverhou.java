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

public class Lveverhou extends Item {
    public Lveverhou(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            float currentDengji = PlayerAttrubuteAPI.getDengji(pPlayer);
            ShuXingSet.setDengji(pPlayer,(int) (currentDengji - 1));
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.literal("Nhấp chuột phải để sử dụng viên thuốc thử nghiệm, ngay lập tức làm giảm mức độ xuống một.").withStyle(ChatFormatting.DARK_RED));
        list.add(Component.literal("Lưu ý! Điều này sẽ không làm tăng các thuộc tính khác của người chơi!").withStyle(ChatFormatting.DARK_RED));
        list.add(Component.literal("Lưu ý! Cấp độ đạt được khi sử dụng loại thần dược này không thể dùng để hoàn thành các thành tích!").withStyle(ChatFormatting.DARK_RED));
    }
}