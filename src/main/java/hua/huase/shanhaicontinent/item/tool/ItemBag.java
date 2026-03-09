package hua.huase.shanhaicontinent.item.tool;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.ShulkerItemStackInvWrapper;
import org.jetbrains.annotations.Nullable;

public class ItemBag extends Item {
    public ItemBag(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
    {
        var ret = ShulkerItemStackInvWrapper.createDefaultProvider(stack);
        return ret;
    }
}
