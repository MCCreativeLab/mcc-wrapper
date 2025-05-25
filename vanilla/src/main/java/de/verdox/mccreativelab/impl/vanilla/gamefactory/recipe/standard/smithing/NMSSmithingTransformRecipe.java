package de.verdox.mccreativelab.impl.vanilla.gamefactory.recipe.standard.smithing;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.standard.smithing.MCCSmithingTransformRecipe;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;

public class NMSSmithingTransformRecipe extends NMSSmithingRecipe<SmithingTransformRecipe> implements MCCSmithingTransformRecipe {
    public static final MCCConverter<SmithingTransformRecipe, NMSSmithingTransformRecipe> CONVERTER = converter(NMSSmithingTransformRecipe.class, SmithingTransformRecipe.class, NMSSmithingTransformRecipe::new, MCCHandle::getHandle);

    public NMSSmithingTransformRecipe(SmithingTransformRecipe handle) {
        super(handle);
    }

    public NMSSmithingTransformRecipe(SmithingTransformRecipe handle, boolean custom) {
        super(handle, custom);
    }

    @Override
    @MCCReflective
    public MCCItemStack getResult() {
        return conversionService.wrap(ReflectionUtils.readFieldFromClass(handle, "result", new TypeToken<>() {}), new TypeToken<>() {});
    }

    @Override
    public String getGroup() {
        return handle.group();
    }
}
