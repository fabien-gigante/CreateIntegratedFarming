package plus.dragons.createintegratedfarming.data;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import plus.dragons.createintegratedfarming.common.CIFCommon;

import static plus.dragons.createintegratedfarming.common.CIFCommon.REGISTRATE;

@Mod(CIFCommon.ID)
public class CIFData {
    public CIFData(IEventBus modBus) {
        if (!DatagenModLoader.isRunningDataGen())
            return;
        REGISTRATE.registerForeignLocalization();
    }
}
