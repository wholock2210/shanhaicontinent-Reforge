package hua.huase.shanhaicontinent.datagen.level;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.STRUCTURE, SHStructures::bootstrapSTRUCTURE)
            .add(Registries.STRUCTURE_SET, SHStructures::bootstrapSTRUCTURE_SET)

            .add(Registries.CONFIGURED_FEATURE, SHConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, SHPlacedFeatures::bootstrap)
//            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, SHBiomeModifierSerializers::bootstrap)
//            .add(ForgeRegistries.Keys.BIOMES, SHBiome::bootstrap)
//            .add(ForgeRegistries.Keys.BIOMES, ModBiomes::createBiomes)
//            .add(Registries.BIOME_SOURCE, SHBiomeSource::createBiomes)
//            .add(Registries.FLAT_LEVEL_GENERATOR_PRESET, FlatLevelGeneratorPresets::bootstrap)



//            .add(Registries.NOISE_SETTINGS, NoiseGenSettingRegistry::bootstrap)
//            .add(Registries.DIMENSION_TYPE, DimensionTypeRegistry::bootstrap)
//            .add(Registries.LEVEL_STEM, LevelStemRegistry::bootstrap)
            ;

    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER, Set.of("minecraft", SHMainBus.MOD_ID));
    }
}

