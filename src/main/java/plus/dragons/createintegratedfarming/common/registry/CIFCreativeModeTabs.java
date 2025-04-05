package plus.dragons.createintegratedfarming.common.registry;

import com.simibubi.create.AllCreativeModeTabs;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.createintegratedfarming.common.CIFCommon;
import plus.dragons.createintegratedfarming.util.CIFLang;

import static plus.dragons.createintegratedfarming.common.CIFCommon.REGISTRATE;
import static plus.dragons.createintegratedfarming.common.registry.CIFBlocks.*;

public class CIFCreativeModeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, CIFCommon.ID);
    public static final Holder<CreativeModeTab> BASE = TABS.register("base", CIFCreativeModeTabs::base);

    public static void register(IEventBus modBus) {
        TABS.register(modBus);
    }

    private static CreativeModeTab base(ResourceLocation id) {
        return CreativeModeTab.builder()
                .title(REGISTRATE.addLang("itemGroup", id, "Create: Integrated Farming"))
                .withTabsBefore(AllCreativeModeTabs.BASE_CREATIVE_TAB.getId())
                .icon(CHICKEN_COOP::asStack)
                .displayItems(CIFCreativeModeTabs::buildBaseContents)
                .build();
    }

    private static void buildBaseContents(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        output.accept(EMPTY_CHICKEN_COOP);
        output.accept(CHICKEN_COOP);
    }
}
