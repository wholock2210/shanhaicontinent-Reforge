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


    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_Text = createKey("ore_text");

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String p_255643_) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SHMainBus.MOD_ID,p_255643_));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {


        RuleTest ruletest1 = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest ruletest2 = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreConfiguration.TargetBlockState> list =
                List.of(
                    OreConfiguration.target(ruletest1, BlockInit.SOUL_BLOCK.get().defaultBlockState()),
                    OreConfiguration.target(ruletest2, BlockInit.SOUL_BLOCK.get().defaultBlockState())
                );

        FeatureUtils.register(context, ORE_Text, Feature.ORE, new OreConfiguration(list, 9));

    }
}
