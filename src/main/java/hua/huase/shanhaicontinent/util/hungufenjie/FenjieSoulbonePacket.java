package hua.huase.shanhaicontinent.util.hungufenjie;

import hua.huase.shanhaicontinent.init.ItemInit;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class FenjieSoulbonePacket {
    public FenjieSoulbonePacket() {
    }
    public static void encode(FenjieSoulbonePacket packet, FriendlyByteBuf buf) {}
    public static FenjieSoulbonePacket decode(FriendlyByteBuf buf) {
        return new FenjieSoulbonePacket();
    }

    public static void handle(FenjieSoulbonePacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            if (!(player.containerMenu instanceof FenjieGUIMenu menu)) return;

            if (menu.slots.get(0).hasItem()) {
                ItemStack inputStack = menu.slots.get(0).getItem();
                Item inputItem = inputStack.getItem();

                // 判断是不是魂骨
                if (ItemInit.HUNGULIST.stream().anyMatch(reg -> reg.get() == inputItem)) {
                    CompoundTag inputTag = inputStack.getTag();
                    if (inputTag != null && inputTag.contains("shanhaiitematuble", Tag.TAG_COMPOUND)) {
                        CompoundTag attributesTag = inputTag.getCompound("shanhaiitematuble");  // 获取属性子Tag
                        Random random = new Random();
                        ItemStack outputStack = new ItemStack(ModItems.hungu_jinghua.get());
                        CompoundTag outputTag = new CompoundTag();
                        CompoundTag essenceTag = outputStack.getOrCreateTag();
                        if (!essenceTag.contains("shanhaiitematuble", Tag.TAG_COMPOUND)) {
                            essenceTag.put("shanhaiitematuble", new CompoundTag());
                        }
                        CompoundTag essenceAttributesTag = essenceTag.getCompound("shanhaiitematuble");
                        Map<String, Double> inheritChances = new HashMap<>();
                        inheritChances.put("wugong", 0.30);
                        inheritChances.put("wufang", 0.80);
                        inheritChances.put("wuchuan", 0.20);
                        inheritChances.put("zhenshang", 0.20);
                        inheritChances.put("maxshengming", .80);
                        inheritChances.put("baojishanghai", 0.40);
                        inheritChances.put("baojilv", 0.40);
                        inheritChances.put("kangbao", 0.80);
                        inheritChances.put("xixue", 0.60);
                        inheritChances.put("mingzhong", 0.55);
                        inheritChances.put("shanbi", 0.15);

                        boolean hasInherited = false;
                        for (Map.Entry<String, Double> entry : inheritChances.entrySet()) {
                            String key = entry.getKey();
                            double chance = entry.getValue();

                            if (attributesTag.contains(key)) {
                                if (random.nextDouble() <= chance) {
                                    float originalValue = attributesTag.getFloat(key);
                                    float inheritedValue = originalValue * 0.10f;
                                    int finalValue = Math.max(1, (int) inheritedValue);
                                    essenceAttributesTag.putInt(key, finalValue);
                                    hasInherited = true;
                                }
                            }
                        }
                        if (hasInherited) {
                            outputStack.setTag(essenceTag);
                        }
                        if (!player.getInventory().add(outputStack)) {
                            player.drop(outputStack, false);
                        }
                        menu.slots.get(0).set(ItemStack.EMPTY);
                        menu.broadcastChanges();
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }

}
