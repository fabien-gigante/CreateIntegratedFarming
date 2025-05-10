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

package plus.dragons.createintegratedfarming.client.ponder.scene;

import static vectorwing.farmersdelight.common.block.OrganicCompostBlock.COMPOSTING;

import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.WorldSectionElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class MiscScene {
    public static void fishing(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("fishing_net", "Using Fishing Net on Contraptions");
        scene.configureBasePlate(0, 0, 6);
        scene.scaleSceneView(0.7f);
        var contraptionSelection = util.select().fromTo(4, 2, 4, 4, 3, 5)
                .add(util.select().position(4, 4, 4))
                .add(util.select().fromTo(3, 3, 5, 0, 1, 5));
        scene.world().showSection(util.select().everywhere().substract(contraptionSelection).substract(util.select().fromTo(0, 0, 6, 5, 3, 7)), Direction.DOWN);
        ElementLink<WorldSectionElement> fillSpaceWater = scene.world().showIndependentSection(util.select().fromTo(0, 1, 6, 4, 3, 6), Direction.DOWN);
        ElementLink<WorldSectionElement> fillSpaceWater2 = scene.world().showIndependentSection(util.select().fromTo(4, 1, 7, 4, 3, 7), Direction.DOWN);
        scene.world().moveSection(fillSpaceWater, util.vector().of(0, 0, -1), 0);
        scene.world().moveSection(fillSpaceWater2, util.vector().of(0, 0, -2), 0);
        ElementLink<WorldSectionElement> contraption = scene.world().showIndependentSection(contraptionSelection, Direction.DOWN);
        scene.idle(10);

        scene.world().configureCenterOfRotation(contraption, util.vector().centerOf(4, 1, 4));
        scene.overlay().showText(60)
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(util.vector().blockSurface(util.grid().at(2, 2, 5), Direction.NORTH))
                .text("Whenever Fishing Nets are moved as part of an animated Contraption...");
        scene.idle(70);

        scene.world().rotateBearing(util.grid().at(4, 1, 4), -360, 140);
        scene.world().rotateSection(contraption, 0, -360, 0, 140);
        scene.overlay().showText(100)
                .pointAt(util.vector().blockSurface(util.grid().at(2, 2, 5), Direction.EAST))
                .text("They will fish in open water, like a player fishing with a Fishing Rod")
                .placeNearTarget();
        scene.idle(140);

        scene.overlay().showText(80)
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(util.vector().blockSurface(util.grid().at(2, 2, 5), Direction.NORTH))
                .text("Fishing Net will catch small animal in water");
        scene.world().hideSection(util.select().fromTo(0, 1, 0, 5, 3, 2), Direction.NORTH);
        var salmon = scene.world().createEntity(level -> {
            var fish = EntityType.SALMON.create(level);
            assert fish != null;
            var pos = util.vector().centerOf(2, 2, 1);
            fish.setPos(pos.x, pos.y, pos.z);
            return fish;
        });
        scene.idle(10);

        scene.world().rotateBearing(util.grid().at(4, 1, 4), -360, 140);
        scene.world().rotateSection(contraption, 0, -360, 0, 140);
        scene.idle(28);
        scene.world().modifyEntity(salmon, Entity::discard);
        scene.idle(20);
        scene.overlay().showControls(util.vector().centerOf(4, 3, 4), Pointing.UP, 60).rightClick()
                .withItem(Items.SALMON.getDefaultInstance());
        scene.idle(82);
    }

    public static void spoutCatalyze(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("spout.catalyze_organic_compost", "Catalyzing Organic Compost");
        scene.configureBasePlate(0, 0, 3);

        scene.world().modifyBlockEntity(util.grid().at(1, 3, 1), SpoutBlockEntity.class, be -> {
            var tank = be.getBehaviour(SmartFluidTankBehaviour.TYPE);
            tank.getPrimaryHandler().setFluid(new FluidStack(Fluids.WATER, 1000));
        });
        scene.world().showSection(util.select().everywhere(), Direction.DOWN);

        var spout = util.select().position(1, 3, 1);
        var compost = util.grid().at(1, 1, 1);

        scene.overlay().showText(100)
                .text("Degradation process of Organic Compost can be speed up via Spout")
                .pointAt(util.vector().centerOf(1, 3, 1))
                .placeNearTarget();

        scene.world().modifyBlockEntityNBT(spout, SpoutBlockEntity.class, nbt -> nbt.putInt("ProcessingTicks", 20));
        scene.idle(20);
        scene.world().modifyBlock(compost, bs -> bs.setValue(COMPOSTING, 1), false);
        scene.idle(10);

        scene.world().modifyBlockEntityNBT(spout, SpoutBlockEntity.class, nbt -> nbt.putInt("ProcessingTicks", 20));
        scene.idle(20);
        scene.world().modifyBlock(compost, bs -> bs.setValue(COMPOSTING, 3), false);
        scene.idle(10);

        scene.world().modifyBlockEntityNBT(spout, SpoutBlockEntity.class, nbt -> nbt.putInt("ProcessingTicks", 20));
        scene.idle(20);
        scene.world().modifyBlock(compost, bs -> bs.setValue(COMPOSTING, 5), false);
        scene.idle(10);

        scene.world().modifyBlockEntityNBT(spout, SpoutBlockEntity.class, nbt -> nbt.putInt("ProcessingTicks", 20));
        scene.idle(20);
        scene.world().modifyBlock(compost, bs -> bs.setValue(COMPOSTING, 7), false);
        scene.idle(10);

        scene.world().modifyBlockEntityNBT(spout, SpoutBlockEntity.class, nbt -> nbt.putInt("ProcessingTicks", 20));
        scene.idle(20);
        scene.world().modifyBlock(compost, bs -> ModBlocks.RICH_SOIL.get().defaultBlockState(), false);
    }
}
