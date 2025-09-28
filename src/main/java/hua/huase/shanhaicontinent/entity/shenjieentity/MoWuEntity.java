package hua.huase.shanhaicontinent.entity.shenjieentity;

import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.init.ItemInit;
import hua.huase.shanhaicontinent.register.ModEntities;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

import static hua.huase.shanhaicontinent.init.ItemInit.DANFANGLIST;
import static hua.huase.shanhaicontinent.init.ItemInit.DANYAOLIST;

public class MoWuEntity extends Monster {
    public MoWuEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.MOWU.get(), world);
    }

    public MoWuEntity(@NotNull EntityType<MoWuEntity> type, Level world) {
        super(type, world);
        setMaxUpStep(1f);
        xpReward = 20;
        setNoAi(false);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        // 攻击目标
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true) {
            @Override
            protected double getAttackReachSqr(@NotNull LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });

        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));

        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));

        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));

        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.goalSelector.addGoal(6, new FloatGoal(this));

        // 被攻击时反击
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));

        // 主动攻击玩家
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        if (source.getEntity() instanceof Player player) {
            Random random = new Random();
            if (random.nextInt(100) < 9 + looting) {
                this.spawnAtLocation(new ItemStack(ModItems.MOWU_XUENANG.get()));
            } else if (random.nextInt(100) < 80) {
                this.spawnAtLocation(new ItemStack(Items.ROTTEN_FLESH));
            } else if (random.nextInt(100) < 65){
                this.spawnAtLocation(new ItemStack(ModItems.hanjing_suipian.get()));
            }
        }
        if(random.nextInt(4)==0) {
            this.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
                double log = Math.log10(capability.getNianxian() + 1);
                RegistryObject<Item> itemRegistryObject = DANYAOLIST.get(Math.min((int) log, DANFANGLIST.size() - 1));
                dropitem(itemRegistryObject.get());
            });
        }
    }
    private void dropitem(Item netherStar) {
        ItemEntity itementity = this.spawnAtLocation(netherStar);
        if (itementity != null) {
            itementity.setExtendedLifetime();
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.husk.converted_to_zombie"));
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.husk.converted_to_zombie"))), 0.15f, 1);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt")));
    }

    @Override
    public SoundEvent getDeathSound() {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death")));
    }

    @Override
    public boolean hurt(DamageSource damagesource, float amount) {
        if (damagesource.is(DamageTypes.FALL))
            return false;
        return super.hurt(damagesource, amount);
    }

    public static void init() {
        SpawnPlacements.register(ModEntities.MOWU.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random) && Mob.checkMobSpawnRules(entityType, world, reason, pos, random)));
        DungeonHooks.addDungeonMob(ModEntities.MOWU.get(), 180);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
        builder = builder.add(Attributes.MAX_HEALTH, 20);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }
}