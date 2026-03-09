package hua.huase.shanhaicontinent.init;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.filuid.ColdiceFluid;
import hua.huase.shanhaicontinent.filuid.HotyangquanFluid;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

public class ShanhaicontinentModFluids {
    public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS, SHMainBus.MOD_ID);
    public static final RegistryObject<FlowingFluid> COLDICE = REGISTRY.register("coldice", ColdiceFluid.Source::new);
    public static final RegistryObject<FlowingFluid> FLOWING_COLDICE = REGISTRY.register("flowing_coldice", ColdiceFluid.Flowing::new);
    public static final RegistryObject<FlowingFluid> HOTYANGQUAN = REGISTRY.register("hotyangquan", HotyangquanFluid.Source::new);
    public static final RegistryObject<FlowingFluid> FLOWING_HOTYANGQUAN = REGISTRY.register("flowing_hotyangquan", HotyangquanFluid.Flowing::new);


    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class FluidsClientSideHandler {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(COLDICE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_COLDICE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(HOTYANGQUAN.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_HOTYANGQUAN.get(), RenderType.translucent());
        }
    }
}
