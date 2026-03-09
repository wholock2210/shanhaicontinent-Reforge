package hua.huase.shanhaicontinent.filuid;


import hua.huase.shanhaicontinent.init.ShanhaicontinentModFluidTypes;
import hua.huase.shanhaicontinent.init.ShanhaicontinentModFluids;
import hua.huase.shanhaicontinent.register.ModBlock;
import hua.huase.shanhaicontinent.register.ModItems;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;

public abstract class HotyangquanFluid extends ForgeFlowingFluid {
    public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> ShanhaicontinentModFluidTypes.HOTYANGQUAN_TYPE.get(), () -> ShanhaicontinentModFluids.HOTYANGQUAN.get(),
            () -> ShanhaicontinentModFluids.FLOWING_HOTYANGQUAN.get()).explosionResistance(100f).tickRate(30).bucket(() -> ModItems.HOTYANGQUAN_BUCKET.get()).block(() -> (LiquidBlock) ModBlock.HOTYANGQUAN.get());

    private HotyangquanFluid() {
        super(PROPERTIES);
    }

    public static class Source extends HotyangquanFluid {
        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends HotyangquanFluid {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }
}
