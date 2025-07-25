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

package plus.dragons.createintegratedfarming.integration.createwinery;

import create_winery.block.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.block.CropGrowEvent;

public class CreateWineryIntegration {
    public static void register() {
        NeoForge.EVENT_BUS.addListener(CropGrowEvent.Pre.class, event -> {
            var block = event.getState().getBlock();
            var mature = block instanceof RedGrapeBushStage3Block || block instanceof WhiteGrapeBushStage3Block;
            if (mature) event.setResult(CropGrowEvent.Pre.Result.DO_NOT_GROW);
        });
    }
}
