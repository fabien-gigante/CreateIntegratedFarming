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

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createintegratedfarming.client.ponder.scene.FishingNetScene;
import plus.dragons.createintegratedfarming.client.ponder.scene.RoostScene;
import plus.dragons.createintegratedfarming.common.registry.CIFBlocks;

public class CIFPonderScenes {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?, ?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        HELPER.forComponents(CIFBlocks.CHICKEN_ROOST)
                .addStoryBoard("fishing_net/catch", FishingNetScene::catchFish, CIFPonderTags.FARMING_APPLIANCES)
                .addStoryBoard("fishing_net/fishing", FishingNetScene::fishing);

        HELPER.forComponents(CIFBlocks.ROOST)
                .addStoryBoard("roost/catch", RoostScene::capture, CIFPonderTags.FARMING_APPLIANCES)
                .addStoryBoard("roost/lead", RoostScene::lead);


        HELPER.forComponents(CIFBlocks.CHICKEN_ROOST)
                .addStoryBoard("roost/operate", RoostScene::operate, CIFPonderTags.FARMING_APPLIANCES)
                .addStoryBoard("roost/spout", RoostScene::fluid);
    }
}
