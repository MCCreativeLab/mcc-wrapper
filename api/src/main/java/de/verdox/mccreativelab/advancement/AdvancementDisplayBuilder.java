package de.verdox.mccreativelab.advancement;

import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface AdvancementDisplayBuilder {
    // TODO: @NotNull AdvancementDisplayBuilder setFrame(@NotNull AdvancementDisplay.Frame frame);

    @NotNull AdvancementDisplayBuilder setTitle(@NotNull Component title);

    @NotNull AdvancementDisplayBuilder setDescription(@NotNull Component description);

    @NotNull AdvancementDisplayBuilder setIcon(@NotNull MCCItemStack icon);

    @NotNull AdvancementDisplayBuilder setShowToast(boolean showToast);

    @NotNull AdvancementDisplayBuilder setAnnounceToChat(boolean announceToChat);

    @NotNull AdvancementDisplayBuilder setHidden(boolean hidden);

    @NotNull AdvancementDisplayBuilder setBackground(@NotNull MCCTypedKey background); // TODO: Implement generic

    @NotNull AdvancementDisplayBuilder setX(float x);

    @NotNull AdvancementDisplayBuilder setY(float y);
}