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
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public class Integration {
    public static final Integration FARMERS_DELIGHT = new Integration("farmersdelight");
    public static final Integration MY_NETHERS_DELIGHT = new Integration("mynethersdelight");

    private final String id;

    public Integration(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isLoaded() {
        return ModList.get().isLoaded(id);
    }

    public ResourceLocation asResource(String location) {
        return ResourceLocation.fromNamespaceAndPath(id, location);
    }
}
