package hua.huase.shanhaicontinent.item;

import hua.huase.shanhaicontinent.capability.itemattribute.ItemAttributeCapabilityProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static hua.huase.shanhaicontinent.register.ModItems.xiancaolist;

public class BoneItem extends Item implements ItemAttribute {
    public static final String ENHANCE_COUNT_KEY = "enhance_count";
    public static final int MAX_ENHANCE_COUNT = 5;
    private int arramIndex;

    public BoneItem(Properties p_41383_) {
        super(p_41383_);
        p_41383_.stacksTo(1);
        arramIndex = 0;
    }

    public int getArramIndex() {
        return arramIndex;
    }

    public BoneItem setArramIndex(int arramIndex) {
        this.arramIndex = arramIndex;
        return this;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        ItemAttribute.appendItemAttribute(itemStack, tooltip);
        if (isXiancaoItem(itemStack.getItem())) {
            tooltip.add(Component.translatable("默认o键打开属性面板，可在属性面板装配").withStyle(ChatFormatting.WHITE));
        } else {
            int count = itemStack.getOrCreateTag().getInt(ENHANCE_COUNT_KEY);
            ChatFormatting color = count >= MAX_ENHANCE_COUNT ? ChatFormatting.RED : ChatFormatting.GREEN;
            tooltip.add(Component.literal("强化次数: " + count + "/" + MAX_ENHANCE_COUNT).withStyle(color));
            tooltip.add(Component.translatable("默认o键打开属性面板，可在属性面板装配").withStyle(ChatFormatting.WHITE));
        }
    }

    // 检查物品是否在仙草列表中
    private boolean isXiancaoItem(Item item) {
        return xiancaolist.stream().anyMatch(regObj -> regObj.get() == item);
    }

    @Override
    public ItemAttributeCapabilityProvider creatItemAttribute() {
        return new ItemAttributeCapabilityProvider<>();
    }

    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @org.jetbrains.annotations.Nullable CompoundTag nbt) {
        Tag tag = stack.getOrCreateTag().get("shanhaiitematuble");
        ItemAttributeCapabilityProvider itemAttributeCapabilityProvider = this.creatItemAttribute();
        itemAttributeCapabilityProvider.deserializeNBT((CompoundTag) tag);
        return itemAttributeCapabilityProvider;
    }
}