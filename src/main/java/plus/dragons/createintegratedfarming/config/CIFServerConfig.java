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

package plus.dragons.createintegratedfarming.config;

import net.createmod.catnip.config.ConfigBase;

public class CIFServerConfig extends ConfigBase {
    public final ConfigGroup harvester = group(1, "harvester", Comments.harvester);
    public final ConfigBool mushroomColoniesDropSelf = b(false,
            "mushroomColoniesDropSelf",
            Comments.mushroomColoniesDropSelf);

    public final ConfigGroup fishingNet = group(1, "fishingNet", Comments.fishingNet);
    public final ConfigBool fishingNetChecksOpenWater = b(true,
            "checksOpenWater",
            Comments.fishingNetChecksOpenWater);
    public final ConfigInt fishingNetCooldownMultiplier = i(8, 1, 256,
            "cooldownMultiplier",
            Comments.fishingNetCooldownMultiplier);
    public final ConfigInt fishingNetMaxRecordedBlocks = i(8, 1, 64,
            "maxRecordedBlocks",
            Comments.fishingNetMaxRecordedBlocks);

    @Override
    public String getName() {
        return "server";
    }

    static class Comments {
        static final String harvester =
                "Settings for the Harvester";
        static final String mushroomColoniesDropSelf =
                "If harvesting mushroom colonies drops itself instead of corresponding mushroom.";
        static final String fishingNet =
                "Settings for the Fishing Net.";
        static final String[] fishingNetChecksOpenWater = {
                "If Fishing Net should check for open water.",
                "When disabled, fishing treasures will not be available in caught items."
        };
        static final String[] fishingNetCooldownMultiplier = {
                "Fishing Net's cooldown will be multiplied by this value.",
                "The base cooldown is the same as Fishing Rod's lure speed (100 ~ 600 ticks)."
        };
        static final String[] fishingNetMaxRecordedBlocks = {
                "The maximum amount of the visited valid blocks for fishing recorded by the Fishing Net.",
                "Fishing Net's chance of successful catch depends on [amount] / [maximum amount] of visited valid blocks.",
                "Increasing this value will reduce the efficiency of Fishing Net that travels in a fixed short route.",
                "Example: Fishing Net placed near rotating axis"
        };
    }
}
