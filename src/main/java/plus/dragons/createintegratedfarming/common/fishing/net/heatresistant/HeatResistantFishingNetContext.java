package plus.dragons.createintegratedfarming.common.fishing.net.heatresistant;

import com.scouter.netherdepthsupgrade.entity.NDUEntity;
import com.scouter.netherdepthsupgrade.entity.entities.LavaFishingBobberEntity;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import plus.dragons.createintegratedfarming.common.fishing.net.FishingNetFakePlayer;
import plus.dragons.createintegratedfarming.config.CIFConfig;
import plus.dragons.createintegratedfarming.mixin.integration.LavaFishingBobberEntityInvoker;

import java.util.HashSet;
import java.util.Set;

public class HeatResistantFishingNetContext {
    protected final ItemStack fishingRod;
    protected final LavaFishingBobberEntity fishingHook;
    protected final Set<BlockPos> visitedBlocks = new HashSet<>(
            Math.min(16, CIFConfig.server().fishingNetMaxRecordedBlocks.get()));
    public int timeUntilCatch;

    public HeatResistantFishingNetContext(ServerLevel level, ItemStack fishingRod) {
        this.fishingRod = fishingRod;
        this.fishingHook = new LavaFishingBobberEntity(NDUEntity.LAVA_BOBBER.get(), level);
        this.fishingHook.setOwner(new FishingNetFakePlayer(level,null));
        this.reset(level);
    }

    public void reset(ServerLevel level) {
        this.visitedBlocks.clear();
        int lureSpeed = (int) (EnchantmentHelper.getFishingTimeReduction(level, fishingRod, fishingHook) * 20.0F);
        this.timeUntilCatch = (Mth.nextInt(fishingHook.getRandom(), 100, 600) - lureSpeed) *
                CIFConfig.server().fishingNetCooldownMultiplier.get();
    }

    public boolean visitNewPositon(ServerLevel level, BlockPos pos) {
        boolean inLava = ((LavaFishingBobberEntityInvoker)fishingHook).createintegratedfarming$invokeCalculateOpenLava(pos);
        if (!inLava)
            return false;
        if (visitedBlocks.size() < CIFConfig.server().fishingNetMaxRecordedBlocks.get())
            visitedBlocks.add(pos);
        return true;
    }

    public LootParams buildLootContext(MovementContext context, ServerLevel level, BlockPos pos) {
        fishingHook.setPos(context.position);
        return new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, context.position)
                .withParameter(LootContextParams.TOOL, fishingRod)
                .withParameter(LootContextParams.THIS_ENTITY, fishingHook)
                .withParameter(LootContextParams.ATTACKING_ENTITY, context.contraption.entity)
                .withLuck(EnchantmentHelper.getFishingLuckBonus(level, fishingRod, context.contraption.entity))
                .create(LootContextParamSets.FISHING);
    }

    public boolean canCatch() {
        int maxRecorded = CIFConfig.server().fishingNetMaxRecordedBlocks.get();
        if (maxRecorded == 0)
            return true;
        return fishingHook.getRandom().nextInt(maxRecorded) < visitedBlocks.size();
    }

    public void invalidate(ServerLevel level) {
        reset(level);
        fishingHook.discard();
    }

    public FishingHook getFishingHook() {
        return fishingHook;
    }

    public ItemStack getFishingRod() {
        return fishingRod;
    }
}
