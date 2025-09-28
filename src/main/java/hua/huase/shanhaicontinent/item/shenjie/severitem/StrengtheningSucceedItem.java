package hua.huase.shanhaicontinent.item.shenjie.severitem;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static hua.huase.shanhaicontinent.register.ModItems.STRENGTHENING_SUCCEED;

public class StrengtheningSucceedItem extends Item {
    public StrengtheningSucceedItem(Properties properties) {
        super(properties);
    }
    public static ItemStack createStack(int successRate) {
        ItemStack stack = new ItemStack(STRENGTHENING_SUCCEED.get());
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("SuccessRate", successRate);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        int rate = getSuccessRate(stack);
        tooltip.add(Component.literal("可增加成功率: +" + rate + "%")
                .withStyle(ChatFormatting.GREEN));
    }

    @Override
    public Component getName(ItemStack stack) {
        int rate = getSuccessRate(stack);
        return super.getName(stack).copy()
                .append(" [+" + rate + "%]")
                .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x55FF55)));
    }

    public static int getSuccessRate(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("SuccessRate")) {
            return stack.getTag().getInt("SuccessRate");
        }
        return 10; // 默认值
    }
}