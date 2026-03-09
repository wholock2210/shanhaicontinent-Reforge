package hua.huase.shanhaicontinent.item;

import hua.huase.shanhaicontinent.datagen.level.SHStructureTagGenerator;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class SHEyeitem  extends Item {
    public SHEyeitem(Item.Properties pProperties) {
        super(pProperties);
    }


    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.NONE);
        if (blockhitresult.getType() == HitResult.Type.BLOCK && pLevel.getBlockState(blockhitresult.getBlockPos()).is(Blocks.END_PORTAL_FRAME)) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            if (pLevel instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel)pLevel;
                BlockPos blockpos = serverlevel.findNearestMapStructure(SHStructureTagGenerator.LANDMARK, pPlayer.blockPosition(), 100, false);
                if (blockpos != null) {
                    EyeOfEnder eyeofender = new EyeOfEnder(pLevel, pPlayer.getX(), pPlayer.getY(0.5D), pPlayer.getZ());
                    eyeofender.setItem(itemstack);
                    eyeofender.signalTo(blockpos);
                    pLevel.gameEvent(GameEvent.PROJECTILE_SHOOT, eyeofender.position(), GameEvent.Context.of(pPlayer));
                    pLevel.addFreshEntity(eyeofender);
                    if (pPlayer instanceof ServerPlayer) {
                        CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayer)pPlayer, blockpos);
                    }

                    pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                    pLevel.levelEvent((Player)null, 1003, pPlayer.blockPosition(), 0);
                    if (!pPlayer.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    pPlayer.swing(pHand, true);
                    return InteractionResultHolder.success(itemstack);
                }
            }

            return InteractionResultHolder.consume(itemstack);
        }
    }
}
