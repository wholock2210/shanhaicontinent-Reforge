package hua.huase.shanhaicontinent.register;

import hua.huase.shanhaicontinent.SHMainBus;
import hua.huase.shanhaicontinent.block.*;
import hua.huase.shanhaicontinent.block.shenjie.*;
import hua.huase.shanhaicontinent.block.shenjie.fluid.ColdiceBlock;
import hua.huase.shanhaicontinent.block.shenjie.fluid.HotyangquanBlock;
import hua.huase.shanhaicontinent.block.shenjie.wuyongblock.JYQiang;
import hua.huase.shanhaicontinent.block.shenjie.wuyongblock.Louti;
import hua.huase.shanhaicontinent.block.shenjie.wuyongblock.SunsetLeaver;
import hua.huase.shanhaicontinent.block.shenjie.wuyongblock.SunsetLogBlock;
import hua.huase.shanhaicontinent.worldgen.tree.ShenJieTreeSaplingBlock;
import hua.huase.shanhaicontinent.worldgen.tree.ShenJie_tree;
import hua.huase.shanhaicontinent.worldgen.tree.SunsetTree;
import hua.huase.shanhaicontinent.worldgen.tree.SunsetTreeSapling;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlock {
    public static final DeferredRegister<Block> Blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, SHMainBus.MOD_ID);

    public static final RegistryObject<Block> SHENLAN_BLOCK = Blocks.register("shenlan_block", () -> new Block(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.BEDROCK)));

    public static final RegistryObject<Block> DENGXIANTAI_BLOCK = Blocks.register("dengxiantai_block", () -> new DengXianTai(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.BEDROCK).noOcclusion()));

    public static final RegistryObject<Block> GRASSSJ_BLOCK = Blocks.register("grasssj_block", () -> new grasssj_block(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.GRASS_BLOCK)));

    public static final RegistryObject<Block> XUANBING_BLOCK = Blocks.register("xuanbing_block", () -> new Block(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.OBSIDIAN)));

    public static final RegistryObject<Block> SHENJIEMU_BLOCK = Blocks.register("shenjiemu_block", () -> new Block(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.BIRCH_WOOD)));

    public static final RegistryObject<Block> SHENJIEMUSHUYE_BLOCK = Blocks.register("shenjiemushuye_block", () -> new shenjiemushuye_block(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.BIRCH_LEAVES)));

    public static final RegistryObject<Block> SHENJIESHUSHUMIAO_SAPLING = Blocks.register("shenjieshushumiao_sapling", () -> new ShenJieTreeSaplingBlock(new ShenJie_tree(), BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.OAK_SAPLING)));

    public static final RegistryObject<Block> XUANBINGBLOCK = Blocks.register("xuanbingblock", () -> new Block(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.OBSIDIAN)));

    public static final RegistryObject<Block> SHENJIE_CSM = Blocks.register("shenjie_csm", () -> new shenjie_csm(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.BEDROCK).noCollission()));

    public static final RegistryObject<Block> JIANYING_BLOCK = Blocks.register("jianying_block", () -> new Block(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.BEDROCK)));

    public static final RegistryObject<Block> JIANYING_QIANG = Blocks.register("jianying_qiang", () -> new JYQiang(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.BEDROCK)));

    public static final RegistryObject<Block> JIANYING_LOUTI = Blocks.register("jianying_louti", Louti::new);

    public static final RegistryObject<Block> ZHAOHUANTAI_BLOCK = Blocks.register("zhaohuantai_block", () -> new ZhaoHuanTai(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.BEDROCK).noOcclusion()));

    public static final RegistryObject<Block> CIXUE_ORE = Blocks.register("cixue_ore", () -> new Block(Block.Properties.copy(net.minecraft.world.level.block.Blocks.OBSIDIAN)));

    public static final RegistryObject<Block> HANJING_GLASS = Blocks.register("hanjing_glass", () -> new Block(Block.Properties.copy(net.minecraft.world.level.block.Blocks.OBSIDIAN)));

    public static final RegistryObject<Block> artifact_workbench = Blocks.register("artifact_workbench", () -> new ArtifactBlock(Block.Properties.copy(net.minecraft.world.level.block.Blocks.OBSIDIAN).noOcclusion()));

    public static final RegistryObject<Block> zixiaoxianjin = Blocks.register("zixiaoxianjin", () -> new Block(Block.Properties.copy(net.minecraft.world.level.block.Blocks.OBSIDIAN)));

    public static final RegistryObject<Block> HUNGU_FENJIE = Blocks.register("hungu_fenjie", () -> new HunGuFenJie(Block.Properties.copy(net.minecraft.world.level.block.Blocks.OBSIDIAN).noOcclusion()));

    public static final RegistryObject<Block> COLDICE = Blocks.register("coldice", ColdiceBlock::new);
    public static final RegistryObject<Block> HOTYANGQUAN = Blocks.register("hotyangquan", HotyangquanBlock::new);

    public static final RegistryObject<Block> FERTILE_DIRT = Blocks.register("fertile_dirt", () -> new FertileDirtBlock(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.GRASS_BLOCK)));

    public static final RegistryObject<Block> sunset_log = Blocks.register("sunset_log", () -> new SunsetLogBlock(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.CHERRY_WOOD)));
    public static final RegistryObject<Block> sunset_leave = Blocks.register("sunset_leave", () -> new SunsetLeaver(BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block> sunset_sapling = Blocks.register("sunset_sapling", () -> new SunsetTreeSapling(new SunsetTree(), BlockBehaviour.Properties.copy(net.minecraft.world.level.block.Blocks.OAK_SAPLING)));

    public static final RegistryObject<Block> BLACKSOIL = Blocks.register("blacksoil",
            () -> new Block(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).requiresCorrectToolForDrops().lightLevel(state -> 0).emissiveRendering((state, level, pos) -> false).destroyTime(9999999f).explosionResistance(9999999f)
            ) {
                @Override
                public PushReaction getPistonPushReaction(BlockState state) {
                    return PushReaction.BLOCK;
                }
                @Override
                public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
                }
            });

    public static final RegistryObject<Block> ZIXIAOXIANJIN_BLOCK = Blocks.register("zixiaoxianjin_block", () -> new Block(Block.Properties.copy(net.minecraft.world.level.block.Blocks.OBSIDIAN)));

    public static final RegistryObject<Block> STRENGTHENING_TABLE = Blocks.register("strengthening_table", () -> new StrengtheningTableBlock(Block.Properties.copy(net.minecraft.world.level.block.Blocks.OBSIDIAN).noOcclusion()));


}