package hua.huase.shanhaicontinent.block.shenjie;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class shenjie_csm extends Block {

    public shenjie_csm(Properties properties) {
        super(properties);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.dimensionType().natural() && pLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && pRandom.nextInt(2000) < pLevel.getDifficulty().getId()) {
            while (pLevel.getBlockState(pPos).is(this)) {
                pPos = pPos.below();
            }

            if (pLevel.getBlockState(pPos).isValidSpawn(pLevel, pPos, EntityType.ZOMBIFIED_PIGLIN)) {
                Entity $$4 = EntityType.ZOMBIFIED_PIGLIN.spawn(pLevel, pPos.above(), MobSpawnType.STRUCTURE);
                if ($$4 != null) {
                    $$4.setPortalCooldown();
                }
            }
        }
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(100) == 0) {
            pLevel.playLocalSound((double) pPos.getX() + 0.5, (double) pPos.getY() + 0.5, (double) pPos.getZ() + 0.5, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, pRandom.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int $$4 = 0; $$4 < 4; ++$$4) {
            double $$5 = (double) pPos.getX() + pRandom.nextDouble();
            double $$6 = (double) pPos.getY() + pRandom.nextDouble();
            double $$7 = (double) pPos.getZ() + pRandom.nextDouble();
            double $$8 = ((double) pRandom.nextFloat() - 0.5) * 0.5;
            double $$9 = ((double) pRandom.nextFloat() - 0.5) * 0.5;
            double $$10 = ((double) pRandom.nextFloat() - 0.5) * 0.5;
            int $$11 = pRandom.nextInt(2) * 2 - 1;
            if (!pLevel.getBlockState(pPos.west()).is(this) && !pLevel.getBlockState(pPos.east()).is(this)) {
                $$5 = (double) pPos.getX() + 0.5 + 0.25 * (double) $$11;
                $$8 = (double) (pRandom.nextFloat() * 2.0F * (float) $$11);
            } else {
                $$7 = (double) pPos.getZ() + 0.5 + 0.25 * (double) $$11;
                $$10 = (double) (pRandom.nextFloat() * 2.0F * (float) $$11);
            }

            pLevel.addParticle(ParticleTypes.PORTAL, $$5, $$6, $$7, $$8, $$9, $$10);
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer serverPlayer) {
            ResourceKey<Level> shenjieWorld = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("shanhaicontinent", "shenjieworld"));
            ResourceKey<Level> overworld = Level.OVERWORLD;

            if (level.dimension() == shenjieWorld) {
                ServerLevel overworldLevel = serverPlayer.server.getLevel(overworld);
                if (overworldLevel != null) {
                    serverPlayer.changeDimension(overworldLevel, new ITeleporter() {
                        @Override
                        public Entity placeEntity(Entity entity, ServerLevel currentLevel, ServerLevel destLevel, float yaw, Function<Boolean, Entity> repositionEntity) {
                            return repositionEntity.apply(false);
                        }
                    });
                }
            } else {
                ServerLevel shenjieLevel = serverPlayer.server.getLevel(shenjieWorld);
                if (shenjieLevel != null && serverPlayer.level() != shenjieLevel) {
                    serverPlayer.changeDimension(shenjieLevel, new ITeleporter() {
                        @Override
                        public Entity placeEntity(Entity entity, ServerLevel currentLevel, ServerLevel destLevel, float yaw, Function<Boolean, Entity> repositionEntity) {
                            return repositionEntity.apply(false);
                        }
                    });
                }
            }
        }
    }
}