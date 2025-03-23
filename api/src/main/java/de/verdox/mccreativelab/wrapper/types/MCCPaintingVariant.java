package de.verdox.mccreativelab.wrapper.types;

import java.util.Optional;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.key.Key;

public interface MCCPaintingVariant {
    public int getWidth();

    public int getHeight();

    public Key getAssetId();

    public Optional<Component> getTitle();

    public Optional<Component> getAuthor();
}