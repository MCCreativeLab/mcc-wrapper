package de.verdox.mccreativelab.wrapper.entity;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.MCCKeyedWrapper;
import de.verdox.mccreativelab.wrapper.annotations.MCCBuiltIn;
import de.verdox.mccreativelab.wrapper.annotations.MCCInstantiationSource;
import de.verdox.mccreativelab.wrapper.platform.serialization.MCCSerializers;
import de.verdox.mccreativelab.wrapper.typed.MCCRegistries;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.vserializer.generic.Serializer;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@MCCInstantiationSource(sourceClasses = {MCCEntity.class})
@MCCBuiltIn(syncState = MCCBuiltIn.SyncState.SYNCED, clientEntersErrorStateOnDesync = true)
public interface MCCEntityType<T extends MCCEntity> extends MCCKeyedWrapper {
    Serializer<MCCEntityType<?>> SERIALIZER = MCCSerializers.KEYED_WRAPPER("entityType", new TypeToken<>() {});

    default CompletableFuture<T> summon(@NotNull MCCLocation location) {
        return location.world().summon(location, this);
    }

    MCCEntity constructNewEntity();

    @Override
    default Key getRegistryKey() {
        return MCCRegistries.ENTITY_TYPE.key();
    }
}
