package de.verdox.mccreativelab.impl.vanilla.entity.types;

import com.google.common.reflect.TypeToken;
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
        return readFieldFromHandle("target", new TypeToken<>() {});
    }

    @Override
    public void setOwner(UUID owner) {
        handle.setTarget(owner);
    }

    @Override
    public UUID getThrower() {
        return readFieldFromHandle("thrower", new TypeToken<>() {});
    }

    @Override
    public void setThrower(UUID thrower) {
        writeFieldInHandle("thrower", thrower);
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
        int health = readFieldFromHandle("health", new TypeToken<>() {});
        return (short) health;
    }

    @Override
    public void setHealth(short health) {
        writeFieldInHandle("health", (int) health);
    }

    @Override
    public short getAge() {
        return (short) handle.getAge();
    }

    @Override
    public void setAge(short age) {
        writeFieldInHandle("age", (int) age);
    }

    @Override
    public short getPickupDelay() {
        int pickupDelay = readFieldFromHandle("pickupDelay", new TypeToken<>() {});
        return (short) pickupDelay;
    }

    @Override
    public void setPickupDelay(short pickupDelay) {
        writeFieldInHandle("pickupDelay", (int) pickupDelay);
    }
}
