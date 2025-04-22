package plus.dragons.createintegratedfarming.common.ranching.roost;

import com.simibubi.create.api.registry.SimpleRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface RoostCapturable {
    SimpleRegistry<EntityType<?>, RoostCapturable> REGISTRY = SimpleRegistry.create();

    ItemInteractionResult captureBlock(Level level, BlockState state, BlockPos pos, ItemStack stack, Player player, Entity entity);

    InteractionResult captureItem(Level level, ItemStack stack, InteractionHand hand, Player player, Entity entity);
}
