package hua.huase.shanhaicontinent.item;

import hua.huase.shanhaicontinent.capability.AttrubuteAPI;
import hua.huase.shanhaicontinent.capability.itemattribute.ItemAttributeCapabilityProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class BoneItem extends Item implements ItemAttribute{
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

    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        ItemAttribute.appendItemAttribute(itemStack, list);

        list.add(Component.translatable("默认o键打开属性面板，可在属性面板装配").withStyle(ChatFormatting.WHITE));
    }

    @Override
    public ItemAttributeCapabilityProvider creatItemAttribute() {
        return new ItemAttributeCapabilityProvider<>();
    }
}
