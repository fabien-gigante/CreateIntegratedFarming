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

import com.simibubi.create.AllBlocks;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createintegratedfarming.client.ponder.scene.MiscScene;
import plus.dragons.createintegratedfarming.client.ponder.scene.MyNethersDelightScene;
import plus.dragons.createintegratedfarming.client.ponder.scene.NetherDepthsUpgradeScene;
import plus.dragons.createintegratedfarming.client.ponder.scene.RoostScene;
import plus.dragons.createintegratedfarming.common.registry.CIFBlocks;
import plus.dragons.createintegratedfarming.integration.ModIntegration;

public class CIFPonderScenes {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?, ?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        HELPER.forComponents(CIFBlocks.FISHING_NET)
                .addStoryBoard("fishing_net/fishing", MiscScene::fishing, CIFPonderTags.FISHING_APPLIANCES, AllCreatePonderTags.CONTRAPTION_ACTOR);

        HELPER.forComponents(AllBlocks.SPOUT)
                .addStoryBoard("spout/organic_compost_catalyze", MiscScene::spoutCatalyze, CIFPonderTags.FARMING_APPLIANCES);

        HELPER.forComponents(CIFBlocks.ROOST)
                .addStoryBoard("roost/catch", RoostScene::capture, CIFPonderTags.RANCHING_APPLIANCES)
                .addStoryBoard("roost/lead", RoostScene::lead);

        HELPER.forComponents(CIFBlocks.CHICKEN_ROOST)
                .addStoryBoard("roost/operate", RoostScene::operate, CIFPonderTags.RANCHING_APPLIANCES, AllCreatePonderTags.ARM_TARGETS)
                .addStoryBoard("roost/spout", RoostScene::fluid);

        CIFBlocks.LAVA_FISHING_NET.asOptional().ifPresent(block -> HELPER.forComponents(CIFBlocks.LAVA_FISHING_NET)
                .addStoryBoard("fishing_net/lava_fishing", NetherDepthsUpgradeScene::lavaFishing, CIFPonderTags.FISHING_APPLIANCES, AllCreatePonderTags.CONTRAPTION_ACTOR));

        if (ModIntegration.MYNETHERSDELIGHT.enabled()) {
            HELPER.forComponents(AllBlocks.SPOUT)
                    .addStoryBoard("spout/letios_compost_catalyze", MyNethersDelightScene::catalyze, CIFPonderTags.FARMING_APPLIANCES);
        }
    }
}
