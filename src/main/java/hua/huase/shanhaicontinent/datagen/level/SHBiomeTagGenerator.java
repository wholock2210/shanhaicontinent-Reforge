package hua.huase.shanhaicontinent.datagen.level;

import hua.huase.shanhaicontinent.SHMainBus;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.tags.BiomeTags.IS_END;
import static net.minecraft.tags.BiomeTags.IS_NETHER;

public class SHBiomeTagGenerator extends BiomeTagsProvider {
    public static final TagKey<Biome> SH_GUFENGXIAOWU = TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(SHMainBus.MOD_ID, "has_structure/gufenxiaowu"));


    public SHBiomeTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, SHMainBus.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(SH_GUFENGXIAOWU).addTag(BiomeTags.IS_OVERWORLD)
                .addTag(IS_END)
        ;
//        tag(BiomesInit.TEXT_BIOMES, Tags.Biomes.IS_PLAINS);

    }

    @SafeVarargs
    private void tag(ResourceKey<Biome> biome, TagKey<Biome>... tags)
    {
        for(TagKey<Biome> key : tags)
        {
            tag(key).add(biome);
        }
    }
    @Override
    public String getName() {
        return "Ice and Fire Biome Tags";
    }
}
