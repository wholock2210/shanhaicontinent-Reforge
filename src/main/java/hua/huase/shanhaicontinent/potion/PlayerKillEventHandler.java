package hua.huase.shanhaicontinent.potion;

import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.init.SHModMobEffectsinit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerKillEventHandler {
    @SubscribeEvent
    public static void onPlayerKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            MobEffectInstance shixueBuff = player.getEffect(SHModMobEffectsinit.xiuluo_shixue.get());
            if (shixueBuff != null) {
                int newLevel = shixueBuff.getAmplifier() + 1;
                player.addEffect(new MobEffectInstance(SHModMobEffectsinit.xiuluo_shixue.get(), shixueBuff.getDuration(), newLevel));
                float currentWugong = PlayerAttrubuteAPI.getWugong(player);
                float newWugong = currentWugong * 1.03f;
                CompoundTag nbt = player.getPersistentData();
                nbt.putFloat("shixue_wugong_bonus", newWugong - currentWugong);
            }
        }
    }
}