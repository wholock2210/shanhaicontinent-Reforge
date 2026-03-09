package hua.huase.shanhaicontinent.item.jineng;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface Jineng {

     boolean isBelongToPlayer(Player player, ItemStack itemStack);

    String getPlayer(ItemStack itemStack);

    void belongToPlayer(Player player, ItemStack itemStack);
     void setNianxian(Player player, ItemStack itemStack, int nianxian);
     int getNianxian(Player player, ItemStack itemStack);

}
