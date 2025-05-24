package de.verdox.mccreativelab.impl.vanilla.mixins;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import de.verdox.mccreativelab.impl.vanilla.util.mixin.MutableRecipeMap;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Map;

@Mixin(RecipeMap.class)
public abstract class MixinRecipeMap implements MutableRecipeMap {
    @Accessor("byType")
    public abstract Multimap<RecipeType<?>, RecipeHolder<?>> getByType();

    @Accessor("byKey")
    public abstract Map<ResourceKey<Recipe<?>>, RecipeHolder<?>> getByKey();

    @Inject(method = "create", at = @At("RETURN"), cancellable = true)
    private static void injectCreate(Iterable<RecipeHolder<?>> recipes, CallbackInfoReturnable<RecipeMap> cir) {
        ImmutableMultimap.Builder<RecipeType<?>, RecipeHolder<?>> builder = ImmutableMultimap.builder();
        ImmutableMap.Builder<ResourceKey<Recipe<?>>, RecipeHolder<?>> builder2 = ImmutableMap.builder();

        for (RecipeHolder<?> recipeHolder : recipes) {
            builder.put(recipeHolder.value().getType(), recipeHolder);
            builder2.put(recipeHolder.id(), recipeHolder);
        }

        Multimap<RecipeType<?>, RecipeHolder<?>> multimap = com.google.common.collect.LinkedHashMultimap.create(builder.build());
        Map<ResourceKey<Recipe<?>>, RecipeHolder<?>> map = com.google.common.collect.Maps.newLinkedHashMap(builder2.build());

        RecipeMap modifiedMap = createRecipeMapViaReflection(multimap, map);
        cir.setReturnValue(modifiedMap);
    }

    @Unique
    @MCCReflective
    private static RecipeMap createRecipeMapViaReflection(Multimap<RecipeType<?>, RecipeHolder<?>> recipes, Map<ResourceKey<Recipe<?>>, RecipeHolder<?>> byKey) {
        try {
            Constructor<RecipeMap> constructor = RecipeMap.class.getDeclaredConstructor(Multimap.class, Map.class);
            constructor.setAccessible(true);
            return constructor.newInstance(recipes, byKey);
        } catch (Exception e) {
            throw new RuntimeException("Unable to instantiate RecipeMap via reflection", e);
        }
    }

    @Override
    public void mcc_wrapper$addCustomRecipe(RecipeHolder<?> holder) {
        Collection<RecipeHolder<?>> recipes = getByType().get(holder.value().getType());

        if (getByKey().containsKey(holder.id())) {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + holder.id());
        } else {
            recipes.add(holder);
            getByKey().put(holder.id(), holder);
        }
    }

    @Override
    public <T extends RecipeInput> boolean mcc_wrapper$removeAnyRecipe(ResourceKey<Recipe<T>> mcKey) {
        final RecipeHolder<Recipe<T>> remove = (RecipeHolder<Recipe<T>>) getByKey().remove(mcKey);
        if (remove == null) {
            return false;
        }
        final Collection<? extends RecipeHolder<? extends Recipe<T>>> recipes = this.byType(remove.value().getType());
        return recipes.remove(remove);
    }

    public <I extends RecipeInput, T extends Recipe<I>> Collection<RecipeHolder<T>> byType(RecipeType<T> type) {
        return (Collection) getByType().get(type);
    }
}
