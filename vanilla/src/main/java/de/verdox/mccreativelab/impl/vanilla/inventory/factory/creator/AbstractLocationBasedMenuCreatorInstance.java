package de.verdox.mccreativelab.impl.vanilla.inventory.factory.creator;

import de.verdox.mccreativelab.wrapper.annotations.MCCRequireVanillaElement;
import de.verdox.mccreativelab.wrapper.inventory.types.MCCLocatedContainerMenu;
import de.verdox.mccreativelab.wrapper.inventory.types.menu.creator.LocationBasedMenuCreatorInstance;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerLevelAccess;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractLocationBasedMenuCreatorInstance<F extends MCCLocatedContainerMenu<?, ?>> extends AbstractMenuCreatorInstance<F> implements LocationBasedMenuCreatorInstance<F> {
    private final MCCLocation mccLocation;
    protected ContainerLevelAccess containerLevelAccess;

    public AbstractLocationBasedMenuCreatorInstance(@MCCRequireVanillaElement @Nullable MCCLocation mccLocation) {
        if (mccLocation != null) {
            mccLocation.requireVanilla();
        }
        this.mccLocation = mccLocation;
        if (mccLocation == null) {
            this.containerLevelAccess = ContainerLevelAccess.NULL;
        } else {
            this.containerLevelAccess = ContainerLevelAccess.create(converter().unwrap(mccLocation.world()), new BlockPos(mccLocation.blockX(), mccLocation.blockY(), mccLocation.blockZ()));
        }
    }

    @Override
    public MCCLocation getLocation() {
        return mccLocation;
    }
}
