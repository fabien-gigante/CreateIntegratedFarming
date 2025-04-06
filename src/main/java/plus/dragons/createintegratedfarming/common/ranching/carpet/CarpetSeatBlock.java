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

import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

public class CarpetSeatBlock extends CarpetBlock {
    public CarpetSeatBlock(Properties properties) {
        super(properties);
    }

    protected CarpetSeatEntity getOrCreateEmptySeat(Level level, BlockPos pos) {
        List<CarpetSeatEntity> seats = level.getEntitiesOfClass(CarpetSeatEntity.class, new AABB(pos));
        CarpetSeatEntity seat;
        if (seats.isEmpty()) {
            seat = new CarpetSeatEntity(level, pos);
            level.addFreshEntity(seat);
        } else {
            seat = seats.getFirst();
            seat.ejectPassengers();
        }
        return seat;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        Optional<Mob> optional = level
                .getEntities(
                        EntityTypeTest.forClass(Mob.class),
                        player.getBoundingBox().inflate(Leashable.LEASH_TOO_FAR_DIST),
                        SeatBlock::canBePickedUp
                )
                .stream()
                .filter(mob -> mob.getLeashHolder() == player)
                .findFirst();
        if (optional.isEmpty())
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        Mob leashed = optional.get();
        leashed.startRiding(getOrCreateEmptySeat(level, pos), true);
        if (leashed instanceof TamableAnimal tamable)
            tamable.setInSittingPose(true);
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }
}
