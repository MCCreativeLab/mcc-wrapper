package de.verdox.mccreativelab.wrapper.inventory;

import de.verdox.mccreativelab.wrapper.inventory.types.menu.creator.MenuCreatorInstance;
import net.kyori.adventure.text.Component;

public class MCCMenuProvider<F extends MCCContainerMenu<?, ?>> {
    private final MenuCreatorInstance<F> creatorInstance;
    private Component title;

    public MCCMenuProvider(MenuCreatorInstance<F> creatorInstance, Component title) {
        this.creatorInstance = creatorInstance;
        this.title = title;
    }

    public MenuCreatorInstance<F> getCreatorInstance() {
        return creatorInstance;
    }

    public Component getTitle() {
        return title;
    }

    public void setTitle(Component title) {
        this.title = title;
    }
}
