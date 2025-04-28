package de.verdox.mccreativelab.wrapper.entity;

public enum MCCTeleportFlag {
    /**
     * If all passengers should not be required to be removed prior to teleportation.
     * <p>
     * Note:
     * Teleporting to a different world with this flag present while the entity has entities riding it
     * will cause this teleportation to return false and not occur.
     */
    RETAIN_PASSENGERS,
    /**
     * If the entity should not be dismounted if they are riding another entity.
     * <p>
     * Note:
     * Teleporting to a different world with this flag present while this entity is riding another entity will
     * cause this teleportation to return false and not occur.
     */
    RETAIN_VEHICLE,
    /**
     * Indicates that a player should not have their current open inventory closed when teleporting.
     * <p>
     * Note:
     * This option will be ignored when teleported to a different world.
     */
    RETAIN_OPEN_INVENTORY;
}
