package plus.dragons.createintegratedfarming.mixin.integration;

import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(targets = "com.scouter.netherdepthsupgrade.entity.entities.LavaFishingBobberEntity")
public interface LavaFishingBobberEntityInvoker {
    @Invoker("calculateOpenLava")
    boolean invokeCalculateOpenLava(BlockPos pos);
}
