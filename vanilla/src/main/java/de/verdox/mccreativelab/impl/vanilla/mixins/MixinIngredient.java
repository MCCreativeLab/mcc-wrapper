package de.verdox.mccreativelab.impl.vanilla.mixins;

import de.verdox.mccreativelab.gamefactory.recipe.RecipePredicate;
import de.verdox.mccreativelab.impl.vanilla.util.mixin.PredicateIngredient;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(Ingredient.class)
public class MixinIngredient implements PredicateIngredient {
    // Paper start - Add itemPredicate field
    @Nullable
    public RecipePredicate itemPredicate;
    // Paper end

    @Override
    public RecipePredicate getItemPredicate() {
        return itemPredicate;
    }

    @Override
    public void setItemPredicate(RecipePredicate predicate) {
        this.itemPredicate = predicate;
    }


    @Inject(method = "test", at = @At("HEAD"), cancellable = true)
    public void injectPredicateTest(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (this.itemPredicate != null) {
            cir.setReturnValue(this.itemPredicate.predicate().test(MCCPlatform.getInstance().getConversionService().wrap(stack, MCCItemStack.class)));
        }
    }

    @Inject(method = "display", at = @At("HEAD"), cancellable = true)
    public void injectDisplay(CallbackInfoReturnable<SlotDisplay> cir) {
        if (this.itemPredicate != null) {
            List<SlotDisplay> displays = this.itemPredicate
                    .recipeBookExamples()
                    .stream()
                    .map(mccItemStack -> MCCPlatform.getInstance().getConversionService().unwrap(mccItemStack, ItemStack.class))
                    .map(item -> (SlotDisplay) new SlotDisplay.ItemStackSlotDisplay(item)).toList();
            cir.setReturnValue(new SlotDisplay.Composite(displays));
        }
    }
}
