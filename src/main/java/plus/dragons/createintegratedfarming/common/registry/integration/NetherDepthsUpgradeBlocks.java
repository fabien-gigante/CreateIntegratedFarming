package plus.dragons.createintegratedfarming.common.registry.integration;

import com.simibubi.create.AllTags;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SoundType;
import plus.dragons.createintegratedfarming.common.fishing.net.heatresistant.HeatResistantFishingNet;
import plus.dragons.createintegratedfarming.common.fishing.net.heatresistant.HeatResistantFishingNetMovementBehaviour;

import static com.simibubi.create.foundation.data.TagGen.axeOnly;
import static plus.dragons.createintegratedfarming.common.CIFCommon.REGISTRATE;

public class NetherDepthsUpgradeBlocks {
    public static final BlockEntry<HeatResistantFishingNet> HEAT_RESISTANT_FISHING_NET= REGISTRATE
            .block("heat_resistant_fishing_net", HeatResistantFishingNet::new)
            .lang("Heat-Resistant Fishing Net")
            .initialProperties(SharedProperties::wooden)
            .properties(prop -> prop
                    .mapColor(DyeColor.GREEN)
                    .sound(SoundType.SCAFFOLDING)
                    .noOcclusion())
            .transform(axeOnly())
            .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .blockstate(BlockStateGen.directionalBlockProvider(false))
            .onRegister(block -> MovementBehaviour.REGISTRY.register(block, new HeatResistantFishingNetMovementBehaviour()))
            .simpleItem()
            .register();

    public static final BlockEntry<HeatResistantFishingNet> CRIMSON_HEAT_RESISTANT_FISHING_NET= REGISTRATE
            .block("crimson_heat_resistant_fishing_net", HeatResistantFishingNet::new)
            .lang("Crimson Heat-Resistant Fishing Net")
            .initialProperties(SharedProperties::wooden)
            .properties(prop -> prop
                    .mapColor(DyeColor.RED)
                    .sound(SoundType.SCAFFOLDING)
                    .noOcclusion())
            .transform(axeOnly())
            .tag(AllTags.AllBlockTags.WINDMILL_SAILS.tag)
            .blockstate(BlockStateGen.directionalBlockProvider(false))
            .onRegister(block -> MovementBehaviour.REGISTRY.register(block, new HeatResistantFishingNetMovementBehaviour()))
            .simpleItem()
            .register();

    public static void register() {}
}
