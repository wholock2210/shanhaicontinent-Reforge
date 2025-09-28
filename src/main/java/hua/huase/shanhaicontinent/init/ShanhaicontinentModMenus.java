package hua.huase.shanhaicontinent.init;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.util.gui.StrengtheningGUIMenu;
import hua.huase.shanhaicontinent.util.hungufenjie.FenjieGUIMenu;
import hua.huase.shanhaicontinent.util.jinengmianban.JinengmianbanMenu;
import hua.huase.shanhaicontinent.world.inventory.MianbanMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ShanhaicontinentModMenus {
    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, SHMainBus.MOD_ID);

    public static final RegistryObject<MenuType<MianbanMenu>> MIANBAN = REGISTER.register("mianban",
            () -> IForgeMenuType.create(MianbanMenu::new)// 使用 IForgeMenuType.create()
    );

    public static final RegistryObject<MenuType<JinengmianbanMenu>> JINENGMIANBAN = REGISTER.register("jinengmianban",
            () -> IForgeMenuType.create(JinengmianbanMenu::new));

    public static final RegistryObject<MenuType<FenjieGUIMenu>> FENJIE_GUI = REGISTER.register("fenjie_gui",
            () -> IForgeMenuType.create(FenjieGUIMenu::new));

    public static final RegistryObject<MenuType<StrengtheningGUIMenu>> STRENGTHENING_GUI = REGISTER.register("strengthening_gui",
            () -> IForgeMenuType.create(StrengtheningGUIMenu::new));


    public static final RegistryObject<MenuType<hua.huase.shanhaicontinent.util.artifact.ArtifactMenu>> ARTIFACT = REGISTER.register("artifact",
            () -> IForgeMenuType.create(hua.huase.shanhaicontinent.util.artifact.ArtifactMenu::new));
}