package hua.huase.shanhaicontinent.block.entityblock.flowerblock;

import hua.huase.shanhaicontinent.init.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class SHFlowerBlock extends CropBlock {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };

    private static final ForgeConfigSpec.BooleanValue SLOW_GROWTH = ModConfig.slowGrowth;
    private static final List<Supplier<Fluid>> ACCELERATOR_FLUIDS = List.of(
            () -> ShanhaicontinentModFluids.COLDICE.get(),
            () -> ShanhaicontinentModFluids.HOTYANGQUAN.get()
    );

    public SHFlowerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    //获取渲染箱
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE_BY_AGE[this.getAge(blockState)];
    }

    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.is(BlockInit.SOUL_BLOCK.get());
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return MAX_AGE;
    }

    public int getAge(BlockState blockState) {
        return blockState.getValue(this.getAgeProperty());
    }

    public BlockState getStateForAge(int i) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), Integer.valueOf(i));
    }
/*

    public final boolean isMaxAge(BlockState blockState) {
        return this.getAge(blockState) >= this.getMaxAge();
    }
*/

    public boolean isRandomlyTicking(BlockState blockState) {
        return !this.isMaxAge(blockState);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;

        if (SLOW_GROWTH.get()) {
            handleSlowGrowthMode(state, level, pos, random);
        } else {
            handleOriginalGrowth(state, level, pos);
        }
    }

    private void handleSlowGrowthMode(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getRawBrightness(pos, 0) >= 9) {
            // 基础生长概率 (原版的5%)
            float baseChance = 0.05F * getModifiedGrowthSpeed(this, level, pos);
            if (random.nextFloat() < baseChance) {
                growPlant(state, level, pos);
            }
        }
    }

    private static float getModifiedGrowthSpeed(Block block, BlockGetter level, BlockPos pos) {
        if (!SLOW_GROWTH.get()) {
            return 1.0F; // 原版模式不应用加速
        }

        float speedMultiplier = 1.0F;
        boolean foundFluid = false;

        // 检查周围20格内的流体方块
        for (BlockPos checkPos : BlockPos.withinManhattan(pos, 20, 3, 20)) {
            if (level.getBlockState(checkPos).getFluidState().getType() instanceof FlowingFluid fluid) {
                if (isAcceleratorFluid(fluid)) {
                    float distance = (float) Math.sqrt(pos.distSqr(checkPos));
                    speedMultiplier += getBoostByDistance(distance);
                    foundFluid = true;
                }
            }
        }

        return foundFluid ? speedMultiplier : 1.0F;
    }

    private static boolean isAcceleratorFluid(Fluid fluid) {
        return ACCELERATOR_FLUIDS.stream().anyMatch(s -> s.get() == fluid);
    }

    private static float getBoostByDistance(float distance) {
        if (distance <= 5) return 0.6F;  // +120%
        if (distance <= 10) return 0.4F; // +60%
        if (distance <= 20) return 0.3F; // +20%
        return 0.0F;
    }

    private void handleOriginalGrowth(BlockState state, ServerLevel level, BlockPos pos) {
        if (level.getRawBrightness(pos, 0) >= 9) {
            growPlant(state, level, pos);
        }
    }

    private void growPlant(BlockState state, ServerLevel level, BlockPos pos) {
        int age = getAge(state);
        if (age < getMaxAge()) {
            level.setBlock(pos, getStateForAge(age + 1), 2);
            additionalAttributes(state, level, pos, level.random);
        }
    }

    private void additionalAttributes(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if(blockState.getBlock().equals(BlockInit.bianhua.get()) ){

            Player nearestPlayer = serverLevel.getNearestPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 16,false);
            if(nearestPlayer != null){
                nearestPlayer.addEffect(new MobEffectInstance(SHModMobEffectsinit.zhiwu_bianhua.get(), 800, 0));
            }

        }

    }

    public void growCrops(Level level, BlockPos blockPos, BlockState blockState) {
        int i = this.getAge(blockState) + this.getBonemealAgeIncrease(level);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        level.setBlock(blockPos, this.getStateForAge(i), 2);
    }

    protected int getBonemealAgeIncrease(Level level) {
        return Mth.nextInt(level.random, 2, 5);
    }

    protected static float getGrowthSpeed(Block block, BlockGetter blockGetter, BlockPos blockPos) {
        float f = 1.0F;
        BlockPos blockpos = blockPos.below();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = blockGetter.getBlockState(blockpos.offset(i, 0, j));
                if (blockstate.canSustainPlant(blockGetter, blockpos.offset(i, 0, j), net.minecraft.core.Direction.UP, (net.minecraftforge.common.IPlantable) block)) {
                    f1 = 1.0F;
                    if (blockstate.isFertile(blockGetter, blockPos.offset(i, 0, j))) {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos1 = blockPos.north();
        BlockPos blockpos2 = blockPos.south();
        BlockPos blockpos3 = blockPos.west();
        BlockPos blockpos4 = blockPos.east();
        boolean flag = blockGetter.getBlockState(blockpos3).is(block) || blockGetter.getBlockState(blockpos4).is(block);
        boolean flag1 = blockGetter.getBlockState(blockpos1).is(block) || blockGetter.getBlockState(blockpos2).is(block);
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = blockGetter.getBlockState(blockpos3.north()).is(block) || blockGetter.getBlockState(blockpos4.north()).is(block) || blockGetter.getBlockState(blockpos4.south()).is(block) || blockGetter.getBlockState(blockpos3.south()).is(block);
            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return (level.getRawBrightness(pos, 0) >= 8 || level.canSeeSky(pos))
                && super.canSurvive(state, level, pos);
    }

    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (entity instanceof Ravager && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, entity)) {
            level.destroyBlock(blockPos, true, entity);
        }

        super.entityInside(blockState, level, blockPos, entity);
    }

    protected ItemLike getBaseSeedId() {
        return ItemInit.fengxinzi_seed.get();
    }

    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(this.getBaseSeedId());
    }

    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean b) {
        return !this.isMaxAge(blockState);
    }

    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        this.growCrops(serverLevel, blockPos, blockState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(AGE);
    }


    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("可在魂土上种植").withStyle(ChatFormatting.GRAY));
    }

}