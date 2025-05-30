package de.verdox.mccreativelab.impl.vanilla.entity.types;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.entity.NMSEntity;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
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
        return conversionService.wrap(handle.getItem(), new TypeToken<>() {});
    }

    @Override
    public void setItem(MCCItemStack item) {
        handle.setItem(conversionService.unwrap(item));
    }

    @Override
    @MCCReflective
    public short getHealth() {
        int health = readFieldFromHandle("health", new TypeToken<>() {});
        return (short) health;
    }

    @Override
    @MCCReflective
    public void setHealth(short health) {
        writeFieldInHandle("health", (int) health);
    }

    @Override
    public short getAge() {
        return (short) handle.getAge();
    }

    @Override
    @MCCReflective
    public void setAge(short age) {
        writeFieldInHandle("age", (int) age);
    }

    @Override
    @MCCReflective
    public short getPickupDelay() {
        int pickupDelay = readFieldFromHandle("pickupDelay", new TypeToken<>() {});
        return (short) pickupDelay;
    }

    @Override
    @MCCReflective
    public void setPickupDelay(short pickupDelay) {
        writeFieldInHandle("pickupDelay", (int) pickupDelay);
    }
}
