package hua.huase.shanhaicontinent.init;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.block.entityblock.flowerblock.SHFlowerBlock;
import hua.huase.shanhaicontinent.block.entityblock.pot.PotBlock;
import hua.huase.shanhaicontinent.block.entityblock.soulblock.SoulBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SHMainBus.MOD_ID);

    public static final RegistryObject<Block> SAPPHIRE_BLOCK = registerBlock("text_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> SOUL_BLOCK = registerBlock("soul_block", () -> new SoulBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).randomTicks().strength(0.6F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> POT_BLOCK = registerBlock("pot_block", () -> new PotBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> baisuilan = registerFlowerBlock("baisuilan", () -> new SHFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> fengxinzi = registerFlowerBlock("fengxinzi", () -> new SHFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> hanxiaohua = registerFlowerBlock("hanxiaohua", () -> new SHFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> hehuan = registerFlowerBlock("hehuan", () -> new SHFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> heshouwu = registerFlowerBlock("heshouwu", () -> new SHFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> qiuhaitang = registerFlowerBlock("qiuhaitang", () -> new SHFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> shancha = registerFlowerBlock("shancha", () -> new SHFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> wangyoucao = registerFlowerBlock("wangyoucao", () -> new SHFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> xiwu = registerFlowerBlock("xiwu", () -> new SHFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> xunyicao = registerFlowerBlock("xunyicao", () -> new SHFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> yueguanghua = registerFlowerBlock("yueguanghua", () -> new SHFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> zhushamei = registerFlowerBlock("zhushamei", () -> new SHFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> bianhua = registerFlowerBlock("bianhua", () -> new SHFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));


    public static ArrayList<RegistryObject<Block>> FLOWERLIST = new ArrayList<>();
    static {
        FLOWERLIST.add(baisuilan);
        FLOWERLIST.add(fengxinzi);
        FLOWERLIST.add(hanxiaohua);
        FLOWERLIST.add(hehuan);
        FLOWERLIST.add(heshouwu);
        FLOWERLIST.add(qiuhaitang);
        FLOWERLIST.add(shancha);
        FLOWERLIST.add(wangyoucao);
        FLOWERLIST.add(xiwu);
        FLOWERLIST.add(xunyicao);
        FLOWERLIST.add(yueguanghua);
        FLOWERLIST.add(zhushamei);
        FLOWERLIST.add(bianhua);
    }



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerFlowerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
//        registerFlowerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerFlowerBlockItem(String name, RegistryObject<T> block) {
        ItemInit.ITEMS.register(name+"_fruit", () -> new Item(new Item.Properties()));
        return ItemInit.ITEMS.register(name+"_seed", () -> new ItemNameBlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
