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

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import plus.dragons.createintegratedfarming.common.registry.CIFBlocks;

public class RoostScene {
    public static void capture(SceneBuilder builder, SceneBuildingUtil util) { // TODO Strange Chicken walk animation. stopInPlace WalkAnimation have no use
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("roost.catch", "Using Roost");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(10);
        BlockPos center = util.grid().at(2, 1, 2);

        scene.world().createEntity(level -> {
            Chicken chicken = EntityType.CHICKEN.create(level);
            Vec3 v = center.getBottomCenter();
            chicken.setPosRaw(v.x, v.y, v.z);
            chicken.setYRot(chicken.yRotO = 180);
            chicken.yHeadRotO = 180;
            chicken.yHeadRot = 180;
            chicken.setOnGround(true);
            return chicken;
        });

        scene.idle(20);
        scene.overlay().showControls(util.vector().centerOf(center.above()), Pointing.DOWN, 40).rightClick()
                .withItem(CIFBlocks.ROOST.asStack());
        scene.idle(10);
        scene.overlay().showText(60)
                .text("Right-click a bird with the empty roost to catch it")
                .attachKeyFrame()
                .pointAt(util.vector().blockSurface(center, Direction.WEST))
                .placeNearTarget();
        scene.idle(50);
        scene.world().modifyEntities(Chicken.class, Entity::discard);
        scene.idle(20);

        scene.world().createEntity(level -> {
            Chicken chicken = EntityType.CHICKEN.create(level);
            chicken.setBaby(true);
            Vec3 v = center.getBottomCenter();
            chicken.setPosRaw(v.x, v.y, v.z);
            chicken.setYRot(chicken.yRotO = 180);
            chicken.yHeadRotO = 180;
            chicken.yHeadRot = 180;
            chicken.setOnGround(true);
            return chicken;
        });
        scene.overlay().showText(60)
                .text("Generally, baby birds cannot be caught")
                .attachKeyFrame()
                .colored(PonderPalette.RED)
                .pointAt(util.vector().blockSurface(center, Direction.WEST))
                .placeNearTarget();
        scene.idle(50);
        scene.world().modifyEntities(Chicken.class, Entity::discard);
        scene.idle(20);

        scene.world().showSection(util.select().position(2, 1, 2), Direction.DOWN);
        scene.idle(20);
        scene.overlay().showControls(util.vector().topOf(center.above()), Pointing.DOWN, 40).rightClick()
                .withItem(CIFBlocks.ROOST.asStack());
        scene.idle(10);
        scene.overlay().showText(60)
                .text("Alternatively, bird can be collected from their Spawners directly")
                .attachKeyFrame()
                .pointAt(util.vector().blockSurface(center, Direction.WEST))
                .placeNearTarget();
        scene.idle(70);

        scene.world().modifyBlock(center,state -> CIFBlocks.CHICKEN_ROOST.getDefaultState(),false);
        scene.overlay().showText(50)
                .text("Roost with bird")
                .attachKeyFrame()
                .pointAt(util.vector().blockSurface(center, Direction.WEST))
                .placeNearTarget();
        scene.idle(60);
    }

    public static void lead(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("roost.lead", "Using Lead with Roost");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(10);

        BlockPos roost = util.grid().at(1, 1, 2);
        scene.world().createEntity(level -> {
            var chicken = EntityType.CHICKEN.create(level);
            Vec3 v = util.grid().at(3,1,2).getBottomCenter();
            chicken.setPosRaw(v.x, v.y, v.z);
            chicken.setYRot(chicken.yRotO = 180);
            chicken.yHeadRotO = 180;
            chicken.yHeadRot = 180;
            chicken.setOnGround(true);
            return chicken;
        });
        scene.idle(10);
        scene.world().showSection(util.select().position(1,1,2), Direction.UP);
        scene.idle(10);
        scene.overlay().showControls(util.vector().centerOf(3,2,2), Pointing.DOWN, 20).rightClick().withItem(Items.LEAD.getDefaultInstance());
        scene.idle(10);
        scene.overlay().showControls(util.vector().centerOf(1,2,2), Pointing.DOWN, 20).rightClick().withItem(Items.LEAD.getDefaultInstance());
        scene.world().modifyEntities(Chicken.class, Entity::discard);
        scene.world().modifyBlock(roost,state -> CIFBlocks.CHICKEN_ROOST.getDefaultState(),false);
        scene.idle(20);
        scene.overlay().showText(60)
                .text("Use a Lead to bring bird into the roost")
                .pointAt(util.vector().blockSurface(roost, Direction.WEST))
                .placeNearTarget();
        scene.idle(70);

        scene.addKeyframe();
        scene.overlay().showControls(util.vector().centerOf(1,2,2), Pointing.DOWN, 20).rightClick().withItem(Items.LEAD.getDefaultInstance());
        scene.world().modifyBlock(roost,state -> CIFBlocks.ROOST.getDefaultState(),false);
        scene.world().createEntity(level -> {
            var chicken = EntityType.CHICKEN.create(level);
            Vec3 v = util.grid().at(2,1,2).getBottomCenter();
            chicken.setPosRaw(v.x, v.y, v.z);
            chicken.setYRot(chicken.yRotO = 180);
            chicken.yHeadRotO = 180;
            chicken.yHeadRot = 180;
            chicken.setOnGround(true);
            return chicken;
        });
        scene.idle(20);
        scene.overlay().showText(60)
                .text("The bird in the Roost can be brought out by Lead")
                .pointAt(util.vector().centerOf(2,1,2))
                .placeNearTarget();
        scene.idle(70);

        scene.overlay().showText(60)
                .text("Poultry feeding and poultry product collecting, see Ponder of Chicken Roost")
                .pointAt(util.vector().centerOf(1,1,2))
                .attachKeyFrame()
                .placeNearTarget();
        scene.idle(70);
    }

    public static void operate(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
    }

    public static void fluid(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
    }
}
