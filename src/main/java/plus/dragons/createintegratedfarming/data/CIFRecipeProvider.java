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

package plus.dragons.createintegratedfarming.data;

import static com.simibubi.create.AllItems.ANDESITE_ALLOY;
import static net.minecraft.world.item.Items.*;
import static plus.dragons.createdragonsplus.data.recipe.CreateRecipeBuilders.manualApplication;
import static plus.dragons.createdragonsplus.data.recipe.CreateRecipeBuilders.splashing;
import static plus.dragons.createdragonsplus.data.recipe.VanillaRecipeBuilders.shaped;
import static plus.dragons.createdragonsplus.data.recipe.VanillaRecipeBuilders.shapeless;
import static plus.dragons.createintegratedfarming.common.registry.CIFBlocks.*;
import static vectorwing.farmersdelight.common.registry.ModItems.CANVAS;
import static vectorwing.farmersdelight.common.registry.ModItems.SAFETY_NET;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.neoforge.common.Tags;

public class CIFRecipeProvider extends RecipeProvider {
    public CIFRecipeProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        shaped().output(EMPTY_CHICKEN_COOP)
                .define('#', CANVAS.get())
                .define('b', BAMBOO)
                .define('c', STICKY_HAY_CARPET)
                .pattern("b b")
                .pattern("#c#")
                .pattern("b#b")
                .unlockedBy("has_canvas", has(CANVAS.get()))
                .accept(output);
        shaped().output(HAY_CARPET, 3)
                .define('#', HAY_BLOCK)
                .pattern("##")
                .unlockedBy("has_hay_block", has(HAY_BLOCK))
                .accept(output);
        shapeless().output(STICKY_HAY_CARPET)
                .require(HAY_CARPET)
                .require(SLIME_BALL)
                .unlockedBy("has_hay_block", has(HAY_BLOCK))
                .accept(output);
        manualApplication(STICKY_HAY_CARPET.getId())
                .require(HAY_CARPET)
                .require(SLIME_BALL)
                .output(STICKY_HAY_CARPET)
                .build(output);
        splashing(STICKY_HAY_CARPET.getId())
                .require(STICKY_HAY_CARPET.get())
                .output(HAY_CARPET)
                .build(output);
        shaped().output(FISHING_NET, 2)
                .define('#', SAFETY_NET.get())
                .define('/', Tags.Items.RODS_WOODEN)
                .define('a', ANDESITE_ALLOY)
                .pattern("#/")
                .pattern("/a")
                .unlockedBy("has_andesite_alloy", has(ANDESITE_ALLOY))
                .accept(output);
    }
}
