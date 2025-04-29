package plus.dragons.createintegratedfarming.mixin.integration;

import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.scouter.netherdepthsupgrade.events.ForgeEvents")
public class ForgeEventsMixin {
    @Inject(method = "changeFish", at = @At(value = "HEAD"), cancellable = true)
    private static void createintegratedfarming$temporaryFixNullPlayerIssue(ItemFishedEvent event, CallbackInfo ci) {
        if(event.getEntity()==null) ci.cancel();
    }

    // Due to reason unknown, FishingNetFakePlayer will be occasionally marked as DISCARD. This causes getOwner to return null.
    // Also, FishingHook entity allows null owner, which also leads to null player issue in ItemFishedEvent
}
