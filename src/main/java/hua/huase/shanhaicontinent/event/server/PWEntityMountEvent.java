package hua.huase.shanhaicontinent.event.server;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapability;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.entity.hunhuan.HunhuanEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PWEntityMountEvent {
    @SubscribeEvent
    public static void entityMountEvent(EntityMountEvent event){
        Entity entityMounting = event.getEntityMounting();
        Entity entityBeingMounted = event.getEntityBeingMounted();
//禁止怪物上船
        entityMounting.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            event.setCanceled(true);
        });

    }
}
