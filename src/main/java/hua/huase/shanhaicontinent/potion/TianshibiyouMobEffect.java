package hua.huase.shanhaicontinent.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.Map;

public class TianshibiyouMobEffect extends SHBaseMobEffect implements PotionAnimation {
    public TianshibiyouMobEffect() {
        super(MobEffectCategory.BENEFICIAL);
    }

    @Override
    public float getWufang(LivingEntity livingEntity, Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry, float value) {
        return value * 0.70f; // 70% 物理防御
    }

    @Override
    public float getWugong(LivingEntity livingEntity, Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry, float value) {
        return value * 0.20f; // 20% 物理攻击
    }

    @Override
    public float getXixue(LivingEntity livingEntity, Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry, float value) {
        return value * 0.30f; // 10% 吸血
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 如果不需要每一帧都执行逻辑，可以留空
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // 返回 false，避免每一帧都强制执行逻辑
        return false;
    }

    @Override
    public void renderPlayer(RenderPlayerEvent.Post event) {
        // 如果需要渲染玩家效果，可以在这里实现
    }

}