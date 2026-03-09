package hua.huase.shanhaicontinent.potion.subscriber;

import hua.huase.shanhaicontinent.init.SHModMobEffectsinit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerTickEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) { // 只在 tick 开始时执行
            Player player = event.player;

            if (player.hasEffect(SHModMobEffectsinit.luocha_tianwei.get())) {
                // 定义检测范围：100 格范围，10 格高
                double range = 50.0;
                double height = 10.0;
                AABB area = new AABB(
                        player.getX() - range, player.getY() - height, player.getZ() - range,
                        player.getX() + range, player.getY() + height, player.getZ() + range
                );

                for (Entity entity : player.level().getEntities(player, area)) {
                    if (entity instanceof LivingEntity livingEntity && !(entity instanceof Player)) {
                        // 为生物添加发光效果，持续 2 秒
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0)); // 40 ticks = 2 秒
                    }
                }
            }
        }
    }
}