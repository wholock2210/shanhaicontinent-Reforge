package hua.huase.shanhaicontinent.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;

public class HaiShenxinyang extends SHBaseMobEffect{
    public HaiShenxinyang() {
        super(MobEffectCategory.BENEFICIAL);
    }

    @Override
    public float getBaojilv(LivingEntity livingEntity, Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry, float value) {
        return value * 0.50f;
    }

    @Override
    public float getBaojishanghai(LivingEntity livingEntity, Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry, float value) {
        return value * 0.50f;
    }

    @Override
    public float getXixue(LivingEntity livingEntity, Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry, float value) {
        return value * 0.30f;
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

}
