package plus.dragons.createintegratedfarming.common.fishing.net.heatresistant;

import com.scouter.netherdepthsupgrade.entity.AbstractLavaFish;
import com.simibubi.create.AllShapes;
import com.simibubi.create.api.schematic.requirement.SpecialBlockItemRequirement;
import com.simibubi.create.content.schematics.requirement.ItemRequirement;
import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;
import net.createmod.catnip.placement.IPlacementHelper;
import net.createmod.catnip.placement.PlacementHelpers;
import net.createmod.catnip.placement.PlacementOffset;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class HeatResistantFishingNet extends WrenchableDirectionalBlock implements WaterAndLavaLoggedBlock, SpecialBlockItemRequirement {
    protected static final int PLACEMENT_HELPER_ID = PlacementHelpers.register(new HeatResistantFishingNet.PlacementHelper());

    public HeatResistantFishingNet(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(FACING, Direction.UP)
                .setValue(FLUID, FluidContained.EMPTY));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        IPlacementHelper placementHelper = PlacementHelpers.get(PLACEMENT_HELPER_ID);
        if (!player.isShiftKeyDown() && player.mayBuild()) {
            if (placementHelper.matchesItem(stack)) {
                placementHelper
                        .getOffset(player, level, state, pos, hitResult)
                        .placeInWorld(level, (BlockItem) stack.getItem(), player, hand, hitResult);
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AllShapes.SAIL.get(state.getValue(FACING));
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        var dimensions = entity.getDimensions(Pose.SWIMMING);
        if (entity.getType().is(EntityTypeTags.AQUATIC) || entity instanceof AbstractLavaFish && dimensions.width() <= 1 && dimensions.height() <= 1) {
            entity.makeStuckInBlock(state, new Vec3(0.25, 0.05, 0.25));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FLUID));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState stateForPlacement = defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        return withFluid(stateForPlacement, context);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return fluidState(state);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        updateFluid(level, state, pos);
        return state;
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.isSuppressingBounce()) {
            super.fallOn(level, state, pos, entity, fallDistance);
        } else {
            entity.causeFallDamage(fallDistance, 0.0F, level.damageSources().fall());
        }
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter level, Entity entityIn) {
        if (entityIn.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(level, entityIn);
        } else {
            this.bounceEntity(entityIn);
        }
    }

    protected void bounceEntity(Entity entity) {
        Vec3 vec3d = entity.getDeltaMovement();
        if (vec3d.y < 0.0) {
            double entityWeightOffset = entity instanceof LivingEntity ? 0.6 : 0.8;
            entity.setDeltaMovement(vec3d.x, -vec3d.y * entityWeightOffset, vec3d.z);
        }
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Fluids.WATER.getPickupSound();
    }

    @Override
    public ItemRequirement getRequiredItems(BlockState state, @Nullable BlockEntity blockEntity) {
        return null; //TODO
    }

    protected static class PlacementHelper implements IPlacementHelper {
        @Override
        public Predicate<ItemStack> getItemPredicate() {
            return stack -> stack.getItem() instanceof BlockItem blockItem &&
                    blockItem.getBlock() instanceof HeatResistantFishingNet;
        }

        @Override
        public Predicate<BlockState> getStatePredicate() {
            return state -> state.getBlock() instanceof HeatResistantFishingNet;
        }

        @Override
        public PlacementOffset getOffset(Player player, Level level, BlockState state, BlockPos pos, BlockHitResult hitResult) {
            List<Direction> directions = IPlacementHelper.orderedByDistanceExceptAxis(
                    pos,
                    hitResult.getLocation(),
                    state.getValue(HeatResistantFishingNet.FACING).getAxis(),
                    direction -> level.getBlockState(pos.relative(direction)).canBeReplaced());
            if (directions.isEmpty()) {
                return PlacementOffset.fail();
            } else {
                return PlacementOffset.success(
                        pos.relative(directions.getFirst()),
                        placed -> {
                            FluidState fluidstate = level.getFluidState(pos.relative(directions.getFirst()));
                            var result = placed.setValue(FACING, state.getValue(FACING));
                            if(fluidstate.getType() == Fluids.WATER) {
                                result = result.setValue(FLUID,FluidContained.WATER);
                            } else  if(fluidstate.getType() == Fluids.LAVA) {
                                result = result.setValue(FLUID,FluidContained.LAVA);
                            }
                            return result;
                        });
            }
        }
    }
}
