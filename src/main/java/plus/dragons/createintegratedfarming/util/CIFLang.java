package plus.dragons.createintegratedfarming.util;

import net.createmod.catnip.lang.LangBuilder;
import net.createmod.catnip.lang.LangNumberFormat;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.ApiStatus.Internal;
import plus.dragons.createdragonsplus.common.CDPCommon;
import plus.dragons.createdragonsplus.util.CDPLang;
import plus.dragons.createintegratedfarming.common.CIFCommon;

@Internal
public class CIFLang {
    public static LangBuilder builder() {
        return new LangBuilder(CIFCommon.ID);
    }

    public static LangBuilder number(double d) {
        return builder().text(LangNumberFormat.format(d));
    }

    public static LangBuilder text(String text) {
        return builder().text(text);
    }

    public static LangBuilder translate(String key, Object... args) {
        return builder().translate(key, args);
    }

    public static LangBuilder description(String category, ResourceLocation location, Object... args) {
        return builder().add(Component.translatable(Util.makeDescriptionId(category, location), args));
    }

    public static LangBuilder description(String category, ResourceLocation location, String suffix, Object... args) {
        return builder().add(Component.translatable(Util.makeDescriptionId(category, location) + "." + suffix, args));
    }

    public static LangBuilder description(Holder<?> holder, Object... args) {
        var key = holder.getKey();
        if (key == null)
            throw new IllegalArgumentException("Can not build description for unregistered object: " + holder);
        return description(key.registry().getPath(), key.location(), args);
    }

    public static LangBuilder description(Holder<?> holder, String suffix, Object... args) {
        var key = holder.getKey();
        if (key == null)
            throw new IllegalArgumentException("Can not build description for unregistered object: " + holder);
        return description(key.registry().getPath(), key.location(), suffix, args);
    }

    public static LangBuilder block(BlockState state) {
        return builder().add(state.getBlock().getName());
    }

    public static LangBuilder item(ItemStack stack) {
        return builder().add(stack.getHoverName().copy());
    }

    public static LangBuilder fluid(FluidStack stack) {
        return builder().add(stack.getHoverName().copy());
    }
}
