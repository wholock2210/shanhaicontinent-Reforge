package hua.huase.shanhaicontinent.entity.shenjieentity.severentity.demonking;

import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.entity.shenjieentity.MoWuEntity;
import hua.huase.shanhaicontinent.register.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Random;

public class DemonKingSkills {
    private static final Random RANDOM = new Random();
    private final DemonKingEntity demonKing;
    private int skillCooldown = 0;

    public DemonKingSkills(DemonKingEntity demonKing) {
        this.demonKing = demonKing;
    }

    public void tick() {
        if (skillCooldown > 0) {
            skillCooldown--;
            return;
        }

        // 每5秒(100 ticks)释放一个技能
        if (demonKing.tickCount % 100 == 0) {
            int skill = RANDOM.nextInt(5);
            switch (skill) {
                case 0 -> performShadowStrike();
                case 1 -> performHellfireWave();
                case 2 -> performLifeDrain();
                case 3 -> performSummonMinions();
                case 4 -> performDarkVortex();
            }
            skillCooldown = 20; // 短暂冷却防止连续释放
        }
    }

    // 技能1: 暗影突袭 - 瞬间移动到随机玩家身后并攻击
    private void performShadowStrike() {
        demonKing.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            List<Player> players = demonKing.level().getEntitiesOfClass(Player.class,
                    new AABB(demonKing.getX() - 15, demonKing.getY() - 5, demonKing.getZ() - 15,
                            demonKing.getX() + 15, demonKing.getY() + 5, demonKing.getZ() + 15));

            if (!players.isEmpty()) {
                Player target = players.get(RANDOM.nextInt(players.size()));
                // 瞬移到玩家身后
                double angle = Math.toRadians(target.getYRot());
                double dx = -Math.sin(angle) * 2;
                double dz = Math.cos(angle) * 2;
                demonKing.teleportTo(target.getX() + dx, target.getY(), target.getZ() + dz);

                // 攻击
                target.hurt(demonKing.damageSources().mobAttack(demonKing), cap.getWugong() * 0.8f);

                // 特效
                if (!demonKing.level().isClientSide) {
                    ((ServerLevel)demonKing.level()).sendParticles(ParticleTypes.SMOKE,
                            demonKing.getX(), demonKing.getY() + 1, demonKing.getZ(),
                            30, 0.5, 0.5, 0.5, 0.1);
                    demonKing.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 0.8F);
                }
            }
        });
    }

    // 技能2: 地狱火波 - 扇形范围火焰伤害
    private void performHellfireWave() {
        demonKing.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            float damage = cap.getWugong() * 1.2f;
            float range = 8.0f;
            float angleRange = 90.0f;

            // 获取前方扇形范围内的实体
            List<LivingEntity> entities = demonKing.level().getEntitiesOfClass(LivingEntity.class,
                    new AABB(demonKing.getX() - range, demonKing.getY() - 2, demonKing.getZ() - range,
                            demonKing.getX() + range, demonKing.getY() + 3, demonKing.getZ() + range));

            for (LivingEntity entity : entities) {
                if (entity == demonKing) continue;

                // 检查是否在扇形范围内
                double dx = entity.getX() - demonKing.getX();
                double dz = entity.getZ() - demonKing.getZ();
                double angle = Math.toDegrees(Math.atan2(dz, dx)) - demonKing.getYRot();
                angle = (angle + 360) % 360;
                if (angle > 180) angle -= 360;

                if (Math.abs(angle) < angleRange / 2) {
                    entity.hurt(demonKing.damageSources().mobAttack(demonKing), damage);
                    entity.setSecondsOnFire(5);
                }
            }

            // 特效
            if (!demonKing.level().isClientSide) {
                ((ServerLevel)demonKing.level()).sendParticles(ParticleTypes.FLAME,
                        demonKing.getX(), demonKing.getY() + 1, demonKing.getZ(),
                        100, 2, 2, 2, 0.05);
                demonKing.playSound(SoundEvents.BLAZE_SHOOT, 1.5F, 0.8F);
            }
        });
    }

    // 技能3: 生命汲取 - 吸取周围玩家生命值恢复自身
    private void performLifeDrain() {
        demonKing.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            float range = 10.0f;
            float damage = cap.getWugong() * 0.5f;
            float healAmount = 0;

            List<Player> players = demonKing.level().getEntitiesOfClass(Player.class,
                    new AABB(demonKing.getX() - range, demonKing.getY() - 2, demonKing.getZ() - range,
                            demonKing.getX() + range, demonKing.getY() + 3, demonKing.getZ() + range));

            for (Player player : players) {
                if (player.hurt(demonKing.damageSources().magic(), damage)) {
                    healAmount += damage * 0.5f;
                }
            }

            // 恢复生命值，不超过最大值
            if (healAmount > 0) {
                demonKing.heal(Math.min(healAmount, demonKing.getMaxHealth() - demonKing.getHealth()));
            }

            // 特效
            if (!demonKing.level().isClientSide) {
                ((ServerLevel)demonKing.level()).sendParticles(ParticleTypes.SOUL,
                        demonKing.getX(), demonKing.getY() + 1, demonKing.getZ(),
                        50, 1, 1, 1, 0.1);
                demonKing.playSound(SoundEvents.WITHER_SHOOT, 1.0F, 1.2F);
            }
        });
    }

    // 技能4: 召唤仆从 - 召唤3个小恶魔协助战斗
    private void performSummonMinions() {
        demonKing.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            if (!demonKing.level().isClientSide) {
                for (int i = 0; i < 3; i++) {
                    double angle = i * 120;
                    double dx = Math.cos(Math.toRadians(angle)) * 2;
                    double dz = Math.sin(Math.toRadians(angle)) * 2;

//                     这里假设有小恶魔实体类，你需要替换为实际的实体类
                     MoWuEntity minion = new MoWuEntity(ModEntities.MOWU.get(), demonKing.level());
                     minion.moveTo(demonKing.getX() + dx, demonKing.getY(), demonKing.getZ() + dz,
                                  demonKing.getYRot(), demonKing.getXRot());
                     demonKing.level().addFreshEntity(minion);

                    ((ServerLevel)demonKing.level()).sendParticles(ParticleTypes.PORTAL,
                            demonKing.getX() + dx, demonKing.getY(), demonKing.getZ() + dz,
                            20, 0.5, 0.5, 0.5, 0.1);
                }
                demonKing.playSound(SoundEvents.EVOKER_PREPARE_SUMMON, 1.0F, 0.8F);
            }
        });
    }

    // 技能5: 黑暗漩涡 - 将玩家拉向自己并造成伤害
    private void performDarkVortex() {
        demonKing.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            float range = 12.0f;
            float damage = cap.getWugong() * 0.3f;

            List<Player> players = demonKing.level().getEntitiesOfClass(Player.class,
                    new AABB(demonKing.getX() - range, demonKing.getY() - 3, demonKing.getZ() - range,
                            demonKing.getX() + range, demonKing.getY() + 3, demonKing.getZ() + range));

            for (Player player : players) {
                // 计算方向向量
                double dx = demonKing.getX() - player.getX();
                double dy = demonKing.getY() - player.getY();
                double dz = demonKing.getZ() - player.getZ();
                double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                if (distance > 1.5) {
                    // 拉向恶魔王
                    double strength = 0.4 * (1 - distance / range);
                    player.setDeltaMovement(
                            player.getDeltaMovement().add(dx / distance * strength,
                                    dy / distance * strength * 0.5,
                                    dz / distance * strength));
                }

                // 近距离造成伤害
                if (distance < 3) {
                    player.hurt(demonKing.damageSources().mobAttack(demonKing), damage);
                }
            }

            // 特效
            if (!demonKing.level().isClientSide) {
                ((ServerLevel)demonKing.level()).sendParticles(ParticleTypes.SMOKE,
                        demonKing.getX(), demonKing.getY() + 1, demonKing.getZ(),
                        100, 3, 3, 3, 0.05);
                demonKing.playSound(SoundEvents.ENDER_DRAGON_GROWL, 1.0F, 1.5F);
            }
        });
    }
}