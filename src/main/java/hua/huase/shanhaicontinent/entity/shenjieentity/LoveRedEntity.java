package hua.huase.shanhaicontinent.entity.shenjieentity;


import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import hua.huase.shanhaicontinent.register.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class LoveRedEntity extends Monster {

    private LivingEntity attackTarget = null;
    private int attackCooldown = 0;

    public LoveRedEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.LOVERED.get(), world);
    }

    public LoveRedEntity(EntityType<LoveRedEntity> type, Level world) {
        super(type, world);
        setMaxUpStep(0.6f);
        xpReward = 0;
        setNoAi(true);
        setPersistenceRequired();
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public MobType getMobType() {
        return MobType.ILLAGER;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
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
        if (damagesource.is(DamageTypes.IN_FIRE))
            return false;
        if (damagesource.is(DamageTypes.FALL))
            return false;
        if (damagesource.is(DamageTypes.DROWN))
            return false;

        Entity source = damagesource.getEntity();
        if (source instanceof LivingEntity living) {
            this.attackTarget = living;
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
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if (attackCooldown > 0) {
            attackCooldown--;
        }

        if (attackTarget != null && attackTarget.isAlive()) {
            double distance = this.distanceTo(attackTarget);

            if (distance <= 5) {
                // 近战攻击，冷却20tick
                if (attackCooldown == 0) {
                    this.swing(InteractionHand.MAIN_HAND);
                    this.doHurtTarget(attackTarget);
                    attackCooldown = 20;
                }
            } else if (distance <= 20) {
                // 远程攻击，发射火球，冷却40tick
                if (attackCooldown == 0) {
                    shootFireballAt(attackTarget);
                    attackCooldown = 10;
                }
            } else {
                attackTarget = null;
            }
        } else {
            attackTarget = null;
        }
    }

    private void shootFireballAt(LivingEntity target) {
        Level world = this.level();
        if (!world.isClientSide) {
            double dx = target.getX() - this.getX();
            double dy = target.getY(0.5) - this.getY(0.5);
            double dz = target.getZ() - this.getZ();

            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

            double vx = dx / dist * 0.5;
            double vy = dy / dist * 0.5;
            double vz = dz / dist * 0.5;

            CustomFireball fireball = new CustomFireball(world, this, vx, vy, vz);
            fireball.setPos(this.getX(), this.getY(0.5), this.getZ());
            world.addFreshEntity(fireball);

            // 粒子效果
            for (int i = 0; i < 10; i++) {
                double px = this.getX() + vx * i * 0.5 + (world.random.nextDouble() - 0.5) * 0.2;
                double py = this.getY(0.5) + vy * i * 0.5 + (world.random.nextDouble() - 0.5) * 0.2;
                double pz = this.getZ() + vz * i * 0.5 + (world.random.nextDouble() - 0.5) * 0.2;
                world.addParticle(ParticleTypes.FLAME, px, py, pz, 0, 0, 0);
            }
        }
    }

    public static class CustomFireball extends SmallFireball {
        public CustomFireball(Level level, LivingEntity owner, double dx, double dy, double dz) {
            super(level, owner, dx, dy, dz);
        }

        @Override
        protected void onHitBlock(@NotNull BlockHitResult result) {
            // 不点燃方块，直接消失
            this.discard();
        }

        @Override
        protected void onHitEntity(EntityHitResult result) {
            Entity entity = result.getEntity();
            if (entity instanceof LivingEntity livingEntity) {
                DamageSource damageSource = this.level().damageSources().indirectMagic(this, this.getOwner());
                livingEntity.hurt(damageSource, 6.0F); // 造成伤害
                // 不点燃实体，取消 setSecondsOnFire
            }
            this.discard();
        }
    }

    public static void init() {
        SpawnPlacements.register(ModEntities.LOVERED.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) ->
                        world.getDifficulty() != Difficulty.PEACEFUL
                                && Monster.isDarkEnoughToSpawn(world, pos, random)
                                && Mob.checkMobSpawnRules(entityType, world, reason, pos, random));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
        builder = builder.add(Attributes.MAX_HEALTH, 10);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16);
        return builder;
    }
}

