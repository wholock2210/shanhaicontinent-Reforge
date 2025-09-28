package hua.huase.shanhaicontinent.item;

import hua.huase.shanhaicontinent.entity.hunhe.HunheEntity;
import hua.huase.shanhaicontinent.init.BlockInit;
import hua.huase.shanhaicontinent.init.EntityInit;
import hua.huase.shanhaicontinent.init.ModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static hua.huase.shanhaicontinent.SHMainBus.random;

public class HunyePing extends Item implements ICurioItem,Nengliang {
    private final int absorbRangeLevel;
    private static final Map<Integer, Integer> RANGE_MAP = Map.of(
            1, 3,
            2, 4,
            3, 6,
            4, 8,
            5, 10,
            6, 13,
            7, 15,
            8, 20,
            9, 25
    );
    private static final int HEIGHT_RANGE = 5;

    public HunyePing(Properties properties,int absorbRangeLevel) {
        super(properties);
        this.absorbRangeLevel = Math.min(9, Math.max(1, absorbRangeLevel));
    }

    public int getAbsorbRange() {
        return RANGE_MAP.getOrDefault(absorbRangeLevel, 3);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && player.getAbilities().mayBuild && !player.isShiftKeyDown()) {
            CompoundTag tag = stack.getOrCreateTag();
            boolean isAutoAbsorbing = !tag.getBoolean("auto_absorb");
            tag.putBoolean("auto_absorb", isAutoAbsorbing);

            player.sendSystemMessage(Component.translatable(
                    isAutoAbsorbing ? "自动吸收已开启 (半径: %s格)" : "自动吸收已关闭",
                    getAbsorbRange()
            ).withStyle(isAutoAbsorbing ? ChatFormatting.GREEN : ChatFormatting.RED));

            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player)) return;
        if (player.level().isClientSide()) return;
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.getBoolean("auto_absorb")) return;
        if (getNengliang(player, stack) >= getMaxnengliang()) return;
        if (player.level().getGameTime() % 10 != 0) return;
        int range = getAbsorbRange();
        AABB area = new AABB(
                player.getX() - range, player.getY() - HEIGHT_RANGE, player.getZ() - range,
                player.getX() + range, player.getY() + HEIGHT_RANGE, player.getZ() + range
        );
        List<Entity> hunheEntities = player.level().getEntitiesOfClass(
                Entity.class, area,
                e -> e.getType() == EntityInit.HUNHE.get()
        );
        if (!hunheEntities.isEmpty()) {
            hunheEntities.sort(Comparator.comparingDouble(e -> e.distanceToSqr(player)));
            HunheEntity nearest = (HunheEntity) hunheEntities.get(0);
            float finalValue = ModConfig.enablebalance.get() ?
                    nearest.getValue() * 0.5f :
                    nearest.getValue();
            setNengliang(
                    player,
                    stack,
                    getNengliang(player, stack) + (int) finalValue
            );
            nearest.discard();
            player.level().playSound(
                    null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.7F, 1.0F
            );
        }
    }

    // 右键方块（耕地转换）
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        BlockState state = level.getBlockState(pos);

        if (state.getBlock() instanceof FarmBlock && getNengliang(player, stack) > 0) {
            if (!level.isClientSide) {
                setNengliang(player, stack, getNengliang(player, stack) - (random.nextInt(15) + 13));
                level.setBlock(pos, BlockInit.SOUL_BLOCK.get().defaultBlockState(), 11);
            }
            player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.1F, 0.9F);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;

        Player player = event.player;
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        for (ItemStack stack : new ItemStack[]{mainHand, offHand}) {
            if (stack.getItem() instanceof HunyePing hunyePing) {
                CompoundTag tag = stack.getOrCreateTag();
                if (!tag.getBoolean("auto_absorb") || hunyePing.getNengliang(player, stack) >= hunyePing.getMaxnengliang()) {
                    continue;
                }
                if (player.level().getGameTime() % 10 != 0) continue;

                int range = hunyePing.getAbsorbRange();
                AABB area = new AABB(
                        player.getX() - range, player.getY() - HEIGHT_RANGE, player.getZ() - range,
                        player.getX() + range, player.getY() + HEIGHT_RANGE, player.getZ() + range
                );
                List<Entity> hunheEntities = player.level().getEntitiesOfClass(
                        Entity.class, area,
                        e -> e.getType() == EntityInit.HUNHE.get()
                );

                if (!hunheEntities.isEmpty()) {
                    hunheEntities.sort(Comparator.comparingDouble(e -> e.distanceToSqr(player)));
                    HunheEntity nearest = (HunheEntity) hunheEntities.get(0);
                    float finalValue = ModConfig.enablebalance.get() ?
                            nearest.getValue() * 0.5f :
                            nearest.getValue();
                    hunyePing.setNengliang(
                            player,
                            stack,
                            hunyePing.getNengliang(player, stack) + (int) finalValue
                    );

                    nearest.discard();

                    player.level().playSound(
                            null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.7F, 1.0F
                    );
                }
            }
        }
    }

    private int maxnengliang =1200;


    @Override
    public void setNengliang(Player player, ItemStack itemStack, int value) {
        CompoundTag orCreateTag = itemStack.getOrCreateTag();

        if(value>=maxnengliang){
            orCreateTag.putInt("sh_nengliang",maxnengliang);
            if(player !=null){
                player.sendSystemMessage(Component.translatable("能量已满").withStyle(ChatFormatting.GREEN));
            }
        }else if(value<=0){
            orCreateTag.putInt("sh_nengliang",0);
            if(player !=null){
                player.sendSystemMessage(Component.translatable("能量耗尽").withStyle(ChatFormatting.DARK_GRAY));
            }
        }else {
            orCreateTag.putInt("sh_nengliang",value);
        }

    }

    @Override
    public int getNengliang(Player player, ItemStack itemStack) {
        CompoundTag orCreateTag = itemStack.getOrCreateTag();
        return orCreateTag.getInt("sh_nengliang");
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.yourmod.hunye_ping.energy",
                        this.getNengliang(null, stack),
                        this.getMaxnengliang())
                .withStyle(ChatFormatting.YELLOW));

        boolean isAutoOn = stack.getOrCreateTag().getBoolean("auto_absorb");
        tooltip.add(Component.translatable("tooltip.yourmod.hunye_ping.auto_absorb",
                isAutoOn ? Component.translatable("tooltip.yourmod.on").withStyle(ChatFormatting.GREEN)
                        : Component.translatable("tooltip.yourmod.off").withStyle(ChatFormatting.RED))
        );

        tooltip.add(Component.translatable("tooltip.yourmod.hunye_ping.range",
                        Component.literal(String.valueOf(getAbsorbRange())).withStyle(ChatFormatting.GOLD))
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.translatable("tooltip.yourmod.hunye_ping.fushou")
                .withStyle(ChatFormatting.WHITE));

        tooltip.add(Component.translatable("tooltip.yourmod.hunye_ping.usage_air")
                .withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.translatable("tooltip.yourmod.hunye_ping.usage_farmland")
                .withStyle(ChatFormatting.WHITE));
    }

    public int getMaxnengliang() {
        return maxnengliang;
    }

    public HunyePing setMaxnengliang(int maxnengliang) {
        this.maxnengliang = maxnengliang;
        return this;
    }
}
