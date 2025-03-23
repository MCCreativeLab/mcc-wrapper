package de.verdox.mccreativelab.impl.paper.platform.converter;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;

public class ComponentConverter implements MCCConverter<net.minecraft.network.chat.Component, Component> {

    @Override
    public MCCConverter.ConversionResult<Component> wrap(net.minecraft.network.chat.Component nativeType) {
        return done(PaperAdventure.asAdventure(nativeType));
    }

    @Override
    public MCCConverter.ConversionResult<net.minecraft.network.chat.Component> unwrap(Component platformImplType) {
        return done(PaperAdventure.asVanilla(platformImplType));
    }

    @Override
    public Class<Component> apiImplementationClass() {
        return Component.class;
    }

    @Override
    public Class<net.minecraft.network.chat.Component> nativeMinecraftType() {
        return net.minecraft.network.chat.Component.class;
    }
}