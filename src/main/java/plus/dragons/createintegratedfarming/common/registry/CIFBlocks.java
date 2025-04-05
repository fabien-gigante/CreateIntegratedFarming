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

import static com.simibubi.create.foundation.data.TagGen.axeOnly;
import static plus.dragons.createdragonsplus.data.recipe.VanillaRecipeBuilders.shaped;
import static plus.dragons.createintegratedfarming.common.CIFCommon.REGISTRATE;

import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.bus.api.IEventBus;
import plus.dragons.createintegratedfarming.common.ranching.chicken.ChickenCoopBlock;
import plus.dragons.createintegratedfarming.common.ranching.chicken.CoopBlock;
import plus.dragons.createintegratedfarming.common.ranching.chicken.CoopBlockItem;
import vectorwing.farmersdelight.common.registry.ModItems;

public class CIFBlocks {
    public static final BlockEntry<CoopBlock> EMPTY_CHICKEN_COOP = REGISTRATE
            .block("empty_chicken_coop", CoopBlock::new)
            .properties(prop -> prop.strength(1.5F).sound(SoundType.BAMBOO_WOOD))
            .blockstate((ctx, prov) -> prov.horizontalBlock(ctx.get(), AssetLookup.standardModel(ctx, prov)))
            .transform(axeOnly())
            .item(CoopBlockItem::new)
            .recipe((ctx, prov) -> shaped()
                    .output(ctx.get())
                    .define('b', Items.BAMBOO)
                    .define('#', ModItems.CANVAS.get())
                    .define('w', Items.WHEAT)
                    .pattern("b  ")
                    .pattern("#w#")
                    .pattern("b#b")
                    .accept(prov))
            .build()
            .register();
    public static final BlockEntry<ChickenCoopBlock> CHICKEN_COOP = REGISTRATE
            .block("chicken_coop", ChickenCoopBlock::new)
            .properties(prop -> prop.strength(1.5F).sound(SoundType.BAMBOO_WOOD))
            .blockstate((ctx, prov) -> prov.horizontalBlock(ctx.get(), AssetLookup.standardModel(ctx, prov)))
            .item()
            .build()
            .register();

    public static void register(IEventBus modBus) {}
}
