package hua.huase.shanhaicontinent.worldgen;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.register.ModBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;


public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> SHENJIE_TREE = registerKey("shenjie_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> SUNSET_TREE = registerKey("sunset_tree");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?,?>> context) {


        register(context,SHENJIE_TREE,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlock.SHENJIEMU_BLOCK.get()),
                new StraightTrunkPlacer(5,4,3),
                BlockStateProvider.simple(ModBlock.SHENJIEMUSHUYE_BLOCK.get()),
                new BlobFoliagePlacer(ConstantInt.of(3),ConstantInt.of(2),3),
                new TwoLayersFeatureSize(1,0,2)).build());

        register(context,SUNSET_TREE,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlock.sunset_log.get()),
                new StraightTrunkPlacer(5,4,3),
                BlockStateProvider.simple(ModBlock.sunset_leave.get()),
                new BlobFoliagePlacer(ConstantInt.of(3),ConstantInt.of(2),3),
                new TwoLayersFeatureSize(1,0,2)).build());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,new ResourceLocation(SHMainBus.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration,F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?,?>> context,
                                                                                         ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC configuration) {
        context.register(key,new ConfiguredFeature<>(feature, configuration));
    }
}