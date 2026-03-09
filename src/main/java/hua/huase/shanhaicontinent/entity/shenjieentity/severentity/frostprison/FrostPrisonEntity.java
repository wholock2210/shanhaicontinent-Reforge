package hua.huase.shanhaicontinent.entity.shenjieentity.severentity.frostprison;

import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import hua.huase.shanhaicontinent.register.ModEntities;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;

public class FrostPrisonEntity extends Monster {

    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.PROGRESS);
    private final FrostPrisonSkills skills;
    private static final double FIELD_RADIUS = 100.0;

    public FrostPrisonEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.FROST_PRISON.get(), world);
    }

    public FrostPrisonEntity(EntityType<FrostPrisonEntity> type, Level world) {
        super(type, world);
        setMaxUpStep(0.6f);
        xpReward = 100;
        setNoAi(false);
        this.skills = new FrostPrisonSkills(this);
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        if (source.getEntity() instanceof Player player) {
            Random random = new Random();
            int jinghuaCount = random.nextInt(2) + 1;
            int xuenangCount = random.nextInt(1) + 1;
            int pojingdanCount = random.nextInt(2) + 1; // 生成 1~4 的随机数
            this.spawnAtLocation(new ItemStack(ModItems.MOWU_XUENANG.get(),jinghuaCount));
            this.spawnAtLocation(new ItemStack(ModItems.MOWU_JINGHUA.get(),xuenangCount));
            this.spawnAtLocation(new ItemStack(ModItems.pojingdan.get(), pojingdanCount));
            if (random.nextInt(100) < 50) {
                this.spawnAtLocation(new ItemStack(ModItems.danfang_fengming.get()));
            }
            if (random.nextInt(100) < 50) {
                this.spawnAtLocation(new ItemStack(ModItems.danfang_zixiao.get()));
            }
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
    public void tick() {
        super.tick();
        updateFlightRestriction();
        skills.tick(); // 更新技能系统
    }

    private void updateFlightRestriction() {
        AABB area = new AABB(
                this.getX() - FIELD_RADIUS, this.getY() - FIELD_RADIUS, this.getZ() - FIELD_RADIUS,
                this.getX() + FIELD_RADIUS, this.getY() + FIELD_RADIUS, this.getZ() + FIELD_RADIUS
        );

        List<Player> players = this.level().getEntitiesOfClass(Player.class, area);
        for (Player player : players) {
            if (!player.isCreative() && !player.isSpectator()) {
                // 使用特定标记区分不同实体的禁飞效果
                player.getPersistentData().putBoolean("frost_prison_no_fly", true);
                player.getAbilities().mayfly = false;
                if (player.getAbilities().flying) {
                    player.getAbilities().flying = false;
                    player.onUpdateAbilities();
                }
            }
        }
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        // 实体移除时解除禁飞效果
        AABB area = new AABB(
                this.getX() - FIELD_RADIUS, this.getY() - FIELD_RADIUS, this.getZ() - FIELD_RADIUS,
                this.getX() + FIELD_RADIUS, this.getY() + FIELD_RADIUS, this.getZ() + FIELD_RADIUS
        );

        List<Player> players = this.level().getEntitiesOfClass(Player.class, area);
        for (Player player : players) {
            player.getPersistentData().remove("frost_prison_no_fly");
            // 注意：这里只是移除了标记，实际飞行能力恢复由玩家自己的逻辑处理
        }

        super.remove(reason);
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
    }

    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
        builder = builder.add(Attributes.MAX_HEALTH, 20);
        builder = builder.add(Attributes.ARMOR, 1);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
        return builder;
    }
}