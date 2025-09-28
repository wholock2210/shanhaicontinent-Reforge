package hua.huase.shanhaicontinent.filuid;

import hua.huase.shanhaicontinent.init.ShanhaicontinentModFluidTypes;
import hua.huase.shanhaicontinent.init.ShanhaicontinentModFluids;
import hua.huase.shanhaicontinent.register.ModBlock;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;

public abstract class ColdiceFluid extends ForgeFlowingFluid {
    public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> ShanhaicontinentModFluidTypes.COLDICE_TYPE.get(), () -> ShanhaicontinentModFluids.COLDICE.get(),
            () -> ShanhaicontinentModFluids.FLOWING_COLDICE.get()).explosionResistance(100f).block(() -> (LiquidBlock) ModBlock.COLDICE.get());

    private ColdiceFluid() {
        super(PROPERTIES);
    }

    public static class Source extends ColdiceFluid {
        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends ColdiceFluid {
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
