package hua.huase.shanhaicontinent.event.server;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.CapabilityRegistryHandler;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapability;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerHunHuanAPI;
import hua.huase.shanhaicontinent.entity.hunhuan.HunhuanEntity;
import hua.huase.shanhaicontinent.init.AdvenceInit;
import hua.huase.shanhaicontinent.init.ItemInit;
import hua.huase.shanhaicontinent.init.SHModMobEffectsinit;
import hua.huase.shanhaicontinent.item.DanYaoItem;
import hua.huase.shanhaicontinent.item.guoshi.WuhunGuoshiItem;
import hua.huase.shanhaicontinent.network.SynsAPI;
import hua.huase.shanhaicontinent.potion.PotionAnimation;
import hua.huase.shanhaicontinent.potion.ZhiwuBianhua;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PWSleepingTimeCheckEvent {

    private static Map<UUID,Integer> stageHashMap=new HashMap<>();

    @SubscribeEvent
    public static void onSleepingTimeCheckEvent(SleepingTimeCheckEvent event) {


        Player entityPlayer = event.getEntity();

        stageHashMap.putIfAbsent(entityPlayer.getUUID(), 0);
        Integer integer = stageHashMap.get(entityPlayer.getUUID());

        if(integer == 100 && entityPlayer instanceof ServerPlayer serverPlayer ){
            zhuansheng(serverPlayer);
        }

        stageHashMap.put(entityPlayer.getUUID(),++integer);

    }

    private static void zhuansheng(ServerPlayer entityPlayer) {
        entityPlayer.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            if(capability.getJingshenli()<=5000 && capability.getDengji() <=99)return;
            if(!entityPlayer.hasEffect(SHModMobEffectsinit.zhiwu_bianhua.get())) return;
            for (String s : capability.getWuhunListsname()) {
                ItemStack itemStack = WuhunGuoshiItem.getWuhunguo(s);
                if(!itemStack.isEmpty()){
                     if (!entityPlayer.addItem(itemStack)) {
                         ItemEntity itementity = entityPlayer.drop(itemStack, false);
                         if (itementity != null) {
                             itementity.setNoPickUpDelay();
                             itementity.setTarget(entityPlayer.getUUID());
                         }
                     }
                }
            }
            AdvenceInit.menghuiwangutrigger.trigger(entityPlayer);
//            清除丹药使用次数
            clearUsedNum(entityPlayer);
            PlayerAttributeCapability newplayerAttributeCapability = new PlayerAttributeCapability();
            PlayerHunHuanAPI.zhuansheng(newplayerAttributeCapability,capability,entityPlayer);
            capability.deserializeNBT(newplayerAttributeCapability.serializeNBT());

            SynsAPI.synsPlayerAttribute(entityPlayer);
        });

    }

    private static void clearUsedNum(ServerPlayer serverPlayer) {

        ServerStatsCounter stats = serverPlayer.getStats();
        for (RegistryObject<Item> itemRegistryObject : ItemInit.DANYAOLIST) {
            if(itemRegistryObject.get() instanceof DanYaoItem danYaoItem && danYaoItem.getMaxused()>0){
                stats.setValue(serverPlayer,Stats.ITEM_USED.get(danYaoItem),0);
            }
        }


    }


    @SubscribeEvent
    public static void onSleepingTimeCheckEvent(PlayerWakeUpEvent event) {
        stageHashMap.remove(event.getEntity().getUUID());
    }


}
