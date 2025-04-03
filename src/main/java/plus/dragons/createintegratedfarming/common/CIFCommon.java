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

package plus.dragons.createintegratedfarming.common;

import com.simibubi.create.foundation.item.ItemDescription;
import net.createmod.catnip.lang.FontHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import plus.dragons.createdragonsplus.common.CDPRegistrate;
import plus.dragons.createintegratedfarming.config.CIFConfig;
import plus.dragons.createintegratedfarming.integration.Integration;
import plus.dragons.createintegratedfarming.integration.farmersdelight.FarmersDelightIntegration;
import plus.dragons.createintegratedfarming.integration.mynethersdelight.MyNethersDelightIntegration;

@Mod(CIFCommon.ID)
public class CIFCommon {
    public static final String ID = "create_integrated_farming";
    public static final CDPRegistrate REGISTRATE = new CDPRegistrate(ID)
            .setTooltipModifier(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE));

    public CIFCommon(IEventBus modBus, ModContainer modContainer) {
        REGISTRATE.registerEventListeners(modBus);
        if (Integration.FARMERS_DELIGHT.isLoaded()) {
            modBus.register(new FarmersDelightIntegration());
        }
        if (Integration.MY_NETHERS_DELIGHT.isLoaded()) {
            modBus.register(new MyNethersDelightIntegration());
        }
        modBus.register(new CIFConfig(modContainer));
    }
}
