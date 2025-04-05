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

package plus.dragons.createintegratedfarming.integration;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public class Integration {
    public static final Integration FARMERS_DELIGHT = new Integration("farmersdelight");
    public static final Integration MMLIB = new Integration("mysterious_mountain_lib");
    public static final Integration CREATE_ENCHANTABLE_MACHINERY = new Integration("createenchantablemachinery");
    public static final Integration CREATE_CRAFT_AND_ADDITIONS = new Integration("createadditions");

    private final String modId;

    public Integration(String modId) {
        this.modId = modId;
    }

    public String getModId() {
        return modId;
    }

    public boolean isLoaded() {
        return ModList.get().isLoaded(modId);
    }

    public ModLoadedCondition condition() {
        return new ModLoadedCondition(modId);
    }

    public ResourceLocation asResource(String location) {
        return ResourceLocation.fromNamespaceAndPath(modId, location);
    }
}
