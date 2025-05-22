package de.verdox.mccreativelab.impl.paper.mixins;

import de.verdox.mccreativelab.impl.paper.recipe.PredicateChoice;
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
    private final List<PredicateChoice> predicateChoices = new ArrayList<>();

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
            for (PredicateChoice predicateChoice : this.predicateChoices) {
                if (predicateChoice.test(stack.getBukkitStack())) {
                    this.contents.account(new ItemOrExact.Exact(stack), count);
                    cir.setReturnValue(true); // Signalisiert, dass der Stack berücksichtigt wurde
                    return;
                }
            }
        }
    }
}
