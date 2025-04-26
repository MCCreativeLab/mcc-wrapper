package de.verdox.mccreativelab.wrapper.registry;

import de.verdox.vserializer.generic.SerializationContext;
import de.verdox.vserializer.generic.Serializer;
import de.verdox.vserializer.json.JsonSerializerContext;
import net.kyori.adventure.key.Keyed;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public abstract class RegistrySerializerBootstrapper<T extends Keyed> {
    private static final Logger LOGGER = Logger.getLogger(RegistrySerializerBootstrapper.class.getSimpleName());

    protected final OpenRegistry<T> registry;
    protected final Serializer<T> elementSerializer;

    public RegistrySerializerBootstrapper(OpenRegistry<T> registry, Serializer<T> elementSerializer) {
        this.registry = registry;
        this.elementSerializer = elementSerializer;
    }

    public final void bootstrap(T... standardValues) {

        loadRegistryEntries();
        if (registry.keySet().isEmpty()) {
            for (T standardValue : standardValues) {
                registry.register(standardValue.key(), standardValue);
                saveAsPreset(standardValue);
            }
        }
    }

    protected abstract void loadRegistryEntries();

    protected abstract void saveAsPreset(T standardValue);

    public static class Json<T extends Keyed> extends RegistrySerializerBootstrapper<T> {
        private final SerializationContext context = new JsonSerializerContext();
        private final File registryFolder;

        public Json(File registryFolder, OpenRegistry<T> registry, Serializer<T> elementSerializer) {
            super(registry, elementSerializer);
            this.registryFolder = registryFolder;
            this.registryFolder.mkdirs();
        }

        @Override
        protected void loadRegistryEntries() {
            try (Stream<Path> stream = Files.walk(registryFolder.toPath(), 1).skip(1)) {
                stream.filter(path -> path.toFile().getName().contains(".json"))
                        .forEach(path -> {
                            try {
                                T deserialized = elementSerializer.deserialize(context.readFromFile(path.toFile()));
                                if (deserialized == null)
                                    return;
                                registry.register(deserialized.key(), deserialized);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An error occurred while loading registry entries for registry " + registry.getRegistryKey(), e);
            }
        }

        @Override
        protected void saveAsPreset(T standardValue) {
            File file = new File(registryFolder + "/" + standardValue.key().namespace() + "_" + standardValue.key().value() + ".json");
            try {
                context.writeToFile(elementSerializer.serialize(context, standardValue), file);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "An error occurred while saving a registry preset for registry " + registry.getRegistryKey(), e);
            }
        }
    }
}
