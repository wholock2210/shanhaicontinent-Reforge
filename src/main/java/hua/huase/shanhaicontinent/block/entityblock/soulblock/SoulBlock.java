package hua.huase.shanhaicontinent.block.entityblock.soulblock;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public class SoulBlock extends Block {
    public SoulBlock(Properties properties) {
        super(properties);
    }


    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("用于栽培药材，可由《魂液瓶》消耗能量，右键耕地获得").withStyle(ChatFormatting.GRAY));
    }

}
