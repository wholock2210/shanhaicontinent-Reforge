package hua.huase.shanhaicontinent.item;

import hua.huase.shanhaicontinent.capability.itemattribute.ItemAttributeCapabilityProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ItemAttribute {
    public  ItemAttributeCapabilityProvider creatItemAttribute();

    public int naew = 0;

    public static void appendItemAttribute(ItemStack itemStack, List<Component> list) {
        itemStack.getCapability(ItemAttributeCapabilityProvider.CAPABILITY).ifPresent(itemAttributeCapability -> {
//                Tag tag = itemStack.getOrCreateTag().get("shanhaiitematuble");
//                itemAttributeCapability.deserializeNBT((CompoundTag) tag);
            if(itemAttributeCapability.getNianxian()==0){
                list.add(Component.translatable("未装配").withStyle(ChatFormatting.GRAY));
                return;
            }

            if(itemAttributeCapability.getNianxian()!=0){
                list.add(Component.translatable("年限", (int)itemAttributeCapability.getNianxian()).withStyle(ChatFormatting.YELLOW));
            }
            if(itemAttributeCapability.getMaxshengming()!=0){
                list.add(Component.translatable("最大生命", (int)itemAttributeCapability.getMaxshengming()).withStyle(ChatFormatting.YELLOW));
            }
            if(itemAttributeCapability.getWugong()!=0){
                list.add(Component.translatable("物攻", (int)itemAttributeCapability.getWugong()).withStyle(ChatFormatting.YELLOW));
            }

            if(itemAttributeCapability.getWufang()!=0){
                list.add(Component.translatable("物防", (int)itemAttributeCapability.getWufang()).withStyle(ChatFormatting.YELLOW));
            }
            if(itemAttributeCapability.getBaojishanghai()!=0){
                list.add(Component.translatable("爆伤", (int)itemAttributeCapability.getBaojishanghai()).withStyle(ChatFormatting.YELLOW));
            }
            if(itemAttributeCapability.getBaojilv()!=0){
                list.add(Component.translatable("爆率", (int)itemAttributeCapability.getBaojilv()).withStyle(ChatFormatting.YELLOW));
            }
            if(itemAttributeCapability.getZhenshang()!=0){
                list.add(Component.translatable("真伤", (int)itemAttributeCapability.getZhenshang()).withStyle(ChatFormatting.YELLOW));
            }
            if(itemAttributeCapability.getWuchuan()!=0){
                list.add(Component.translatable("物穿", (int)itemAttributeCapability.getWuchuan()).withStyle(ChatFormatting.YELLOW));
            }
            if(itemAttributeCapability.getKangbao()!=0){
                list.add(Component.translatable("抗暴", (int)itemAttributeCapability.getKangbao()).withStyle(ChatFormatting.YELLOW));
            }
            if(itemAttributeCapability.getXixue()!=0){
                list.add(Component.translatable("吸血", (int)itemAttributeCapability.getXixue()).withStyle(ChatFormatting.YELLOW));
            }
            if(itemAttributeCapability.getShengminghuifu()!=0){
                list.add(Component.translatable("回复", (int)itemAttributeCapability.getShengminghuifu()).withStyle(ChatFormatting.YELLOW));
            }
            if(itemAttributeCapability.getMingzhong()!=0){
                list.add(Component.translatable("命中", (int)itemAttributeCapability.getMingzhong()).withStyle(ChatFormatting.YELLOW));
            }
            if(itemAttributeCapability.getShanbi()!=0){
                list.add(Component.translatable("闪避", (int)itemAttributeCapability.getShanbi()).withStyle(ChatFormatting.YELLOW));
            }
        });
    }

}
