package de.verdox.mccreativelab.wrapper.item;

import de.verdox.mccreativelab.wrapper.annotations.MCCBuiltIn;
import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.annotations.MCCInstantiationSource;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@MCCInstantiationSource(sourceClasses = MCCItemStack.class)
@MCCBuiltIn(syncState = MCCBuiltIn.SyncState.SYNCED, clientEntersErrorStateOnDesync = true)
public interface MCCItemType extends MCCKeyedWrapper {
    @NotNull
    MCCItemStack createItem();

    /**
     * Returns the item standard component map
     * @return the standard component map of this item type
     */
    MCCDataComponentMap getItemStandardComponentMap();

    default boolean isSame(@Nullable MCCItemStack stack) {
        if (stack == null)
            return false;
        return equals(stack.getType());
    }

    boolean isEmpty();
}
