package hua.huase.shanhaicontinent.item.jineng;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class JinengBase extends Item implements Jineng {
    public JinengBase(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isBelongToPlayer(Player player, ItemStack itemStack) {

        CompoundTag orCreateTag = itemStack.getOrCreateTag();
        String string = orCreateTag.getString("sh_playername");
        return player.getName().toString().equals(string);
    }
    @Override
    public String getPlayer(ItemStack itemStack) {

        CompoundTag orCreateTag = itemStack.getOrCreateTag();
        String string = orCreateTag.getString("sh_playername");
        return string;
    }

    @Override
    public void belongToPlayer(Player player, ItemStack itemStack) {
        CompoundTag orCreateTag = itemStack.getOrCreateTag();
        orCreateTag.putString("sh_playername",player.getName().toString());
    }

    @Override
    public int getNianxian(Player player, ItemStack itemStack) {
        CompoundTag orCreateTag = itemStack.getOrCreateTag();
        return orCreateTag.getInt("sh_nianxian");
    }
    @Override
    public void setNianxian(Player player, ItemStack itemStack, int nianxian) {
        CompoundTag orCreateTag = itemStack.getOrCreateTag();
        orCreateTag.putInt("sh_nianxian",nianxian);
    }


    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("拥有者",this.getPlayer(itemStack)).withStyle(ChatFormatting.DARK_GRAY));
        list.add(Component.translatable("年限",this.getNianxian(null,itemStack)).withStyle(ChatFormatting.AQUA));
    }
}
