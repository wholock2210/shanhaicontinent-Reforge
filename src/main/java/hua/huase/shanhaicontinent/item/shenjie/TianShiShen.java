package hua.huase.shanhaicontinent.item.shenjie;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.command.ChangeAttributeCommand;
import hua.huase.shanhaicontinent.item.jineng.WuqiBase;
import hua.huase.shanhaicontinent.register.ModBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TianShiShen extends WuqiBase {
    public TianShiShen(Tier tier, int i, float v, Properties properties) {
        super(tier, i, v, properties);
    }

    //天使神增幅: 攻击力，生命值，防御力，暴击率，暴击伤害，回复，命中，闪避 增幅百分比:7%

    public float getWugong(Player player, ItemStack itemStack, float value, EquipmentSlot offhand) {
        if(offhand == EquipmentSlot.OFFHAND){
            float dengji = PlayerAttrubuteAPI.getDengji(player);
            value += value * 0.07f * (dengji / 10);
        }
        return value;
    }

    public float getWufang(Player player, ItemStack itemStack, float value, EquipmentSlot offhand) {
        if(offhand == EquipmentSlot.OFFHAND){
            float dengji = PlayerAttrubuteAPI.getDengji(player);
            value += value * 0.09f * (dengji / 10);
        }
        return value;
    }

    public float getMaxShengming(Player player, ItemStack itemStack, float value, EquipmentSlot offhand) {
        if(offhand == EquipmentSlot.OFFHAND){
            float dengji = PlayerAttrubuteAPI.getDengji(player);
            value += value * 0.1f * (dengji / 10);
        }
        return value;
    }

    public float getBaojilv(Player player, ItemStack itemStack, float value, EquipmentSlot offhand) {
        if(offhand == EquipmentSlot.OFFHAND){
            float dengji = PlayerAttrubuteAPI.getDengji(player);
            value += value * 0.07f * (dengji / 10);
        }
        return value;
    }

    public float getBaojishanghai(Player player, ItemStack itemStack, float value, EquipmentSlot offhand) {
        if(offhand == EquipmentSlot.OFFHAND){
            float dengji = PlayerAttrubuteAPI.getDengji(player);
            value += 10f * (dengji / 10);
        }
        return value;
    }

    public float getShengminghuifu(Player player, ItemStack itemStack, float value, EquipmentSlot offhand) {
        if(offhand == EquipmentSlot.OFFHAND){
            float dengji = PlayerAttrubuteAPI.getDengji(player);
            value += value * 0.1f * (dengji / 10);
        }
        return value;
    }

    public float getShanbi(Player player, ItemStack itemStack, float value, EquipmentSlot offhand) {
        if(offhand == EquipmentSlot.OFFHAND){
            float dengji = PlayerAttrubuteAPI.getDengji(player);
            value += 12f * (dengji / 10);
        }
        return value;
    }

    public float getMinghzong(Player player, ItemStack itemStack, float value, EquipmentSlot offhand) {
        if(offhand == EquipmentSlot.OFFHAND){
            float dengji = PlayerAttrubuteAPI.getDengji(player);
            value += 10f * (dengji / 10);
        }
        return value;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();

        if (level.isClientSide()) {
            return InteractionResult.PASS;
        }
        CompoundTag nbt = itemstack.getTag();
        if (nbt != null && nbt.contains("sh_playername")) {
            String itemPlayerName = nbt.getString("sh_playername");
            if (itemPlayerName.equals("literal{" + player.getName().getString() + "}")) {
                xuanbingblockyesno(level, pos, player, itemstack);
            } else {
                player.sendSystemMessage(Component.translatable("不是本人"));
                return InteractionResult.PASS;
            }
        } else {
            return InteractionResult.PASS;
        }

        return InteractionResult.PASS;
    }

    private static boolean messageSent = false;

    public static void xuanbingblockyesno(Level level, BlockPos pos, Player player, ItemStack itemstack) {
        if (level.isClientSide() || messageSent) {
            return;
        }
        messageSent = true;
        BlockState blockState = level.getBlockState(pos);
        if (blockState.is(ModBlock.HANJING_GLASS.get())) {
            int playerJsl = (int) PlayerAttrubuteAPI.getJingshenli(player);

            if (playerJsl >= 10000) {
                ChangeAttributeCommand.setJingshenli(player, playerJsl - 10000);
                level.setBlock(pos, ModBlock.SHENJIE_CSM.get().defaultBlockState(), 2);

                level.sendBlockUpdated(pos, blockState, ModBlock.SHENJIE_CSM.get().defaultBlockState(), 3);

                scheduleDelayedTask(level, pos, player);
                player.sendSystemMessage(Component.translatable("转化成功"));
            } else {
                player.sendSystemMessage(Component.translatable("不足10000"));
            }
        }
        messageSent = false;
    }


    public static void scheduleDelayedTask(LevelAccessor level, BlockPos pos , Player player) {
        if (level instanceof ServerLevel serverLevel) {
            SHMainBus.queueServerWork(600, () -> {
                blockRemovalTick(serverLevel, pos, player);
            });
        }
    }

    public static void blockRemovalTick(Level level, BlockPos pos, Player player) {
        BlockState state = level.getBlockState(pos);
        if (state.is(ModBlock.SHENJIE_CSM.get())) {
            long currentTime = level.getGameTime();
            long storedTime = getStoredTime(pos);

            if (currentTime - storedTime >= 600) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                player.sendSystemMessage(Component.translatable("传送门无"));
            }
        }
    }

    public static long getStoredTime(BlockPos pos) {
        return 0;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemStack,level,list,flag);
        list.add(Component.translatable("《天使神力》"));
        list.add(Component.translatable("天使神简介1"));
        list.add(Component.translatable("天使神简介2"));
        list.add(Component.translatable("天使神简介3"));
        }
    }