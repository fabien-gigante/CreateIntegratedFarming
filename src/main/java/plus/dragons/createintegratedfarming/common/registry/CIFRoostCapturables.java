package plus.dragons.createintegratedfarming.common.registry;

import net.minecraft.world.entity.EntityType;
import plus.dragons.createintegratedfarming.common.ranching.roost.RoostCapturable;

public class CIFRoostCapturables {
    public static void register() {
        RoostCapturable.REGISTRY.register(EntityType.CHICKEN, CIFBlocks.CHICKEN_ROOST.get());
    }
}
