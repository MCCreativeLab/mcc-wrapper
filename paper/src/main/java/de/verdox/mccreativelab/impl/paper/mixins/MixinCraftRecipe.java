package de.verdox.mccreativelab.impl.paper.mixins;

import com.google.common.base.Preconditions;
import de.verdox.mccreativelab.impl.paper.recipe.PredicateChoice;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.bukkit.craftbukkit.inventory.CraftRecipe;
import org.bukkit.inventory.RecipeChoice;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftRecipe.class)
public class MixinCraftRecipe {


    // Injection in die Methode toIngredient(...)
    @Inject(
            method = "toIngredient(Lorg/bukkit/inventory/RecipeChoice;Z)Lnet/minecraft/world/item/crafting/Ingredient;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void injectToIngredient(RecipeChoice bukkit, boolean requireNotEmpty, CallbackInfoReturnable<Ingredient> cir) {
        if (bukkit instanceof PredicateChoice predicateChoice) {
            Ingredient ingredient = ofPredicateChoice(predicateChoice);

            if (requireNotEmpty) {
                Preconditions.checkArgument(!ingredient.isEmpty(), "Recipe requires at least one non-air choice");
            }

            cir.setReturnValue(ingredient);
        }
    }

    // Injection in die Methode toBukkit(...)
    @Inject(
            method = "toBukkit(Lnet/minecraft/world/item/crafting/Ingredient;)Lorg/bukkit/inventory/RecipeChoice;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void injectToBukkit(Ingredient list, CallbackInfoReturnable<RecipeChoice> cir) {
        if (((PredicateIngredient) (Object) list).getItemPredicate() != null) {
            cir.setReturnValue(((PredicateIngredient) (Object) list).getItemPredicate());
        }
    }

    private static Ingredient ofPredicateChoice(PredicateChoice predicateChoice) {
        Ingredient ingredient = Ingredient.of(predicateChoice.recipeBookExamples().stream().map(itemStack -> ((org.bukkit.craftbukkit.inventory.CraftItemStack) itemStack).handle).map(ItemStack::getItem));
        ((PredicateIngredient) (Object) ingredient).setItemPredicate(predicateChoice);
        return ingredient;
    }

}
