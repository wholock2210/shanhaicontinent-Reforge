package hua.huase.shanhaicontinent.util.procedures;

import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CraftingPacket {
    private final ResourceLocation itemKey;

    public CraftingPacket(ResourceLocation itemKey) {
        this.itemKey = itemKey;
    }

    public static void encode(CraftingPacket packet, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(packet.itemKey);
    }

    public static CraftingPacket decode(FriendlyByteBuf buffer) {
        return new CraftingPacket(buffer.readResourceLocation());
    }

    public static void handle(CraftingPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player != null) {
                // 在服务器端执行消耗材料和生成物品的逻辑
                handleCrafting(player, packet.itemKey);
            }
        });
        context.get().setPacketHandled(true);
    }

    private static void handleCrafting(ServerPlayer player, ResourceLocation itemKey) {
        if (itemKey.equals(new ResourceLocation("shanhaicontinent:lingpai_haishen"))) {
            if (checkPlayerHasMaterials(player, ModItems.haiyang_zhixin.get(), 1, ModItems.XUANBING_STONE.get(), 64, ModItems.MOWU_JINGHUA.get(), 64, ModItems.zixiaoxianjin_ingot.get(), 20)) {
                consumeMaterials(player, ModItems.haiyang_zhixin.get(), 1, ModItems.XUANBING_STONE.get(), 64, ModItems.MOWU_JINGHUA.get(), 64, ModItems.zixiaoxianjin_ingot.get(), 20);
                ItemStack outputItem = new ItemStack(ModItems.HAISHEN_SANCHAJI.get());
                addNbtTag(outputItem, player.getDisplayName().getString());
                player.getInventory().add(outputItem);
            }
        } else if (itemKey.equals(new ResourceLocation("shanhaicontinent:lingpai_tianshishen"))) {
            if (checkPlayerHasMaterials(player, ModItems.tianshi_zhiguan.get(), 1, ModItems.XUANBING_STONE.get(), 64, ModItems.MOWU_JINGHUA.get(), 64, ModItems.zixiaoxianjin_ingot.get(), 20)) {
                consumeMaterials(player, ModItems.tianshi_zhiguan.get(), 1, ModItems.XUANBING_STONE.get(), 64, ModItems.MOWU_JINGHUA.get(), 64, ModItems.zixiaoxianjin_ingot.get(), 20);
                ItemStack outputItem = new ItemStack(ModItems.TIANSHISHEN_JIAN.get());
                addNbtTag(outputItem, player.getDisplayName().getString());
                player.getInventory().add(outputItem);
            }
        } else if(itemKey.equals(new ResourceLocation("shanhaicontinent:lingpai_xiuluoshen"))) {
            if (checkPlayerHasMaterials(player, ModItems.xiuluo_zhiyin.get(), 1, ModItems.XUANBING_STONE.get(), 64, ModItems.MOWU_JINGHUA.get(), 64, ModItems.zixiaoxianjin_ingot.get(), 20)) {
                consumeMaterials(player, ModItems.xiuluo_zhiyin.get(), 1, ModItems.XUANBING_STONE.get(), 64, ModItems.MOWU_JINGHUA.get(), 64, ModItems.zixiaoxianjin_ingot.get(), 20);
                ItemStack outputItem = new ItemStack(ModItems.XIULUOSHEN_JIAN.get());
                addNbtTag(outputItem, player.getDisplayName().getString());
                player.getInventory().add(outputItem);
            }
        }else if(itemKey.equals(new ResourceLocation("shanhaicontinent:lingpai_luochashen"))) {
            if (checkPlayerHasMaterials(player, ModItems.luocha_zhitong.get(), 1, ModItems.XUANBING_STONE.get(), 64, ModItems.MOWU_JINGHUA.get(), 64, ModItems.zixiaoxianjin_ingot.get(), 20)) {
                consumeMaterials(player, ModItems.luocha_zhitong.get(), 1, ModItems.XUANBING_STONE.get(), 64, ModItems.MOWU_JINGHUA.get(), 64, ModItems.zixiaoxianjin_ingot.get(), 20);
                ItemStack outputItem = new ItemStack(ModItems.LUOCHASHEN_MOLIAN.get());
                addNbtTag(outputItem, player.getDisplayName().getString());
                player.getInventory().add(outputItem);
            }
        }
    }

    private static boolean checkPlayerHasMaterials(ServerPlayer player, Item item1, int count1, Item item2, int count2, Item item3, int count3, Item item4, int count4) {
        return player.getInventory().countItem(item1) >= count1 &&
                player.getInventory().countItem(item2) >= count2 &&
                player.getInventory().countItem(item3) >= count3 &&
                player.getInventory().countItem(item4) >= count4;
    }

    private static void consumeMaterials(ServerPlayer player, Item item1, int count1, Item item2, int count2, Item item3, int count3, Item item4, int count4) {
        player.getInventory().clearOrCountMatchingItems(stack -> stack.getItem() == item1, count1, player.inventoryMenu.getCraftSlots());
        player.getInventory().clearOrCountMatchingItems(stack -> stack.getItem() == item2, count2, player.inventoryMenu.getCraftSlots());
        player.getInventory().clearOrCountMatchingItems(stack -> stack.getItem() == item3, count3, player.inventoryMenu.getCraftSlots());
        player.getInventory().clearOrCountMatchingItems(stack -> stack.getItem() == item4, count4, player.inventoryMenu.getCraftSlots());
    }

    public static void addNbtTag(ItemStack itemStack, String playerName) {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("sh_playername", "literal{" + playerName + "}");
        itemStack.setTag(nbt);
    }
}