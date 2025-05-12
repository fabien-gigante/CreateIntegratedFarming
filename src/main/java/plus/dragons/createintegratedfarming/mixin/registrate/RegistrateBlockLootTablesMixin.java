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

package plus.dragons.createintegratedfarming.mixin.registrate;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RegistrateBlockLootTables.class)
public class RegistrateBlockLootTablesMixin {
    @Final
    @Shadow
    private AbstractRegistrate<?> parent;

    @Inject(method = "getKnownBlocks", at = @At("RETURN"), cancellable = true)
    private void workaroundRegistrateLootTableDataGen(CallbackInfoReturnable<Iterable<Block>> cir) {
        cir.setReturnValue(parent.getAll(Registries.BLOCK).stream().filter(entry -> !entry.is(ResourceLocation.fromNamespaceAndPath("create_integrated_farming", "lava_fishing_net"))).map(Supplier::get).collect(Collectors.toList()));
    }
}
