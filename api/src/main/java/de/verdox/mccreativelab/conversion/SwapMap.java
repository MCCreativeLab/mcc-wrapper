package de.verdox.mccreativelab.conversion;

import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;
import de.verdox.vserializer.SerializableField;
import de.verdox.vserializer.generic.Serializer;
import de.verdox.vserializer.generic.SerializerBuilder;
import de.verdox.vserializer.json.JsonSerializerContext;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class SwapMap {
    public static final Serializer<SwapMap> SERIALIZER = SerializerBuilder.create("swapMap", SwapMap.class)
            .constructor(
                    new SerializableField<SwapMap, Map<Description, Description>>("cache", Serializer.Map.create(Description.SERIALIZER, Description.SERIALIZER, HashMap::new), SwapMap::getNativeToApiClasses),
                    descriptionDescriptionMap -> {
                        SwapMap swapMap = new SwapMap();
                        if(descriptionDescriptionMap != null){
                            swapMap.nativeToApiClasses.putAll(descriptionDescriptionMap);
                        }
                        return swapMap;
                    }
            )
            .build();

    private static final Logger LOGGER = Logger.getLogger(SwapMap.class.getSimpleName());
    Map<Description, Description> nativeToApiClasses = new HashMap<>();

    public Map<Description, Description> getNativeToApiClasses() {
        return nativeToApiClasses;
    }

    public ClassType<?> swap(ClassType<?> classDescription) {
        var desc = Description.from(classDescription);
        var found = nativeToApiClasses.getOrDefault(desc, desc);
        return ClassType.create(found.className, found.packageName);
    }

    public record Description(String className, String packageName, @Nullable Description declaringClass) {
        public static Description from(ClassType<?> classDescription) {
            return new Description(classDescription.getClassName(), classDescription.getPackageName(), classDescription.getDeclaringClass() != null ? Description.from(classDescription.getDeclaringClass().getRawType()) : null);
        }

        public static Description from(Class<?> clazz) {
            return from(ClassType.from(clazz));
        }

        public static final Serializer<Description> SERIALIZER = SerializerBuilder.create("description", Description.class)
                .constructor(
                        (s) -> new SerializableField<>("className", Serializer.Primitive.STRING, Description::className),
                        (s) -> new SerializableField<>("package", Serializer.Primitive.STRING, Description::packageName),
                        (s) -> new SerializableField<>("declaringClass", s, Description::declaringClass),
                        Description::new
                )
                .build();
    }

    public void saveToFile(File file) {
        JsonSerializerContext serializationContext = new JsonSerializerContext();
        try {
            serializationContext.writeToFile(SERIALIZER.serialize(serializationContext, this), file);
            LOGGER.info("Generated swap map at: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SwapMap loadFromFile(File file) {
        JsonSerializerContext serializationContext = new JsonSerializerContext();
        try {
            var result = SERIALIZER.deserialize(serializationContext.readFromFile(file));
            LOGGER.info("Loaded swap map from " + file.getAbsolutePath() + " with " + result.nativeToApiClasses.size() + " entries");
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
