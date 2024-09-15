package hua.huase.shanhaicontinent.item.armor;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

import static hua.huase.shanhaicontinent.item.armor.SHArmorMaterial.mingtie;

public class SHArmorBaseItem extends ArmorItem {
    public SHArmorBaseItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }
    @Override
    public  <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        int maxDamage = stack.getMaxDamage();
        int damageValue = stack.getDamageValue();
        int i = maxDamage - damageValue-1;
        amount = (int) Math.sqrt(amount);
        return Math.min(Math.min(i,amount),200);
    }


    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String layer) {
        if (slot == EquipmentSlot.LEGS) {
            return SHMainBus.MOD_ID+":textures/armor/" +this.getMaterial().getName()+ "_layer_2.png";
        } else {
            return SHMainBus.MOD_ID+":textures/armor/" +this.getMaterial().getName()+ "_layer_1.png";
        }
    }

    public float setMaxshengming(ItemStack stack ,float value) {
        return (stack.getMaxDamage()-stack.getDamageValue())*0.02f;
    }

    public float setMaxshengmingTaozhuang(ItemStack stack, float value) {
        if(this.getMaterial() == SHArmorMaterial.mingtie){
            return value*0.1f;
        }
        if(this.getMaterial() == SHArmorMaterial.heijin){
            return value*0.15f;
        }
        if(this.getMaterial() == SHArmorMaterial.lanlingjin){
            return value*0.2f;
        }
        if(this.getMaterial() == SHArmorMaterial.lanhaizuan){
            return value*0.3f;
        }
        if(this.getMaterial() == SHArmorMaterial.cixuexianjin){
            return value*0.5f;
        }
        return 0;
    }

    public float setWufang(ItemStack stack, float value) {
        return (stack.getMaxDamage()-stack.getDamageValue())*0.01f;
    }

    public float setWufangTaozhuang(ItemStack stack, float value) {
        if(this.getMaterial() == SHArmorMaterial.mingtie){
            return value*0.2f;
        }
        if(this.getMaterial() == SHArmorMaterial.heijin){
            return value*0.3f;
        }
        if(this.getMaterial() == SHArmorMaterial.lanlingjin){
            return value*0.4f;
        }
        if(this.getMaterial() == SHArmorMaterial.lanhaizuan){
            return value*0.6f;
        }
        if(this.getMaterial() == SHArmorMaterial.cixuexianjin){
            return value*1.0f;
        }
        return 0;
    }

    public float setShengminghuifuTaozhuang(ItemStack stack, float value) {

        if(this.getMaterial() == SHArmorMaterial.mingtie){
            return 4;
        }
        if(this.getMaterial() == SHArmorMaterial.heijin){
            return 8;
        }
        if(this.getMaterial() == SHArmorMaterial.lanlingjin){
            return 16;
        }
        if(this.getMaterial() == SHArmorMaterial.lanhaizuan){
            return 32;
        }
        if(this.getMaterial() == SHArmorMaterial.cixuexianjin){
            return 64;
        }
        return 0;
    }


    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("最大生命", (int)((stack.getMaxDamage()-stack.getDamageValue())*0.02f)).withStyle(ChatFormatting.YELLOW));
        list.add(Component.translatable("物防", (int)((stack.getMaxDamage()-stack.getDamageValue())*0.01f)).withStyle(ChatFormatting.YELLOW));

        list.add(Component.translatable("套装效果").withStyle(ChatFormatting.LIGHT_PURPLE));


        if(this.getMaterial() == SHArmorMaterial.mingtie){
            list.add(Component.translatable("最大生命","10%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("物防","20%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("回复","4").withStyle(ChatFormatting.AQUA));
        }
        if(this.getMaterial() == SHArmorMaterial.heijin){
            list.add(Component.translatable("最大生命","15%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("物防","30%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("回复","8").withStyle(ChatFormatting.AQUA));
        }
        if(this.getMaterial() == SHArmorMaterial.lanlingjin){
            list.add(Component.translatable("最大生命","20%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("物防","40%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("回复","16").withStyle(ChatFormatting.AQUA));
        }
        if(this.getMaterial() == SHArmorMaterial.lanhaizuan){
            list.add(Component.translatable("最大生命","30%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("物防","60%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("回复","32").withStyle(ChatFormatting.AQUA));
        }
        if(this.getMaterial() == SHArmorMaterial.cixuexianjin){
            list.add(Component.translatable("最大生命","50%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("物防","100%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("回复","64").withStyle(ChatFormatting.AQUA));
        }
    }
}
