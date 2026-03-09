package hua.huase.shanhaicontinent.block.shenjie;

import hua.huase.shanhaicontinent.block.Zzhaohuantaientity;
import hua.huase.shanhaicontinent.entity.shenjieentity.TianFaEntity;
import hua.huase.shanhaicontinent.entity.shenjieentity.severentity.frostprison.FrostPrisonEntity;
import hua.huase.shanhaicontinent.register.ModEntities;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class ZhaoHuanTai extends BaseEntityBlock {
    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 8, 16);

    public ZhaoHuanTai(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof Zzhaohuantaientity zhe)) {
            return InteractionResult.PASS;
        }

        if (zhe.isOnCooldown()) {
            if (!pLevel.isClientSide) {
                pPlayer.displayClientMessage(
                        Component.literal("§c召唤冷却中...还剩: " + zhe.getFormattedCooldown()),
                        false
                );
            }
            return InteractionResult.FAIL;
        }

        ItemStack itemInSlot = zhe.getInventory().getStackInSlot(0);

        if (pPlayer.isShiftKeyDown()) {
            if (ItemStack.isSameItem(itemInSlot, new ItemStack(ModItems.HEART_OF_ICE.get()))) {
                if (trySummonFrostPrison(pLevel, pPos, zhe)) {
                    return InteractionResult.SUCCESS;
                }
            }
            else if (ItemStack.isSameItem(itemInSlot, new ItemStack(ModItems.ZHAOHUANSHI.get()))) {
                playSummonEffects(pLevel, pPos);
                zhe.setLastSummonTime(pLevel.getGameTime());
                pLevel.scheduleTick(pPos, this, 10);
                zhe.clearInventory();

                if (!pLevel.isClientSide) {
                    awardAdvancementToNearbyPlayers((ServerLevel) pLevel, pPos);
                }
                return InteractionResult.SUCCESS;
            }
        }
        if (!itemInSlot.isEmpty()) {
            // 空手取出物品
            if (pPlayer.getItemInHand(pHand).isEmpty()) {
                pPlayer.getInventory().add(itemInSlot);
                zhe.clearInventory();
                return InteractionResult.SUCCESS;
            }
        }
        // 放入物品
        else if (!pLevel.isClientSide) {
            ItemStack itemInHand = pPlayer.getItemInHand(pHand);
            if (!itemInHand.isEmpty() && zhe.addItem(itemInHand)) {
                itemInHand.shrink(1);
                zhe.setChanged();
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.CONSUME;
    }

    private boolean trySummonFrostPrison(Level level, BlockPos pos, Zzhaohuantaientity be) {
        playFrostSummonEffects(level, pos);
        be.setLastSummonTime(level.getGameTime());
        level.scheduleTick(pos, this, 10);
        be.clearInventory();

        if (!level.isClientSide) {
            awardFrostAdvancement((ServerLevel) level, pos);
        }
        return true;
    }

    private void playFrostSummonEffects(Level level, BlockPos pos) {
        for (int i = 0; i < 100; i++) {
            double offsetX = (level.random.nextDouble() - 0.5) * 3.0;
            double offsetZ = (level.random.nextDouble() - 0.5) * 3.0;
            level.addParticle(ParticleTypes.SNOWFLAKE,
                    pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5,
                    offsetX, 0.2, offsetZ);
        }
        for (int i = 0; i < 30; i++) {
            level.addParticle(ParticleTypes.ITEM_SNOWBALL,
                    pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                    (level.random.nextDouble() - 0.5) * 0.5,
                    level.random.nextDouble() * 0.5,
                    (level.random.nextDouble() - 0.5) * 0.5);
        }
        level.playSound(null, pos,
                SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0F, 0.8F);
        level.playSound(null, pos,
                SoundEvents.POWDER_SNOW_BREAK, SoundSource.BLOCKS, 1.5F, 1.0F);
    }

    private void awardFrostAdvancement(ServerLevel level, BlockPos pos) {
        ResourceLocation id = new ResourceLocation("shanhaicontinent", "shenjie/baizhebu/summon_frost_prison");
        Advancement advancement = level.getServer().getAdvancements().getAdvancement(id);
        if (advancement == null) return;

        level.getEntitiesOfClass(ServerPlayer.class, new AABB(pos).inflate(10.0))
                .forEach(player -> player.getAdvancements().award(advancement, "summon_frost_prison"));
    }

    private void awardAdvancementToNearbyPlayers(ServerLevel level, BlockPos pos) {
        ResourceLocation advancementId = new ResourceLocation("shanhaicontinent", "shenjie/baizhebu/summon_tianfa");
        Advancement advancement = level.getServer().getAdvancements().getAdvancement(advancementId);
        if (advancement == null) return;
        AABB area = new AABB(pos).inflate(10.0);
        List<ServerPlayer> nearbyPlayers = level.getEntitiesOfClass(
                ServerPlayer.class,
                area,
                player -> player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) <= 100.0
        );
        for (ServerPlayer player : nearbyPlayers) {
            player.getAdvancements().award(advancement, "summon_tianfa");
        }
    }


    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof Zzhaohuantaientity zhe)) return;

        if (zhe.shouldSpawnThisTick()) {
            ItemStack lastItem = zhe.getLastUsedItem();
            BlockPos spawnPos = pPos.above(3);

            if (ItemStack.isSameItem(lastItem, new ItemStack(ModItems.ZHAOHUANSHI.get()))) {
                TianFaEntity tianFa = new TianFaEntity(ModEntities.TIANFA.get(), pLevel);
                tianFa.setPos(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
                pLevel.addFreshEntity(tianFa);
                zhe.markSpawned();
            } else if (ItemStack.isSameItem(lastItem, new ItemStack(ModItems.HEART_OF_ICE.get()))) {
                FrostPrisonEntity frostPrison = new FrostPrisonEntity(ModEntities.FROST_PRISON.get(), pLevel);
                frostPrison.setPos(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
                pLevel.addFreshEntity(frostPrison);
                zhe.markSpawned();
            }
        }
    }

    /**
     * 播放召唤特效
     */
    private void playSummonEffects(Level pLevel, BlockPos pPos) {
        // 粒子效果
        for (int i = 0; i < 100; i++) {
            double offsetX = (pLevel.random.nextDouble() - 0.5) * 2.0;
            double offsetY = (pLevel.random.nextDouble() - 0.5) * 2.0;
            double offsetZ = (pLevel.random.nextDouble() - 0.5) * 2.0;
            pLevel.addParticle(ParticleTypes.END_ROD, pPos.getX() + 0.5, pPos.getY() + 1.0, pPos.getZ() + 0.5, offsetX, offsetY, offsetZ);
        }

        // 闪电特效
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(pLevel);
        lightningBolt.moveTo(pPos.getX() + 0.5, pPos.getY() + 1, pPos.getZ() + 0.5);
        pLevel.addFreshEntity(lightningBolt);

        // 烟雾特效
        for (int i = 0; i < 50; i++) {
            double offsetX = (pLevel.random.nextDouble() - 0.5) * 2.0;
            double offsetY = (pLevel.random.nextDouble() - 0.5) * 2.0;
            double offsetZ = (pLevel.random.nextDouble() - 0.5) * 2.0;
            pLevel.addParticle(ParticleTypes.SMOKE, pPos.getX() + 0.5, pPos.getY() + 1.0, pPos.getZ() + 0.5, offsetX, offsetY, offsetZ);
        }

        // 火焰特效
        for (int i = 0; i < 50; i++) {
            double offsetX = (pLevel.random.nextDouble() - 0.5) * 2.0;
            double offsetY = (pLevel.random.nextDouble() - 0.5) * 2.0;
            double offsetZ = (pLevel.random.nextDouble() - 0.5) * 2.0;
            pLevel.addParticle(ParticleTypes.FLAME, pPos.getX() + 0.5, pPos.getY() + 1.0, pPos.getZ() + 0.5, offsetX, offsetY, offsetZ);
        }

        // 播放音效
        pLevel.playSound(null, pPos, SoundEvents.EVOKER_CAST_SPELL, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @NotNull
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new Zzhaohuantaientity(pPos, pState);
    }
}