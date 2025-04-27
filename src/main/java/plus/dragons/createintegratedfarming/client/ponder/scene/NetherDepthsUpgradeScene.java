package plus.dragons.createintegratedfarming.client.ponder.scene;

import com.scouter.netherdepthsupgrade.entity.NDUEntity;
import com.scouter.netherdepthsupgrade.items.NDUItems;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.WorldSectionElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;

public class NetherDepthsUpgradeScene {
    public static void lavaFishing(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("lava_fishing_net", "Using Lava Fishing Net on Contraptions");
        scene.configureBasePlate(0, 0, 6);
        scene.scaleSceneView(0.76f);
        var contraptionSelection = util.select().fromTo(0, 1, 5, 3, 3, 5)
                .add(util.select().position(4, 2, 5));
        scene.world().showSection(util.select().everywhere().substract(contraptionSelection), Direction.DOWN);
        ElementLink<WorldSectionElement> contraption = scene.world().showIndependentSection(contraptionSelection, Direction.DOWN);
        scene.idle(10);

        scene.world().configureCenterOfRotation(contraption, util.vector().centerOf(4, 3, 5));
        scene.overlay().showText(60)
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(util.vector().blockSurface(util.grid().at(2, 1, 5), Direction.NORTH))
                .text("Whenever Lava Fishing Nets are moved as part of an animated Contraption...");
        scene.idle(70);

        scene.world().rotateBearing(util.grid().at(4, 3, 5), -360, 140);
        scene.world().rotateSection(contraption, 0, -360, 0, 140);
        scene.overlay().showText(100)
                .pointAt(util.vector().blockSurface(util.grid().at(2, 2, 5), Direction.EAST))
                .text("They will fish in lava, like a player fishing with a Lava Fishing Rod")
                .placeNearTarget();
        scene.idle(140);

        scene.overlay().showText(80)
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(util.vector().blockSurface(util.grid().at(2, 1, 5), Direction.NORTH))
                .text("Lava Fishing Net will catch small animal in lava");
        scene.world().hideSection(util.select().fromTo(0, 1, 0, 5, 3, 2), Direction.NORTH);
        var salmon = scene.world().createEntity(level -> {
            var fish = NDUEntity.OBSIDIAN_FISH.get().create(level);
            var pos = util.vector().centerOf(2, 2, 1);
            fish.setPos(pos.x, pos.y, pos.z);
            return fish;
        });
        scene.idle(10);

        scene.world().rotateBearing(util.grid().at(4, 3, 5), -360, 140);
        scene.world().rotateSection(contraption, 0, -360, 0, 140);
        scene.idle(20);
        scene.world().modifyEntity(salmon, Entity::discard);
        scene.idle(120);
        scene.overlay().showControls(util.vector().centerOf(0, 2, 5), Pointing.UP, 40).rightClick()
                .withItem(NDUItems.OBSIDIANFISH.toStack());
        scene.idle(40);
    }
}
