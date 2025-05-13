package de.verdox.mccreativelab.container;

import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import org.jetbrains.annotations.NotNull;

public interface MCCCustomInventory {

    @NotNull Slot[] getSlots();
    @NotNull MCCItemStack quickMoveStack(@NotNull MCCPlayer player, int slot);

    interface Slot {
        default void onQuickCraft(@NotNull MCCItemStack stack, int amount) {
        }

        default void onSwapCraft(int amount) {
        }

        default void onTake(@NotNull MCCPlayer player, @NotNull MCCItemStack stack) {
        }

        default boolean mayPlace(@NotNull MCCItemStack stack) {
            return true;
        }

        default int getMaxStackSize() {
            return 64;
        }

        default int getMaxStackSize(@NotNull MCCItemStack stack) {
            return Math.min(getMaxStackSize(), stack.getMaxStackSize());
        }

        default boolean isActive() {
            return true;
        }

        default boolean mayPickUp(@NotNull MCCPlayer player){
            return true;
        }
    }
}