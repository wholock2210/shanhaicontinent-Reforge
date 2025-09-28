package hua.huase.shanhaicontinent.entity.shenjieentity.severentity.evileyetyrant;

import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import hua.huase.shanhaicontinent.capability.CapabilityAttributeBase;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.register.ModEntities;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EvileyetyrantEntity extends Monster {
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.PROGRESS);
    private int stompCooldown = 0;
    private int immobilizeCooldown = 0;
    private int chargeCooldown = 0;
    private int knockDownCooldown = 0;
    private int skillTimer = 0; // 新增技能计时器


    public EvileyetyrantEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.EVILEYETYRANT.get(), world);
    }

    public EvileyetyrantEntity(EntityType<EvileyetyrantEntity> type, Level world) {
        super(type, world);
        setMaxUpStep(0.6f);
        xpReward = 100;
        setNoAi(false);
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        if (source.getEntity() instanceof Player player) {
            Random random = new Random();
            random.nextInt(100);
            this.spawnAtLocation(new ItemStack(ModItems.TYRANT_HEART.get()));
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) { // 将true改为false，不强制持续追击
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                // 更合理的攻击距离计算
                return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
        // 主动攻击玩家的目标选择器
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new FloatGoal(this));

        // 添加这些新目标
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new FloatGoal(this));

    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());

        // 冷却计时
        if (stompCooldown > 0) stompCooldown--;
        if (immobilizeCooldown > 0) immobilizeCooldown--;
        if (chargeCooldown > 0) chargeCooldown--;
        if (knockDownCooldown > 0) knockDownCooldown--;

        LivingEntity target = this.getTarget();
        if (target == null) return;

        // 每3秒（60 ticks）执行一次技能检查
        if (++skillTimer >= 60) {
            skillTimer = 0;
            executeRandomSkill(target);
        }
    }

    private void executeRandomSkill(LivingEntity target) {
        List<SkillEntry> availableSkills = new ArrayList<>();
        double distanceSqr = this.distanceToSqr(target);

        if (distanceSqr < 64 && stompCooldown <= 0) {
            availableSkills.add(new SkillEntry(this::performStompAttack, () -> stompCooldown = 100));
        }
        if (distanceSqr < 100 && immobilizeCooldown <= 0) {
            availableSkills.add(new SkillEntry(() -> performImmobilizeSkill(target), () -> immobilizeCooldown = 200));
        }
        if (distanceSqr < 225 && chargeCooldown <= 0) {
            availableSkills.add(new SkillEntry(this::performChargeAttack, () -> chargeCooldown = 150));
        }
        if (target instanceof Player && knockDownCooldown <= 0) {
            availableSkills.add(new SkillEntry(() -> performKnockDownSkill((Player)target), () -> knockDownCooldown = 300));
        }

        if (!availableSkills.isEmpty()) {
            SkillEntry selected = availableSkills.get(this.random.nextInt(availableSkills.size()));
            selected.execute();
            selected.setCooldown();
        }
    }

    private void performImmobilizeSkill(LivingEntity target) {
        // 施加虚弱2和缓慢255效果
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1)); // 5秒
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 254)); // 255级

        // 播放特效
        if (!this.level().isClientSide) {
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.WITCH,
                    target.getX(), target.getY() + 1, target.getZ(),
                    30, 0.5, 0.5, 0.5, 0.1);
            this.playSound(SoundEvents.EVOKER_CAST_SPELL, 1.0F, 0.8F);
        }
    }



    private void performKnockDownSkill(Player player) {
        // 禁用飞行能力
        player.getAbilities().mayfly = false;
        player.getAbilities().flying = false;
        player.onUpdateAbilities();
        // 5秒后恢复飞行检查
        if (!this.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
            MinecraftForge.EVENT_BUS.register(new Object() {
                @SubscribeEvent
                public void onTick(TickEvent.ServerTickEvent event) {
                    if (event.phase == TickEvent.Phase.END) {
                        if (--ticksRemaining <= 0) {
                            updatePlayerFly(serverPlayer);
                            MinecraftForge.EVENT_BUS.unregister(this);
                        }
                    }
                }
                private int ticksRemaining = 100; // 5秒
            });
        }
        if (!this.level().isClientSide) {
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.CLOUD,
                    player.getX(), player.getY() + 1, player.getZ(),
                    30, 0.5, 0.5, 0.5, 0.1);
            this.playSound(SoundEvents.ENDER_DRAGON_FLAP, 1.0F, 0.5F);
        }
    }

    private static class SkillEntry {
        private final Runnable execute;
        private final Runnable setCooldown;

        public SkillEntry(Runnable execute, Runnable setCooldown) {
            this.execute = execute;
            this.setCooldown = setCooldown;
        }

        public void execute() {
            execute.run();
        }

        public void setCooldown() {
            setCooldown.run();
        }
    }



    private void performChargeAttack() {
        this.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            LivingEntity target = this.getTarget();
            if (target != null) {
                Vec3 direction = target.position().subtract(this.position()).normalize();
                this.setDeltaMovement(direction.scale(2.0));
                this.hurtMarked = true;

                // 使用延迟任务处理持续伤害
                if (!this.level().isClientSide) {
                    ServerLevel serverLevel = (ServerLevel)this.level();
                    for (int i = 0; i < 10; i++) {
                        int finalI = i;
                        serverLevel.getServer().execute(() -> {
                            if (this.isAlive()) {
                                AABB hitbox = this.getBoundingBox().inflate(1.0);
                                List<Player> players = serverLevel.getEntitiesOfClass(Player.class, hitbox);
                                for (Player player : players) {
                                    player.hurt(this.damageSources().mobAttack(this), cap.getWugong() * 2);
                                    player.push(direction.x * 2.0, 0.5, direction.z * 2.0);
                                }

                                // 只在最后一次播放特效
                                if (finalI == 9) {
                                    serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK,
                                            this.getX(), this.getY(), this.getZ(),
                                            10, 1.0, 1.0, 1.0, 0.1);
                                    this.playSound(SoundEvents.RAVAGER_ROAR, 1.0F, 0.7F);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void updatePlayerFly(Player player) {
        // 重新检查飞行条件
        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            if (!player.isCreative() && !player.isSpectator()) {
                if (PlayerAttrubuteAPI.getMaxjingshenli(player) > 5000) {
                    player.getAbilities().mayfly = true;
                } else {
                    player.getAbilities().mayfly = false;
                }
                player.onUpdateAbilities();
            }
        });
    }


    private void performStompAttack() {
        // 获取能力属性
        this.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(cap -> {
            // 创建踩踏效果
            new StompSkill(this.level(), this.position(), cap.getWugong() * 3).execute();

            // 播放特效和音效
            if (!this.level().isClientSide) {
                ((ServerLevel)this.level()).sendParticles(ParticleTypes.EXPLOSION,
                        this.getX(), this.getY(), this.getZ(),
                        20, 2, 2, 2, 0.1);
                this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 1.0F);
            }
        });
    }


    @Override
    public MobType getMobType() {
        return MobType.ILLAGER;
    }

    @Override
    public double getMyRidingOffset() {
        return -0.35D;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    @Override
    public boolean hurt(DamageSource damagesource, float amount) {
        if (damagesource.getDirectEntity() instanceof AbstractArrow)
            return false;
        if (damagesource.getDirectEntity() instanceof ThrownPotion || damagesource.getDirectEntity() instanceof AreaEffectCloud)
            return false;
        if (damagesource.is(DamageTypes.FALL))
            return false;
        if (damagesource.is(DamageTypes.LIGHTNING_BOLT))
            return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH))
            return false;
        if (damagesource.is(DamageTypes.WITHER) || damagesource.is(DamageTypes.WITHER_SKULL))
            return false;
        return super.hurt(damagesource, amount);
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    public static void init() {
        SpawnPlacements.register(ModEntities.EVILEYETYRANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));

    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
        builder = builder.add(Attributes.MAX_HEALTH, 20);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 20);
        builder = builder.add(Attributes.ATTACK_SPEED, 1.15);

        return builder;
    }
}
