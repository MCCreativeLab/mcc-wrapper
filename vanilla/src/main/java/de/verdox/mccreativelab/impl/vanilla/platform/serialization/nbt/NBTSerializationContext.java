package de.verdox.mccreativelab.impl.vanilla.platform.serialization.nbt;

import de.verdox.vserializer.blank.BlankSerializationArray;
import de.verdox.vserializer.blank.BlankSerializationContainer;
import de.verdox.vserializer.blank.BlankSerializationContext;
import de.verdox.vserializer.generic.*;
import net.minecraft.nbt.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NBTSerializationContext extends BlankSerializationContext {
    private static final Logger LOGGER = Logger.getLogger(NBTSerializationContext.class.getSimpleName());

    public SerializationElement fromNBT(Tag tag) {
        if (tag instanceof CollectionTag<?> collectionTag) {
            BlankSerializationArray blankSerializationArray = new BlankSerializationArray(this);
            collectionTag.forEach(sub -> {
                SerializationElement element = fromNBT(sub);
                if (element != null && !element.isNull()) {
                    blankSerializationArray.add(element);
                }
            });
            return blankSerializationArray;
        } else if (tag instanceof CompoundTag compoundTag) {
            BlankSerializationContainer container = new BlankSerializationContainer(this);
            for (String key : compoundTag.getAllKeys()) {
                SerializationElement element = fromNBT(compoundTag.get(key));
                if (element != null && !element.isNull()) {
                    container.set(key, element);
                }
            }
            return container;
        } else if (tag instanceof ByteTag primitive) {
            return create(primitive.getAsByte());
        } else if (tag instanceof ShortTag primitive) {
            return create(primitive.getAsShort());
        } else if (tag instanceof IntTag primitive) {
            return create(primitive.getAsInt());
        } else if (tag instanceof LongTag primitive) {
            return create(primitive.getAsLong());
        } else if (tag instanceof FloatTag primitive) {
            return create(primitive.getAsFloat());
        } else if (tag instanceof DoubleTag primitive) {
            return create(primitive.getAsDouble());
        } else if (tag instanceof StringTag stringTag) {
            return create(stringTag.getAsString());
        } else if (tag instanceof EndTag) {
            return createNull();
        }
        LOGGER.log(Level.SEVERE, "Unknown tag type: " + tag.getType().getPrettyName() + " for tag " + tag.getClass().getName());
        return null;
    }

    public Tag fromAPI(SerializationElement serializationElement) {
        if (serializationElement instanceof SerializationContainer container) {
            CompoundTag compoundTag = new CompoundTag();
            for (String childKey : container.getChildKeys()) {
                var e = fromAPI(container.get(childKey));
                if (e == null) continue;
                compoundTag.put(childKey, e);
            }
            return compoundTag;
        } else if (serializationElement instanceof SerializationArray array) {
            if (array.isByteArray()) {
                return NbtOps.INSTANCE.createByteList(ByteBuffer.wrap(array.getAsByteArray()));
            } else if (array.isIntArray()) {
                return NbtOps.INSTANCE.createIntList(Arrays.stream(array.getAsIntArray()));
            } else if (array.isLongArray()) {
                return NbtOps.INSTANCE.createLongList(Arrays.stream(array.getAsLongArray()));
            } else {
                return NbtOps.INSTANCE.createList(array.stream().map(this::fromAPI).filter(Objects::nonNull));
            }
        } else if (serializationElement instanceof SerializationPrimitive primitive) {
            if (primitive.isBoolean()) {
                return NbtOps.INSTANCE.createBoolean(primitive.getAsBoolean());
            } else if (primitive.isByte()) {
                return NbtOps.INSTANCE.createByte(primitive.getAsByte());
            } else if (primitive.isShort()) {
                return NbtOps.INSTANCE.createShort(primitive.getAsShort());
            } else if (primitive.isInteger()) {
                return NbtOps.INSTANCE.createInt(primitive.getAsInt());
            } else if (primitive.isLong()) {
                return NbtOps.INSTANCE.createLong(primitive.getAsLong());
            } else if (primitive.isFloat()) {
                return NbtOps.INSTANCE.createFloat(primitive.getAsFloat());
            } else if (primitive.isDouble()) {
                return NbtOps.INSTANCE.createDouble(primitive.getAsDouble());
            } else if (primitive.isString()) {
                return NbtOps.INSTANCE.createString(primitive.getAsString());
            }
        }
        return null;
    }

    @Override
    public void writeToFile(SerializationElement element, File file) {
        Tag tag = fromAPI(element);
        if (tag instanceof CompoundTag compoundTag) {
            try {
                NbtIo.write(compoundTag, file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException("Cannot write tags of type " + tag.getType().getPrettyName());
    }

    @Override
    public SerializationElement readFromFile(File file) {
        try {
            return fromNBT(NbtIo.read(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
