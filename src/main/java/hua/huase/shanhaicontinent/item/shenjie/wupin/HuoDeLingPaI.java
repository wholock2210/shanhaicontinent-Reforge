package hua.huase.shanhaicontinent.item.shenjie.wupin;

import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import java.util.Random;

public class HuoDeLingPaI extends Item {
    public HuoDeLingPaI(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {

            if (context.getPlayer() instanceof ServerPlayer player) {
                dropRandomItems(player);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    private void dropRandomItems(Player pPlayer) {
        Item[] items = new Item[]{
                ModItems.LINGPAI_HAISHEN.get(),
                ModItems.LINGPAI_TIANSHISHEN.get(),
                ModItems.LINGPAI_XIULUOSHEN.get(),
                ModItems.LINGPAI_LUOCHASHEN.get()
        };
        int[] probabilities = new int[]{25, 25, 25, 25};
        Random random = new Random();
        int randomValue = random.nextInt(100);
        ItemStack itemStack = null;

        if (randomValue < probabilities[0]) {
            itemStack = new ItemStack(items[0]);
        } else if (randomValue < probabilities[0] + probabilities[1]) {
            itemStack = new ItemStack(items[1]);
        } else if (randomValue < probabilities[0] + probabilities[1] + probabilities[2]) {
            itemStack = new ItemStack(items[2]);
        } else {
            itemStack = new ItemStack(items[3]);
        }

        CompoundTag nbt = itemStack.getOrCreateTag();
        String playerName = pPlayer.getName().getString();
        nbt.putString("sh_playername", "literal{" + playerName + "}");
        itemStack.setTag(nbt);

        if (!pPlayer.getInventory().add(itemStack)) {
            pPlayer.drop(itemStack, false);
        }

        broadcastItemAcquisition(pPlayer, itemStack);
    }


    private void broadcastItemAcquisition(Player pPlayer, ItemStack itemStack) {
        pPlayer.sendSystemMessage(Component.translatable("已获得令牌"));
    }
}