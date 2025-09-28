package hua.huase.shanhaicontinent.item.armor;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SHArmorBaseItem extends ArmorItem {
    public SHArmorBaseItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    public static ItemStack getLowTaozhuang(Iterable<ItemStack> armorSlots) {
        ItemStack itemStack = ItemStack.EMPTY;
        int num = 7;
        for (ItemStack armorSlot : armorSlots) {
            if(!armorSlot.isEmpty() && armorSlot.getItem() instanceof SHArmorBaseItem shArmorBaseItem){
                int materialNum = SHArmorBaseItem.getMaterialNum(shArmorBaseItem.getMaterial());
                if(materialNum<num){
                    itemStack = armorSlot;
                    num = materialNum;
                }
            }else {
                return ItemStack.EMPTY;
            }
        }
        return itemStack;
    }

    public static int getMaterialNum(ArmorMaterial material){

        if(material == SHArmorMaterial.mingtie){
            return 1;
        }
        if(material == SHArmorMaterial.heijin){
            return 2;
        }
        if(material == SHArmorMaterial.lanlingjin){
            return 3;
        }
        if(material == SHArmorMaterial.lanhaizuan){
            return 4;
        }
        if(material == SHArmorMaterial.cixuexianjin){
            return 5;
        }
        if (material == SHArmorMaterial.xuanbing){
            return 6;
        }
        return 0;
    }

    @Override
    public  <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        int maxDamage = stack.getMaxDamage();
        int damageValue = stack.getDamageValue();

        int i = maxDamage - damageValue-1;
        int i1 = (int) (stack.getMaxDamage() * SHArmorBaseItem.getMaterialNum(this.material)* SHArmorBaseItem.getMaterialNum(this.material)*0.05f);
        if(i1 <amount){
            i = (int) Math.sqrt(amount);
        }
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
        if (this.getMaterial() == SHArmorMaterial.xuanbing){
            return value*1.0f;
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
        if(this.getMaterial() == SHArmorMaterial.xuanbing){
            return value*1.5f;
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
        if(this.getMaterial() == SHArmorMaterial.xuanbing){
            return 80;
        }
        return 0;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        Iterable<ItemStack> armorSlots = player.getArmorSlots();
        Map<ArmorMaterial, Integer> materialCount = new HashMap<>();
        for (ItemStack stack : armorSlots) {
            if (stack.getItem() instanceof SHArmorBaseItem armorItem) {
                materialCount.merge(armorItem.getMaterial(), 1, Integer::sum);
            }
        }
        materialCount.forEach((material, count) -> {
            if (count == 4) applySetEffects(player, material);
        });
    }

    private static void applySetEffects(Player player, ArmorMaterial material) {
        if (material == SHArmorMaterial.lanhaizuan) {
            player.removeEffect(MobEffects.WATER_BREATHING);
            player.addEffect(new MobEffectInstance(
                    MobEffects.WATER_BREATHING, 60, 0, false, false, true));
        }
        else if (material == SHArmorMaterial.cixuexianjin) {
            player.removeEffect(MobEffects.FIRE_RESISTANCE);
            player.addEffect(new MobEffectInstance(
                    MobEffects.FIRE_RESISTANCE, 60, 0, false, false, true));
        }
        else if (material == SHArmorMaterial.xuanbing) {
            player.removeEffect(MobEffects.FIRE_RESISTANCE);
            player.addEffect(new MobEffectInstance(
                    MobEffects.FIRE_RESISTANCE, 60, 0, false, false, true));
            MobEffectInstance currentSpeed = player.getEffect(MobEffects.MOVEMENT_SPEED);
            if (currentSpeed == null || currentSpeed.getAmplifier() < 0) {
                player.removeEffect(MobEffects.MOVEMENT_SPEED);
                player.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SPEED, 60, 0, false, false, true));
            }
        }
    }

    static {
        MinecraftForge.EVENT_BUS.register(SHArmorBaseItem.class);
    }



    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {



        list.add(Component.translatable("套装描述").withStyle(ChatFormatting.DARK_AQUA));

//        int i1 = (int) (stack.getMaxDamage() * SHArmorBaseItem.getMaterialNum(this.material)* SHArmorBaseItem.getMaterialNum(this.material)*0.05f);
//        list.add(Component.translatable("套装描述"+i1).withStyle(ChatFormatting.DARK_AQUA));

        if(stack.getMaxDamage()-stack.getDamageValue()<=1){
            list.add(Component.translatable("已破损").withStyle(ChatFormatting.DARK_RED));
            return;
        }


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
            list.add(Component.literal("Buff：水下呼吸").withStyle(ChatFormatting.AQUA));
        }
        if(this.getMaterial() == SHArmorMaterial.cixuexianjin){
            list.add(Component.translatable("最大生命","50%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("物防","100%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("回复","64").withStyle(ChatFormatting.AQUA));
            list.add(Component.literal("Buff：抗火").withStyle(ChatFormatting.AQUA));
        }

        if (this.getMaterial() == SHArmorMaterial.xuanbing){
            list.add(Component.translatable("最大生命","100%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("物防","150%").withStyle(ChatFormatting.AQUA));
            list.add(Component.translatable("回复","80").withStyle(ChatFormatting.AQUA));
            list.add(Component.literal("Buff：抗火，速度II").withStyle(ChatFormatting.AQUA));
        }

        list.add(Component.translatable("可通过造化炉修理").withStyle(ChatFormatting.WHITE));
    }
}
