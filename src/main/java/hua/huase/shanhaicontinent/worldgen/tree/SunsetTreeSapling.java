package hua.huase.shanhaicontinent.worldgen.tree;

import hua.huase.shanhaicontinent.register.ModBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class SunsetTreeSapling extends SaplingBlock {

    private static Set<Block> VALID_SOILS;

    public SunsetTreeSapling(AbstractTreeGrower tree, BlockBehaviour.Properties properties) {
        super(tree, properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        if (VALID_SOILS == null) {
            VALID_SOILS = Set.of(
                    Blocks.GRASS_BLOCK,
                    Blocks.DIRT,
                    Blocks.PODZOL,
                    ModBlock.FERTILE_DIRT.get(),
                    ModBlock.GRASSSJ_BLOCK.get()
            );
        }
        return VALID_SOILS.contains(state.getBlock());
    }
}