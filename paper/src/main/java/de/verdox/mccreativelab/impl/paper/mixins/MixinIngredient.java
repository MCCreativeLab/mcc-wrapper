package de.verdox.mccreativelab.impl.paper.mixins;

import de.verdox.mccreativelab.impl.paper.recipe.PredicateChoice;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

@Mixin(Ingredient.class)
public class MixinIngredient implements PredicateIngredient {
    // Paper start - Add itemPredicate field
    @Nullable
    public PredicateChoice itemPredicate;
    // Paper end

    // Paper start - Add static constructor for PredicateChoice
    @Invoker("of")
    private static Ingredient invokeOf(Stream<? extends ItemLike> items) {
        throw new AssertionError(); // Will be transformed by Mixin
    }

    @Override
    public PredicateChoice getItemPredicate() {
        return itemPredicate;
    }

    @Override
    public void setItemPredicate(PredicateChoice predicate) {
        this.itemPredicate = predicate;
    }


    @Inject(method = "test", at = @At("HEAD"), cancellable = true)
    public void injectPredicateTest(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (this.itemPredicate != null) {
            cir.setReturnValue(this.itemPredicate.test(stack.getBukkitStack()));
        }
    }

    @Inject(method = "display", at = @At("HEAD"), cancellable = true)
    public void injectDisplay(CallbackInfoReturnable<SlotDisplay> cir) {
        if (this.itemPredicate != null) {
            List<SlotDisplay> displays = this.itemPredicate.recipeBookExamples().stream()
                    .map(item -> (SlotDisplay) new SlotDisplay.ItemStackSlotDisplay(
                            ((org.bukkit.craftbukkit.inventory.CraftItemStack) item).handle
                    )).toList();
            cir.setReturnValue(new SlotDisplay.Composite(displays));
        }
    }
}
