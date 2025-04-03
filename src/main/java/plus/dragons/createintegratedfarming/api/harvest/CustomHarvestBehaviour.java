/*
 * Copyright (C) 2025  DragonsPlus
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package plus.dragons.createintegratedfarming.api.harvest;

import com.simibubi.create.AllTags;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.api.registry.SimpleRegistry;
import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * CustomHarvestBehaviour provides full control for a block's interaction with {@link HarvesterMovementBehaviour}.
 * <p>
 * For specifically disable the harvest behaviour, add the block to {@link AllTags.AllBlockTags#NON_HARVESTABLE} instead.
 */
@FunctionalInterface
public interface CustomHarvestBehaviour {
    SimpleRegistry<Block, CustomHarvestBehaviour> REGISTRY = SimpleRegistry.create();

    /**
     * Harvest the block and collect the drops.
     * @param behaviour the actual {@link HarvesterMovementBehaviour} of the harvester,
     *                  usually used for calling {@link MovementBehaviour#dropItem(MovementContext, ItemStack)}.
     * @param context the {@link MovementContext} of the harvester.
     * @param pos the {@link BlockPos} of the block to be harvested.
     * @param state the {@link BlockState} of the block to be harvested.
     */
    void harvest(HarvesterMovementBehaviour behaviour, MovementContext context, BlockPos pos, BlockState state);

    /**
     * Shortcut for {@code harvesterReplants} config.
     */
    static boolean replant() {
        return AllConfigs.server().kinetics.harvesterReplants.get();
    }

    /**
     * Shortcut for {@code harvestPartiallyGrown} config.
     */
    static boolean partial() {
        return AllConfigs.server().kinetics.harvestPartiallyGrown.get();
    }
}
