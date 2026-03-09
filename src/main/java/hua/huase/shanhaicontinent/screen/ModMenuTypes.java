package hua.huase.shanhaicontinent.screen;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, SHMainBus.MOD_ID);

    public static final RegistryObject<MenuType<PotMenu>> GEM_POLISHING_MENU =
            registerMenuType("pot_menu", PotMenu::new);
    public static final RegistryObject<MenuType<PlayerAttrubuteContainerMenu>> PLAYER_ATTRUBUTE_MENU =
            registerMenuType("player_attrubute_menu", PlayerAttrubuteContainerMenu::new);
//    client


    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }



    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.GEM_POLISHING_MENU.get(), PotScreen::new);
            MenuScreens.register(ModMenuTypes.PLAYER_ATTRUBUTE_MENU.get(), PlayerAttrubuteContiainerScreen::new);
        }
    }
}
