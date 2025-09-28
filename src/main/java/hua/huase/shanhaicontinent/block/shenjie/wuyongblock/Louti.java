package hua.huase.shanhaicontinent.block.shenjie.wuyongblock;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class Louti extends StairBlock {
    public Louti() {
        super(() -> Blocks.AIR.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.BEDROCK).strength(-1,3600000));
    }
    @Override
    public float getExplosionResistance() {
        return 9999f;
    }
}