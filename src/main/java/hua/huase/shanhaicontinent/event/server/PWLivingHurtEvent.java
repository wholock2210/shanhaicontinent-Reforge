package hua.huase.shanhaicontinent.event.server;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.AttrubuteAPI;
import hua.huase.shanhaicontinent.init.SHModMobEffectsinit;
import hua.huase.shanhaicontinent.potion.Jineng_huang_6;
import hua.huase.shanhaicontinent.potion.PotionAttribute;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

import static hua.huase.shanhaicontinent.SHMainBus.random;

@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PWLivingHurtEvent {
    @SubscribeEvent
    public static void livingHurtEvent(LivingHurtEvent event){
        LivingEntity directEntity = event.getEntity();
        if(directEntity == null)return;
        float amount = event.getAmount();
        DamageSource source = event.getSource();
        Entity causingEntity = source.getEntity();
        float v = huiProcessing(directEntity, causingEntity, amount);
        event.setAmount(v);


    }
    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent event){
        displayerDamage(event.getEntity(),event);
    }

    private static void displayerDamage(LivingEntity livingEntity, LivingDamageEvent event) {

        if(livingEntity.getHealth()-event.getAmount()<=0){
            Map<MobEffect, MobEffectInstance> activeEffectsMap = livingEntity.getActiveEffectsMap();
//            防止java.util.ConcurrentModificationException
            Boolean b = false;
            for (Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry : activeEffectsMap.entrySet()) {
                if(mobEffectMobEffectInstanceEntry.getKey() instanceof Jineng_huang_6 potionAttribute){
                    potionAttribute.chufaEvent(livingEntity,event);
                    b=true;
                }
            }
            if(b){
                livingEntity.removeEffect(SHModMobEffectsinit.jineng_huang_6.get());
            }
        }
    }

    public static float huiProcessing(Entity directEntity,Entity causingEntity,float amount){
//处理闪避
        float v1 = Math.max(AttrubuteAPI.getShanbi(directEntity) - AttrubuteAPI.getMinghzong(causingEntity), 0f) / (Math.max(AttrubuteAPI.getShanbi(directEntity) - AttrubuteAPI.getMinghzong(causingEntity), 0f) + 100f);
        if((float)random.nextInt(100)/100f<=v1){
            return 0f;
        }

//处理减伤
        float jianshang =1- Math.max(AttrubuteAPI.getWufang(directEntity) - AttrubuteAPI.getWuchuan(causingEntity), 0f)/(Math.max(AttrubuteAPI.getWufang(directEntity) - AttrubuteAPI.getWuchuan(causingEntity), 0f)+500f);
//处理暴击概率
        boolean b = random.nextInt(100) <= AttrubuteAPI.getBaojilv(causingEntity);

        float v = b? (AttrubuteAPI.getWugong(causingEntity)*jianshang + AttrubuteAPI.getZhenshang(causingEntity)+amount*jianshang) * Math.max((AttrubuteAPI.getBaojishanghai(causingEntity)-AttrubuteAPI.getKangbao(directEntity)+100f)/100f,1f):(AttrubuteAPI.getWugong(causingEntity)*jianshang + AttrubuteAPI.getZhenshang(causingEntity)+amount);

// 处理吸血
        if(causingEntity != null &&causingEntity instanceof LivingEntity livingEntity &&!livingEntity.isDeadOrDying())
            livingEntity.setHealth(AttrubuteAPI.getXixue(causingEntity)/(AttrubuteAPI.getXixue(causingEntity)+100)*v+livingEntity.getHealth());
        return v;

    }

}
