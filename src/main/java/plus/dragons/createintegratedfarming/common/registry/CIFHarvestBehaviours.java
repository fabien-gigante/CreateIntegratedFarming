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

package plus.dragons.createintegratedfarming.common.registry;

import create_winery.block.*;
import create_winery.init.CreateWineryModBlocks;
import create_winery.init.CreateWineryModItems;
import plus.dragons.createintegratedfarming.api.harvester.CustomHarvestBehaviour;
import plus.dragons.createintegratedfarming.common.farming.harvest.HighCropHarvestBehaviour;
import plus.dragons.createintegratedfarming.common.farming.harvest.MushroomColonyHarvestBehaviour;
import plus.dragons.createintegratedfarming.common.farming.harvest.SimpleHarvestBehaviour;
import plus.dragons.createintegratedfarming.common.farming.harvest.TomatoHarvestBehaviour;
import vectorwing.farmersdelight.common.block.TomatoVineBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class CIFHarvestBehaviours {
    public static void registerFarmersDelight() {
        CustomHarvestBehaviour.REGISTRY.registerProvider(MushroomColonyHarvestBehaviour::create);
        var tomato = (TomatoVineBlock) ModBlocks.TOMATO_CROP.get();
        CustomHarvestBehaviour.REGISTRY.register(tomato, new TomatoHarvestBehaviour(tomato));
    }

    public static void registerMmlib() {
        CustomHarvestBehaviour.REGISTRY.registerProvider(HighCropHarvestBehaviour::create);
    }

    public static void registerCreateWinery() {
        var harvestedRed = CreateWineryModBlocks.RED_GRAPE_BUSH_STAGE_1.get();
        var harvestedWhite = CreateWineryModBlocks.WHITE_GRAPE_BUSH_STAGE_1.get();
        var grapesRed = CreateWineryModItems.RED_GRAPES.get();
        var grapesWhite = CreateWineryModItems.WHITE_GRAPES.get();
        CustomHarvestBehaviour.REGISTRY.registerProvider(
                block -> {
                    if (block instanceof RedGrapeBushStage3Block) return new SimpleHarvestBehaviour(harvestedRed, grapesRed);
                    if (block instanceof WhiteGrapeBushStage3Block) return new SimpleHarvestBehaviour(harvestedWhite, grapesWhite);
                    if (CustomHarvestBehaviour.partial()) {
                        if (block instanceof RedGrapeBushStage2Block) return new SimpleHarvestBehaviour(harvestedRed, null);
                        if (block instanceof RedGrapeBushStage2Block) return new SimpleHarvestBehaviour(harvestedWhite, null);
                    }
                    return null;
                });
    }
}
