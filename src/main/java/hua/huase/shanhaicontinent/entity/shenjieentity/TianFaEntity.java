package hua.huase.shanhaicontinent.entity.shenjieentity;

import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import hua.huase.shanhaicontinent.entity.shenjieentity.jineng.TianFaSkills;
import hua.huase.shanhaicontinent.register.ModBlock;
import hua.huase.shanhaicontinent.register.ModEntities;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
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
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class TianFaEntity extends Monster {
    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.PROGRESS);
    private boolean isFirstPhaseTriggered = false;
    private boolean isSecondPhaseTriggered = false;
    private boolean isFinalPhaseTriggered = false;
    private final TianFaSkills skills = new TianFaSkills(this);
    private int skillCooldown = 0;
    private int tickCountForAoe = 0;
    private boolean isMusicPlaying = false;

    public TianFaEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.TIANFA.get(), world);
    }

    public TianFaEntity(EntityType<TianFaEntity> type, Level world) {
        super(type, world);
        setMaxUpStep(1f);
        xpReward = 300;
        setNoAi(false);
        setPersistenceRequired();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isDeadOrDying()) {
            stopBossMusic();
            return;
        }

        if (!this.level().isClientSide) {
            checkAndPlayBossMusic();
        }
    }

    private void checkAndPlayBossMusic() {
        double radius = 100.0;
        boolean hasPlayersNearby = false;

        for (ServerPlayer player : ((ServerLevel) this.level()).players()) {
            if (player.distanceTo(this) <= radius) {
                hasPlayersNearby = true;
                if (!this.isMusicPlaying) {
                    playBossMusic(player);
                }
            }
        }

        if (!hasPlayersNearby && this.isMusicPlaying) {
            stopBossMusic();
        }
    }

    private void playBossMusic(ServerPlayer player) {
        ResourceLocation soundLocation = new ResourceLocation("shanhaicontinent", "syntheticdream_music");
        SoundEvent soundEvent = ForgeRegistries.SOUND_EVENTS.getValue(soundLocation);

        if (soundEvent != null) {
            Holder<SoundEvent> soundHolder = BuiltInRegistries.SOUND_EVENT.wrapAsHolder(soundEvent);
            ClientboundSoundPacket packet = new ClientboundSoundPacket(soundHolder, SoundSource.RECORDS,
                    player.getX(), player.getY(), player.getZ(), 1.0f, 1.0f, player.level().random.nextLong());
            player.connection.send(packet);
            this.isMusicPlaying = true;
        }
    }

    private void stopBossMusic() {
        if (this.level() instanceof ServerLevel serverLevel) {
            ResourceLocation soundLocation = new ResourceLocation("shanhaicontinent", "syntheticdream_music");
            ClientboundStopSoundPacket packet = new ClientboundStopSoundPacket(soundLocation, SoundSource.RECORDS);
            for (ServerPlayer player : serverLevel.players()) {
                player.connection.send(packet);
            }
            this.isMusicPlaying = false;
        }
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        stopBossMusic();
    }

    @Override
    public void remove(RemovalReason pReason) {
        super.remove(pReason);
        stopBossMusic();
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
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        if (source.getEntity() instanceof Player player) {
            Random random = new Random();
            int pojingdanCount = random.nextInt(2) + 1; // 生成 1~4 的随机数
            int jinghuaCount = random.nextInt(1) + 1; // 生成 1~4 的随机数
            this.spawnAtLocation(new ItemStack(ModItems.pojingdan.get(), pojingdanCount));
            this.spawnAtLocation(new ItemStack(ModItems.SYNTHETIC_DREAM.get()));
            this.spawnAtLocation(new ItemStack(ModItems.MOWU_JINGHUA.get(),jinghuaCount));
            if (random.nextInt(100) < 50) {
                this.spawnAtLocation(new ItemStack(ModItems.danfang_fengming.get()));
            }
            if (random.nextInt(100) < 50) {
                this.spawnAtLocation(new ItemStack(ModItems.danfang_zixiao.get()));
            }
        }
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.parrot.imitate.zombie"));
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.parrot.imitate.zombie"))), 0.15f, 1);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        stopBossMusic();
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    @Override
    public boolean hurt(DamageSource damagesource, float amount) {
        if (damagesource.is(DamageTypes.IN_FIRE) ||
                damagesource.is(DamageTypes.FALL) ||
                damagesource.is(DamageTypes.DROWN) ||
                damagesource.is(DamageTypes.LIGHTNING_BOLT)) {
            return false;
        }
        return super.hurt(damagesource, amount);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
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

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());

        if (this.getTarget() != null && --skillCooldown <= 0) {
            if (this.random.nextFloat() < 0.6f) { // 60%概率触发技能
                int skillType = this.random.nextInt(3) + 1; // 随机选择1-3号技能
                switch (skillType) {
                    case 1 -> skills.performLeapAttack();
                    case 2 -> skills.performBlackHole();
                    case 3 -> skills.performLightningStrike();
                }
                skillCooldown = 100; // 5秒冷却(20 ticks = 1秒)
            }
        }

        if (this.isInWater() && this.tickCount % 100 == 0) {
            performAoeAttack();
        }

        if (this.getCommandSenderWorld().players().stream().noneMatch(player -> player.distanceTo(this) <= 300)) {
            this.discard();
            return;
        }

        float healthPercentage = this.getHealth() / this.getMaxHealth();

        if (!this.isFirstPhaseTriggered && healthPercentage <= 0.5) {
            triggerFirstPhase();
        }

        if (!this.isSecondPhaseTriggered && healthPercentage <= 0.3) {
            triggerSecondPhase();
        }

        if (!this.isFinalPhaseTriggered && healthPercentage <= 0.1) {
            triggerFinalPhase();
        }

        LivingEntity target = this.getTarget();
        if (target != null && this.distanceTo(target) > 10 && this.distanceTo(target) <= 30 && this.tickCount % 200 == 0) {
            shootFireballAtTarget(target);
        }

        if (target != null) {
            destroyBlocksInFront();
        }
    }

    private void triggerFirstPhase() {
        this.isFirstPhaseTriggered = true;
        this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1));
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 1));
        this.addEffect(new MobEffectInstance(MobEffects.JUMP, 200, 1));
        tickCountForAoe = 0;
    }

    private void triggerSecondPhase() {
        this.isSecondPhaseTriggered = true;
        summonMinions();
    }

    private void triggerFinalPhase() {
        this.isFinalPhaseTriggered = true;
        performFinalPhaseFireballAttack();
    }

    private void shootFireballAtTarget(LivingEntity target) {
        SmallFireball fireball = new SmallFireball(this.getCommandSenderWorld(), this, target.getX() - this.getX(), target.getY() - this.getY(), target.getZ() - this.getZ());
        fireball.setPos(this.getX(), this.getY() + 1.5, this.getZ());
        this.getCommandSenderWorld().addFreshEntity(fireball);
    }

    private void destroyBlocksInFront() {
        BlockPos pos = this.blockPosition();
        Vec3 direction = this.getViewVector(1.0F);
        int dirX = (int) Math.round(direction.x);
        int dirZ = (int) Math.round(direction.z);

        BlockPos frontPos = pos.offset(dirX, 0, dirZ);
        destroyBlockIfValid(this.getCommandSenderWorld(), frontPos);
        destroyBlockIfValid(this.getCommandSenderWorld(), frontPos.above());
        destroyBlockIfValid(this.getCommandSenderWorld(), frontPos.above(2));
        destroyBlockIfValid(this.getCommandSenderWorld(), frontPos.above(3));
    }

    private void performAoeAttack() {
        Level level = this.getCommandSenderWorld();
        if (level instanceof ServerLevel serverLevel) {
            AABB attackRange = this.getBoundingBox().inflate(8);
            level.getEntities(this, attackRange, entity -> entity instanceof Player).forEach(entity -> {
                if (entity instanceof Player player) {
                    DamageSource damageSource = serverLevel.damageSources().mobAttack(this);
                    player.hurt(damageSource, 10.0F);
                }
            });
            serverLevel.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 10, 0.5, 0.5, 0.5, 0.1);
        }
    }

    private void summonMinions() {
        if (this.tickCount % 600 == 0 && this.getCommandSenderWorld().getEntities(this, this.getBoundingBox().inflate(10), e -> e instanceof MoWuEntity).size() < 5) {
            MoWuEntity minion = new MoWuEntity(ModEntities.MOWU.get(), this.getCommandSenderWorld());
            minion.setPos(this.getX() + this.random.nextInt(3) - 1, this.getY(), this.getZ() + this.random.nextInt(3) - 1);
            this.getCommandSenderWorld().addFreshEntity(minion);
        }
    }

    private void performFinalPhaseFireballAttack() {
        Level level = this.getCommandSenderWorld();
        if (level instanceof ServerLevel serverLevel) {
            double radius = 10.0;
            for (int i = 0; i < 360; i += 15) {
                for (int j = -30; j <= 30; j += 30) {
                    double angle = Math.toRadians(i);
                    double yOffset = Math.toRadians(j);
                    double dx = Math.cos(angle) * Math.cos(yOffset);
                    double dy = Math.sin(yOffset);
                    double dz = Math.sin(angle) * Math.cos(yOffset);

                    CustomFireball fireball = new CustomFireball(level, this, dx, dy, dz);
                    fireball.setPos(this.getX(), this.getY() + 1.5, this.getZ());
                    level.addFreshEntity(fireball);
                }
            }
        }
    }

    /**
     * 自定义火球类，禁止点燃方块，但可以燃烧实体
     */
    public static class CustomFireball extends SmallFireball {
        public CustomFireball(Level level, LivingEntity owner, double dx, double dy, double dz) {
            super(level, owner, dx, dy, dz);
        }

        @Override
        protected void onHitBlock(@NotNull BlockHitResult result) {
        }

        @Override
        protected void onHitEntity(EntityHitResult result) {
            // 对实体造成伤害并点燃
            Entity entity = result.getEntity();
            if (entity instanceof LivingEntity livingEntity) {
                // 获取 DamageSources
                DamageSources damageSources = livingEntity.level().damageSources();

                DamageSource damageSource = damageSources.indirectMagic(this, this.getOwner());
                livingEntity.hurt(damageSource, 6.0F); // 造成伤害
                livingEntity.setSecondsOnFire(5); // 点燃实体 5 秒
            }
            super.onHitEntity(result);
        }
    }

    private void destroyBlockIfValid(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!(state.is(Blocks.BEDROCK) ||
                state.is(ModBlock.ZHAOHUANTAI_BLOCK.get()) ||
                state.is(ModBlock.JIANYING_QIANG.get()) ||
                state.is(ModBlock.JIANYING_LOUTI.get()) ||
                state.is(ModBlock.JIANYING_BLOCK.get()))) {
            level.destroyBlock(pos, false);
        }
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 10);
        builder = builder.add(Attributes.ARMOR, 10);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 10);
        builder = builder.add(Attributes.FOLLOW_RANGE, 20);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 20);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 5);
        return builder;
    }
}