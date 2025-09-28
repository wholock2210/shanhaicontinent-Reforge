package hua.huase.shanhaicontinent.item.shenjie.severitem;

import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapability;
import hua.huase.shanhaicontinent.capability.monsterattribute.MonsterAttributeCapabilityProvider;
import hua.huase.shanhaicontinent.entity.hunhuan.HunhuanEntity;
import hua.huase.shanhaicontinent.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;


public class GodGiveHunhuan extends Item {
    private final int nianxian;

    public GodGiveHunhuan(Properties pProperties, int nianxian) {
        super(pProperties.stacksTo(1).fireResistant());
        this.nianxian = nianxian;
    }

    public int getNianxian() {
        return nianxian;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (world.isClientSide) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
        BlockHitResult hitResult = Item.getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);
        BlockPos blockPos = hitResult.getBlockPos();
        Vec3 spawnPos = Vec3.atBottomCenterOf(blockPos.above());
        HunhuanEntity hunhuan = EntityInit.HUNHUAN.get().create(world);
        if (hunhuan == null) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        hunhuan.setPos(spawnPos);
        hunhuan.getCapability(MonsterAttributeCapabilityProvider.CAPABILITY)
                .ifPresent(cap -> {
                    MonsterAttributeCapability newCap = MonsterAttributeCapability.createWithNianxian(this.nianxian);
                    newCap.setShenci(true);
                    cap.deserializeNBT(newCap.serializeNBT());
                });
        world.addFreshEntity(hunhuan);
        if (!player.isCreative()) {
            player.getItemInHand(hand).shrink(1);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}