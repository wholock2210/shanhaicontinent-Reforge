package hua.huase.shanhaicontinent.capability;

import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapability;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.item.armor.SHArmorBaseItem;
import hua.huase.shanhaicontinent.potion.PotionAttribute;
import hua.huase.shanhaicontinent.potion.SHBaseMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Map;

public interface AttrubuteAPI {

    /*基础属性
    生命          最大生命
    物攻          物防
    暴击伤害       暴击率
    真伤          物穿
    抗暴
    吸血          生命回复
    命中          闪避
           */
    static float getShengming(Entity entity){
        float value = 0;
        if(entity instanceof Player player){
            value+= PlayerAttrubuteAPI.getShengming(player);
        }
        if(entity instanceof Monster monster){
            LazyOptional<MonsterAttributeCapability> capability = monster.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY);
            if(capability.isPresent()){
                MonsterAttributeCapability monsterAttributeCapability = capability.orElseThrow(RuntimeException::new);
                value += monsterAttributeCapability.getShengming();
            }
        }
        return value;
    }
    static float getMaxshengming(Entity entity){
        float value = 0;
        if(entity instanceof Player player){
            value+= PlayerAttrubuteAPI.getMaxshengming(player);
        }
        if(entity instanceof Monster monster){
            LazyOptional<MonsterAttributeCapability> capability = monster.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY);
            if(capability.isPresent()){
                MonsterAttributeCapability monsterAttributeCapability = capability.orElseThrow(RuntimeException::new);
                value += monsterAttributeCapability.getMaxshengming();
            }
        }
        if(entity instanceof LivingEntity livingEntity){
            Map<MobEffect, MobEffectInstance> activeEffectsMap = livingEntity.getActiveEffectsMap();
            for (Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry : activeEffectsMap.entrySet()) {
                if(mobEffectMobEffectInstanceEntry.getKey() instanceof PotionAttribute potionAttribute){
                    value += potionAttribute.getMaxshengming(livingEntity,mobEffectMobEffectInstanceEntry,value);
                }
            }

            Iterable<ItemStack> armorSlots = livingEntity.getArmorSlots();
            if(armorSlots!=null){
                Boolean istaozhuang = true;
                for (ItemStack armorSlot : armorSlots) {
                    if(!armorSlot.isEmpty() && armorSlot.getItem() instanceof SHArmorBaseItem shArmorBaseItem && armorSlot.getMaxDamage()-armorSlot.getDamageValue()>1){
                        value +=shArmorBaseItem.setMaxshengming(armorSlot , value);
                    }else {
                        istaozhuang = false;
                    }
                }
                if(istaozhuang){
                    ItemStack itemBySlot = SHArmorBaseItem.getLowTaozhuang(armorSlots);
                    if(!itemBySlot.isEmpty() && itemBySlot.getItem() instanceof SHArmorBaseItem shArmorBaseItem){
                        value +=shArmorBaseItem.setMaxshengmingTaozhuang(itemBySlot , value);
                    }
                }

            }
        }

        return value;
    }
    static float getWugong(Entity entity){
        float value = 0;
        if(entity instanceof Player player){
            value+= PlayerAttrubuteAPI.getWugong(player);
        }
        if(entity instanceof Monster monster){
            LazyOptional<MonsterAttributeCapability> capability = monster.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY);
            if(capability.isPresent()){
                MonsterAttributeCapability monsterAttributeCapability = capability.orElseThrow(RuntimeException::new);
                value += monsterAttributeCapability.getWugong();
            }
        }
        if(entity instanceof LivingEntity livingEntity){
            Map<MobEffect, MobEffectInstance> activeEffectsMap = livingEntity.getActiveEffectsMap();
            for (Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry : activeEffectsMap.entrySet()) {
                if(mobEffectMobEffectInstanceEntry.getKey() instanceof PotionAttribute potionAttribute){
                    value += potionAttribute.getWugong(livingEntity,mobEffectMobEffectInstanceEntry,value);
                }
            }

        }
        return value;
    }
    static float getWufang(Entity entity){
        float value = 0;
        if(entity instanceof Player player){
            value+= PlayerAttrubuteAPI.getWufang(player);
        }
        if(entity instanceof Monster monster){
            LazyOptional<MonsterAttributeCapability> capability = monster.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY);
            if(capability.isPresent()){
                MonsterAttributeCapability monsterAttributeCapability = capability.orElseThrow(RuntimeException::new);
                value += monsterAttributeCapability.getWufang();
            }
        }
        if(entity instanceof LivingEntity livingEntity){
            Map<MobEffect, MobEffectInstance> activeEffectsMap = livingEntity.getActiveEffectsMap();
            for (Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry : activeEffectsMap.entrySet()) {
                if(mobEffectMobEffectInstanceEntry.getKey() instanceof PotionAttribute potionAttribute){
                    value += potionAttribute.getWufang(livingEntity,mobEffectMobEffectInstanceEntry,value);
                }
            }


            Iterable<ItemStack> armorSlots = livingEntity.getArmorSlots();
            if(armorSlots!=null){
                Boolean istaozhuang = true;
                for (ItemStack armorSlot : armorSlots) {
                    if(!armorSlot.isEmpty() && armorSlot.getItem() instanceof SHArmorBaseItem shArmorBaseItem && armorSlot.getMaxDamage()-armorSlot.getDamageValue()>1){
                        value +=shArmorBaseItem.setWufang(armorSlot , value);
                    }else {
                        istaozhuang = false;
                    }
                }
                if(istaozhuang){
                    ItemStack itemBySlot = SHArmorBaseItem.getLowTaozhuang(armorSlots);
                    if(!itemBySlot.isEmpty() && itemBySlot.getItem() instanceof SHArmorBaseItem shArmorBaseItem){
                        value +=shArmorBaseItem.setWufangTaozhuang(itemBySlot , value);
                    }
                }
            }

        }


        return value;
    }
    static float getBaojishanghai(Entity entity){
        float value = 0;
        if(entity instanceof Player player){
            value+= PlayerAttrubuteAPI.getBaojishanghai(player);
        }
        if(entity instanceof Monster monster){
            LazyOptional<MonsterAttributeCapability> capability = monster.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY);
            if(capability.isPresent()){
                MonsterAttributeCapability monsterAttributeCapability = capability.orElseThrow(RuntimeException::new);
                value += monsterAttributeCapability.getBaojishanghai();
            }
        }
        if(entity instanceof LivingEntity livingEntity){
            Map<MobEffect, MobEffectInstance> activeEffectsMap = livingEntity.getActiveEffectsMap();
            for (Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry : activeEffectsMap.entrySet()) {
                if(mobEffectMobEffectInstanceEntry.getKey() instanceof PotionAttribute potionAttribute){
                    value += potionAttribute.getBaojishanghai(livingEntity,mobEffectMobEffectInstanceEntry,value);
                }
            }

        }
        return value;
    }
    static float getBaojilv(Entity entity){
        float value = 0;
        if(entity instanceof Player player){
            value+= PlayerAttrubuteAPI.getBaojilv(player);
        }
        if(entity instanceof Monster monster){
            LazyOptional<MonsterAttributeCapability> capability = monster.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY);
            if(capability.isPresent()){
                MonsterAttributeCapability monsterAttributeCapability = capability.orElseThrow(RuntimeException::new);
                value += monsterAttributeCapability.getBaojilv();
            }
        }
        if(entity instanceof LivingEntity livingEntity){
            Map<MobEffect, MobEffectInstance> activeEffectsMap = livingEntity.getActiveEffectsMap();
            for (Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry : activeEffectsMap.entrySet()) {
                if(mobEffectMobEffectInstanceEntry.getKey() instanceof PotionAttribute potionAttribute){
                    value += potionAttribute.getBaojilv(livingEntity,mobEffectMobEffectInstanceEntry,value);
                }
            }

        }
        return value;
    }
    static float getZhenshang(Entity entity){
        float value = 0;
        if(entity instanceof Player player){
            value+= PlayerAttrubuteAPI.getZhenshang(player);
        }
        if(entity instanceof Monster monster){
            LazyOptional<MonsterAttributeCapability> capability = monster.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY);
            if(capability.isPresent()){
                MonsterAttributeCapability monsterAttributeCapability = capability.orElseThrow(RuntimeException::new);
                value += monsterAttributeCapability.getZhenshang();
            }
        }
        if(entity instanceof LivingEntity livingEntity){
            Map<MobEffect, MobEffectInstance> activeEffectsMap = livingEntity.getActiveEffectsMap();
            for (Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry : activeEffectsMap.entrySet()) {
                if(mobEffectMobEffectInstanceEntry.getKey() instanceof PotionAttribute potionAttribute){
                    value += potionAttribute.getZhenshang(livingEntity,mobEffectMobEffectInstanceEntry,value);
                }
            }

        }
        return value;
    }
    static float getWuchuan(Entity entity){
        float value = 0;
        if(entity instanceof Player player){
            value+= PlayerAttrubuteAPI.getWuchuan(player);
        }
        if(entity instanceof Monster monster){
            LazyOptional<MonsterAttributeCapability> capability = monster.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY);
            if(capability.isPresent()){
                MonsterAttributeCapability monsterAttributeCapability = capability.orElseThrow(RuntimeException::new);
                value += monsterAttributeCapability.getWuchuan();
            }
        }
        if(entity instanceof LivingEntity livingEntity){
            Map<MobEffect, MobEffectInstance> activeEffectsMap = livingEntity.getActiveEffectsMap();
            for (Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry : activeEffectsMap.entrySet()) {
                if(mobEffectMobEffectInstanceEntry.getKey() instanceof PotionAttribute potionAttribute){
                    value += potionAttribute.getWuchuan(livingEntity,mobEffectMobEffectInstanceEntry,value);
                }
            }

        }
        return value;
    }
    static float getKangbao(Entity entity){
        float value = 0;
        if(entity instanceof Player player){
            value+= PlayerAttrubuteAPI.getKangbao(player);
        }
        if(entity instanceof Monster monster){
            LazyOptional<MonsterAttributeCapability> capability = monster.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY);
            if(capability.isPresent()){
                MonsterAttributeCapability monsterAttributeCapability = capability.orElseThrow(RuntimeException::new);
                value += monsterAttributeCapability.getKangbao();
            }
        }
        if(entity instanceof LivingEntity livingEntity){
            Map<MobEffect, MobEffectInstance> activeEffectsMap = livingEntity.getActiveEffectsMap();
            for (Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry : activeEffectsMap.entrySet()) {
                if(mobEffectMobEffectInstanceEntry.getKey() instanceof PotionAttribute potionAttribute){
                    value += potionAttribute.getKangbao(livingEntity,mobEffectMobEffectInstanceEntry,value);
                }
            }

        }
        return value;
    }
    static float getXixue(Entity entity){
        float value = 0;
        if(entity instanceof Player player){
            value+= PlayerAttrubuteAPI.getXixue(player);
        }
        if(entity instanceof Monster monster){
            LazyOptional<MonsterAttributeCapability> capability = monster.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY);
            if(capability.isPresent()){
                MonsterAttributeCapability monsterAttributeCapability = capability.orElseThrow(RuntimeException::new);
                value += monsterAttributeCapability.getXixue();
            }
        }
        if(entity instanceof LivingEntity livingEntity){
            Map<MobEffect, MobEffectInstance> activeEffectsMap = livingEntity.getActiveEffectsMap();
            for (Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry : activeEffectsMap.entrySet()) {
                if(mobEffectMobEffectInstanceEntry.getKey() instanceof PotionAttribute potionAttribute){
                    value += potionAttribute.getXixue(livingEntity,mobEffectMobEffectInstanceEntry,value);
                }
            }

        }
        return value;
    }
    static float getMinghzong(Entity entity){
        float value = 0;
        if(entity instanceof Player player){
            value+= PlayerAttrubuteAPI.getMinghzong(player);
        }
        if(entity instanceof Monster monster){
            LazyOptional<MonsterAttributeCapability> capability = monster.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY);
            if(capability.isPresent()){
                MonsterAttributeCapability monsterAttributeCapability = capability.orElseThrow(RuntimeException::new);
                value += monsterAttributeCapability.getMingzhong();
            }
        }
        if(entity instanceof LivingEntity livingEntity){
            Map<MobEffect, MobEffectInstance> activeEffectsMap = livingEntity.getActiveEffectsMap();
            for (Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry : activeEffectsMap.entrySet()) {
                if(mobEffectMobEffectInstanceEntry.getKey() instanceof PotionAttribute potionAttribute){
                    value += potionAttribute.getMinghzong(livingEntity,mobEffectMobEffectInstanceEntry,value);
                }
            }

        }
        return value;
    }
    static float getShanbi(Entity entity){
        float value = 0;
        if(entity instanceof Player player){
            value+= PlayerAttrubuteAPI.getShanbi(player);
        }
        if(entity instanceof Monster monster){
            LazyOptional<MonsterAttributeCapability> capability = monster.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY);
            if(capability.isPresent()){
                MonsterAttributeCapability monsterAttributeCapability = capability.orElseThrow(RuntimeException::new);
                value += monsterAttributeCapability.getShanbi();
            }
        }
        if(entity instanceof LivingEntity livingEntity){
            Map<MobEffect, MobEffectInstance> activeEffectsMap = livingEntity.getActiveEffectsMap();
            for (Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry : activeEffectsMap.entrySet()) {
                if(mobEffectMobEffectInstanceEntry.getKey() instanceof PotionAttribute potionAttribute){
                    value += potionAttribute.getShanbi(livingEntity,mobEffectMobEffectInstanceEntry,value);
                }
            }

        }
        return value;
    }
    static float getShengminghuifu(Entity entity){
        float value = 0;
        if(entity instanceof Player player){
            value+= PlayerAttrubuteAPI.getShengminghuifu(player);
        }
        if(entity instanceof Monster monster){
            LazyOptional<MonsterAttributeCapability> capability = monster.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY);
            if(capability.isPresent()){
                MonsterAttributeCapability monsterAttributeCapability = capability.orElseThrow(RuntimeException::new);
                value += monsterAttributeCapability.getShengminghuifu();
            }
        }
        if(entity instanceof LivingEntity livingEntity){
            Map<MobEffect, MobEffectInstance> activeEffectsMap = livingEntity.getActiveEffectsMap();
            for (Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry : activeEffectsMap.entrySet()) {
                if(mobEffectMobEffectInstanceEntry.getKey() instanceof PotionAttribute potionAttribute){
                    value += potionAttribute.getShengminghuifu(livingEntity,mobEffectMobEffectInstanceEntry,value);
                }
            }


            Iterable<ItemStack> armorSlots = livingEntity.getArmorSlots();
            if(armorSlots!=null){
                Boolean istaozhuang = true;
                for (ItemStack armorSlot : armorSlots) {
                    if(!armorSlot.isEmpty() && armorSlot.getItem() instanceof SHArmorBaseItem shArmorBaseItem && armorSlot.getMaxDamage()-armorSlot.getDamageValue()>1){
//                        value +=shArmorBaseItem.setMaxshengming(armorSlot , value);
                    }else {
                        istaozhuang = false;
                    }
                }
                if(istaozhuang){
                    ItemStack itemBySlot = SHArmorBaseItem.getLowTaozhuang(armorSlots);
                    if(!itemBySlot.isEmpty() && itemBySlot.getItem() instanceof SHArmorBaseItem shArmorBaseItem){
                        value +=shArmorBaseItem.setShengminghuifuTaozhuang(itemBySlot , value);
                    }
                }

            }

        }
        return value;
    }
}
