package hua.huase.shanhaicontinent.world.structure;


import com.google.common.collect.Maps;
import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.world.structure.GufengxiaowuPiecees;
import hua.huase.shanhaicontinent.world.structure.GufengxiaowuStructure;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.EndCityPieces;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;
import java.util.function.Supplier;

public class SHStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, SHMainBus.MOD_ID);

    public static final RegistryObject<StructureType<GufengxiaowuStructure>> gufengxiaowu01 =
            registerType("gufengxiaowu01", () -> () -> GufengxiaowuStructure.CODEC);

    private static <P extends Structure> RegistryObject<StructureType<P>> registerType(String name, Supplier<StructureType<P>> factory) {
        return STRUCTURE_TYPES.register(name, factory);
    }

}
