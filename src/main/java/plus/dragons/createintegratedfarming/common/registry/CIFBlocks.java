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
import static plus.dragons.createintegratedfarming.common.CIFCommon.REGISTRATE;

import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import plus.dragons.createintegratedfarming.common.fishing.net.FishingNetBlock;
import plus.dragons.createintegratedfarming.common.fishing.net.FishingNetMovementBehaviour;
import plus.dragons.createintegratedfarming.common.ranching.carpet.CarpetSeatBlock;
import plus.dragons.createintegratedfarming.common.ranching.chicken.ChickenCoopBlock;
import plus.dragons.createintegratedfarming.common.ranching.chicken.CoopBlock;
import plus.dragons.createintegratedfarming.common.ranching.chicken.CoopBlockItem;

public class CIFBlocks {
    public static final BlockEntry<CoopBlock> EMPTY_CHICKEN_COOP = REGISTRATE
            .block("empty_chicken_coop", CoopBlock::new)
            .properties(prop -> prop.strength(1.5F).sound(SoundType.BAMBOO_WOOD))
            .blockstate((ctx, prov) -> prov.horizontalBlock(ctx.get(), AssetLookup.standardModel(ctx, prov)))
            .transform(axeOnly())
            .item(CoopBlockItem::new)
            .build()
            .register();
    public static final BlockEntry<ChickenCoopBlock> CHICKEN_COOP = REGISTRATE
            .block("chicken_coop", ChickenCoopBlock::new)
            .properties(prop -> prop.strength(1.5F).sound(SoundType.BAMBOO_WOOD))
            .blockstate((ctx, prov) -> prov.horizontalBlock(ctx.get(), AssetLookup.standardModel(ctx, prov)))
            .item()
            .build()
            .register();
    public static final BlockEntry<CarpetBlock> HAY_CARPET = REGISTRATE
            .block("hay_carpet", CarpetBlock::new)
            .properties(prop -> prop
                    .mapColor(MapColor.COLOR_YELLOW)
                    .sound(SoundType.GRASS)
                    .strength(0.1f)
                    .pushReaction(PushReaction.DESTROY)
            )
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models()
                    .carpet(ctx.getName(), ResourceLocation.withDefaultNamespace("block/hay_block_top"))))
            .tag(BlockTags.MINEABLE_WITH_HOE)
            .item()
            .compostable(.5f)
            .build()
            .register();
    public static final BlockEntry<CarpetSeatBlock> STICKY_HAY_CARPET = REGISTRATE
            .block("sticky_hay_carpet", CarpetSeatBlock::new)
            .initialProperties(HAY_CARPET)
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models()
                    .carpet(ctx.getName(), ResourceLocation.withDefaultNamespace("block/hay_block_top"))))
            .tag(BlockTags.MINEABLE_WITH_HOE)
            .item()
            .compostable(.5f)
            .build()
            .register();
    public static final BlockEntry<FishingNetBlock> FISHING_NET = REGISTRATE
            .block("fishing_net", FishingNetBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(prop -> prop
                    .mapColor(DyeColor.BROWN)
                    .sound(SoundType.SCAFFOLDING)
                    .noOcclusion())
            .transform(axeOnly())
            .blockstate(BlockStateGen.directionalBlockProvider(false))
            .onRegister(block -> MovementBehaviour.REGISTRY.register(block, new FishingNetMovementBehaviour()))
            .simpleItem()
            .register();

    public static void register(IEventBus modBus) {}
}
