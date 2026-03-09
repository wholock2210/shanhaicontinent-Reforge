package hua.huase.shanhaicontinent.world.structure;


import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.world.structure.gufengxiaowu.GufengxiaowuStructure;
import hua.huase.shanhaicontinent.world.structure.jvxiange.JvxiangeStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SHStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, SHMainBus.MOD_ID);

    public static final RegistryObject<StructureType<GufengxiaowuStructure>> gufengxiaowu01 =
            registerType("gufengxiaowu01", () -> () -> GufengxiaowuStructure.CODEC);

    public static final RegistryObject<StructureType<JvxiangeStructure>> jvxiange =
            registerType("jvxiange", () -> () -> JvxiangeStructure.CODEC);

    private static <P extends Structure> RegistryObject<StructureType<P>> registerType(String name, Supplier<StructureType<P>> factory) {
        return STRUCTURE_TYPES.register(name, factory);
    }
}