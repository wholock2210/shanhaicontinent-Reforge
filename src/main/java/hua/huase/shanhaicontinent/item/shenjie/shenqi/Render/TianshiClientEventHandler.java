package hua.huase.shanhaicontinent.item.shenjie.shenqi.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "shanhaicontinent", value = Dist.CLIENT)
public class TianshiClientEventHandler {
    private static boolean isSkillActive = false;
    private static Vec3 skillCenter;
    private static double skillRadius;
    private static int skillDuration;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && isSkillActive) {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                // 生成金色粒子效果
                spawnGoldenParticles(level, skillCenter, skillRadius);

                // 减少技能持续时间
                skillDuration--;
                if (skillDuration <= 0) {
                    isSkillActive = false; // 技能持续时间结束
                }
            }
        }
    }

    public static void startSkillEffect(Vec3 center, double radius, int duration) {
        isSkillActive = true;
        skillCenter = center;
        skillRadius = radius;
        skillDuration = duration;
    }

    private static void spawnGoldenParticles(Level level, Vec3 center, double radius) {
        // 粒子类型为金色粒子（ParticleTypes.GLOW）
        ParticleOptions particle = ParticleTypes.GLOW;

        // 生成球形范围的粒子
        int particleCount = 300; // 粒子数量
        for (int i = 0; i < particleCount; i++) {
            // 随机生成粒子位置
            double theta = level.random.nextDouble() * Math.PI * 2; // 随机角度
            double phi = level.random.nextDouble() * Math.PI; // 随机仰角
            double x = center.x + radius * Math.sin(phi) * Math.cos(theta);
            double y = center.y + radius * Math.cos(phi);
            double z = center.z + radius * Math.sin(phi) * Math.sin(theta);

            // 生成金色粒子
            level.addParticle(particle, x, y, z, 0.0, 0.0, 0.0);
        }
    }
}