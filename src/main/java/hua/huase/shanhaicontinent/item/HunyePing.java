package hua.huase.shanhaicontinent.item;

import hua.huase.shanhaicontinent.init.BlockInit;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

import static hua.huase.shanhaicontinent.SHMainBus.random;
import static net.minecraft.sounds.SoundEvents.EXPERIENCE_ORB_PICKUP;

public class HunyePing extends Item implements Nengliang{
    public HunyePing(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos blockpos = useOnContext.getClickedPos();
        Player player = useOnContext.getPlayer();
        BlockState blockstate = level.getBlockState(blockpos);
        ItemStack itemstack = useOnContext.getItemInHand();

        if (blockstate.getBlock() instanceof FarmBlock farmBlock && this.getNengliang(player,itemstack)>0) {
            if (player instanceof ServerPlayer) {
                this.setNengliang(player,itemstack,this.getNengliang(player,itemstack)-random.nextInt(5)-3);
                level.setBlock(blockpos, BlockInit.SOUL_BLOCK.get().defaultBlockState(), 11);

                }

            player.playSound(EXPERIENCE_ORB_PICKUP, 1.1F, 0.9F);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }


    private int maxnengliang =100;


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


    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {

        list.add(Component.translatable("能量",this.getNengliang(null,itemStack),this.getMaxnengliang()).withStyle(ChatFormatting.YELLOW));
        list.add(Component.translatable("右键<魂核>收集能量；右键<耕地>消耗能量，转化<魂土>").withStyle(ChatFormatting.WHITE));
    }

    public int getMaxnengliang() {
        return maxnengliang;
    }

    public HunyePing setMaxnengliang(int maxnengliang) {
        this.maxnengliang = maxnengliang;
        return this;
    }
}
