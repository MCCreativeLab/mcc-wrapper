package de.verdox.mccreativelab.custom.item;

import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public abstract class MCCCustomItemType implements MCCItemType {

    private final Key key;
    private final MCCDataComponentMap standardComponents;

    public MCCCustomItemType(Key key, MCCDataComponentMap standardComponents) {
        this.key = key;
        this.standardComponents = standardComponents;
    }

    @Override
    public @NotNull MCCItemStack createItem() {
        return null;
    }

    @Override
    public MCCDataComponentMap getItemStandardComponentMap() {
        return standardComponents;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public @NotNull Key key() {
        return key;
    }
}
