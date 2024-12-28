package de.verdox.mccreativelab.wrapper.item;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.annotations.MCCBuiltIn;
import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.annotations.MCCInstantiationSource;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.typed.MCCRegistries;
import de.verdox.vserializer.generic.Serializer;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@MCCInstantiationSource(sourceClasses = MCCItemStack.class)
@MCCBuiltIn(syncState = MCCBuiltIn.SyncState.SYNCED, clientEntersErrorStateOnDesync = true)
public interface MCCItemType extends MCCKeyedWrapper {
    Serializer<MCCItemType> SERIALIZER = MCCKeyedWrapper.createSerializer("item", new TypeToken<>() {});

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

    @Override
    default Key getRegistryKey(){
        return MCCRegistries.ITEM_REGISTRY.key();
    }
}
