package hua.huase.shanhaicontinent.init;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.filuid.types.ColdiceFluidType;
import hua.huase.shanhaicontinent.filuid.types.HotyangquanFluidType;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ShanhaicontinentModFluidTypes {
    public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, SHMainBus.MOD_ID);
    public static final RegistryObject<FluidType> COLDICE_TYPE = REGISTRY.register("coldice", ColdiceFluidType::new);
    public static final RegistryObject<FluidType> HOTYANGQUAN_TYPE = REGISTRY.register("hotyangquan", HotyangquanFluidType::new);
}
