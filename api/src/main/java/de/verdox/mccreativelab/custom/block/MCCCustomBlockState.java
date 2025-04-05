package de.verdox.mccreativelab.custom.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import org.jetbrains.annotations.NotNull;

public interface MCCCustomBlockState extends MCCBlockState {
    int getLightEmission();

    @Override
    @NotNull MCCCustomBlockType getBlockType();
}
