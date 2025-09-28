package hua.huase.shanhaicontinent.entity.shenjieentity.severentity.frostprison;

import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.init.SHModMobEffectsinit;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import java.util.List;
import java.util.Random;

public class FrostPrisonSkills {
    private final FrostPrisonEntity frostPrison;
    private final Random random = new Random();
    private int skillCooldown = 0;
    private int fieldCooldown = 0;

    public FrostPrisonSkills(FrostPrisonEntity frostPrison) {
        this.frostPrison = frostPrison;
    }

    public void tick() {
        if (skillCooldown > 0) skillCooldown--;
        if (fieldCooldown > 0) fieldCooldown--;

        if (frostPrison.getTarget() != null) {
            if (frostPrison.tickCount % 100 == 0 && skillCooldown <= 0) {
                frostPrison.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
                    float multiplier = getRandomMultiplier();
                    executeSkill(random.nextInt(8), cap.getWugong(), multiplier);
                });
                skillCooldown = 20;
            }

            if (frostPrison.tickCount % 200 == 0 && fieldCooldown <= 0 && random.nextFloat() < 0.5f) {
                executeField();
                fieldCooldown = 40;
            }
        }
    }

    private float getRandomMultiplier() {
        float[] multipliers = {1.2f, 1.5f, 1.8f, 2.0f};
        return multipliers[random.nextInt(multipliers.length)];
    }

    private void executeSkill(int skill, float wugong, float multiplier) {
        float damage = wugong * multiplier;

        switch (skill) {
            case 0 -> groundStomp(damage);         // 地面踩踏
            case 1 -> whirlwindSlash(damage);     // 旋风斩
            case 2 -> shockwave(damage);           // 冲击波(带FROST_BINDING)
            case 3 -> powerCleave(damage);        // 强力劈砍
            case 4 -> crushingBlow(damage);       // 粉碎打击(带FROST_BINDING)
            case 5 -> leapingSmash(damage);       // 跳跃重击
            case 6 -> furiousCharge(damage);      // 狂暴冲锋
            case 7 -> devastatingRoar(damage);    // 毁灭咆哮
        }
    }

    // ========== 领域技能 ==========
    private void executeField() {
        frostPrison.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            AABB area = new AABB(
                    frostPrison.getX() - 30, frostPrison.getY() - 5, frostPrison.getZ() - 30,
                    frostPrison.getX() + 30, frostPrison.getY() + 5, frostPrison.getZ() + 30
            );

            List<Player> players = frostPrison.level().getEntitiesOfClass(Player.class, area);
            for (Player player : players) {
                addEffectIfNotPresent(player, SHModMobEffectsinit.FROST_BINDING.get(), 160, 0);
            }

            // 特效和音效
            spawnParticles(ParticleTypes.SNOWFLAKE, 500, 15, 3, 15, 0.1);
            playSound(SoundEvents.POWDER_SNOW_BREAK, 1.5f, 0.8f);
        });
    }

    // ========== 8个物理技能实现 ==========

    // 技能1: 地面踩踏 - 圆形范围伤害
    private void groundStomp(float damage) {
        AABB area = getAreaAround(5, 1, 2);
        affectEntitiesInArea(area, damage * 2.0f, MobEffects.MOVEMENT_SLOWDOWN, 60, 1);

        // 震动效果
        spawnParticles(ParticleTypes.CLOUD, 100, 3, 0, 3, 0.2);
        playSound(SoundEvents.GENERIC_EXPLODE, 1.0f, 0.5f);
    }

    // 技能2: 旋风斩 - 旋转攻击周围敌人
    private void whirlwindSlash(float damage) {
        frostPrison.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            AABB area = getAreaAround(4, 1, 2);
            List<LivingEntity> entities = getEntitiesInArea(area);

            for (LivingEntity entity : entities) {
                if (entity != frostPrison) {
                    entity.hurt(frostPrison.damageSources().mobAttack(frostPrison), damage * 1.3f);
                    entity.knockback(0.8f,
                            entity.getX() - frostPrison.getX(),
                            entity.getZ() - frostPrison.getZ());
                }
            }

            // 旋转特效
            for (int i = 0; i < 8; i++) {
                double angle = i * Math.PI / 4;
                double x = frostPrison.getX() + Math.sin(angle) * 3;
                double z = frostPrison.getZ() + Math.cos(angle) * 3;
                spawnParticlesAt(x, frostPrison.getY(), z, ParticleTypes.SWEEP_ATTACK, 1);
            }
            playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.2f, 0.8f);
        });
    }

    // 技能3: 冲击波 (带FROST_BINDING)
    private void shockwave(float damage) {
        AABB area = getAreaAround(8, 0, 2);
        affectEntitiesInArea(area, damage * 1.5f, SHModMobEffectsinit.FROST_BINDING.get(), 60, 0);

        // 冲击波特效
        for (double d = 1; d <= 8; d += 0.5) {
            spawnParticlesAt(
                    frostPrison.getX() + frostPrison.getLookAngle().x * d,
                    frostPrison.getY(),
                    frostPrison.getZ() + frostPrison.getLookAngle().z * d,
                    ParticleTypes.CRIT, 2
            );
        }
        playSound(SoundEvents.PLAYER_ATTACK_CRIT, 1.5f, 0.7f);
    }

    // 技能4: 强力劈砍 - 前方扇形范围高伤害
    private void powerCleave(float damage) {
        frostPrison.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            AABB area = calculateConeArea(6, 60);
            List<LivingEntity> entities = getEntitiesInArea(area);

            for (LivingEntity entity : entities) {
                entity.hurt(frostPrison.damageSources().mobAttack(frostPrison), damage * 1.5f);
            }

            // 劈砍特效
            spawnParticles(ParticleTypes.SWEEP_ATTACK, 30, 2, 1, 2, 0.1);
            playSound(SoundEvents.PLAYER_ATTACK_STRONG, 1.3f, 0.6f);
        });
    }

    // 技能5: 粉碎打击 (带FROST_BINDING)
    private void crushingBlow(float damage) {
        frostPrison.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            LivingEntity target = frostPrison.getTarget();
            if (target != null && target.distanceToSqr(frostPrison) < 16) {
                target.hurt(frostPrison.damageSources().mobAttack(frostPrison), damage * 2.0f);

                addEffectIfNotPresent(target, SHModMobEffectsinit.FROST_BINDING.get(), 60, 0);

                // 重击特效
                spawnParticlesAt(target.getX(), target.getY(), target.getZ(),
                        ParticleTypes.CRIT, 20);
                playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 1.5f, 0.5f);
            }
        });
    }

    // 技能6: 跳跃重击 - 跳起后落地造成范围伤害
    private void leapingSmash(float damage) {
        frostPrison.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            // 模拟跳跃
            frostPrison.setDeltaMovement(frostPrison.getDeltaMovement().add(0, 0.5, 0));

            // 延迟执行落地效果
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if (frostPrison.isAlive()) {
                                AABB area = getAreaAround(4, 0, 2);
                                affectEntitiesInArea(area, damage * 3.0f, SHModMobEffectsinit.FROST_BINDING.get(), 60, 0);

                                spawnParticles(ParticleTypes.EXPLOSION, 50, 2, 0, 2, 0.1);
                                playSound(SoundEvents.GENERIC_EXPLODE, 1.2f, 0.6f);
                            }
                        }
                    },
                    800 // 0.8秒后执行
            );
        });
    }

    // 技能7: 狂暴冲锋 - 向目标冲锋
    private void furiousCharge(float damage) {
        frostPrison.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            LivingEntity target = frostPrison.getTarget();
            if (target != null && target.distanceToSqr(frostPrison) < 100) {
                // 计算冲锋方向
                double dx = target.getX() - frostPrison.getX();
                double dz = target.getZ() - frostPrison.getZ();
                double distance = Math.sqrt(dx * dx + dz * dz);

                // 设置冲锋速度
                frostPrison.setDeltaMovement(
                        dx / distance * 2.0,
                        0.2,
                        dz / distance * 2.0
                );

                // 冲锋路径上的伤害
                AABB path = new AABB(
                        frostPrison.getX(), frostPrison.getY(), frostPrison.getZ(),
                        target.getX(), target.getY(), target.getZ()
                ).inflate(1.5);

                List<LivingEntity> entities = getEntitiesInArea(path);
                for (LivingEntity entity : entities) {
                    if (entity != frostPrison) {
                        entity.hurt(frostPrison.damageSources().mobAttack(frostPrison), damage * 1.6f);
                        entity.knockback(1.2f, dx, dz);
                    }
                }

                // 冲锋特效
                spawnParticles(ParticleTypes.CLOUD, 100, 0.5, 0.5, 0.5, 0.1);
                playSound(SoundEvents.RAVAGER_ROAR, 1.0f, 0.8f);
            }
        });
    }

    // 技能8: 毁灭咆哮 - 大范围震慑效果
    private void devastatingRoar(float damage) {
        AABB area = getAreaAround(10, 3, 3);
        affectEntitiesInArea(area, damage * 2.0f, MobEffects.WEAKNESS, 60, 1);

        // 咆哮特效
        spawnParticles(ParticleTypes.NOTE, 200, 5, 2, 5, 0.2);
        playSound(SoundEvents.ENDER_DRAGON_GROWL, 1.5f, 0.7f);
    }

    // ========== 辅助方法 ==========
    private AABB getAreaAround(double xz, double yMin, double yMax) {
        return new AABB(
                frostPrison.getX() - xz, frostPrison.getY() - yMin, frostPrison.getZ() - xz,
                frostPrison.getX() + xz, frostPrison.getY() + yMax, frostPrison.getZ() + xz
        );
    }

    private AABB calculateConeArea(double range, double angleDegrees) {
        // 计算前方扇形区域
        double angle = Math.toRadians(frostPrison.getYRot());
        double halfAngle = Math.toRadians(angleDegrees) / 2;

        return new AABB(
                frostPrison.getX(), frostPrison.getY(), frostPrison.getZ(),
                frostPrison.getX() + Math.sin(angle) * range,
                frostPrison.getY() + 2,
                frostPrison.getZ() + Math.cos(angle) * range
        ).inflate(range * Math.sin(halfAngle), 1, range * Math.sin(halfAngle));
    }

    private List<LivingEntity> getEntitiesInArea(AABB area) {
        return frostPrison.level().getEntitiesOfClass(LivingEntity.class, area);
    }

    private void affectEntitiesInArea(AABB area, float damage, MobEffect effect, int duration, int amplifier) {
        List<LivingEntity> entities = getEntitiesInArea(area);
        for (LivingEntity entity : entities) {
            if (entity != frostPrison) {
                entity.hurt(frostPrison.damageSources().mobAttack(frostPrison), damage);
                if (effect != null) {
                    addEffectIfNotPresent(entity, effect, duration, amplifier);
                }
            }
        }
    }

    private void addEffectIfNotPresent(LivingEntity entity, MobEffect effect, int duration, int amplifier) {
        MobEffectInstance existing = entity.getEffect(effect);
        if(existing == null || existing.getDuration() <= 80) { // 只剩余4 ticks时才会刷新
            entity.addEffect(new MobEffectInstance(
                    effect,
                    duration,
                    amplifier,
                    false,
                    true
            ));
        }
    }

    private void spawnParticles(ParticleOptions particle, int count,
                                double xzSpread, double ySpread, double speed, double offset) {
        if (!frostPrison.level().isClientSide()) {
            ServerLevel level = (ServerLevel)frostPrison.level();
            level.sendParticles(particle,
                    frostPrison.getX(), frostPrison.getY() + 1, frostPrison.getZ(),
                    count, xzSpread, ySpread, xzSpread, speed);
        }
    }

    private void spawnParticlesAt(double x, double y, double z,
                                  ParticleOptions particle, int count) {
        if (!frostPrison.level().isClientSide()) {
            ServerLevel level = (ServerLevel)frostPrison.level();
            level.sendParticles(particle, x, y, z, count, 0, 0, 0, 0);
        }
    }

    private void playSound(SoundEvent sound, float volume, float pitch) {
        frostPrison.playSound(sound, volume, pitch);
    }
}