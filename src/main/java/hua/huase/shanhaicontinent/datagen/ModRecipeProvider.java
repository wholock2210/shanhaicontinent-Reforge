package hua.huase.shanhaicontinent.datagen;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.datagen.util.SHFinishedRecipe;
import hua.huase.shanhaicontinent.init.BlockInit;
import hua.huase.shanhaicontinent.init.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
//    private static final List<ItemLike> SAPPHIRE_SMELTABLES = List.of(ModItems.RAW_SAPPHIRE.get(),
//            ModBlocks.SAPPHIRE_ORE.get(), ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), ModBlocks.NETHER_SAPPHIRE_ORE.get(),
//            ModBlocks.END_STONE_SAPPHIRE_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

//
//        oreSmelting(pWriter, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 200, "sapphire");
//        oreBlasting(pWriter, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 100, "sapphire");
//
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.POT_BLOCK.get())
//                .pattern("   ")
                .pattern("SXS")
                .pattern("SCS")
                .define('X', Items.DIAMOND)
                .define('S', Items.GOLD_INGOT)
                .define('C', Items.IRON_INGOT)
                .unlockedBy(getHasName(BlockInit.POT_BLOCK.get()), has(Items.IRON_INGOT))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemInit.hunyeping.get())
//                .pattern("   ")
                .pattern("SXS")
                .pattern("SCS")
                .define('X', Items.ENDER_PEARL)
                .define('S', Blocks.GLASS)
                .define('C', Items.EMERALD)
                .unlockedBy(getHasName(ItemInit.hunyeping.get()), has(Items.ENDER_PEARL))
                .save(pWriter);

        potrecipe(pWriter);

        shoreSmelting(pWriter,BlockInit.blockmingtieore.get()       ,ItemInit.itemmingtie     .get(),3,200);
        shoreSmelting(pWriter,BlockInit.blockheijinore.get()        ,ItemInit.itemheijin      .get(),4,300);
        shoreSmelting(pWriter,BlockInit.blocklanlingjinore.get()    ,ItemInit.itemlanlingjin  .get(),5,400);
        shoreSmelting(pWriter,BlockInit.blocklanhaizuanore.get()    ,ItemInit.itemlanhaizuan  .get(),6,500);
        shoreSmelting(pWriter,BlockInit.blockcixuexianjinore.get()  ,ItemInit.itemcixuexianjin.get(),7,600);
        shoreSmelting(pWriter,BlockInit.blockkongjianshiore.get()   ,ItemInit.itemkongjianshi.get(),4,200);


        compositeAndDecomposeBlock(pWriter,ItemInit.itemmingtie     .get(),BlockInit.blockmingtie_block.get()     );
        compositeAndDecomposeBlock(pWriter,ItemInit.itemheijin      .get(),BlockInit.blockheijin_block.get()      );
        compositeAndDecomposeBlock(pWriter,ItemInit.itemlanlingjin  .get(),BlockInit.blocklanlingjin_block.get()  );
        compositeAndDecomposeBlock(pWriter,ItemInit.itemlanhaizuan  .get(),BlockInit.blocklanhaizuan_block.get()  );
        compositeAndDecomposeBlock(pWriter,ItemInit.itemcixuexianjin.get(),BlockInit.blockcixuexianjin_block.get());
        compositeAndDecomposeBlock(pWriter,ItemInit.itemkongjianshi .get(),BlockInit.blockkongjianshi_block.get() );

//
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 9)
//                .requires(ModBlocks.SAPPHIRE_BLOCK.get())
//                .unlockedBy(getHasName(ModBlocks.SAPPHIRE_BLOCK.get()), has(ModBlocks.SAPPHIRE_BLOCK.get()))
//                .save(pWriter);
    }

    protected static void potrecipe(Consumer<FinishedRecipe> pWriter){

        pWriter.accept(
                new SHFinishedRecipe()
                        .setPEIFANG(    ItemInit.baisuilan_fruit.get())
                        .setRANLIAO(    BlockInit.SOUL_BLOCK.get())
                        .setJIN(        BlockInit.SOUL_BLOCK.get())
                        .setMU(         BlockInit.SOUL_BLOCK.get())
                        .setSHUI(       BlockInit.SOUL_BLOCK.get())
                        .setHUO(        BlockInit.SOUL_BLOCK.get())
                        .setTU(         BlockInit.SOUL_BLOCK.get())
                        .setJEIGUO(     BlockInit.SOUL_BLOCK.get())
                        .setNum(10)
        );



//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemInit.hunyeping.get())
////                .pattern("   ")
//                .pattern("SXS")
//                .pattern("SCS")
//                .define('X', Items.ENDER_PEARL)
//                .define('S', Blocks.GLASS)
//                .define('C', Items.EMERALD)
//                .unlockedBy(getHasName(ItemInit.hunyeping.get()), has(Items.ENDER_PEARL))
//                .save(pWriter,SHMainBus.MOD_ID + ":pot/"  + getItemName(Blocks.CHISELED_STONE_BRICKS));

    }

//    合成块
    protected static void compositeAndDecomposeBlock(Consumer<FinishedRecipe> pWriter , ItemLike like , ItemLike pResult){

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pResult)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', like)
                .unlockedBy(getHasName(pResult), has(like))
                .save(pWriter,SHMainBus.MOD_ID + ":" + getItemName(pResult) + "_composite" + "_" + getItemName(like));



//
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, like, 9)
                .requires(pResult)
                .unlockedBy(getHasName(like), has(pResult))
                .save(pWriter,SHMainBus.MOD_ID + ":" + getItemName(like) + "_decompose" + "_" + getItemName(pResult));
    }



//矿物熔炼
    protected static void shoreSmelting(Consumer<FinishedRecipe> pWriter , ItemLike like , ItemLike pResult, int i, int i1){
        SimpleCookingRecipeBuilder.generic(Ingredient.of(like), RecipeCategory.MISC, pResult,
                        i, i1, RecipeSerializer.SMELTING_RECIPE)
                .group("sapphire").unlockedBy(getHasName(like), has(like))
                .save(pWriter,  SHMainBus.MOD_ID + ":" + getItemName(pResult) + "_from_smelting" + "_" + getItemName(like));

    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                    pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  SHMainBus.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
