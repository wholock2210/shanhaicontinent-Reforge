package hua.huase.shanhaicontinent.entity.shenjieentity.jineng;

import hua.huase.shanhaicontinent.entity.shenjieentity.TianFaEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TianFaSkills {
    private final TianFaEntity entity;

    public TianFaSkills(TianFaEntity entity) {
        this.entity = entity;
    }

    // 技能1: 跃击
    public void performLeapAttack() {
        Level level = entity.level();
        if (level.isClientSide) return;
        entity.setDeltaMovement(0, 1.2, 0);
        entity.hurtMarked = true;
        ((ServerLevel) level).getServer().execute(() -> {
            if (entity.isAlive()) {
                // 落地效果
                AABB area = entity.getBoundingBox().inflate(10);
                List<Player> players = level.getEntitiesOfClass(Player.class, area);

                for (Player player : players) {
                    // 造成1.5倍伤害
                    float damage = (float) (entity.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.5);
                    player.hurt(entity.damageSources().mobAttack(entity), damage);
                    // 屏幕晃动效果 - 使用DEMO_EVENT
                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.connection.send(new ClientboundGameEventPacket(
                                ClientboundGameEventPacket.DEMO_EVENT, 2.0f));
                    }
                }

                // 粒子效果
                ((ServerLevel) level).sendParticles(ParticleTypes.EXPLOSION_EMITTER,
                        entity.getX(), entity.getY(), entity.getZ(),
                        20, 3, 0.5, 3, 0.2);
            }
        });
    }


    // 技能2: 吸星大法
    public void performBlackHole() {
        Level level = entity.level();
        if (level.isClientSide) return;

        AABB area = entity.getBoundingBox().inflate(15);
        List<Player> players = level.getEntitiesOfClass(Player.class, area);

        for (Player player : players) {
            // 将玩家拉向实体
            Vec3 direction = entity.position().subtract(player.position()).normalize();
            player.setDeltaMovement(direction.scale(0.8));
            player.hurtMarked = true;

            // 当玩家接近时造成伤害
            if (player.distanceTo(entity) < 3) {
                float damage = (float) (entity.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.6);
                player.hurt(entity.damageSources().mobAttack(entity), damage);
            }
        }

        // 粒子效果
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.PORTAL,
                    entity.getX(), entity.getY() + 1, entity.getZ(),
                    100, 2, 2, 2, 0.1);
        }
    }

    // 技能3: 召唤雷电
    public void performLightningStrike() {
        Level level = entity.level();
        if (level.isClientSide) return;

        AABB area = entity.getBoundingBox().inflate(20);
        List<Player> players = level.getEntitiesOfClass(Player.class, area);

        for (Player player : players) {
            // 在玩家位置召唤闪电
            if (level instanceof ServerLevel serverLevel) {
                LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
                if (lightning != null) {
                    lightning.moveTo(player.getX(), player.getY(), player.getZ());
                    level.addFreshEntity(lightning);
                    // 额外造成2倍伤害
                    float damage = (float) (entity.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2);
                    player.hurt(entity.damageSources().mobAttack(entity), damage);
                }
            }
        }
    }
}