package hua.huase.shanhaicontinent.block.shenjie.fluid;


import hua.huase.shanhaicontinent.init.SHModMobEffectsinit;
import hua.huase.shanhaicontinent.init.ShanhaicontinentModFluids;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.LiquidBlock;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public class ColdiceBlock extends LiquidBlock {
    private static final Map<ItemEntity, Integer> itemTimerMap = new WeakHashMap<>();

    public ColdiceBlock() {
        super(ShanhaicontinentModFluids.COLDICE::get,
                BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(100f)
                        .lightLevel(s -> 15).noCollission().noLootTable().liquid()
                        .pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
    }

    @Override
    public ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        Player player = level instanceof Level ? ((Level)level).getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), -1, null) : null;

        if (player != null && player.getAbilities().instabuild) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            return new ItemStack(ModItems.COLDICE_BUCKET.get());
        } else {
            return new ItemStack(ModItems.ICECOLD_BUCKET.get());
        }
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);

        if (pLevel.isClientSide) return;
        if (pEntity instanceof LivingEntity livingEntity) {
            // 检查是否是创造模式的玩家
            if (pEntity instanceof Player player && player.getAbilities().instabuild) {
                return;
            }

            livingEntity.addEffect(new MobEffectInstance(
                    SHModMobEffectsinit.COLDICE.get(),
                    1200, // 60秒
                    0,    // 效果等级
                    false, // 无环境粒子
                    true   // 显示图标
            ));
        }
        else if (pEntity instanceof ItemEntity itemEntity) {
            handleItemConversion(pLevel, itemEntity);
        }
    }

    private void handleItemConversion(Level level, ItemEntity itemEntity) {
        // 跳过已经是冰方块的掉落物
        if (itemEntity.getItem().getItem() == Items.ICE) {
            return;
        }

        // 更新计时器
        int timer = itemTimerMap.getOrDefault(itemEntity, 0) + 1;
        itemTimerMap.put(itemEntity, timer);

        // 30秒（600 ticks）后转换
        if (timer >= 200) {
            convertToIceItem(level, itemEntity);
            itemTimerMap.remove(itemEntity);
        }
    }

    private void convertToIceItem(Level level, ItemEntity originalItem) {
        ItemStack iceStack = new ItemStack(Items.ICE, originalItem.getItem().getCount());
        ItemEntity iceItem = new ItemEntity(
                level,
                originalItem.getX(),
                originalItem.getY(),
                originalItem.getZ(),
                iceStack
        );

        iceItem.setDeltaMovement(originalItem.getDeltaMovement());
        iceItem.setPickUpDelay(40); // 设置短暂的拾取延迟

        originalItem.discard();
        level.addFreshEntity(iceItem);

        level.playSound(null, originalItem.blockPosition(),
                SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 0.8f, 1.0f);

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE,
                    originalItem.getX(), originalItem.getY() + 0.5, originalItem.getZ(),
                    15, 0.3, 0.2, 0.3, 0.1);
        }
    }

    // 服务器tick更新方法
    public static void onServerTick(ServerLevel level) {
        Iterator<Map.Entry<ItemEntity, Integer>> iterator = itemTimerMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ItemEntity, Integer> entry = iterator.next();
            ItemEntity itemEntity = entry.getKey();

            // 检查掉落物是否有效或仍在流体中
            if (!itemEntity.isAlive() || !isInColdiceFluid(level, itemEntity)) {
                iterator.remove();
            }
        }
    }

    private static boolean isInColdiceFluid(Level level, ItemEntity itemEntity) {
        BlockPos pos = itemEntity.blockPosition();
        return level.getFluidState(pos).getType().isSame(ShanhaicontinentModFluids.COLDICE.get());
    }
}