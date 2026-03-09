package hua.huase.shanhaicontinent.entity.shenjieentity.severentity.demonking;

import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import hua.huase.shanhaicontinent.register.ModEntities;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;

public class DemonKingEntity extends Monster {

    private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.PURPLE, ServerBossEvent.BossBarOverlay.PROGRESS);
    private boolean fieldActive = false;
    private static final double FIELD_RADIUS = 50.0;
    private final DemonKingSkills skills;

    public DemonKingEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.DEMON_KING.get(), world);
    }

    public DemonKingEntity(EntityType<DemonKingEntity> type, Level world) {
        super(type, world);
        setMaxUpStep(0.6f);
        xpReward = 100;
        setNoAi(false);
        this.skills = new DemonKingSkills(this);
    }

    @Override
    public void tick() {
        super.tick();
        skills.tick();
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        if (source.getEntity() instanceof Player player) {
            Random random = new Random();
            int jinghuaCount = random.nextInt(10) + 1;
            int xuenangCount = random.nextInt(3) + 1;
            this.spawnAtLocation(new ItemStack(ModItems.MOWU_XUENANG.get(),jinghuaCount));
            if (random.nextInt(100) < 50) {
                this.spawnAtLocation(new ItemStack(ModItems.MOWU_JINGHUA.get(),xuenangCount));
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

        boolean result = super.hurt(damagesource, amount);
        if (!fieldActive && this.getHealth() <= this.getMaxHealth() * 0.9) {
            activateField();
        }

        return result;
    }

    private void activateField() {
        this.fieldActive = true;
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());

        // 如果领域激活，处理玩家飞行状态
        if (fieldActive) {
            AABB area = new AABB(
                    this.getX() - FIELD_RADIUS, this.getY() - FIELD_RADIUS, this.getZ() - FIELD_RADIUS,
                    this.getX() + FIELD_RADIUS, this.getY() + FIELD_RADIUS, this.getZ() + FIELD_RADIUS
            );

            List<Player> players = this.level().getEntitiesOfClass(Player.class, area);
            for (Player player : players) {
                if (!player.isCreative() && !player.isSpectator()) {
                    // 标记玩家被击落
                    player.getPersistentData().putBoolean("demon_king_field", true);
                    player.getAbilities().mayfly = false;
                    if (player.getAbilities().flying) {
                        player.getAbilities().flying = false;
                        player.onUpdateAbilities();
                    }
                }
            }
        }
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        if (fieldActive) {
            AABB area = new AABB(
                    this.getX() - FIELD_RADIUS, this.getY() - FIELD_RADIUS, this.getZ() - FIELD_RADIUS,
                    this.getX() + FIELD_RADIUS, this.getY() + FIELD_RADIUS, this.getZ() + FIELD_RADIUS
            );
            List<Player> players = this.level().getEntitiesOfClass(Player.class, area);
            for (Player player : players) {
                player.getPersistentData().remove("demon_king_field");
            }
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

    public static void init() {
        SpawnPlacements.register(ModEntities.DEMON_KING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.6);
        builder = builder.add(Attributes.MAX_HEALTH, 20);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 24);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
        return builder;
    }
}