package hua.huase.shanhaicontinent.item.shenjie.shenqi.DropHandler;

import hua.huase.shanhaicontinent.item.shenjie.TianShiShen;
import hua.huase.shanhaicontinent.item.shenjie.luochashen;
import hua.huase.shanhaicontinent.item.shenjie.shenqi.Molian;
import hua.huase.shanhaicontinent.register.ModEntities;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "shanhaicontinent")
public class LuoChaShenDropHandler {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity().level() instanceof ServerLevel)) return;

        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack offhandItem = player.getOffhandItem();

            if (offhandItem.getItem() instanceof luochashen) { // 假设LuoChaShen是类名
                LivingEntity killedEntity = event.getEntity();
                float dropChance = getDropChanceForEntity(killedEntity);

                if (player.getRandom().nextFloat() < dropChance) {
                    ItemStack fragment = new ItemStack(ModItems.luocha_suipian.get());
                    killedEntity.spawnAtLocation(fragment);
                }
            }
        }
    }
    private static float getDropChanceForEntity(LivingEntity entity) {
        if (entity.getType() == EntityType.ENDERMAN) {
            return 0.001f; // 末影人0.1%
        }
        else if (entity.getType() == ModEntities.MOWU.get()) {
            return 0.009f; // mod生物0.9%
        }
        else {
            return 0.007f; // 其他0.7%
        }
    }
}