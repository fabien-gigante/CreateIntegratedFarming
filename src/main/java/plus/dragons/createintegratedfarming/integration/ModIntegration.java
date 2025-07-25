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

import create_winery.block.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import plus.dragons.createintegratedfarming.common.registry.CIFBlockSpoutingBehaviours;
import plus.dragons.createintegratedfarming.common.registry.CIFHarvestBehaviours;
import plus.dragons.createintegratedfarming.integration.createwinery.CreateWineryIntegration;
import plus.dragons.createintegratedfarming.integration.netherdepthsupgrade.NethersDepthsUpgradeIntegration;

public enum ModIntegration {
    FARMERSDELIGHT(Constants.FARMERS_DELIGHT) {
        @Override
        public void onCommonSetup() {
            CIFHarvestBehaviours.registerFarmersDelight();
            CIFBlockSpoutingBehaviours.registerFarmersDelight();
        }
    },
    MYNETHERSDELIGHT(Constants.MY_NETHERS_DELIGHT) {
        @Override
        public void onCommonSetup() {
            CIFBlockSpoutingBehaviours.registerMyNethersDelight();
        }
    },
    MMLIB(Constants.MMLIB) {
        @Override
        public void onCommonSetup() {
            CIFHarvestBehaviours.registerMmlib();
        }
    },
    CREATE_ENCHANTABLE_MACHINERY(Constants.CREATE_ENCHANTABLE_MACHINERY),
    CREATE_CRAFT_AND_ADDITIONS(Constants.CREATE_CRAFT_AND_ADDITIONS),
    NETHER_DEPTHS_UPGRADE(Constants.NETHER_DEPTHS_UPGRADE) {
        @Override
        public void onConstructMod() {
            NethersDepthsUpgradeIntegration.register();
        }
    },
    CREATE_WINERY(Constants.CREATE_WINERY) {
        @Override
        public void onCommonSetup() {
            CIFHarvestBehaviours.registerCreateWinery();
            CreateWineryIntegration.register();
        }
    };

    private final String id;

    ModIntegration(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public boolean enabled() {
        return ModList.get().isLoaded(id);
    }

    public ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(id, path);
    }

    public ModLoadedCondition condition() {
        return new ModLoadedCondition(id);
    }

    public void onConstructMod() {}

    public void onCommonSetup() {}

    @OnlyIn(Dist.CLIENT)
    public void onClientSetup() {}

    public static class Constants {
        public static final String FARMERS_DELIGHT = "farmersdelight";
        public static final String MY_NETHERS_DELIGHT = "mynethersdelight";
        public static final String MMLIB = "mysterious_mountain_lib";
        public static final String CREATE_ENCHANTABLE_MACHINERY = "createenchantablemachinery";
        public static final String CREATE_CRAFT_AND_ADDITIONS = "createadditions";
        public static final String NETHER_DEPTHS_UPGRADE = "netherdepthsupgrade";
        public static final String CREATE_WINERY = "create_winery";
    }
}
