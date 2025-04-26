package plus.dragons.createintegratedfarming.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.contraptions.Contraption;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.createintegratedfarming.api.multifluidlogged.WaterAndLavaLoggedBlock;

import static plus.dragons.createintegratedfarming.api.multifluidlogged.WaterAndLavaLoggedBlock.FLUID;

@Mixin(Contraption.class)
public abstract class ContraptionMixin {
    @Inject(method = "removeBlocksFromWorld", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z",
            shift = At.Shift.AFTER))
    private void createintegratedfarming$fixRemoveBlockLeaveNoFluid(Level world, BlockPos offset, CallbackInfo ci, @Local Block blockIn, @Local BlockState oldState, @Local(ordinal = 1) BlockPos add) {
        if (blockIn instanceof WaterAndLavaLoggedBlock && oldState.hasProperty(FLUID)
                && oldState.getValue(FLUID) != WaterAndLavaLoggedBlock.FluidContained.EMPTY) {
            var f = oldState.getValue(FLUID);
            int flags = Block.UPDATE_MOVE_BY_PISTON | Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_KNOWN_SHAPE
                    | Block.UPDATE_CLIENTS | Block.UPDATE_IMMEDIATE;
            if(f== WaterAndLavaLoggedBlock.FluidContained.WATER)
                world.setBlock(add, Blocks.WATER.defaultBlockState(), flags);
            else world.setBlock(add, Blocks.LAVA.defaultBlockState(), flags);
        }
    }

    @ModifyArg(method = "addBlocksToWorld", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"), index = 1)
    private BlockState createintegratedfarming$fixAddBlocksToWorldIgnoreFluid(BlockState state, @Local(ordinal = 0) BlockPos targetPos, @Local(ordinal = 0, argsOnly = true) Level world) {
        var result = state;
        if (state.getBlock() instanceof WaterAndLavaLoggedBlock
                && state.hasProperty(FLUID)) {
            FluidState fluidState = world.getFluidState(targetPos);
            result = state.setValue(FLUID, fluidState.getType() == Fluids.WATER ? WaterAndLavaLoggedBlock.FluidContained.WATER:
                    fluidState.getType() == Fluids.LAVA ? WaterAndLavaLoggedBlock.FluidContained.LAVA: WaterAndLavaLoggedBlock.FluidContained.EMPTY);
        }
        return result;
    }
}
