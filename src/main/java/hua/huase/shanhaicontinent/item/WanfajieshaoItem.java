package hua.huase.shanhaicontinent.item;

import hua.huase.shanhaicontinent.capability.playerattribute.PlayerHunHuanAPI;
import hua.huase.shanhaicontinent.item.jineng.Jineng;
import hua.huase.shanhaicontinent.screen.PlayerAttrubuteContainerMenu;
import hua.huase.shanhaicontinent.screen.WanfajieshaoScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class WanfajieshaoItem extends Item {
    public WanfajieshaoItem(Properties p) {
        super(p);
    }

    @OnlyIn(Dist.CLIENT)
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(level.isClientSide){

            Minecraft.getInstance().setScreen(new WanfajieshaoScreen(Component.translatable("玩法介绍").withStyle(ChatFormatting.WHITE)));


//            player.openItemGui();
//            NetworkHooks.openScreen(sender, new SimpleMenuProvider(
//                    (containerId, playerInventory, player) -> new PlayerAttrubuteContainerMenu(containerId, playerInventory),
//                    Component.translatable("menu.title.examplemod.mymenu")
//            ));

        }


        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

}
