package hua.huase.shanhaicontinent.datagen.level;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public final class SHPlacedFeatures {

    public static final ResourceKey<PlacedFeature> blockmingtieore      = registerKey("blockmingtieore");
    public static final ResourceKey<PlacedFeature> blockheijinore       = registerKey("blockheijinore");
    public static final ResourceKey<PlacedFeature> blocklanlingjinore   = registerKey("blocklanlingjinore");
    public static final ResourceKey<PlacedFeature> blocklanhaizuanore   = registerKey("blocklanhaizuanore");
    public static final ResourceKey<PlacedFeature> blockcixuexianjinore = registerKey("blockcixuexianjinore");
    public static final ResourceKey<PlacedFeature> blockkongjianshiore  = registerKey("blockkongjianshiore");


    private static List<PlacementModifier> orePlacement(PlacementModifier pCountPlacement, PlacementModifier pHeightRange) {
        return List.of(pCountPlacement, InSquarePlacement.spread(), pHeightRange, BiomeFilter.biome());
    }
    private static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);

        // Underground
        //TODO: Make it different from copper ore
        context.register(blockmingtieore     , new PlacedFeature(features.getOrThrow(SHConfiguredFeatures.blockmingtieore     ), commonOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80)))));
        context.register(blockheijinore      , new PlacedFeature(features.getOrThrow(SHConfiguredFeatures.blockheijinore      ), commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80)))));
        context.register(blocklanlingjinore  , new PlacedFeature(features.getOrThrow(SHConfiguredFeatures.blocklanlingjinore  ), commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80)))));
        context.register(blocklanhaizuanore  , new PlacedFeature(features.getOrThrow(SHConfiguredFeatures.blocklanhaizuanore  ), commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80)))));
        context.register(blockcixuexianjinore, new PlacedFeature(features.getOrThrow(SHConfiguredFeatures.blockcixuexianjinore), commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80)))));
        context.register(blockkongjianshiore , new PlacedFeature(features.getOrThrow(SHConfiguredFeatures.blockkongjianshiore ), commonOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80)))));

    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(SHMainBus.MOD_ID,name));
    }
}
