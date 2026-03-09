package hua.huase.shanhaicontinent.item.jineng;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;


import javax.annotation.Nullable;
import java.util.List;

public class WuqiBase extends SwordItem implements WuqiAttribute, Jineng {
    public WuqiBase(Tier tier, int i, float v, Properties properties) {
        super(tier, i, v, properties);
    }

    public boolean canBeDepleted() {
        return false;
    }

    @Override
    public float getWugong(Player player, ItemStack itemStack, float value, EquipmentSlot offhand) {
        return value;
    }

    @Override
    public float getMaxShengming(Player player, ItemStack itemStack, float value, EquipmentSlot offhand) {
        return value;
    }

    @Override
    public float getWufang(Player player, ItemStack offhandItem, float value, EquipmentSlot equipmentSlot) {
        return value;
    }

    @Override
    public float getBaojishanghai(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot) {
        return value;
    }

    @Override
    public float getBaojilv(Player player, ItemStack offhandItem, float value, EquipmentSlot equipmentSlot) {
        return value;
    }

    @Override
    public float getZhenshang(Player player, ItemStack offhandItem, float value, EquipmentSlot equipmentSlot) {
        return value;
    }

    @Override
    public float getWuchuan(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot) {
        return value;
    }

    @Override
    public float getKangbao(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot) {
        return value;
    }

    @Override
    public float getXixue(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot) {
        return value;
    }

    @Override
    public float getMinghzong(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot) {
        return value;
    }

    @Override
    public float getShanbi(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot) {
        return value;
    }

    @Override
    public float getShengminghuifu(Player player, ItemStack mainHandItem, float value, EquipmentSlot equipmentSlot) {
        return value;
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
        orCreateTag.putInt("sh_nianxian", nianxian);
    }


    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {

        String playerName = this.getPlayer(itemStack);
        if (playerName.isEmpty()) {
            list.add(Component.literal("拥有者: 未知").withStyle(ChatFormatting.DARK_GRAY));
        } else {
            list.add(Component.translatable("拥有者: " + playerName).withStyle(ChatFormatting.DARK_GRAY));
        }

        if (this.getNianxian(null, itemStack) > 0) {
            list.add(Component.translatable("年限", this.getNianxian(null, itemStack)).withStyle(ChatFormatting.AQUA));
        }

        if (itemStack.getOrCreateTag() != null) {
            byte b = 0;
            CompoundTag wuhunjineng = (CompoundTag) itemStack.getOrCreateTag().get("wuhunjineng" + b);
            while (wuhunjineng != null) {
                ItemStack jineng = ItemStack.of(wuhunjineng);
                if ((!jineng.isEmpty()) && jineng.getItem() instanceof Jineng) {
                    list.add(Component.translatable("武魂技能", jineng.getHoverName().getString(), this.getNianxian(null, jineng)).withStyle(ChatFormatting.YELLOW));
                }
                ++b;
                wuhunjineng = (CompoundTag) itemStack.getOrCreateTag().get("wuhunjineng" + b);
            }
        }
    }
}