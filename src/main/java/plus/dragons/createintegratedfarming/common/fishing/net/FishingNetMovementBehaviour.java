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

package plus.dragons.createintegratedfarming.common.fishing.net;

import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;

public class FishingNetMovementBehaviour implements MovementBehaviour {
    @Override
    public void tick(MovementContext context) {
        if (context.world instanceof ServerLevel level) {
            var fishing = getFishingNetContext(context, level);
            if (fishing.timeUntilCatch > 0)
                fishing.timeUntilCatch--;
        }
    }

    @Override
    public void visitNewPosition(MovementContext context, BlockPos pos) {
        if (context.world instanceof ServerLevel level) {
            var fishing = getFishingNetContext(context, level);
            var inWater = fishing.visitNewPositon(level, pos);
            if (!inWater || fishing.timeUntilCatch > 0)
                return;
            if (fishing.canCatch()) {
                var params = fishing.buildLootContext(context, level, pos);
                LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(BuiltInLootTables.FISHING);
                List<ItemStack> loots = lootTable.getRandomItems(params);
                var event = NeoForge.EVENT_BUS.post(new ItemFishedEvent(loots, 0, fishing.getFishingHook()));
                if (!event.isCanceled()) {
                    loots.forEach(stack -> dropItem(context, stack));
                }
            }
            fishing.reset(level);
        }
    }

    protected FishingNetContext getFishingNetContext(MovementContext context, ServerLevel level) {
        if (!(context.temporaryData instanceof FishingNetContext)) {
            context.temporaryData = new FishingNetContext(level, new ItemStack(Items.FISHING_ROD));
        }
        return (FishingNetContext) context.temporaryData;
    }
}
