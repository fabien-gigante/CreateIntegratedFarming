package plus.dragons.createintegratedfarming.client;

import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import plus.dragons.createintegratedfarming.client.ponder.CIFPonderPlugin;
import plus.dragons.createintegratedfarming.common.CIFCommon;

@Mod(value = CIFCommon.ID, dist = Dist.CLIENT)
public class CIFClient {
    public CIFClient(IEventBus modBus) {
        modBus.addListener(CIFClient::setup);
    }

    public static void setup(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new CIFPonderPlugin());
    }
}
