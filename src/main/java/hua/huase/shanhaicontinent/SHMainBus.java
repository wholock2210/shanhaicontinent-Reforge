package hua.huase.shanhaicontinent;

import hua.huase.shanhaicontinent.init.*;
import hua.huase.shanhaicontinent.init.ModelBlockEntitiesinit;
import hua.huase.shanhaicontinent.capability.CapabilityRegistryHandler;
import hua.huase.shanhaicontinent.network.NetworkHandler;
import hua.huase.shanhaicontinent.init.ModRecipesInit;
import hua.huase.shanhaicontinent.screen.ModMenuTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Random;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SHMainBus.MOD_ID)
@Mod.EventBusSubscriber(modid = SHMainBus.MOD_ID)
public class SHMainBus {
    public static final String MOD_ID = "shanhaicontinent";
    public static final Random random = new Random();


    public static final ResourceLocation HUNHUAN = new ResourceLocation(SHMainBus.MOD_ID, "textures/gui/hunhuan.png");
    public static final ResourceLocation TEXT = new ResourceLocation(SHMainBus.MOD_ID, "textures/entity/hunhe.png");


//    protected static final SimpleChannel channel = NetworkRegistry.newSimpleChannel(new ResourceLocation("ep","sync"),()->"340",(s) -> true, (s) -> true);


    public SHMainBus() throws IOException {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockInit.register(modEventBus);
        ItemInit.register(modEventBus);
        EntityInit.register(modEventBus);
        ModelBlockEntitiesinit.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipesInit.register(modEventBus);
        CreativeModTabsInit.register(modEventBus);
        SHModMobEffectsinit.register(modEventBus);

//        触发器
        new AdvenceInit();

        MinecraftForge.EVENT_BUS.register(this);
//        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(CapabilityRegistryHandler::registerCaps);

        NetworkHandler.register();

        changeAttributesIO();
    }

//    private void addCreative(BuildCreativeModeTabContentsEvent event){
//        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
//            event.accept(ModItems.TEXTITEM);
//            event.accept(ModItems.RHINO_SPANW_EGG);
//        }
//    }




    public static void changeAttributesIO()  {

        try {
            Field privateField = RangedAttribute.class.getDeclaredFields()[1];
            privateField.setAccessible(true);
            privateField.set(Attributes.MAX_HEALTH, Float.MAX_VALUE);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }


}
