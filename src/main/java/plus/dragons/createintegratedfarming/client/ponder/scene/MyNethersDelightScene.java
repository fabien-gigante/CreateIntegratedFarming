package plus.dragons.createintegratedfarming.client.ponder.scene;

import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import com.soytutta.mynethersdelight.common.registry.MNDBlocks;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.Direction;

import static com.soytutta.mynethersdelight.common.block.LetiosCompostBlock.FORGOTING;

public class MyNethersDelightScene {
    public static void catalyze(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("spout.catalyze_letios_compost", "Catalyzing Leteos Compost");
        scene.configureBasePlate(0, 0, 3);
        scene.world().showSection(util.select().everywhere(), Direction.DOWN);
        var spout = util.select().position(1, 3, 1);
        var leteosCompost = util.grid().at(1, 1, 1);

        scene.overlay().showText(100)
                .text("Forgetting process of Leteos Compost can be speed up via Spout in ultra warm dimension")
                .pointAt(util.vector().centerOf(1, 3, 1))
                .placeNearTarget();

        scene.world().modifyBlockEntityNBT(spout, SpoutBlockEntity.class, nbt -> nbt.putInt("ProcessingTicks", 20));
        scene.idle(20);
        scene.world().modifyBlock(leteosCompost, bs-> bs.setValue(FORGOTING,2),false);
        scene.idle(10);

        scene.world().modifyBlockEntityNBT(spout, SpoutBlockEntity.class, nbt -> nbt.putInt("ProcessingTicks", 20));
        scene.idle(20);
        scene.world().modifyBlock(leteosCompost, bs-> bs.setValue(FORGOTING,4),false);
        scene.idle(10);

        scene.world().modifyBlockEntityNBT(spout, SpoutBlockEntity.class, nbt -> nbt.putInt("ProcessingTicks", 20));
        scene.idle(20);
        scene.world().modifyBlock(leteosCompost, bs-> bs.setValue(FORGOTING,7),false);
        scene.idle(10);

        scene.world().modifyBlockEntityNBT(spout, SpoutBlockEntity.class, nbt -> nbt.putInt("ProcessingTicks", 20));
        scene.idle(20);
        scene.world().modifyBlock(leteosCompost, bs-> bs.setValue(FORGOTING,9),false);
        scene.idle(10);

        scene.world().modifyBlockEntityNBT(spout, SpoutBlockEntity.class, nbt -> nbt.putInt("ProcessingTicks", 20));
        scene.idle(20);
        scene.world().modifyBlock(leteosCompost, bs-> bs.setValue(FORGOTING,9),false);
        scene.idle(10);

        scene.world().modifyBlockEntityNBT(spout, SpoutBlockEntity.class, nbt -> nbt.putInt("ProcessingTicks", 20));
        scene.idle(20);
        scene.world().modifyBlock(leteosCompost, bs-> MNDBlocks.RESURGENT_SOIL.get().defaultBlockState(),false);
    }
}
