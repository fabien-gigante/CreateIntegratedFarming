package plus.dragons.createintegratedfarming.api.multifluidlogged;

import net.createmod.catnip.lang.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

public interface WaterAndLavaLoggedBlock extends BucketPickup, LiquidBlockContainer {
    EnumProperty<FluidContained> FLUID = EnumProperty.create("fluid_contained", FluidContained.class);
    default boolean canPlaceLiquid(@Nullable Player player, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        return fluid == Fluids.WATER || fluid == Fluids.LAVA;
    }

    default boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        var fluid = state.getValue(FLUID);
        if (fluid == FluidContained.EMPTY&& fluidState.getType() == Fluids.WATER) {
            if (!level.isClientSide()) {
                level.setBlock(pos, state.setValue(FLUID, FluidContained.WATER), 3);
                level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            }

            return true;
        } if (fluid == FluidContained.EMPTY && fluidState.getType() == Fluids.LAVA) {
            if (!level.isClientSide()) {
                level.setBlock(pos, state.setValue(FLUID, FluidContained.LAVA), 3);
                level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            }

            return true;
        } else {
            return false;
        }
    }

    default ItemStack pickupBlock(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        var fluid = state.getValue(FLUID);
        if (fluid!=FluidContained.EMPTY) {
            level.setBlock(pos, state.setValue(FLUID, FluidContained.EMPTY), 3);
            if (!state.canSurvive(level, pos)) {
                level.destroyBlock(pos, true);
            }

            return fluid == FluidContained.WATER ? new ItemStack(Items.WATER_BUCKET) : new ItemStack(Items.LAVA_BUCKET);
        } else {
            return ItemStack.EMPTY;
        }
    }

    default FluidState fluidState(BlockState state) {
        var fluid = state.getValue(FLUID);
        if(fluid==FluidContained.EMPTY) return Fluids.EMPTY.defaultFluidState();
        else if(fluid==FluidContained.WATER) return Fluids.WATER.getSource(false);
        return Fluids.LAVA.getSource(false);
    }

    default void updateFluid(LevelAccessor level, BlockState state, BlockPos pos) {
        var fluid = state.getValue(FLUID);
        if (fluid!=FluidContained.EMPTY) {
            var f = fluid == FluidContained.WATER ? Fluids.WATER : Fluids.LAVA;
            level.scheduleTick(pos, f, f.getTickDelay(level));
        }

    }

    default BlockState withFluid(BlockState placementState, BlockPlaceContext ctx) {
        return withFluid(ctx.getLevel(), placementState, ctx.getClickedPos());
    }

    static BlockState withFluid(LevelAccessor level, BlockState placementState, BlockPos pos) {
        if (placementState == null)
            return null;
        FluidState ifluidstate = level.getFluidState(pos);
        if (placementState.isAir()){
            return (ifluidstate.getType() == Fluids.WATER || ifluidstate.getType() == Fluids.LAVA) ? ifluidstate.createLegacyBlock() : placementState;
        }
        if (!(placementState.getBlock() instanceof WaterAndLavaLoggedBlock))
            return placementState;
        FluidContained fluid = ifluidstate.getType() == Fluids.WATER? FluidContained.WATER :
                ifluidstate.getType() == Fluids.LAVA? FluidContained.LAVA: FluidContained.EMPTY;
        return placementState.setValue(FLUID, fluid);
    }

    enum FluidContained implements StringRepresentable {
        EMPTY,
        WATER,
        LAVA;

        @Override
        public String getSerializedName() {
            return Lang.asId(name());
        }
    }
}
