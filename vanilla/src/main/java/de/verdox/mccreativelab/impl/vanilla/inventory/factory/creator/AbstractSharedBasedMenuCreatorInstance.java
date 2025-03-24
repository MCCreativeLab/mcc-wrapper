package de.verdox.mccreativelab.impl.vanilla.inventory.factory.creator;

import com.google.common.base.Preconditions;
import de.verdox.mccreativelab.wrapper.annotations.MCCRequireVanillaElement;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainer;
import de.verdox.mccreativelab.wrapper.inventory.types.MCCSharedContainerMenu;
import de.verdox.mccreativelab.wrapper.inventory.types.menu.creator.SharedMenuCreatorInstance;

import java.util.Objects;

public abstract class AbstractSharedBasedMenuCreatorInstance<C extends MCCContainer, F extends MCCSharedContainerMenu<?, ?>> extends AbstractMenuCreatorInstance<F> implements SharedMenuCreatorInstance<C, F> {
    protected final C container;

    public AbstractSharedBasedMenuCreatorInstance(@MCCRequireVanillaElement C container) {
        Objects.requireNonNull(container, "The container cannot be null");
        container.requireVanilla();
        Preconditions.checkArgument(isRightContainerSize(container));
        this.container = container;
    }

    @Override
    public C getSharedContainer() {
        return container;
    }

    public abstract boolean isRightContainerSize(C mccContainer);
}
