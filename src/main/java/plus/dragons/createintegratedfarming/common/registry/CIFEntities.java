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

package plus.dragons.createintegratedfarming.common.registry;

import static plus.dragons.createintegratedfarming.common.CIFCommon.REGISTRATE;

import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import plus.dragons.createintegratedfarming.common.ranching.carpet.CarpetSeatEntity;
import plus.dragons.createintegratedfarming.common.ranching.carpet.CarpetSeatRenderer;

public class CIFEntities {
    public static final EntityEntry<CarpetSeatEntity> CARPET_SEAT = REGISTRATE
            .<CarpetSeatEntity>entity("carpet_seat", CarpetSeatEntity::new, MobCategory.MISC)
            .lang("Carpet Seat")
            .properties(prop -> prop
                    .setTrackingRange(5)
                    .setUpdateInterval(Integer.MAX_VALUE)
                    .setShouldReceiveVelocityUpdates(false)
                    .sized(.25f, 1 / 16f)
                    .fireImmune())
            .renderer(() -> CarpetSeatRenderer::new)
            .register();

    public static void register(IEventBus modBus) {}
}
