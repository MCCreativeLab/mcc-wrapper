package de.verdox.mccreativelab.wrapper.world.acessor.local;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntityType;
import de.verdox.mccreativelab.wrapper.entity.MCCTeleportFlag;
import de.verdox.mccreativelab.wrapper.entity.types.MCCItemEntity;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.world.MCCEntitySpawnReason;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.acessor.global.WorldEntityAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface ChunkEntityAccessor<
        SELF extends ChunkEntityAccessor<SELF, WORLD_ACCESS>,
        WORLD_ACCESS extends WorldEntityAccessor<WORLD_ACCESS, SELF>
        > extends ChunkAccessor<SELF, WORLD_ACCESS> {

    /**
     * Used to summon an entity at a specified location
     * @param location the location
     * @param mccEntityType the entity type
     * @return a future
     */
    <T extends MCCEntity> T summon(@NotNull MCCLocation location, @NotNull MCCEntityType<T> mccEntityType, @NotNull MCCEntitySpawnReason spawnReason);

    /**
     * Teleports an entity to another location.
     *
     * @param location the location
     * @return the future that is completed when the teleportation is done
     */
    boolean teleport(@NotNull MCCLocation location, @NotNull MCCEntity entity, MCCTeleportFlag... flags);

    /**
     * Drops an item at the specified {@link MCCLocation} with a random offset
     * Note that functions will run before the entity is spawned
     *
     * @param location     Location to drop the item
     * @param item         ItemStack to drop
     * @param dropCallback the function to be run before the entity is spawned.
     * @return ItemDrop entity created as a result of this method
     */
    MCCItemEntity dropItemNaturally(MCCLocation location, MCCItemStack item, @Nullable Consumer<MCCItemEntity> dropCallback);

    /**
     * Drops an item at the specified {@link MCCLocation} with a random offset
     * Note that functions will run before the entity is spawned
     *
     * @param location     Location to drop the item
     * @param item         ItemStack to drop
     * @param dropCallback the function to be run before the entity is spawned.
     * @return ItemDrop entity created as a result of this method
     */
    MCCItemEntity dropItem(MCCLocation location, MCCItemStack item, @Nullable Consumer<MCCItemEntity> dropCallback);

    // DEFAULT METHODS

    /**
     * Drops an item at the specified {@link MCCLocation} with a random offset
     * Note that functions will run before the entity is spawned
     *
     * @param location Location to drop the item
     * @param item     ItemStack to drop
     * @return ItemDrop entity created as a result of this method
     */
    default MCCItemEntity dropItemNaturally(MCCLocation location, MCCItemStack item) {
        return dropItemNaturally(location, item, null);
    }
}
