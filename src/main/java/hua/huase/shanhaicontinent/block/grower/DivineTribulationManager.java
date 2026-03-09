package hua.huase.shanhaicontinent.block.grower;

import hua.huase.shanhaicontinent.block.DengXianTai;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapability;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import java.util.*;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.server.level.ServerPlayer;


@Mod.EventBusSubscriber
public class DivineTribulationManager {
    private static final Map<UUID, TribulationData> ACTIVE_PLAYERS = new HashMap<>();

    public static boolean isPlayerInTribulation(Player player) {
        return ACTIVE_PLAYERS.containsKey(player.getUUID());
    }

    public static void startTribulation(Player player, BlockPos altarPos, DengXianTai altarBlock) {
        if (isPlayerInTribulation(player)) return;

        ACTIVE_PLAYERS.put(player.getUUID(), new TribulationData(altarBlock));

    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.getServer() == null) return;

        Iterator<Map.Entry<UUID, TribulationData>> it = ACTIVE_PLAYERS.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, TribulationData> entry = it.next();
            ServerPlayer player = event.getServer().getPlayerList().getPlayer(entry.getKey());

            if (player == null) {
                it.remove();
                continue;
            }

            TribulationData data = entry.getValue();
            data.tickCounter++;

            // 每tick生成粒子效果（跟随玩家）
            spawnParticleCloud(player);

            if (data.tickCounter % 60 == 0 && data.strikeCount < 99) {
                spawnLightning(player);
                data.strikeCount++;

                int progress = Math.round((float)data.strikeCount / 99 * 100);
                player.displayClientMessage(
                        Component.literal(String.format("天雷淬体进度: %d%%", progress))
                                .withStyle(ChatFormatting.YELLOW),
                        true
                );
            }

            if (data.strikeCount >= 99) {
                completeTribulation(player, data);
                it.remove();
            }
        }
    }
    private static void spawnParticleCloud(ServerPlayer player) {
        if (player.level() instanceof ServerLevel level) {
            // 中心点（玩家头顶8格）
            double centerX = player.getX();
            double centerY = player.getY() + 8;
            double centerZ = player.getZ();

            // 水平展开的云团参数
            int cloudRadius = 14;  // 水平半径
            float cloudHeight = 3f; // 垂直高度范围
            int baseParticles = 70; // 粒子总数
            RandomSource random = player.getRandom();

            // 生成水平展开的主云层（80%粒子）
            for (int i = 0; i < baseParticles * 0.8; i++) {
                // 水平圆盘分布
                double radius = random.nextDouble() * cloudRadius;
                double angle = random.nextDouble() * Math.PI * 2;

                double xOffset = radius * Math.cos(angle);
                double zOffset = radius * Math.sin(angle);
                // 限制垂直方向变化范围（关键修改）
                double yOffset = (random.nextDouble() - 0.5) * cloudHeight;

                // 边缘衰减控制
                if (random.nextDouble() > (1 - radius/cloudRadius) * 0.8) continue;

                level.sendParticles(
                        ParticleTypes.LARGE_SMOKE,
                        centerX + xOffset,
                        centerY + yOffset,
                        centerZ + zOffset,
                        1, 0, 0, 0, 0
                );
            }

            // 生成顶部稀薄云层（20%粒子）
            for (int i = 0; i < baseParticles * 0.2; i++) {
                double radius = random.nextDouble() * cloudRadius * 0.8;
                double angle = random.nextDouble() * Math.PI * 2;

                double xOffset = radius * Math.cos(angle);
                double zOffset = radius * Math.sin(angle);
                // 固定在顶部区域
                double yOffset = cloudHeight * 0.7 + random.nextDouble() * 0.6;

                level.sendParticles(
                        ParticleTypes.SMOKE,
                        centerX + xOffset,
                        centerY + yOffset,
                        centerZ + zOffset,
                        1, 0, 0, 0, 0
                );
            }

            // 水平扩散的闪电效果
            if (random.nextInt(15) == 0) {
                double angle = random.nextDouble() * Math.PI * 2;
                double radius = random.nextDouble() * cloudRadius * 0.7;

                double lightningX = centerX + radius * Math.cos(angle);
                double lightningZ = centerZ + radius * Math.sin(angle);

                level.sendParticles(
                        ParticleTypes.ELECTRIC_SPARK,
                        lightningX, centerY + random.nextDouble() * cloudHeight, lightningZ,
                        3, 0.5, 0, 0.5, 0.05
                );
            }
        }
    }

    private static void spawnLightning(ServerPlayer player) {
        if (player.level() instanceof ServerLevel level) {
            try {
                // 1. 生成闪电实体（无着火）
                LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
                if (lightning != null) {
                    lightning.moveTo(player.getX(), player.getY(), player.getZ());
                    lightning.setVisualOnly(true); // 禁用着火效果
                    level.addFreshEntity(lightning);
                }
                float maxshengming = PlayerAttrubuteAPI.getMaxshengming(player);
                float damage = Math.max(1, 8f + (maxshengming * 0.03f)); // 简化公式
                player.hurt(player.damageSources().lightningBolt(), damage);
                // 4. 强制显示受击动画
                player.hurtTime = 10;
                player.invulnerableTime = 10;

                if (player.getHealth() <= 0) {
                    level.getServer().execute(() -> {
                        player.kill(); // 确保死亡
                    });
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static void completeTribulation(Player player, TribulationData data) {

        player.setPose(Pose.STANDING);
        player.getAbilities().invulnerable = false;
        player.onUpdateAbilities();

        data.altarBlock.doublePlayerAttributes(player);
        data.altarBlock.dropRandomItems(player);
        
        if (player instanceof ServerPlayer serverPlayer) {
            unlockTribulationAchievement(serverPlayer);
        }
        
    }

    public static void unlockTribulationAchievement(ServerPlayer player) {
        if (player == null || player.level().isClientSide()) {
            return;
        }
        ResourceLocation achievementId = new ResourceLocation("shanhaicontinent", "joinshanhai/changedengji/tupo_100");
        Advancement advancement = player.server.getAdvancements().getAdvancement(achievementId);
        if (advancement == null) {
            return;
        }
        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
        if (!progress.isDone()) {
            for (String criterion : progress.getRemainingCriteria()) {
                player.getAdvancements().award(advancement, criterion);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player &&
                isPlayerInTribulation(player)) {
            Component deathMessage = Component.literal(
                    String.format("%s 被天雷轰成了渣渣", player.getDisplayName().getString())
            ).withStyle(ChatFormatting.RED, ChatFormatting.BOLD);
            player.server.getPlayerList().broadcastSystemMessage(deathMessage, false);
            player.connection.send(new ClientboundSetTitleTextPacket(
                    Component.translatable("天雷锻体")
            ));
            player.sendSystemMessage(Component.literal("受到重创丢失3000最大精神力").withStyle(ChatFormatting.RED));
            player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
                cap.setDengji(cap.getDengji() - 1);
            });
            player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
                float current = cap.getMaxjingshenli();
                cap.setMaxjingshenli(current - 3000);
            });
            //强制终止渡劫
            TribulationData data = ACTIVE_PLAYERS.remove(player.getUUID());
            if (data != null) {
                player.setPose(Pose.STANDING);
                player.getAbilities().invulnerable = false;
                player.onUpdateAbilities();
            }
        }
    }


    private static class TribulationData {
        final DengXianTai altarBlock;
        int tickCounter = 0;
        int strikeCount = 0;

        public TribulationData(DengXianTai altarBlock) {
            this.altarBlock = altarBlock;
        }
    }
}