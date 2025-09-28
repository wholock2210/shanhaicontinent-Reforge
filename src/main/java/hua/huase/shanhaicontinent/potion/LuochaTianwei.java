package hua.huase.shanhaicontinent.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;

public class LuochaTianwei extends SHBaseMobEffect{
    public LuochaTianwei() {
        super(MobEffectCategory.BENEFICIAL);
    }

    @Override
    public float getWugong(LivingEntity livingEntity, Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry, float value) {
        return value * 0.30f;
    }

    @Override
    public float getShanbi(LivingEntity livingEntity, Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry, float value) {
        return value * 0.10f;
    }

    @Override
    public float getZhenshang(LivingEntity livingEntity, Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry, float value) {
        return value * 0.10f;
    }

    @Override
    public float getBaojishanghai(LivingEntity livingEntity, Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry, float value) {
        return value * 0.3f;
    }

    @Override
    public float getMinghzong(LivingEntity livingEntity, Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry, float value) {
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
