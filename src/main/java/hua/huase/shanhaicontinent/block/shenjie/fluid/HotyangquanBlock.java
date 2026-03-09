package hua.huase.shanhaicontinent.block.shenjie.fluid;

import hua.huase.shanhaicontinent.init.SHModMobEffectsinit;
import hua.huase.shanhaicontinent.init.ShanhaicontinentModFluids;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.phys.AABB;
import java.util.List;
public class HotyangquanBlock extends LiquidBlock {
    public HotyangquanBlock() {
        super(ShanhaicontinentModFluids.HOTYANGQUAN,
                BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(100f)
                        .lightLevel(s -> 15).noCollission().noLootTable().liquid()
                        .pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);

        if (pLevel.isClientSide) return;

        // 处理生物实体 - 赋予灼热效果
        if (pEntity instanceof LivingEntity livingEntity) {
            // 检查是否是创造模式的玩家
            if (pEntity instanceof Player player && player.getAbilities().instabuild) {
                return;
            }

            livingEntity.addEffect(new MobEffectInstance(
                    SHModMobEffectsinit.HOT_YANGQUAN.get(),
                    1200, // 60秒
                    0,    // 效果等级
                    false, // 无环境粒子
                    true   // 显示图标
            ));
        }
        // 处理掉落物 - 直接销毁（不再尝试点燃）
        else if (pEntity instanceof ItemEntity itemEntity) {
            destroyItemEntity(pLevel, pPos, itemEntity);
        }
    }

    @Override
    public ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        Player player = level instanceof Level ? ((Level)level).getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), -1, null) : null;

        if (player != null && player.getAbilities().instabuild) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            return new ItemStack(ModItems.HOTYANGQUAN_BUCKET.get());
        } else {
            return new ItemStack(ModItems.HOT_BUCKET.get());
        }
    }

    private void destroyItemEntity(Level level, BlockPos pos, ItemEntity itemEntity) {
        // 立即销毁物品实体（不经过着火阶段）
        itemEntity.discard();

        // 播放销毁效果
        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5f, 1.0f);

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.LAVA,
                    itemEntity.getX(), itemEntity.getY() + 0.5, itemEntity.getZ(),
                    8, 0.2, 0.2, 0.2, 0.05);
            serverLevel.sendParticles(ParticleTypes.SMOKE,
                    itemEntity.getX(), itemEntity.getY() + 0.5, itemEntity.getZ(),
                    3, 0.1, 0.1, 0.1, 0.05);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // 直接查找并销毁所有掉入的ItemEntity（不再检查着火状态）
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class,
                new AABB(pos).inflate(0.5));

        for (ItemEntity item : items) {
            destroyItemEntity(level, pos, item);
        }
    }
}