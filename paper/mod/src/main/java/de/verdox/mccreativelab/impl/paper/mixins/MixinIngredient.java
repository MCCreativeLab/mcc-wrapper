package de.verdox.mccreativelab.impl.paper.mixins;

import de.verdox.mccreativelab.gamefactory.recipe.RecipePredicate;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.impl.vanilla.util.mixin.PredicateIngredient;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import io.papermc.paper.inventory.recipe.ItemOrExact;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(Ingredient.class)
public class MixinIngredient implements PredicateIngredient {
    // Paper start - Add itemPredicate field
    @Unique
    @Nullable
    public RecipePredicate mcc_wrapper$itemPredicate;
    // Paper end

    @Override
    public RecipePredicate mcc_wrapper$getItemPredicate() {
        return mcc_wrapper$itemPredicate;
    }

    @Override
    public void mcc_wrapper$setItemPredicate(RecipePredicate predicate) {
        this.mcc_wrapper$itemPredicate = predicate;
    }


    @Inject(method = "test", at = @At("HEAD"), cancellable = true)
    public void injectPredicateTest(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (this.mcc_wrapper$itemPredicate != null) {
            cir.setReturnValue(this.mcc_wrapper$itemPredicate.predicate().test(MCCPlatform.getInstance().getConversionService().wrap(stack, MCCItemStack.class)));
        }
    }

    @Inject(method = "acceptsItem(Lio/papermc/paper/inventory/recipe/ItemOrExact;)Z", at = @At("HEAD"), cancellable = true)
    public void injectAcceptsItem(ItemOrExact itemOrExact, CallbackInfoReturnable<Boolean> cir) {
        if(this.mcc_wrapper$itemPredicate != null && itemOrExact instanceof ItemOrExact.Exact exact) {
            cir.setReturnValue(mcc_wrapper$itemPredicate.predicate().test(MCCPlatform.getInstance().getConversionService().wrap(exact.stack(), MCCItemStack.class)));
        }
    }

    @Inject(method = "display", at = @At("HEAD"), cancellable = true)
    public void injectDisplay(CallbackInfoReturnable<SlotDisplay> cir) {
        if (this.mcc_wrapper$itemPredicate != null) {
            List<SlotDisplay> displays = this.mcc_wrapper$itemPredicate
                    .recipeBookExamples()
                    .stream()
                    .map(mccItemStack -> MCCPlatform.getInstance().getConversionService().unwrap(mccItemStack, ItemStack.class))
                    .map(item -> (SlotDisplay) new SlotDisplay.ItemStackSlotDisplay(item)).toList();
            cir.setReturnValue(new SlotDisplay.Composite(displays));
        }
    }
}
