package de.verdox.mccreativelab.wrapper.entity.types;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;

import java.util.UUID;

public interface MCCItemEntity extends MCCEntity {

    UUID getOwner();

    void setOwner(UUID owner);

    UUID getThrower();

    void setThrower(UUID thrower);

    MCCItemStack getItem();

    void setItem(MCCItemStack item);

    short getHealth();

    void setHealth(short health);

    short getAge();

    void setAge(short age);

    short getPickupDelay();

    void setPickupDelay(short pickupDelay);
}
