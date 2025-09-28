package hua.huase.shanhaicontinent.util.artifact;

import hua.huase.shanhaicontinent.block.shenjie.blockentity.ArtifactBlockEntity;
import hua.huase.shanhaicontinent.init.ModRecipesInit;
import hua.huase.shanhaicontinent.recipe.ArtifactRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class StartCraftingPacket {
    private final BlockPos pos;
    private final UUID playerUUID;

    public StartCraftingPacket(BlockPos pos, UUID playerUUID) {
        this.pos = pos;
        this.playerUUID = playerUUID;
    }

    public static void encode(StartCraftingPacket msg, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(msg.pos);
        buffer.writeUUID(msg.playerUUID);
    }

    public static StartCraftingPacket decode(FriendlyByteBuf buffer) {
        return new StartCraftingPacket(buffer.readBlockPos(), buffer.readUUID());
    }

    public static void handle(StartCraftingPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            if (sender != null && sender.level().getBlockEntity(msg.pos) instanceof ArtifactBlockEntity be) {
                Player player = sender.level().getPlayerByUUID(msg.playerUUID);
                if (player != null) {
                    if (be.canStartCrafting(player)) {
                        be.startCrafting(player);

                        ItemStack token = be.getItemHandler().getStackInSlot(0);
                        RecipeManager recipeManager = player.level().getRecipeManager();
                        SimpleContainer container = new SimpleContainer(token);
                        Optional<ArtifactRecipe> recipe = recipeManager.getRecipeFor(
                                ModRecipesInit.ARTIFACT_RECIPE.get(), container, player.level());

                        if (recipe.isPresent()) {
                            recipe.get().consumeMaterials(player);
                        } else {
                            player.sendSystemMessage(Component.literal("§c没有找到匹配的配方！"));
                        }
                    } else {
                        player.sendSystemMessage(Component.literal("§c材料不足，无法锻造！"));
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}