package hua.huase.shanhaicontinent.item.shenjie.wupin;

import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapability;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerHunHuanAPI;
import hua.huase.shanhaicontinent.init.AdvenceInit;
import hua.huase.shanhaicontinent.init.ItemInit;
import hua.huase.shanhaicontinent.item.DanYaoItem;
import hua.huase.shanhaicontinent.item.guoshi.WuhunGuoshiItem;
import hua.huase.shanhaicontinent.network.SynsAPI;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

public class ReincarnationCapsuleItem extends Item {
    public ReincarnationCapsuleItem(Properties properties) {
        super(properties.food(
                new FoodProperties.Builder()
                        .nutrition(0)
                        .saturationMod(0)
                        .alwaysEat()
                        .build()
        ));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer player) {
            // 启动10秒转生倒计时 (200 ticks)
            player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
                cap.setReincarnationTimer(200); // 设置倒计时
                cap.setReincarnationInterrupted(false); // 重置中断标志

                // 添加视觉特效（发光）
                player.addEffect(new MobEffectInstance(
                        MobEffects.GLOWING, 200, 0, false, false, true));

                player.sendSystemMessage(Component.literal("转生开始！10秒后完成转生，玩家死亡会导致转生中断。").withStyle(ChatFormatting.GOLD));
            });
        }
        return super.finishUsingItem(stack, level, entity);
    }

    public static void checkReincarnationProgress(Player player) {
        if (player.level().isClientSide) return;
        LazyOptional<PlayerAttributeCapability> capOpt = player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY);
        if (capOpt.isPresent()) {
            PlayerAttributeCapability cap = capOpt.orElseThrow(null);
            int timer = cap.getReincarnationTimer();
            if (timer > 0) {
                cap.setReincarnationTimer(timer - 1);
                if (timer == 1 && !cap.isReincarnationInterrupted()) {
                    completeReincarnation((ServerPlayer) player, cap);
                }
            }
        }
    }

    public static void cancelReincarnation(Player player) {
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            if (cap.getReincarnationTimer() > 0) {
                cap.setReincarnationInterrupted(true);
                player.sendSystemMessage(Component.literal("转生中断！").withStyle(ChatFormatting.RED));
                player.removeEffect(MobEffects.GLOWING);
            }
        });
    }

    private static void completeReincarnation(ServerPlayer player, PlayerAttributeCapability oldCap) {
        for (String wuhunName : oldCap.getWuhunListsname()) {
            ItemStack fruit = WuhunGuoshiItem.getWuhunguo(wuhunName);
            if (!fruit.isEmpty() && !player.addItem(fruit)) {
                ItemEntity entity = player.drop(fruit, false);
                if (entity != null) {
                    entity.setNoPickUpDelay();
                    entity.setTarget(player.getUUID());
                }
            }
        }

        AdvenceInit.menghuiwangutrigger.trigger(player);

        clearDanyaoUsage(player);

        addReincarnationDebuffs(player);

        // 执行转生
        PlayerAttributeCapability newCap = new PlayerAttributeCapability();
        PlayerHunHuanAPI.zhuansheng(newCap, oldCap, player);
        oldCap.deserializeNBT(newCap.serializeNBT());
        SynsAPI.synsPlayerAttribute(player);
        player.removeEffect(MobEffects.GLOWING);
        player.sendSystemMessage(Component.literal("转生成功！").withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    private static void addReincarnationDebuffs(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2, false, true, true));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2, false, true, true));
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 2, false, true, true));
    }


    private static void clearDanyaoUsage(ServerPlayer player) {
        ServerStatsCounter stats = player.getStats();
        for (RegistryObject<Item> itemRegistryObject : ItemInit.DANYAOLIST) {
            if (itemRegistryObject.get() instanceof DanYaoItem danYaoItem && danYaoItem.getMaxused() > 0) {
                stats.setValue(player, Stats.ITEM_USED.get(danYaoItem), 0);
            }
        }
    }
}