package hua.huase.shanhaicontinent.block;

import hua.huase.shanhaicontinent.block.entityblock.pot.PotBlockEntity;
import hua.huase.shanhaicontinent.block.grower.DivineTribulationManager;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.capability.playerattribute.PlayerAttrubuteAPI;
import hua.huase.shanhaicontinent.register.ModItems;
import hua.huase.shanhaicontinent.util.ShuXingSet;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class DengXianTai extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    private static final Map<UUID, Boolean> TRIBULATION_PLAYERS = new HashMap<>();

    public DengXianTai(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PotBlockEntity) {
                ((PotBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            // 检查是否正在渡劫
            if (DivineTribulationManager.isPlayerInTribulation(pPlayer)) {
                pPlayer.sendSystemMessage(Component.literal("§c正在渡劫中，不可再次点击！").withStyle(ChatFormatting.RED));
                return InteractionResult.FAIL;
            }

            int playerLevel = (int) PlayerAttrubuteAPI.getDengji(pPlayer);
            boolean isHunHuanOpen = pPlayer.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY)
                    .map(cap -> cap.getHunhuankuaiguan() >= 0)
                    .orElse(false);

            if (playerLevel == 99) {
                if (isHunHuanOpen) {
                    DivineTribulationManager.startTribulation(pPlayer, pPos, this);
                    pPlayer.sendSystemMessage(Component.literal("§a开始接受神界天雷淬体..."));
                    pPlayer.sendSystemMessage(Component.literal("§a期间请不要关闭武魂！"));
                } else {
                    pPlayer.sendSystemMessage(Component.literal("§e请先开启武魂").withStyle(ChatFormatting.YELLOW));
                }
            } else if (playerLevel >= 100) {
                pPlayer.sendSystemMessage(Component.literal("§c已经封神不可再次封神").withStyle(ChatFormatting.RED));
            } else {
                pPlayer.sendSystemMessage(Component.literal("§c等级不足，需99级方可渡封神").withStyle(ChatFormatting.RED));
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    public void doublePlayerAttributes(Player player) {
        float currentWugong = PlayerAttrubuteAPI.getWugong(player);
        float currentWufang = PlayerAttrubuteAPI.getWufang(player);
        float currentMaxshengming = PlayerAttrubuteAPI.getMaxshengming(player);
        float currentBaojishanghai = PlayerAttrubuteAPI.getBaojishanghai(player);
        float currentBaojilv = PlayerAttrubuteAPI.getBaojilv(player);
        float currentKangbao = PlayerAttrubuteAPI.getKangbao(player);
        float currentMaxJingshenli = PlayerAttrubuteAPI.getMaxjingshenli(player);
        float currentWuchuan = PlayerAttrubuteAPI.getWuchuan(player);
        float currentXixue = PlayerAttrubuteAPI.getXixue(player);
        float currentDengji = PlayerAttrubuteAPI.getDengji(player);
        float currentShengminghuifu = PlayerAttrubuteAPI.getShengminghuifu(player);
        float currentShanbi = PlayerAttrubuteAPI.getShanbi(player);
        float currentMaxJingyan = PlayerAttrubuteAPI.getMaxJingyan(player);
        float currentMinghzhong = PlayerAttrubuteAPI.getMinghzong(player);

        ShuXingSet.setWugong(player, (int) (currentWugong * 1.5));
        ShuXingSet.setMaxjingyan(player, (int) (currentMaxJingyan * 2));
        ShuXingSet.setMaxjingshenli(player, (int) (currentMaxJingshenli * 1.5));
        ShuXingSet.setMaxshengming(player, (int) (currentMaxshengming * 1.8));
        ShuXingSet.setKangbao(player,(int) (currentKangbao * 1.3));
        ShuXingSet.setDengji(player,(int) (currentDengji + 1));
        ShuXingSet.setWuchuan(player,(int) (currentWuchuan + 30));
        ShuXingSet.setBaojilv(player,(int) (currentBaojilv + 20));
        ShuXingSet.setXixue(player,(int) (currentXixue + 50));
        ShuXingSet.setWufang(player,(int) (currentWufang * 2));
        ShuXingSet.setBaojishanghai(player,(int) (currentBaojishanghai + 60));
        ShuXingSet.setShanbi(player,(int) (currentShanbi + 30));
        ShuXingSet.setShengminghuifu(player,(int) (currentShengminghuifu * 300));
        ShuXingSet.setMinghzhong(player,(int) (currentMinghzhong + 30));

        player.sendSystemMessage(Component.literal("您通过了考验，已封神，属性得到巨额提升！").withStyle(ChatFormatting.YELLOW));
    }

    public void dropRandomItems(Player player) {
        Item[] items = new Item[]{
                ModItems.LINGPAI_HAISHEN.get(),
                ModItems.LINGPAI_TIANSHISHEN.get(),
                ModItems.LINGPAI_XIULUOSHEN.get(),
                ModItems.LINGPAI_LUOCHASHEN.get()
        };

        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                for (Item item : items) {
                    if (stack.getItem() == item) {
                        inventory.setItem(i, ItemStack.EMPTY);
                        break;
                    }
                }
            }
        }

        int[] probabilities = new int[]{25, 25, 25, 25};
        Random random = new Random();
        int randomValue = random.nextInt(100);
        ItemStack itemStack;

        final String shenwei;
        if (randomValue < probabilities[0]) {
            itemStack = new ItemStack(items[0]);
            shenwei = "1";
        } else if (randomValue < probabilities[0] + probabilities[1]) {
            itemStack = new ItemStack(items[1]);
            shenwei = "2";
        } else if (randomValue < probabilities[0] + probabilities[1] + probabilities[2]) {
            itemStack = new ItemStack(items[2]);
            shenwei = "3";
        } else {
            itemStack = new ItemStack(items[3]);
            shenwei = "4";
        }

        player.getCapability(PlayerAttributeCapabilityProvider.CAPABILITY).ifPresent(capability -> {
            capability.setShenwei(shenwei);
        });

        CompoundTag nbt = itemStack.getOrCreateTag();
        String playerName = player.getName().getString();
        nbt.putString("sh_playername", "literal{" + playerName + "}");
        itemStack.setTag(nbt);

        if (!player.getInventory().add(itemStack)) {
            player.drop(itemStack, false);
        }
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PotBlockEntity(pPos, pState);
    }
}