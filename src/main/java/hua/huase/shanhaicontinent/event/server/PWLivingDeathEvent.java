package hua.huase.shanhaicontinent.event.server;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.itemattribute.ItemAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapability;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerHunHuanAPI;
import hua.huase.shanhaicontinent.entity.hunhuan.HunhuanEntity;
import hua.huase.shanhaicontinent.entity.mob.hunmin.HunminEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import hua.huase.shanhaicontinent.init.ModConfig;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static hua.huase.shanhaicontinent.SHMainBus.random;
import static hua.huase.shanhaicontinent.init.ItemInit.HUNGULIST;
import static hua.huase.shanhaicontinent.init.ItemInit.SEEDLIST;

@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PWLivingDeathEvent {
        private static final RandomSource RANDOM = RandomSource.create();

        @SubscribeEvent
        public static void livingDeathEvent(LivingDeathEvent event) {
            LivingEntity entity = event.getEntity();
            if (entity == null || entity.level().isClientSide) return;
            entity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
                int zidingyiValue = cap.getzidingyi();
                Entity sourceEntity = event.getSource().getEntity();
                if (cap.getNianxian() > 1 && sourceEntity instanceof Player player) {
                    int wugongValue = (int) cap.getWugong();
                    if (wugongValue > 0) {
                        float expReward;
                        float playerLevel = PlayerAttrubuteAPI.getDengji(player); // 获取玩家等级
                        // 计算经验倍率
                        float baseMultiplier;      // 基础倍率
                        float levelBonus = 0.0f;  // 等级加成
                        float maxMultiplier;       // 最大倍率
                        if (cap.getNianxian() >= 100000 && cap.getNianxian() <= 1500000) {
                            baseMultiplier = 0.008f; // 0.005
                            maxMultiplier = 0.10f;    // 3.2%
                            if (playerLevel >= 100) {
                                levelBonus = Math.min(
                                        maxMultiplier - baseMultiplier, // 剩余可分配倍率
                                        (playerLevel - 100) * 0.0005f  // 每级+0.0003
                                );
                            }
                        }
                        else {
                            baseMultiplier = 0.008f; // 0.01
                            maxMultiplier = 0.15f;    // 5%
                            if (playerLevel >= 100) {
                                levelBonus = Math.min(
                                        maxMultiplier - baseMultiplier, // 剩余可分配倍率
                                        (playerLevel - 100) * 0.0005f  // 每级+0.0005
                                );
                            }
                        }
                        expReward = wugongValue * (baseMultiplier + levelBonus);

                        if (player instanceof ServerPlayer serverPlayer) {
                            PlayerHunHuanAPI.addJingyan(serverPlayer, expReward);
                        }
                    }
                }

                if (zidingyiValue > 0 && sourceEntity instanceof Player player) {
                    dropSpecialHunhuan(player,zidingyiValue, entity.getOnPos(),cap);
                    addHungu(entity, cap.getNianxian());
                    tryDropStrengstoneItem(entity, cap.getNianxian(), player);
                    tryDropStrengtheningItems(entity, cap.getNianxian(), player);
                    return;
                }
                if (sourceEntity instanceof Player player) {
                    addItemSeed(entity, player);
                    addHungu(entity, cap.getNianxian());
                    tryGenerateHunhuan(cap, entity.level(), entity.getOnPos());
                    tryDropStrengstoneItem(entity, cap.getNianxian(), player);
                    tryDropStrengtheningItems(entity, cap.getNianxian(), player);
                }
            });
        }


        private static void dropSpecialHunhuan(Player player, int zidingyiValue, BlockPos pos, @NotNull MonsterAttributeCapability cap) {
            double dropChance;
            RegistryObject<Item> item;

            switch (zidingyiValue) {
                case 1 -> {
                    dropChance = 0.60;
                    item = ModItems.HUNHUAN_SHIWAN;
                }
                case 2 -> {
                    dropChance = 0.65;
                    item = ModItems.HUNHUAN_BAIWAN;
                }
                case 3 -> {
                    dropChance = 0.70;
                    item = ModItems.HUNHUAN_QIANWAN;
                }
                case 4 -> {
                    dropChance = 0.20;
                    item = ModItems.HUNHUAN_SPECIAL;
                }
                default -> {
                    return;
                }
            }
            if (player.getRandom().nextDouble() > dropChance) {
                return;
            }
            ItemStack itemStack = new ItemStack(item.get());
            CompoundTag monsterTag = cap.serializeNBT();
            itemStack.getOrCreateTag().put("monster_data", monsterTag);
            ItemEntity itemEntity = new ItemEntity(
                    player.level(),
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    itemStack
            );
            player.level().addFreshEntity(itemEntity);
        }

        private static void tryGenerateHunhuan(MonsterAttributeCapability cap, Level level, BlockPos pos) {
            if (!ModConfig.ENABLE_HUNHUAN_PROBABILITY.get()) {
                addHunhuanEntity(cap, level, pos);
                return;
            }
            int nianxian = cap.getNianxian();
            double probability = getHunhuanProbability(nianxian);
            if (RANDOM.nextDouble() <= probability) {
                addHunhuanEntity(cap, level, pos);
            }
        }

        private static double getHunhuanProbability(int nianxian) {
            if (nianxian >= 10000000) {
                return ModConfig.TIER7_PROB.get();
            } else if (nianxian >= 100000) {
                return ModConfig.TIER5_PROB.get();
            } else if (nianxian >= 10000) {
                return ModConfig.TIER4_PROB.get();
            } else if (nianxian >= 1000) {
                return ModConfig.TIER3_PROB.get();
            } else if (nianxian >= 100) {
                return ModConfig.TIER2_PROB.get();
            } else if (nianxian >= 10) {
                return ModConfig.TIER1_PROB.get();
            } else {
                return ModConfig.TIER1_PROB.get();
            }
        }

      //zidingyi的nbt数值表
        // zidingyi = 1 时掉落的是十万年魂环
        // zidingyi = 2 时掉落的是百万年魂环
        // zidingyi = 3 时掉落的是千万年魂环
        // zidingyi = 4 时掉落的是特殊性魂环
        // *

        private static void addHungu(LivingEntity entity, int nianxian) {
            entity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
                int zidingyiValue = cap.getzidingyi(); // 从 Capability 读取
                boolean useBalance = ModConfig.hungupingheng.get();

                double dropChance;
                if (zidingyiValue == 1) {
                    dropChance = 0.04;
                } else if (zidingyiValue == 2) {
                    dropChance = 0.05;
                } else if (zidingyiValue == 3) {
                    dropChance = 0.15;
                } else if (useBalance) {
                    dropChance = calculateDropChance(nianxian);
                } else {
                    dropChance = ModConfig.baseDropChance.get();
                }

                if (RANDOM.nextDouble() > dropChance) {
                    return;
                }

                Item item = HUNGULIST.get(random.nextInt(HUNGULIST.size())).get();
                ItemStack itemStack = new ItemStack(item);

                itemStack.getCapability(ItemAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
                    capability.toUpdateNianxian(nianxian);

                    CompoundTag tag = itemStack.getOrCreateTag();
                    tag.put("shanhaiitematuble", capability.serializeNBT());

                    ItemEntity itemEntity = entity.spawnAtLocation(itemStack);
                    if (itemEntity != null) {
                        itemEntity.setExtendedLifetime();
                    }
                });
            });
        }

        private static double calculateDropChance(int nianxian) {
            if (nianxian >= 10000000) {
                return ModConfig.dropChanceTier7.get();
            } else if (nianxian >= 1000000) {
                return ModConfig.dropChanceTier6.get();
            } else if (nianxian >= 100000) {
                return ModConfig.dropChanceTier5.get();
            } else if (nianxian >= 10000) {
                return ModConfig.dropChanceTier4.get();
            } else if (nianxian >= 1000) {
                return ModConfig.dropChanceTier3.get();
            } else if (nianxian >= 100) {
                return ModConfig.dropChanceTier2.get();
            } else if (nianxian >= 10) {
                return ModConfig.dropChanceTier1.get();
            } else {
                return 0;
            }
        }

        private static void addItemSeed(LivingEntity entity, Player player) {
            if(entity instanceof HunminEntity)return;

            if(random.nextInt(20)==0){
                float dengji = PlayerAttrubuteAPI.getDengji(player)/10;
                ItemEntity itemEntity = entity.spawnAtLocation(SEEDLIST.get((int) Math.min(dengji, 8)).get());
                if (itemEntity != null) {
                    itemEntity.setExtendedLifetime();
                }
                return;
            }

            if(random.nextInt(10)==0){

                ItemEntity itemEntity = entity.spawnAtLocation(SEEDLIST.get(random.nextInt(4) + 9).get());
                if (itemEntity != null) {
                    itemEntity.setExtendedLifetime();
                }
            }

        }

        private static void tryDropStrengtheningItems(LivingEntity entity, int nianxian, Player player) {
            if (nianxian > 10000000) { // 千万年级别
                // 神级掉落 (1%)
                if (RANDOM.nextDouble() <= 0.02565) {
                    ItemStack stack = createSuccessScroll(100, "神级");
                    spawnLegendaryEffect(entity, stack);
                    broadcastServerMessage(player, "⟪神级⟫强化成功率卷 [+100%] 成功率");
                }
                // 普通掉落 (10%)
                else if (RANDOM.nextDouble() <= 0.1) {
                    int rate = 10 + RANDOM.nextInt(61); // 10-70%
                    String tier = rate >= 50 ? "精良" : "普通";
                    ItemStack stack = createSuccessScroll(rate, tier);
                    spawnItemWithEffect(entity, stack);
                }
            }
            else if (nianxian > 1000000) {
                if (RANDOM.nextDouble() <= 0.006) {
                    ItemStack stack = createSuccessScroll(80, "传说");
                    spawnLegendaryEffect(entity, stack);
                    broadcastServerMessage(player, "⟪传说级⟫强化成功率卷 [+80%] 成功率");
                }
                else if (RANDOM.nextDouble() <= 0.02) {
                    int rate = calculateMillionYearRate();
                    ItemStack stack = createSuccessScroll(rate, getTierName(rate));
                    spawnItemWithEffect(entity, stack);
                }
            }
        }
        private static int calculateMillionYearRate() {
            double roll = RANDOM.nextDouble();
            if (roll > 0.95) return 50;    // 5%概率50%
            if (roll > 0.80) return 30 + RANDOM.nextInt(20); // 15%概率30-50%
            return 5 + RANDOM.nextInt(25);  // 80%概率5-30%
        }
        private static String getTierName(int rate) {
            if (rate >= 50) return "精良";
            if (rate >= 30) return "优质";
            return "普通";
        }
        private static ItemStack createSuccessScroll(int successRate, String 传说) {
            ItemStack stack = new ItemStack(ModItems.STRENGTHENING_SUCCEED.get());
            stack.getOrCreateTag().putInt("SuccessRate", successRate);
            return stack;
        }
        private static void broadcastServerMessage(Player player, String message) {
            if (player.level().isClientSide) return;
            Component msg = Component.literal("§6★ 天地异象 §r| ")
                    .append(player.getDisplayName())
                    .append(" §7获得了 ")
                    .append(Component.literal(message).withStyle(ChatFormatting.GOLD));
            player.getServer().getPlayerList().broadcastSystemMessage(msg, false);
            player.getServer().getPlayerList().broadcastAll(
                    new ClientboundSoundPacket(
                            BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE),
                            SoundSource.AMBIENT,
                            player.getX(), player.getY(), player.getZ(),
                            1.0f, 1.0f, RANDOM.nextLong()
                    )
            );
        }

        private static void tryDropStrengstoneItem(LivingEntity entity, int nianxian, Player player) {
            double dropChance = 0;
            int minCount = 1;
            int maxCount = 1;
            if (nianxian > 10000000) {
                dropChance = 0.30;
                minCount = 1;
                maxCount = 210;
            }
            else if (nianxian > 1000000) {
                dropChance = 0.10;
                minCount = 1;
                maxCount = 100;
            }
            else if (nianxian > 100000) {
                dropChance = 0.0625;
                minCount = 1;
                maxCount = 10;
            }
            if (RANDOM.nextDouble() <= dropChance) {
                int dropCount = getRandomCount(minCount, maxCount);
                ItemStack stack = new ItemStack(ModItems.STRENGTHENING_STONE.get(), dropCount);
                ItemEntity itemEntity = new ItemEntity(
                        entity.level(),
                        entity.getX(),
                        entity.getY() + 0.5,
                        entity.getZ(),
                        stack
                );
                itemEntity.setDefaultPickUpDelay();
                entity.level().addFreshEntity(itemEntity);
            }
        }
        private static int getRandomCount(int min, int max) {
            if (min == max) return min;
            return min + RANDOM.nextInt(max - min + 1);
        }

        private static void spawnLegendaryEffect(LivingEntity entity, ItemStack stack) {
            ItemEntity item = new ItemEntity(entity.level(),
                    entity.getX(), entity.getY() + 1, entity.getZ(), stack);
            item.setGlowingTag(true);
            item.setInvulnerable(true);
            item.setDeltaMovement(0, 0.3, 0);
            ParticleOptions particle = stack.getTag().getInt("SuccessRate") == 100 ?
                    ParticleTypes.DRAGON_BREATH : ParticleTypes.ELECTRIC_SPARK;
            spawnItemWithEffect(stack);
            for (int i = 0; i < 30; i++) {
                entity.level().addParticle(particle,
                        entity.getX(), entity.getY() + 1.5, entity.getZ(),
                        RANDOM.nextGaussian() * 0.3,
                        RANDOM.nextDouble() * 0.5,
                        RANDOM.nextGaussian() * 0.3);
            }
            entity.level().addFreshEntity(item);
        }

        private static void spawnItemWithEffect(ItemStack stack) {
            stack.enchant(Enchantments.UNBREAKING, 1);
            stack.getOrCreateTag().putBoolean("Glowing", true);
        }


        private static void spawnItemWithEffect(LivingEntity entity, ItemStack stack) {
            ItemEntity itemEntity = new ItemEntity(
                    entity.level(),
                    entity.getX(),
                    entity.getY() + 0.5,
                    entity.getZ(),
                    stack
            );
            itemEntity.setExtendedLifetime();
            entity.level().addFreshEntity(itemEntity);
        }

        public static void addHunhuanEntity(MonsterAttributeCapability capability, Level level, BlockPos onPos) {
            if (level == null || onPos == null) return;

            HunhuanEntity hunhuanEntity = new HunhuanEntity(EntityInit.HUNHUAN.get(), level);
            hunhuanEntity.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(capability1 -> {
                capability1.deserializeNBT(capability.serializeNBT());
                hunhuanEntity.setPos(onPos.getCenter().add(0, 1, 0));
                level.addFreshEntity(hunhuanEntity);
            });
        }

    }
