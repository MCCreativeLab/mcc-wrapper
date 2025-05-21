package de.verdox.mccreativelab.impl.paper.component.entity;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.impl.vanilla.platform.serialization.nbt.NBTSerializationContext;
import de.verdox.mccreativelab.wrapper.component.AbstractComponent;
import de.verdox.mccreativelab.wrapper.component.entity.MCCPersistent;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.vserializer.generic.SerializationElement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;

public class PaperPersistent extends AbstractComponent<Entity, MCCEntity> implements MCCPersistent {
    public PaperPersistent(MCCEntity entity) {
        super(entity, new TypeToken<>() {});
    }

    @Override
    public boolean isPersistent() {
        return handle.persist;
    }

    @Override
    public void setPersistent(boolean value) {
        handle.persist = value;
    }

    @Override
    public SerializationElement serializePersistentData() {
        CompoundTag compoundTag = new CompoundTag();
        Entity entity = MCCPlatform.getInstance().getConversionService().unwrap(getOwner(), Entity.class);
        entity.saveWithoutId(compoundTag);
        NBTSerializationContext nbtSerializationContext = (NBTSerializationContext) MCCPlatform.getInstance().getNBTSerializationContext();
        return nbtSerializationContext.fromNBT(compoundTag);
    }

    @Override
    public void loadPersistentDataIntoObject(SerializationElement serializationElement) {
        NBTSerializationContext nbtSerializationContext = (NBTSerializationContext) MCCPlatform.getInstance().getNBTSerializationContext();
        Tag tag = nbtSerializationContext.fromAPI(serializationElement);
        if(tag instanceof CompoundTag compoundTag) {
            Entity entity = MCCPlatform.getInstance().getConversionService().unwrap(getOwner(), Entity.class);
            entity.load(compoundTag);
        }
    }
}
