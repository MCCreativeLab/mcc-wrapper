package de.verdox.mccreativelab.impl.vanilla.inventory.factory.creator;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Function4;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.wrapper.annotations.MCCRequireVanillaElement;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainer;
import de.verdox.mccreativelab.wrapper.inventory.types.MCCSharedContainerMenu;
import de.verdox.mccreativelab.wrapper.inventory.types.menu.creator.SharedMenuCreatorInstance;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.kyori.adventure.text.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import org.apache.commons.lang3.function.TriFunction;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

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
