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

import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.content.kinetics.saw.TreeCutter;
import net.minecraft.world.level.block.BambooStalkBlock;

/**
 * Marker interface for blocks that is a part of a vertially growned block with no branches like {@link BambooStalkBlock}.
 * <p>
 * Blocks implementing this interface will be recognized by {@link TreeCutter} and {@link SawBlockEntity}.
 * @see FragileVerticalPlant
 */
public interface VerticalPlant {}
