package hua.huase.shanhaicontinent.datagen;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.datagen.util.SHFinishedRecipe;
import hua.huase.shanhaicontinent.init.BlockInit;
import hua.huase.shanhaicontinent.init.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.RegistryObject;

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
//pot造化炉合成表
        potrecipe(pWriter);
//
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 9)
//                .requires(ModBlocks.SAPPHIRE_BLOCK.get())
//                .unlockedBy(getHasName(ModBlocks.SAPPHIRE_BLOCK.get()), has(ModBlocks.SAPPHIRE_BLOCK.get()))
//                .save(pWriter);
    }

    protected static void potrecipe(Consumer<FinishedRecipe> pWriter){

//        丹药合成

        daoyaohecheng(pWriter,ItemInit.danfang_qihundan     ,ItemInit.baisuilan_fruit       ,ItemInit.danyao_qihundan       ,10,3);
        daoyaohecheng(pWriter,ItemInit.danfang_jvlingdan    ,ItemInit.fengxinzi_fruit       ,ItemInit.danyao_jvlingdan      ,20,3);
        daoyaohecheng(pWriter,ItemInit.danfang_xvanyuandan  ,ItemInit.hanxiaohua_fruit      ,ItemInit.danyao_xvanyuandan    ,30,3);
        daoyaohecheng(pWriter,ItemInit.danfang_yanghundan   ,ItemInit.hehuan_fruit          ,ItemInit.danyao_yanghundan     ,40,3);
        daoyaohecheng(pWriter,ItemInit.danfang_lingbidan    ,ItemInit.heshouwu_fruit        ,ItemInit.danyao_lingbidan      ,50,3);
        daoyaohecheng(pWriter,ItemInit.danfang_haoyuan      ,ItemInit.qiuhaitang_fruit      ,ItemInit.danyao_haoyuan        ,60,3);
        daoyaohecheng(pWriter,ItemInit.danfang_xihundan     ,ItemInit.shancha_fruit         ,ItemInit.danyao_xihundan       ,70,3);
        daoyaohecheng(pWriter,ItemInit.danfang_huangjidan   ,ItemInit.wangyoucao_fruit      ,ItemInit.danyao_huangjidan     ,80,3);
        daoyaohecheng(pWriter,ItemInit.danfang_lushendan    ,ItemInit.xiwu_fruit            ,ItemInit.danyao_lushendan      ,90,3);

        daoyaohecheng(pWriter,ItemInit.danfang_huanyuandan ,ItemInit.xunyicao_fruit      ,ItemInit.danyao_huanyuandan      ,10,3);
        daoyaohecheng(pWriter,ItemInit.danfang_fanmindan   ,ItemInit.yueguanghua_fruit   ,ItemInit.danyao_fanmindan        ,10,3);
        daoyaohecheng(pWriter,ItemInit.danfang_heqidan     ,ItemInit.zhushamei_fruit     ,ItemInit.danyao_heqidan          ,10,3);
        daoyaohecheng(pWriter,ItemInit.danfang_zengqidan   ,ItemInit.bianhua_fruit       ,ItemInit.danyao_zengqidan        ,10,3);
//九花
        daoyaohecheng(pWriter,ItemInit.danfang_jiuhua  ,ItemInit.baisuilan_seed       ,ItemInit.danyao_jiuhua    ,10,3);
        daoyaohecheng(pWriter,ItemInit.danfang_jiuhua  ,ItemInit.fengxinzi_seed       ,ItemInit.danyao_jiuhua    ,20,4);
        daoyaohecheng(pWriter,ItemInit.danfang_jiuhua  ,ItemInit.hanxiaohua_seed      ,ItemInit.danyao_jiuhua    ,30,5);
        daoyaohecheng(pWriter,ItemInit.danfang_jiuhua  ,ItemInit.hehuan_seed          ,ItemInit.danyao_jiuhua    ,40,6);
        daoyaohecheng(pWriter,ItemInit.danfang_jiuhua  ,ItemInit.heshouwu_seed        ,ItemInit.danyao_jiuhua    ,50,7);
        daoyaohecheng(pWriter,ItemInit.danfang_jiuhua  ,ItemInit.qiuhaitang_seed      ,ItemInit.danyao_jiuhua    ,60,8);
        daoyaohecheng(pWriter,ItemInit.danfang_jiuhua  ,ItemInit.shancha_seed         ,ItemInit.danyao_jiuhua    ,70,9);
        daoyaohecheng(pWriter,ItemInit.danfang_jiuhua  ,ItemInit.wangyoucao_seed      ,ItemInit.danyao_jiuhua    ,80,10);
        daoyaohecheng(pWriter,ItemInit.danfang_jiuhua  ,ItemInit.xiwu_seed            ,ItemInit.danyao_jiuhua    ,90,11);

        daoyaohecheng(pWriter,ItemInit.danfang_jiuhua  ,ItemInit.xunyicao_seed        ,ItemInit.danyao_jiuhua    ,10,3);
        daoyaohecheng(pWriter,ItemInit.danfang_jiuhua  ,ItemInit.yueguanghua_seed     ,ItemInit.danyao_jiuhua    ,10,3);
        daoyaohecheng(pWriter,ItemInit.danfang_jiuhua  ,ItemInit.zhushamei_seed       ,ItemInit.danyao_jiuhua    ,10,3);
        daoyaohecheng(pWriter,ItemInit.danfang_jiuhua  ,ItemInit.bianhua_seed         ,ItemInit.danyao_jiuhua    ,10,3);

//        装备修复
        zhuangbeixiufu(pWriter,ItemInit.mingtie_head        ,30);
        zhuangbeixiufu(pWriter,ItemInit.mingtie_chest       ,30);
        zhuangbeixiufu(pWriter,ItemInit.mingtie_feet        ,30);
        zhuangbeixiufu(pWriter,ItemInit.mingtie_legs        ,30);
        zhuangbeixiufu(pWriter,ItemInit.mingtie_axe        ,30);
        zhuangbeixiufu(pWriter,ItemInit.mingtie_hoe        ,30);
        zhuangbeixiufu(pWriter,ItemInit.mingtie_pickaxe    ,30);
        zhuangbeixiufu(pWriter,ItemInit.mingtie_shovel     ,30);
        zhuangbeixiufu(pWriter,ItemInit.mingtie_sword      ,30);

        zhuangbeixiufu(pWriter,ItemInit.heijin_head         ,40);
        zhuangbeixiufu(pWriter,ItemInit.heijin_chest        ,40);
        zhuangbeixiufu(pWriter,ItemInit.heijin_feet         ,40);
        zhuangbeixiufu(pWriter,ItemInit.heijin_legs         ,40);
        zhuangbeixiufu(pWriter,ItemInit.heijin_axe        ,40);
        zhuangbeixiufu(pWriter,ItemInit.heijin_hoe        ,40);
        zhuangbeixiufu(pWriter,ItemInit.heijin_pickaxe    ,40);
        zhuangbeixiufu(pWriter,ItemInit.heijin_shovel     ,40);
        zhuangbeixiufu(pWriter,ItemInit.heijin_sword      ,40);

        zhuangbeixiufu(pWriter,ItemInit.lanlingjin_head      ,50);
        zhuangbeixiufu(pWriter,ItemInit.lanlingjin_chest     ,50);
        zhuangbeixiufu(pWriter,ItemInit.lanlingjin_feet      ,50);
        zhuangbeixiufu(pWriter,ItemInit.lanlingjin_legs      ,50);
        zhuangbeixiufu(pWriter,ItemInit.lanlingjin_axe        ,50);
        zhuangbeixiufu(pWriter,ItemInit.lanlingjin_hoe        ,50);
        zhuangbeixiufu(pWriter,ItemInit.lanlingjin_pickaxe    ,50);
        zhuangbeixiufu(pWriter,ItemInit.lanlingjin_shovel     ,50);
        zhuangbeixiufu(pWriter,ItemInit.lanlingjin_sword      ,50);

        zhuangbeixiufu(pWriter,ItemInit.lanhaizuan_head      ,60);
        zhuangbeixiufu(pWriter,ItemInit.lanhaizuan_chest     ,60);
        zhuangbeixiufu(pWriter,ItemInit.lanhaizuan_feet      ,60);
        zhuangbeixiufu(pWriter,ItemInit.lanhaizuan_legs      ,60);
        zhuangbeixiufu(pWriter,ItemInit.lanhaizuan_axe        ,60);
        zhuangbeixiufu(pWriter,ItemInit.lanhaizuan_hoe        ,60);
        zhuangbeixiufu(pWriter,ItemInit.lanhaizuan_pickaxe    ,60);
        zhuangbeixiufu(pWriter,ItemInit.lanhaizuan_shovel     ,60);
        zhuangbeixiufu(pWriter,ItemInit.lanhaizuan_sword      ,60);

        zhuangbeixiufu(pWriter,ItemInit.cixuexianjin_head        ,80);
        zhuangbeixiufu(pWriter,ItemInit.cixuexianjin_chest       ,80);
        zhuangbeixiufu(pWriter,ItemInit.cixuexianjin_feet        ,80);
        zhuangbeixiufu(pWriter,ItemInit.cixuexianjin_legs        ,80);
        zhuangbeixiufu(pWriter,ItemInit.cixuexianjin_axe         ,80);
        zhuangbeixiufu(pWriter,ItemInit.cixuexianjin_hoe         ,80);
        zhuangbeixiufu(pWriter,ItemInit.cixuexianjin_pickaxe     ,80);
        zhuangbeixiufu(pWriter,ItemInit.cixuexianjin_shovel      ,80);
        zhuangbeixiufu(pWriter,ItemInit.cixuexianjin_sword       ,80);


//装备合成

        zhuangbeihecheng(pWriter,Items.DIAMOND_HELMET       ,ItemInit.itemmingtie ,ItemInit.mingtie_head    ,80);
        zhuangbeihecheng(pWriter,Items.DIAMOND_CHESTPLATE   ,ItemInit.itemmingtie ,ItemInit.mingtie_chest   ,80);
        zhuangbeihecheng(pWriter,Items.DIAMOND_BOOTS        ,ItemInit.itemmingtie ,ItemInit.mingtie_feet    ,80);
        zhuangbeihecheng(pWriter,Items.DIAMOND_LEGGINGS     ,ItemInit.itemmingtie ,ItemInit.mingtie_legs    ,80);
        zhuangbeihecheng(pWriter,Items.DIAMOND_AXE          ,ItemInit.itemmingtie ,ItemInit.mingtie_axe     ,80);
        zhuangbeihecheng(pWriter,Items.DIAMOND_HOE          ,ItemInit.itemmingtie ,ItemInit.mingtie_hoe     ,80);
        zhuangbeihecheng(pWriter,Items.DIAMOND_PICKAXE      ,ItemInit.itemmingtie ,ItemInit.mingtie_pickaxe ,80);
        zhuangbeihecheng(pWriter,Items.DIAMOND_SHOVEL       ,ItemInit.itemmingtie ,ItemInit.mingtie_shovel  ,80);
        zhuangbeihecheng(pWriter,Items.DIAMOND_SWORD        ,ItemInit.itemmingtie ,ItemInit.mingtie_sword   ,80);


        zhuangbeihecheng(pWriter,ItemInit.mingtie_head       ,ItemInit.itemheijin ,ItemInit.heijin_head    ,120);
        zhuangbeihecheng(pWriter,ItemInit.mingtie_chest      ,ItemInit.itemheijin ,ItemInit.heijin_chest   ,120);
        zhuangbeihecheng(pWriter,ItemInit.mingtie_feet       ,ItemInit.itemheijin ,ItemInit.heijin_feet    ,120);
        zhuangbeihecheng(pWriter,ItemInit.mingtie_legs       ,ItemInit.itemheijin ,ItemInit.heijin_legs    ,120);
        zhuangbeihecheng(pWriter,ItemInit.mingtie_axe        ,ItemInit.itemheijin ,ItemInit.heijin_axe     ,120);
        zhuangbeihecheng(pWriter,ItemInit.mingtie_hoe        ,ItemInit.itemheijin ,ItemInit.heijin_hoe     ,120);
        zhuangbeihecheng(pWriter,ItemInit.mingtie_pickaxe    ,ItemInit.itemheijin ,ItemInit.heijin_pickaxe ,120);
        zhuangbeihecheng(pWriter,ItemInit.mingtie_shovel     ,ItemInit.itemheijin ,ItemInit.heijin_shovel  ,120);
        zhuangbeihecheng(pWriter,ItemInit.mingtie_sword      ,ItemInit.itemheijin ,ItemInit.heijin_sword   ,120);

        zhuangbeihecheng(pWriter,ItemInit.heijin_head         ,ItemInit.itemlanlingjin ,ItemInit.lanlingjin_head    ,240);
        zhuangbeihecheng(pWriter,ItemInit.heijin_chest        ,ItemInit.itemlanlingjin ,ItemInit.lanlingjin_chest   ,240);
        zhuangbeihecheng(pWriter,ItemInit.heijin_feet         ,ItemInit.itemlanlingjin ,ItemInit.lanlingjin_feet    ,240);
        zhuangbeihecheng(pWriter,ItemInit.heijin_legs         ,ItemInit.itemlanlingjin ,ItemInit.lanlingjin_legs    ,240);
        zhuangbeihecheng(pWriter,ItemInit.heijin_axe          ,ItemInit.itemlanlingjin ,ItemInit.lanlingjin_axe     ,240);
        zhuangbeihecheng(pWriter,ItemInit.heijin_hoe          ,ItemInit.itemlanlingjin ,ItemInit.lanlingjin_hoe     ,240);
        zhuangbeihecheng(pWriter,ItemInit.heijin_pickaxe      ,ItemInit.itemlanlingjin ,ItemInit.lanlingjin_pickaxe ,240);
        zhuangbeihecheng(pWriter,ItemInit.heijin_shovel       ,ItemInit.itemlanlingjin ,ItemInit.lanlingjin_shovel  ,240);
        zhuangbeihecheng(pWriter,ItemInit.heijin_sword        ,ItemInit.itemlanlingjin ,ItemInit.lanlingjin_sword   ,240);

        zhuangbeihecheng(pWriter,ItemInit.lanlingjin_head     ,ItemInit.itemlanhaizuan ,ItemInit.lanhaizuan_head    ,480);
        zhuangbeihecheng(pWriter,ItemInit.lanlingjin_chest    ,ItemInit.itemlanhaizuan ,ItemInit.lanhaizuan_chest   ,480);
        zhuangbeihecheng(pWriter,ItemInit.lanlingjin_feet     ,ItemInit.itemlanhaizuan ,ItemInit.lanhaizuan_feet    ,480);
        zhuangbeihecheng(pWriter,ItemInit.lanlingjin_legs     ,ItemInit.itemlanhaizuan ,ItemInit.lanhaizuan_legs    ,480);
        zhuangbeihecheng(pWriter,ItemInit.lanlingjin_axe      ,ItemInit.itemlanhaizuan ,ItemInit.lanhaizuan_axe     ,480);
        zhuangbeihecheng(pWriter,ItemInit.lanlingjin_hoe      ,ItemInit.itemlanhaizuan ,ItemInit.lanhaizuan_hoe     ,480);
        zhuangbeihecheng(pWriter,ItemInit.lanlingjin_pickaxe  ,ItemInit.itemlanhaizuan ,ItemInit.lanhaizuan_pickaxe ,480);
        zhuangbeihecheng(pWriter,ItemInit.lanlingjin_shovel   ,ItemInit.itemlanhaizuan ,ItemInit.lanhaizuan_shovel  ,480);
        zhuangbeihecheng(pWriter,ItemInit.lanlingjin_sword    ,ItemInit.itemlanhaizuan ,ItemInit.lanhaizuan_sword   ,480);

        zhuangbeihecheng(pWriter,ItemInit.lanhaizuan_head     ,ItemInit.itemcixuexianjin ,ItemInit.cixuexianjin_head    ,960);
        zhuangbeihecheng(pWriter,ItemInit.lanhaizuan_chest    ,ItemInit.itemcixuexianjin ,ItemInit.cixuexianjin_chest   ,960);
        zhuangbeihecheng(pWriter,ItemInit.lanhaizuan_feet     ,ItemInit.itemcixuexianjin ,ItemInit.cixuexianjin_feet    ,960);
        zhuangbeihecheng(pWriter,ItemInit.lanhaizuan_legs     ,ItemInit.itemcixuexianjin ,ItemInit.cixuexianjin_legs    ,960);
        zhuangbeihecheng(pWriter,ItemInit.lanhaizuan_axe      ,ItemInit.itemcixuexianjin ,ItemInit.cixuexianjin_axe     ,960);
        zhuangbeihecheng(pWriter,ItemInit.lanhaizuan_hoe      ,ItemInit.itemcixuexianjin ,ItemInit.cixuexianjin_hoe     ,960);
        zhuangbeihecheng(pWriter,ItemInit.lanhaizuan_pickaxe  ,ItemInit.itemcixuexianjin ,ItemInit.cixuexianjin_pickaxe ,960);
        zhuangbeihecheng(pWriter,ItemInit.lanhaizuan_shovel   ,ItemInit.itemcixuexianjin ,ItemInit.cixuexianjin_shovel  ,960);
        zhuangbeihecheng(pWriter,ItemInit.lanhaizuan_sword    ,ItemInit.itemcixuexianjin ,ItemInit.cixuexianjin_sword   ,960);


//
//        pWriter.accept(
//                new SHFinishedRecipe()
//                        .setPEIFANG(    ItemInit.baisuilan_fruit.get())
//                        .setRANLIAO(    ItemInit.hunyeping.get())
//                        .setJIN(        BlockInit.blockheijinore.get())
//                        .setMU(         BlockInit.blocklanlingjinore.get())
//                        .setSHUI(       BlockInit.blocklanhaizuanore.get())
//                        .setHUO(        BlockInit.blockcixuexianjinore.get())
//                        .setTU(         Items.AIR)
//                        .setJEIGUO(     BlockInit.SOUL_BLOCK.get())
//                        .setNengliang(10)
//                        .setNum(10)
//        );
    }

    private static void daoyaohecheng(Consumer<FinishedRecipe> pWriter, RegistryObject<Item> danfangQihundan, RegistryObject<Item> baisuilanFruit, RegistryObject<Item> danyaoQihundan, int i, int i1) {

        pWriter.accept(
                new SHFinishedRecipe()
                        .setPEIFANG(    danfangQihundan.get())
                        .setRANLIAO(    ItemInit.hunyeping.get())
                        .setJIN(        baisuilanFruit.get())
                        .setMU(         baisuilanFruit.get())
                        .setSHUI(       baisuilanFruit.get())
                        .setHUO(        baisuilanFruit.get())
                        .setTU(         baisuilanFruit.get())
                        .setJEIGUO(     danyaoQihundan.get())
                        .setNengliang(i)
                        .setNum(i1)
        );
    }

    private static void zhuangbeihecheng(Consumer<FinishedRecipe> pWriter, RegistryObject<Item> itemRegistryObject, RegistryObject<Item> itemRegistryObject1, RegistryObject<Item> result, int nengliang) {

        pWriter.accept(
                new SHFinishedRecipe()
                        .setPEIFANG(    itemRegistryObject.get())
                        .setRANLIAO(    ItemInit.hunyeping.get())
                        .setJIN(        itemRegistryObject1.get())
                        .setMU(         itemRegistryObject1.get())
                        .setSHUI(       itemRegistryObject1.get())
                        .setHUO(        itemRegistryObject1.get())
                        .setTU(         itemRegistryObject1.get())
                        .setJEIGUO(     result.get())
                        .setNengliang(nengliang)
                        .setNum(1)
        );
    }
    private static void zhuangbeihecheng(Consumer<FinishedRecipe> pWriter,ItemLike like, RegistryObject<Item> itemRegistryObject1, RegistryObject<Item> result, int nengliang) {

        pWriter.accept(
                new SHFinishedRecipe()
                        .setPEIFANG(    like)
                        .setRANLIAO(    ItemInit.hunyeping.get())
                        .setJIN(        itemRegistryObject1.get())
                        .setMU(         itemRegistryObject1.get())
                        .setSHUI(       itemRegistryObject1.get())
                        .setHUO(        itemRegistryObject1.get())
                        .setTU(         itemRegistryObject1.get())
                        .setJEIGUO(     result.get())
                        .setNengliang(nengliang)
                        .setNum(1)
        );
    }

    private static void zhuangbeixiufu(Consumer<FinishedRecipe> pWriter, RegistryObject<Item> itemRegistryObject, int nengliang) {

        pWriter.accept(
                new SHFinishedRecipe()
                        .setPEIFANG(    itemRegistryObject.get())
                        .setRANLIAO(    ItemInit.hunyeping.get())
                        .setJIN(        Items.AIR)
                        .setMU(         Items.AIR)
                        .setSHUI(       Items.AIR)
                        .setHUO(        Items.AIR)
                        .setTU(         Items.AIR)
                        .setJEIGUO(     itemRegistryObject.get())
                        .setNengliang(nengliang)
                        .setNum(1)
        );
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
