package de.verdox.mccreativelab.custom.block;

import de.verdox.mccreativelab.custom.annotation.MCCCustomType;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import org.jetbrains.annotations.NotNull;

@MCCCustomType
public interface MCCCustomBlockState extends MCCBlockState {
    @Override
    @NotNull MCCCustomBlockType getBlockType();
}
