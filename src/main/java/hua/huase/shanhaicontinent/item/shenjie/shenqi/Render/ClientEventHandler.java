package hua.huase.shanhaicontinent.item.shenjie.shenqi.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "shanhaicontinent", value = Dist.CLIENT)
public class ClientEventHandler {
    private static boolean isRippling = false;
    private static Vec3 center;
    private static double radius;
    private static final double maxRadius = 20.0;
    private static final double speed = 0.5;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && isRippling) {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                // 生成当前半径的粒子
                int particleCount = 100;
                for (int i = 0; i < particleCount; i++) {
                    double angle = 2 * Math.PI * i / particleCount;
                    double x = center.x + radius * Math.cos(angle);
                    double y = center.y;
                    double z = center.z + radius * Math.sin(angle);
                    level.addParticle(ParticleTypes.DRAGON_BREATH, x, y, z, 0.0, 0.0, 0.0);
                }

                // 增加半径
                radius += speed;

                // 如果达到最大半径，停止扩散
                if (radius > maxRadius) {
                    isRippling = false;
                }
            }
        }
    }

    public static void startRipple(Vec3 centerPos) {
        // 如果已经有扩散圈在运行，先停止它
        isRippling = false;

        // 启动新的扩散圈
        isRippling = true;
        center = centerPos;
        radius = 0.0;
    }
}