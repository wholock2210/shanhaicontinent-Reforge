package hua.huase.shanhaicontinent.datagen;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.init.BlockInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class SHBlockTagsProvider extends IntrinsicHolderTagsProvider<Block> {


    public static final TagKey<Block> end_stone_replace = BlockTags.create(new ResourceLocation("end_stone_replace", SHMainBus.MOD_ID));
    public SHBlockTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider) {
        super(pOutput, Registries.BLOCK, pLookupProvider, (p_255627_) -> {
            return p_255627_.builtInRegistryHolder().key();
        });
    }
//挖掘等级
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                BlockInit.blockmingtieore.get(),
                BlockInit.blockheijinore.get(),
                BlockInit.blocklanlingjinore.get(),
                BlockInit.blocklanhaizuanore.get(),
                BlockInit.blockcixuexianjinore.get(),
                BlockInit.blockkongjianshiore.get(),

                BlockInit.blockmingtie_block      .get(),
                BlockInit.blockheijin_block       .get(),
                BlockInit.blocklanlingjin_block   .get(),
                BlockInit.blocklanhaizuan_block   .get(),
                BlockInit.blockcixuexianjin_block .get(),
                BlockInit.blockkongjianshi_block  .get(),
                BlockInit.POT_BLOCK.get()

        );

         this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
                BlockInit.blockmingtieore.get(),
                BlockInit.blockheijinore.get(),
                BlockInit.blocklanlingjinore.get(),
                BlockInit.blocklanhaizuanore.get(),
                BlockInit.blockcixuexianjinore.get(),
                BlockInit.blockkongjianshiore.get(),

                BlockInit.blockmingtie_block      .get(),
                BlockInit.blockheijin_block       .get(),
                BlockInit.blocklanlingjin_block   .get(),
                BlockInit.blocklanhaizuan_block   .get(),
                BlockInit.blockcixuexianjin_block .get(),
                BlockInit.blockkongjianshi_block  .get()
        );

//         this.tag(end_stone_replace).add(
//                 Blocks.END_STONE
//        );
         this.tag(BlockTags.INFINIBURN_OVERWORLD).add(
                 Blocks.END_STONE
        );

    }
}
