package hua.huase.shanhaicontinent.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;

public class ShixueMobEffect extends SHBaseMobEffect {
    public ShixueMobEffect() {
        super(MobEffectCategory.BENEFICIAL);
    }

    @Override
    public float getWugong(LivingEntity livingEntity, Map.Entry<MobEffect, MobEffectInstance> mobEffectMobEffectInstanceEntry, float value) {
        // 提升 wugong 属性，根据 Buff 等级计算
        int amplifier = mobEffectMobEffectInstanceEntry.getValue().getAmplifier();
        return value * (1 + 0.03f * amplifier); // 每级增加 10%
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // 每 tick 执行逻辑（如果需要）
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // 返回 false，避免每一帧都强制执行逻辑
        return false;
    }
}