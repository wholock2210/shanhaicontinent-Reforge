package hua.huase.shanhaicontinent.datagen.level;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class SHBiomeModifier {
    public static final ResourceKey<BiomeModifier> blockmingtieore      = registerKey("blockmingtieore");
    public static final ResourceKey<BiomeModifier> blockheijinore       = registerKey("blockheijinore");
    public static final ResourceKey<BiomeModifier> blocklanlingjinore   = registerKey("blocklanlingjinore");
    public static final ResourceKey<BiomeModifier> blocklanhaizuanore   = registerKey("blocklanhaizuanore");
    public static final ResourceKey<BiomeModifier> blockcixuexianjinore = registerKey("blockcixuexianjinore");
    public static final ResourceKey<BiomeModifier> blockkongjianshiore  = registerKey("blockkongjianshiore");

    public static final ResourceKey<BiomeModifier> blocklanhaizuanore_end  = registerKey("blocklanhaizuanore_end");
    public static final ResourceKey<BiomeModifier> blockcixuexianjinore_nether  = registerKey("blockcixuexianjinore_nether");



    public static void bootstrap(BootstapContext<BiomeModifier> context) {

        creatmodiffierOver(context,blockmingtieore     ,SHPlacedFeatures.blockmingtieore     );
        creatmodiffierOver(context,blockheijinore      ,SHPlacedFeatures.blockheijinore      );
        creatmodiffierOver(context,blocklanlingjinore  ,SHPlacedFeatures.blocklanlingjinore  );
        creatmodiffierOver(context,blocklanhaizuanore  ,SHPlacedFeatures.blocklanhaizuanore  );
        creatmodiffierOver(context,blockcixuexianjinore,SHPlacedFeatures.blockcixuexianjinore);
        creatmodiffierOver(context,blockkongjianshiore ,SHPlacedFeatures.blockkongjianshiore );



        creatmodiffierEnd(context,blocklanhaizuanore_end  ,SHPlacedFeatures.blocklanhaizuanore_end  );
        creatmodiffierNether(context,blockcixuexianjinore_nether,SHPlacedFeatures.blockcixuexianjinore_nether);

    }

    public static void creatmodiffierOver(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> addEndSapphireOre, ResourceKey<PlacedFeature> blockheijinore){

        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(addEndSapphireOre, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(blockheijinore)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

    }
    public static void creatmodiffierEnd(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> addEndSapphireOre, ResourceKey<PlacedFeature> blockheijinore){

        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(addEndSapphireOre, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(blockheijinore)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

    }
    public static void creatmodiffierNether(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> addEndSapphireOre, ResourceKey<PlacedFeature> blockheijinore){

        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(addEndSapphireOre, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeatures.getOrThrow(blockheijinore)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

    }


    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(SHMainBus.MOD_ID, name));
    }
}
