package hua.huase.shanhaicontinent.item.jineng;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface WuqiAttribute {

     float getWugong(Player player, ItemStack itemStack, float value, EquipmentSlot offhand);

     float getMaxShengming(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot);

     float getWufang(Player player, ItemStack offhandItem, float value, EquipmentSlot equipmentSlot);

     float getBaojishanghai(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot);

     float getBaojilv(Player player, ItemStack offhandItem, float value, EquipmentSlot equipmentSlot);

     float getZhenshang(Player player, ItemStack offhandItem, float value, EquipmentSlot equipmentSlot);

     float getWuchuan(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot);

     float getKangbao(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot);

     float getXixue(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot);

     float getMinghzong(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot);

     float getShanbi(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot);

     float getShengminghuifu(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot);
}
