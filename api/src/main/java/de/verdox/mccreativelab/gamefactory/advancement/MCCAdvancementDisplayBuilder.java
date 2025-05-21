package de.verdox.mccreativelab.gamefactory.advancement;

import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public interface MCCAdvancementDisplayBuilder {
    // TODO: @NotNull AdvancementDisplayBuilder setFrame(@NotNull AdvancementDisplay.Frame frame);

    @NotNull MCCAdvancementDisplayBuilder setTitle(@NotNull Component title);

    @NotNull MCCAdvancementDisplayBuilder setDescription(@NotNull Component description);

    @NotNull MCCAdvancementDisplayBuilder setIcon(@NotNull MCCItemStack icon);

    @NotNull MCCAdvancementDisplayBuilder setShowToast(boolean showToast);

    @NotNull MCCAdvancementDisplayBuilder setAnnounceToChat(boolean announceToChat);

    @NotNull MCCAdvancementDisplayBuilder setHidden(boolean hidden);

    @NotNull MCCAdvancementDisplayBuilder setBackground(@NotNull MCCTypedKey background); // TODO: Implement generic

    @NotNull MCCAdvancementDisplayBuilder setX(float x);

    @NotNull MCCAdvancementDisplayBuilder setY(float y);
}