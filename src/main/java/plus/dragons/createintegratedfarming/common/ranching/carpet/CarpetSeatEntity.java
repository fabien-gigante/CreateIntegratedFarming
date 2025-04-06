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

package plus.dragons.createintegratedfarming.common.ranching.carpet;

import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import plus.dragons.createintegratedfarming.common.registry.CIFEntities;

public class CarpetSeatEntity extends SeatEntity {
    public CarpetSeatEntity(EntityType<? extends CarpetSeatEntity> type, Level level) {
        super(type, level);
    }

    public CarpetSeatEntity(Level world, BlockPos pos) {
        this(CIFEntities.CARPET_SEAT.get(), world);
        this.noPhysics = true;
        this.setPos(Vec3.atBottomCenterOf(pos));
    }

    public static EntityType.Builder<?> build(EntityType.Builder<?> builder) {
        @SuppressWarnings("unchecked")
        EntityType.Builder<SeatEntity> entityBuilder = (EntityType.Builder<SeatEntity>) builder;
        return entityBuilder.sized(0.25f, 0.35f);
    }

    @Override
    public void tick() {
        if (level().isClientSide)
            return;
        boolean blockPresent = level().getBlockState(blockPosition()).getBlock() instanceof CarpetSeatBlock;
        if (isVehicle() && blockPresent)
            return;
        this.discard();
    }
}
