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

package plus.dragons.createintegratedfarming.client.ponder;

import static com.simibubi.create.infrastructure.ponder.AllCreatePonderTags.ARM_TARGETS;
import static com.simibubi.create.infrastructure.ponder.AllCreatePonderTags.CONTRAPTION_ACTOR;
import static vectorwing.farmersdelight.common.registry.ModBlocks.BASKET;

import com.simibubi.create.AllBlocks;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import plus.dragons.createintegratedfarming.common.CIFCommon;
import plus.dragons.createintegratedfarming.common.registry.CIFBlocks;

public class CIFPonderTags {
    public static final ResourceLocation FARMING_APPLIANCES = CIFCommon.asResource("farming_appliances");

    public static final ResourceLocation FISHING_APPLIANCES = CIFCommon.asResource("fishing_appliances");

    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<RegistryEntry<?, ?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        PonderTagRegistrationHelper<ItemLike> itemHelper = helper.withKeyFunction(
                RegisteredObjectsHelper::getKeyOrThrow);

        helper.registerTag(FARMING_APPLIANCES)
                .addToIndex()
                .item(CIFBlocks.ROOST, true, false)
                .title("Farming Appliances")
                .description("Components about farming and ranching")
                .register();

        helper.registerTag(FISHING_APPLIANCES)
                .addToIndex()
                .item(CIFBlocks.FISHING_NET, true, false)
                .title("Fishing Appliances")
                .description("Components about fishing")
                .register();

        HELPER.addToTag(FARMING_APPLIANCES)
                .add(CIFBlocks.ROOST)
                .add(CIFBlocks.CHICKEN_ROOST)
                .add(AllBlocks.MECHANICAL_HARVESTER);

        HELPER.addToTag(FISHING_APPLIANCES)
                .add(CIFBlocks.FISHING_NET);

        HELPER.addToTag(ARM_TARGETS)
                .add(CIFBlocks.CHICKEN_ROOST);

        itemHelper.addToTag(ARM_TARGETS)
                .add(BASKET.get());

        HELPER.addToTag(CONTRAPTION_ACTOR)
                .add(CIFBlocks.FISHING_NET);

        Holder<Block> heatResistantFishingNet = DeferredHolder.create(Registries.BLOCK,
                CIFCommon.asResource("heat_resistant_fishing_net"));
        Holder<Block> crimsonHeatResistantFishingNet = DeferredHolder.create(Registries.BLOCK,
                CIFCommon.asResource("crimson_heat_resistant_fishing_net"));

        if(heatResistantFishingNet.isBound()){
            itemHelper.addToTag(CONTRAPTION_ACTOR)
                    .add(heatResistantFishingNet.value());
            itemHelper.addToTag(FISHING_APPLIANCES)
                    .add(heatResistantFishingNet.value());
        }
        if(crimsonHeatResistantFishingNet.isBound()){
            itemHelper.addToTag(CONTRAPTION_ACTOR)
                    .add(crimsonHeatResistantFishingNet.value());
            itemHelper.addToTag(FISHING_APPLIANCES)
                    .add(crimsonHeatResistantFishingNet.value());
        }
    }
}
