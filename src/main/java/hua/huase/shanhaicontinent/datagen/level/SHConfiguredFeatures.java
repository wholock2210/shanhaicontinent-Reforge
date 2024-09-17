package hua.huase.shanhaicontinent.datagen.level;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.init.BlockInit;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class SHConfiguredFeatures {


    public static final ResourceKey<ConfiguredFeature<?, ?>> blockmingtieore         = createKey("blockmingtieore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> blockheijinore          = createKey("blockheijinore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> blocklanlingjinore      = createKey("blocklanlingjinore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> blocklanhaizuanore      = createKey("blocklanhaizuanore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> blockcixuexianjinore    = createKey("blockcixuexianjinore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> blockkongjianshiore     = createKey("blockkongjianshiore");

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String p_255643_) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SHMainBus.MOD_ID,p_255643_));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {


        RuleTest ruletest1 = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest ruletest2 = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreConfiguration.TargetBlockState> list1 =
                List.of(
                    OreConfiguration.target(ruletest1, BlockInit.blockkongjianshiore.get().defaultBlockState()),
                    OreConfiguration.target(ruletest2, BlockInit.blockkongjianshiore.get().defaultBlockState())
                );
        List<OreConfiguration.TargetBlockState> list2 =
                List.of(
                    OreConfiguration.target(ruletest1, BlockInit.blockcixuexianjinore.get().defaultBlockState()),
                    OreConfiguration.target(ruletest2, BlockInit.blockcixuexianjinore.get().defaultBlockState())
                );
        List<OreConfiguration.TargetBlockState> list3 =
                List.of(
                    OreConfiguration.target(ruletest1, BlockInit.blocklanhaizuanore.get().defaultBlockState()),
                    OreConfiguration.target(ruletest2, BlockInit.blocklanhaizuanore.get().defaultBlockState())
                );
        List<OreConfiguration.TargetBlockState> list4 =
                List.of(
                    OreConfiguration.target(ruletest1, BlockInit.blocklanlingjinore.get().defaultBlockState()),
                    OreConfiguration.target(ruletest2, BlockInit.blocklanlingjinore.get().defaultBlockState())
                );
        List<OreConfiguration.TargetBlockState> list5 =
                List.of(
                    OreConfiguration.target(ruletest1, BlockInit.blockheijinore.get().defaultBlockState()),
                    OreConfiguration.target(ruletest2, BlockInit.blockheijinore.get().defaultBlockState())
                );
        List<OreConfiguration.TargetBlockState> list6 =
                List.of(
                    OreConfiguration.target(ruletest1, BlockInit.blockmingtieore.get().defaultBlockState()),
                    OreConfiguration.target(ruletest2, BlockInit.blockmingtieore.get().defaultBlockState())
                );

        FeatureUtils.register(context, blockmingtieore     , Feature.ORE, new OreConfiguration(list6, 7));
        FeatureUtils.register(context, blockheijinore      , Feature.ORE, new OreConfiguration(list5, 6));
        FeatureUtils.register(context, blocklanlingjinore  , Feature.ORE, new OreConfiguration(list4, 5));
        FeatureUtils.register(context, blocklanhaizuanore  , Feature.ORE, new OreConfiguration(list3, 4));
        FeatureUtils.register(context, blockcixuexianjinore, Feature.ORE, new OreConfiguration(list2, 3));
        FeatureUtils.register(context, blockkongjianshiore , Feature.ORE, new OreConfiguration(list1, 6));

    }
}
