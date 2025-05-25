package de.verdox.mccreativelab.impl.vanilla.util.mixin;

import de.verdox.mccreativelab.gamefactory.item.MCCCustomItemType;

public interface ItemStackWithCustomType {
    MCCCustomItemType getMcc_wrapper$customItemType();

    void setMcc_wrapper$customItemType(MCCCustomItemType customItemType);
}
