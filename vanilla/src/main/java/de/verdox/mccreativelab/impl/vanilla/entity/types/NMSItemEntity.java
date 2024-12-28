package de.verdox.mccreativelab.impl.vanilla.entity.types;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.entity.NMSEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCItemEntity;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.UUID;

public class NMSItemEntity<T extends ItemEntity> extends NMSEntity<T> implements MCCItemEntity {
    public static final MCCConverter<ItemEntity, NMSItemEntity> CONVERTER = MCCHandle.converter(NMSItemEntity.class, ItemEntity.class, NMSItemEntity::new, nmsItemEntity -> (ItemEntity) nmsItemEntity.handle);

    public NMSItemEntity(T handle) {
        super(handle);
    }

    @Override
    public UUID getOwner() {
        return handle.target;
    }

    @Override
    public void setOwner(UUID owner) {
        handle.target = owner;
    }

    @Override
    public UUID getThrower() {
        return handle.thrower;
    }

    @Override
    public void setThrower(UUID thrower) {
        handle.thrower = thrower;
    }

    @Override
    public MCCItemStack getItem() {
        return conversionService.wrap(handle.getItem());
    }

    @Override
    public void setItem(MCCItemStack item) {
        handle.setItem(conversionService.unwrap(item));
    }

    @Override
    public short getHealth() {
        return (short) handle.health;
    }

    @Override
    public void setHealth(short health) {
        handle.health = health;
    }

    @Override
    public short getAge() {
        return (short) handle.age;
    }

    @Override
    public void setAge(short age) {
        handle.age = age;
    }

    @Override
    public short getPickupDelay() {
        return (short) handle.pickupDelay;
    }

    @Override
    public void setPickupDelay(short pickupDelay) {
        handle.pickupDelay = pickupDelay;
    }
}
