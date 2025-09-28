package hua.huase.shanhaicontinent.item.shenjie.wupin;


import hua.huase.shanhaicontinent.init.ShanhaicontinentModFluids;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ColdiceItem extends BucketItem {

    public ColdiceItem() {
        super(ShanhaicontinentModFluids.COLDICE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!player.getAbilities().instabuild) {
            if (!level.isClientSide) {
                player.displayClientMessage(
                        Component.literal("不可使用").withStyle(ChatFormatting.RED),
                        true
                );
            }
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        return super.use(level, player, hand);
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
        list.add(Component.translatable("不可获得"));
    }
}
