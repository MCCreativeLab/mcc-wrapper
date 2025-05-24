package de.verdox.mccreativelab.impl.vanilla.registry.recipe;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.gamefactory.recipe.MCCRecipe;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Optional;

public class NMSRecipeReference extends MCCHandle<RecipeHolder<?>> implements MCCReference<MCCRecipe> {
    public static final MCCConverter<RecipeHolder, NMSRecipeReference> CONVERTER = converter(NMSRecipeReference.class, RecipeHolder.class, NMSRecipeReference::new, holder -> (RecipeHolder) holder.getHandle());

    public NMSRecipeReference(RecipeHolder<?> handle) {
        super(handle);
    }

    @Override
    public Optional<MCCTypedKey<MCCRecipe>> unwrapKey() {
        return Optional.of(conversionService.wrap(handle.id(), new TypeToken<>() {}));
    }

    @Override
    public MCCRecipe get() {
        return conversionService.wrap(handle.value(), MCCRecipe.class);
    }
}
