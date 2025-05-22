package de.verdox.mccreativelab.impl.paper.mixins;

import de.verdox.mccreativelab.gamefactory.recipe.RecipePredicate;
import de.verdox.mccreativelab.impl.vanilla.util.mixin.PredicateIngredient;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import io.papermc.paper.inventory.recipe.ItemOrExact;
import io.papermc.paper.inventory.recipe.StackedContentsExtrasMap;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(StackedContentsExtrasMap.class)
public class MixinStackedContentsExtrasMap {

    @Shadow
    @Final
    private StackedContents<ItemOrExact> contents;

    // Neue Felder für PredicateChoice
    private boolean hasPredicateChoice = false;
    private final List<RecipePredicate> predicateChoices = new ArrayList<>();

    // Injection in initialize() am Ende, um PredicateChoices zu sammeln
    @Inject(method = "initialize", at = @At("TAIL"))
    private void injectInitialize(Recipe<?> recipe, CallbackInfo ci) {
        for (net.minecraft.world.item.crafting.Ingredient ingredient : recipe.placementInfo().ingredients()) {
            if (((PredicateIngredient) (Object) ingredient).getItemPredicate() != null) {
                this.hasPredicateChoice = true;
                this.predicateChoices.add(((PredicateIngredient) (Object) ingredient).getItemPredicate());
            }
        }
    }

    // Injection in accountStack() vor dem return
    @Inject(method = "accountStack", at = @At("TAIL"), cancellable = true)
    private void injectAccountStack(ItemStack stack, int count, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasPredicateChoice) {
            for (RecipePredicate predicateChoice : this.predicateChoices) {
                if (predicateChoice.predicate().test(MCCPlatform.getInstance().getConversionService().wrap(stack, MCCItemStack.class))) {
                    this.contents.account(new ItemOrExact.Exact(stack), count);
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }
}
