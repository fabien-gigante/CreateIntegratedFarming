package plus.dragons.createintegratedfarming.common.fishing.net;

import com.mojang.authlib.GameProfile;
import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.UsernameCache;
import net.neoforged.neoforge.common.util.FakePlayer;
import plus.dragons.createdragonsplus.util.CodeReference;
import plus.dragons.createintegratedfarming.util.CIFLang;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.UUID;

@CodeReference(value = DeployerFakePlayer.class, license = "mit",source = "create")
public class FishingNetFakePlayer extends FakePlayer {
    public static final UUID fallbackID = UUID.fromString("9e2faded-cafe-4ec2-c314-dad129ae971c");
    private UUID owner;

    public FishingNetFakePlayer(ServerLevel level, @Nullable UUID owner) {
        super(level, new FishingNetGameProfile(fallbackID, "FishingNet", owner));
        this.owner = owner;
    }

    @Override
    public OptionalInt openMenu(MenuProvider menuProvider) {
        return OptionalInt.empty();
    }

    @Override
    public Component getDisplayName() {
        return CIFLang.translate("block.fishing_net.damage_source_name").component(); // Do we really need?
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return super.getDefaultDimensions(pose).withEyeHeight(0);
    }

    @Override
    public Vec3 position() {
        return new Vec3(getX(), getY(), getZ());
    }

    @Override
    public float getCurrentItemAttackStrengthDelay() {
        return 1 / 64f;
    }

    @Override
    public boolean canEat(boolean ignoreHunger) {
        return false;
    }

    @Override
    public ItemStack eat(Level level, ItemStack food, FoodProperties foodProperties) {
        food.shrink(1);
        return food;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        return false;
    }

    @Override
    public UUID getUUID() {
        return owner == null ? super.getUUID() : owner;
    }

    @Override
    protected boolean doesEmitEquipEvent(EquipmentSlot equipmentSlot) {
        return false;
    }

    // Credit to Mekanism for this approach. Helps fake players get past claims and
    // protection by other mods
    private static class FishingNetGameProfile extends GameProfile {

        private UUID owner;

        public FishingNetGameProfile(UUID id, String name, UUID owner) {
            super(id, name);
            this.owner = owner;
        }

        @Override
        public UUID getId() {
            return owner == null ? super.getId() : owner;
        }

        @Override
        public String getName() {
            if (owner == null)
                return super.getName();
            String lastKnownUsername = UsernameCache.getLastKnownUsername(owner);
            return lastKnownUsername == null ? super.getName() : lastKnownUsername;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o)
                return true;
            if (!(o instanceof GameProfile otherProfile))
                return false;
            return Objects.equals(getId(), otherProfile.getId()) && Objects.equals(getName(), otherProfile.getName());
        }

        @Override
        public int hashCode() {
            UUID id = getId();
            String name = getName();
            int result = id == null ? 0 : id.hashCode();
            result = 31 * result + (name == null ? 0 : name.hashCode());
            return result;
        }
    }
}
