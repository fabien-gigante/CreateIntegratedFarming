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

package plus.dragons.createintegratedfarming.common.farming.harvest;

import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.foundation.utility.BlockHelper;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import plus.dragons.createintegratedfarming.api.harvester.CustomHarvestBehaviour;

public class SimpleHarvestBehaviour implements CustomHarvestBehaviour {
    private final Block harvested;
    @Nullable
    private final Item item;

    public SimpleHarvestBehaviour(Block harvested, @Nullable Item item) {
        this.harvested = harvested;
        this.item = item;
    }

    @Override
    public void harvest(HarvesterMovementBehaviour behaviour, MovementContext context, BlockPos pos, BlockState state) {
        var level = context.world;
        if (CustomHarvestBehaviour.replant()) {
            if (item != null) behaviour.dropItem(context, new ItemStack(item));
            level.setBlockAndUpdate(pos, harvested.defaultBlockState());
        } else
            BlockHelper.destroyBlockAs(level, pos, null,
                    CustomHarvestBehaviour.getHarvestTool(context), 1, stack -> behaviour.dropItem(context, stack));
    }
}
