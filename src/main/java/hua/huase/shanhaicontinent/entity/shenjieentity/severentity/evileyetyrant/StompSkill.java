package hua.huase.shanhaicontinent.entity.shenjieentity.severentity.evileyetyrant;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class StompSkill {
    private final Level level;
    private final Vec3 centerPos;
    private final float damage;

    public StompSkill(Level level, Vec3 centerPos, float damage) {
        this.level = level;
        this.centerPos = centerPos;
        this.damage = damage;
    }

    public void execute() {
        if (level.isClientSide) return;

        // 获取8格范围内的所有玩家
        AABB area = new AABB(
                centerPos.x - 8, centerPos.y - 2, centerPos.z - 8,
                centerPos.x + 8, centerPos.y + 2, centerPos.z + 8
        );
        List<Player> players = level.getEntitiesOfClass(Player.class, area);

        for (Player player : players) {
            // 计算距离
            double distance = player.distanceToSqr(centerPos.x, centerPos.y, centerPos.z);

            if (distance <= 64) { // 8^2 = 64
                // 造成伤害
                player.hurt(level.damageSources().mobAttack(null), damage);

                // 击退效果
                Vec3 knockbackDir = player.position().subtract(centerPos).normalize();
                player.push(knockbackDir.x * 0.5, 0.3, knockbackDir.z * 0.5);

                // 播放被击中效果
                if (player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundGameEventPacket(
                            ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0));
                }
            }
        }

        // 播放地面震动效果
        if (level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 20; i++) {
                double angle = level.random.nextDouble() * Math.PI * 2;
                double radius = level.random.nextDouble() * 8;
                double x = centerPos.x + Math.cos(angle) * radius;
                double z = centerPos.z + Math.sin(angle) * radius;

                serverLevel.sendParticles(ParticleTypes.CRIT,
                        x, centerPos.y, z,
                        5, 0, 0.1, 0, 0.1);
            }
        }
    }
}