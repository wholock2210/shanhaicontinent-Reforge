package hua.huase.shanhaicontinent.capability.playerattribute;

import hua.huase.shanhaicontinent.capability.itemattribute.ItemAttributeCapability;
import hua.huase.shanhaicontinent.capability.itemattribute.ItemAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapability;
import hua.huase.shanhaicontinent.item.jineng.Jineng;
import hua.huase.shanhaicontinent.item.jineng.WuqiAttribute;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public interface PlayerAttrubuteAPI {

    /*基础属性
    生命          最大生命
    物攻          物防
    暴击伤害       暴击率
    真伤          物穿
    抗暴
    吸血          生命回复
    命中          闪避
           */

    static float getShengming(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getShengming();
            ItemStackHandler boneslot = playerAttributeCapability.getBoneslot();
            for (int i = 0; i < boneslot.getSlots(); i++) {
                ItemStack stackInSlot = boneslot.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    LazyOptional<ItemAttributeCapability> capability1 = stackInSlot.getCapability(ItemAttributeCapabilityProvider.CAPABILITY);
                    if (capability1.isPresent()) {
                        ItemAttributeCapability itemAttributeCapability = capability1.orElseThrow(RuntimeException::new);
                        value += itemAttributeCapability.getShengming();
                    }
                }
            }
            for (MonsterAttributeCapability monsterAttributeCapability : playerAttributeCapability.getWuhunList()) {
                value += monsterAttributeCapability.getShengming() * 0.1;
            }
        }
        return value;
    }

    static float getMaxshengming(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getMaxshengming();
            ItemStackHandler boneslot = playerAttributeCapability.getBoneslot();
            for (int i = 0; i < boneslot.getSlots(); i++) {
                ItemStack stackInSlot = boneslot.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    LazyOptional<ItemAttributeCapability> capability1 = stackInSlot.getCapability(ItemAttributeCapabilityProvider.CAPABILITY);
                    if (capability1.isPresent()) {
                        ItemAttributeCapability itemAttributeCapability = capability1.orElseThrow(RuntimeException::new);
                        value += itemAttributeCapability.getMaxshengming();
                    }
                }
            }
            if (playerAttributeCapability.getWuhunList() != null) {
                for (MonsterAttributeCapability monsterAttributeCapability : playerAttributeCapability.getWuhunList()) {
                    value += monsterAttributeCapability.getMaxshengming() * 0.1;
                }
            }
        }
        return value;
    }

    static float getWugong(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getWugong();
            ItemStackHandler boneslot = playerAttributeCapability.getBoneslot();
            for (int i = 0; i < boneslot.getSlots(); i++) {
                ItemStack stackInSlot = boneslot.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    LazyOptional<ItemAttributeCapability> capability1 = stackInSlot.getCapability(ItemAttributeCapabilityProvider.CAPABILITY);
                    if (capability1.isPresent()) {
                        ItemAttributeCapability itemAttributeCapability = capability1.orElseThrow(RuntimeException::new);
                        value += itemAttributeCapability.getWugong();
                    }
                }
            }
            if (playerAttributeCapability.getWuhunList() != null) {
                for (MonsterAttributeCapability monsterAttributeCapability : playerAttributeCapability.getWuhunList()) {
                    value += monsterAttributeCapability.getWugong() * 0.1;
                }
            }
        }

        ItemStack offhandItem = player.getOffhandItem();
        if(offhandItem.getItem() instanceof WuqiAttribute wuqiAttribute && offhandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,offhandItem)){
            value = wuqiAttribute.getWugong(player,offhandItem,value, EquipmentSlot.OFFHAND);
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if(mainHandItem.getItem() instanceof WuqiAttribute wuqiAttribute && mainHandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,mainHandItem)){
            value = wuqiAttribute.getWugong(player,mainHandItem,value, EquipmentSlot.MAINHAND);
        }


        return value;
    }

    static float getWufang(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getWufang();
            ItemStackHandler boneslot = playerAttributeCapability.getBoneslot();
            for (int i = 0; i < boneslot.getSlots(); i++) {
                ItemStack stackInSlot = boneslot.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    LazyOptional<ItemAttributeCapability> capability1 = stackInSlot.getCapability(ItemAttributeCapabilityProvider.CAPABILITY);
                    if (capability1.isPresent()) {
                        ItemAttributeCapability itemAttributeCapability = capability1.orElseThrow(RuntimeException::new);
                        value += itemAttributeCapability.getWufang();
                    }
                }
            }
            if (playerAttributeCapability.getWuhunList() != null) {
                for (MonsterAttributeCapability monsterAttributeCapability : playerAttributeCapability.getWuhunList()) {
                    value += monsterAttributeCapability.getWufang() * 0.1;
                }
            }
        }



        ItemStack offhandItem = player.getOffhandItem();
        if(offhandItem.getItem() instanceof WuqiAttribute wuqiAttribute && offhandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,offhandItem)){
            value = wuqiAttribute.getWufang(player,offhandItem,value, EquipmentSlot.OFFHAND);
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if(mainHandItem.getItem() instanceof WuqiAttribute wuqiAttribute && mainHandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,mainHandItem)){
            value = wuqiAttribute.getWufang(player,mainHandItem,value, EquipmentSlot.MAINHAND);
        }

        return value;
    }

    static float getBaojishanghai(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getBaojishanghai();
            ItemStackHandler boneslot = playerAttributeCapability.getBoneslot();
            for (int i = 0; i < boneslot.getSlots(); i++) {
                ItemStack stackInSlot = boneslot.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    LazyOptional<ItemAttributeCapability> capability1 = stackInSlot.getCapability(ItemAttributeCapabilityProvider.CAPABILITY);
                    if (capability1.isPresent()) {
                        ItemAttributeCapability itemAttributeCapability = capability1.orElseThrow(RuntimeException::new);
                        value += itemAttributeCapability.getBaojishanghai();
                    }
                }
            }
        }


        ItemStack offhandItem = player.getOffhandItem();
        if(offhandItem.getItem() instanceof WuqiAttribute wuqiAttribute && offhandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,offhandItem)){
            value = wuqiAttribute.getBaojishanghai(player,offhandItem,value, EquipmentSlot.OFFHAND);
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if(mainHandItem.getItem() instanceof WuqiAttribute wuqiAttribute && mainHandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,mainHandItem)){
            value = wuqiAttribute.getBaojishanghai(player,mainHandItem,value, EquipmentSlot.MAINHAND);
        }
        return value;
    }

    static float getBaojilv(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getBaojilv();
            ItemStackHandler boneslot = playerAttributeCapability.getBoneslot();
            for (int i = 0; i < boneslot.getSlots(); i++) {
                ItemStack stackInSlot = boneslot.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    LazyOptional<ItemAttributeCapability> capability1 = stackInSlot.getCapability(ItemAttributeCapabilityProvider.CAPABILITY);
                    if (capability1.isPresent()) {
                        ItemAttributeCapability itemAttributeCapability = capability1.orElseThrow(RuntimeException::new);
                        value += itemAttributeCapability.getBaojilv();
                    }
                }
            }
        }
        ItemStack offhandItem = player.getOffhandItem();
        if(offhandItem.getItem() instanceof WuqiAttribute wuqiAttribute && offhandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,offhandItem)){
            value = wuqiAttribute.getBaojilv(player,offhandItem,value, EquipmentSlot.OFFHAND);
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if(mainHandItem.getItem() instanceof WuqiAttribute wuqiAttribute && mainHandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,mainHandItem)){
            value = wuqiAttribute.getBaojilv(player,mainHandItem,value, EquipmentSlot.MAINHAND);
        }
        return value;
    }

    static float getZhenshang(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getZhenshang();
            ItemStackHandler boneslot = playerAttributeCapability.getBoneslot();
            for (int i = 0; i < boneslot.getSlots(); i++) {
                ItemStack stackInSlot = boneslot.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    LazyOptional<ItemAttributeCapability> capability1 = stackInSlot.getCapability(ItemAttributeCapabilityProvider.CAPABILITY);
                    if (capability1.isPresent()) {
                        ItemAttributeCapability itemAttributeCapability = capability1.orElseThrow(RuntimeException::new);
                        value += itemAttributeCapability.getZhenshang();
                    }
                }
            }
        }
        ItemStack offhandItem = player.getOffhandItem();
        if(offhandItem.getItem() instanceof WuqiAttribute wuqiAttribute && offhandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,offhandItem)){
            value = wuqiAttribute.getZhenshang(player,offhandItem,value, EquipmentSlot.OFFHAND);
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if(mainHandItem.getItem() instanceof WuqiAttribute wuqiAttribute && mainHandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,mainHandItem)){
            value = wuqiAttribute.getZhenshang(player,mainHandItem,value, EquipmentSlot.MAINHAND);
        }
        return value;
    }

    static float getWuchuan(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getWuchuan();
            ItemStackHandler boneslot = playerAttributeCapability.getBoneslot();
            for (int i = 0; i < boneslot.getSlots(); i++) {
                ItemStack stackInSlot = boneslot.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    LazyOptional<ItemAttributeCapability> capability1 = stackInSlot.getCapability(ItemAttributeCapabilityProvider.CAPABILITY);
                    if (capability1.isPresent()) {
                        ItemAttributeCapability itemAttributeCapability = capability1.orElseThrow(RuntimeException::new);
                        value += itemAttributeCapability.getWuchuan();
                    }
                }
            }
        }
        ItemStack offhandItem = player.getOffhandItem();
        if(offhandItem.getItem() instanceof WuqiAttribute wuqiAttribute && offhandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,offhandItem)){
            value = wuqiAttribute.getWuchuan(player,offhandItem,value, EquipmentSlot.OFFHAND);
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if(mainHandItem.getItem() instanceof WuqiAttribute wuqiAttribute && mainHandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,mainHandItem)){
            value = wuqiAttribute.getWuchuan(player,mainHandItem,value, EquipmentSlot.MAINHAND);
        }
        return value;
    }

    static float getKangbao(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getKangbao();
            ItemStackHandler boneslot = playerAttributeCapability.getBoneslot();
            for (int i = 0; i < boneslot.getSlots(); i++) {
                ItemStack stackInSlot = boneslot.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    LazyOptional<ItemAttributeCapability> capability1 = stackInSlot.getCapability(ItemAttributeCapabilityProvider.CAPABILITY);
                    if (capability1.isPresent()) {
                        ItemAttributeCapability itemAttributeCapability = capability1.orElseThrow(RuntimeException::new);
                        value += itemAttributeCapability.getKangbao();
                    }
                }
            }
        }
        ItemStack offhandItem = player.getOffhandItem();
        if(offhandItem.getItem() instanceof WuqiAttribute wuqiAttribute && offhandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,offhandItem)){
            value = wuqiAttribute.getKangbao(player,offhandItem,value, EquipmentSlot.OFFHAND);
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if(mainHandItem.getItem() instanceof WuqiAttribute wuqiAttribute && mainHandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,mainHandItem)){
            value = wuqiAttribute.getKangbao(player,mainHandItem,value, EquipmentSlot.MAINHAND);
        }
        return value;
    }

    static float getXixue(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getXixue();
            ItemStackHandler boneslot = playerAttributeCapability.getBoneslot();
            for (int i = 0; i < boneslot.getSlots(); i++) {
                ItemStack stackInSlot = boneslot.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    LazyOptional<ItemAttributeCapability> capability1 = stackInSlot.getCapability(ItemAttributeCapabilityProvider.CAPABILITY);
                    if (capability1.isPresent()) {
                        ItemAttributeCapability itemAttributeCapability = capability1.orElseThrow(RuntimeException::new);
                        value += itemAttributeCapability.getXixue();
                    }
                }
            }
        }
        ItemStack offhandItem = player.getOffhandItem();
        if(offhandItem.getItem() instanceof WuqiAttribute wuqiAttribute && offhandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,offhandItem)){
            value = wuqiAttribute.getXixue(player,offhandItem,value, EquipmentSlot.OFFHAND);
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if(mainHandItem.getItem() instanceof WuqiAttribute wuqiAttribute && mainHandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,mainHandItem)){
            value = wuqiAttribute.getXixue(player,mainHandItem,value, EquipmentSlot.MAINHAND);
        }
        return value;
    }

    static float getMinghzong(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getMingzhong();
            ItemStackHandler boneslot = playerAttributeCapability.getBoneslot();
            for (int i = 0; i < boneslot.getSlots(); i++) {
                ItemStack stackInSlot = boneslot.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    LazyOptional<ItemAttributeCapability> capability1 = stackInSlot.getCapability(ItemAttributeCapabilityProvider.CAPABILITY);
                    if (capability1.isPresent()) {
                        ItemAttributeCapability itemAttributeCapability = capability1.orElseThrow(RuntimeException::new);
                        value += itemAttributeCapability.getMingzhong();
                    }
                }
            }
        }
        ItemStack offhandItem = player.getOffhandItem();
        if(offhandItem.getItem() instanceof WuqiAttribute wuqiAttribute && offhandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,offhandItem)){
            value = wuqiAttribute.getMinghzong(player,offhandItem,value, EquipmentSlot.OFFHAND);
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if(mainHandItem.getItem() instanceof WuqiAttribute wuqiAttribute && mainHandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,mainHandItem)){
            value = wuqiAttribute.getMinghzong(player,mainHandItem,value, EquipmentSlot.MAINHAND);
        }
        return value;
    }

    static float getShanbi(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getShanbi();
            ItemStackHandler boneslot = playerAttributeCapability.getBoneslot();
            for (int i = 0; i < boneslot.getSlots(); i++) {
                ItemStack stackInSlot = boneslot.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    LazyOptional<ItemAttributeCapability> capability1 = stackInSlot.getCapability(ItemAttributeCapabilityProvider.CAPABILITY);
                    if (capability1.isPresent()) {
                        ItemAttributeCapability itemAttributeCapability = capability1.orElseThrow(RuntimeException::new);
                        value += itemAttributeCapability.getShanbi();
                    }
                }
            }
        }
        ItemStack offhandItem = player.getOffhandItem();
        if(offhandItem.getItem() instanceof WuqiAttribute wuqiAttribute && offhandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,offhandItem)){
            value = wuqiAttribute.getShanbi(player,offhandItem,value, EquipmentSlot.OFFHAND);
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if(mainHandItem.getItem() instanceof WuqiAttribute wuqiAttribute && mainHandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,mainHandItem)){
            value = wuqiAttribute.getShanbi(player,mainHandItem,value, EquipmentSlot.MAINHAND);
        }
        return value;
    }

    static float getShengminghuifu(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getShengminghuifu();
            ItemStackHandler boneslot = playerAttributeCapability.getBoneslot();
            for (int i = 0; i < boneslot.getSlots(); i++) {
                ItemStack stackInSlot = boneslot.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    LazyOptional<ItemAttributeCapability> capability1 = stackInSlot.getCapability(ItemAttributeCapabilityProvider.CAPABILITY);
                    if (capability1.isPresent()) {
                        ItemAttributeCapability itemAttributeCapability = capability1.orElseThrow(RuntimeException::new);
                        value += itemAttributeCapability.getShengminghuifu();
                    }
                }
            }
        }
        ItemStack offhandItem = player.getOffhandItem();
        if(offhandItem.getItem() instanceof WuqiAttribute wuqiAttribute && offhandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,offhandItem)){
            value = wuqiAttribute.getShengminghuifu(player,offhandItem,value, EquipmentSlot.OFFHAND);
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if(mainHandItem.getItem() instanceof WuqiAttribute wuqiAttribute && mainHandItem.getItem() instanceof Jineng jineng && jineng.isBelongToPlayer(player,mainHandItem)){
            value = wuqiAttribute.getShengminghuifu(player,mainHandItem,value, EquipmentSlot.MAINHAND);
        }
        return value;
    }

    static float getMaxJingyan(Player player) {

        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getMaxjingyan();
        }
        return value;
    }


    static float getJingyan(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getJingyan();
        }
        return value;
    }

    static float getDengji(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getDengji();
        }
        return value;
    }

    static float getZhuansheng(Player player) {
        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getZhuanshengshu();
        }
        return value;
    }

    static float getJingshenli(Player player) {

        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getJingshenli();
        }
        return value;
    }

    static float getMaxjingshenli(Player player) {

        float value = 0;
        LazyOptional<PlayerAttributeCapability> capability = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capability.isPresent()) {
            PlayerAttributeCapability playerAttributeCapability = capability.orElseThrow(RuntimeException::new);
            value += playerAttributeCapability.getMaxjingshenli();
        }
        return value;

    }
}
