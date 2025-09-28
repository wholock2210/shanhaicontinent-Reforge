package hua.huase.shanhaicontinent.tab;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ShenJieModTab
{
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SHMainBus.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TEST_TAB = TABS.register("test_tab",() -> new ShenJieTab().output());

}
