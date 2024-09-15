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



    public static void bootstrap(BootstapContext<BiomeModifier> context) {

        creatmodiffier(context,blockmingtieore     ,SHPlacedFeatures.blockmingtieore     );
        creatmodiffier(context,blockheijinore      ,SHPlacedFeatures.blockheijinore      );
        creatmodiffier(context,blocklanlingjinore  ,SHPlacedFeatures.blocklanlingjinore  );
        creatmodiffier(context,blocklanhaizuanore  ,SHPlacedFeatures.blocklanhaizuanore  );
        creatmodiffier(context,blockcixuexianjinore,SHPlacedFeatures.blockcixuexianjinore);
        creatmodiffier(context,blockkongjianshiore ,SHPlacedFeatures.blockkongjianshiore );


    }

    public static void creatmodiffier(BootstapContext<BiomeModifier> context, ResourceKey<BiomeModifier> addEndSapphireOre, ResourceKey<PlacedFeature> blockheijinore){

        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(addEndSapphireOre, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(blockheijinore)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

    }


    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(SHMainBus.MOD_ID, name));
    }
}
