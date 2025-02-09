package de.verdox.mccreativelab.classgenerator;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.classgenerator.codegen.ClassBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedParameterizedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedType;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.registry.MCCTag;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Blocks;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Used to convert classes like Items.class or Blocks.class to MCCreativeLab classes
 */
public class TypedKeyCollectionBuilder extends AbstractClassGenerator {

    public TypedKeyCollectionBuilder(File srcDir, File implSrcDir, String prefix, String suffix, List<Class<?>> excludedTypes, List<String> excludedPackages) {
        super(srcDir, implSrcDir, prefix, suffix, excludedTypes, excludedPackages);
    }

    public <F> List<Result> generateForPlatformGroupingClass(Class<?> groupingClass, TypeToken<F> typeToGroup, ResourceKey<? extends Registry<F>> registryKey, String newClassPackage, String groupClassName) throws IOException, IllegalAccessException {
        Logger.getLogger(TypedKeyCollectionBuilder.class.getSimpleName()).info("Generate collection class " + groupClassName + ".");
        List<Result> list = new LinkedList<>();


        String minecraftRegistryKey = registryKey.location().getPath();
        ClassBuilder classBuilder = new ClassBuilder();
        classBuilder.withPackage(newClassPackage);
        classBuilder.withHeader("public", ClassBuilder.ClassHeader.CLASS, groupClassName, "");

        classBuilder.withField("public static final", CapturedParameterizedType.from(Key.class), "VANILLA_REGISTRY_KEY", "Key.key(\"minecraft\", \"" + minecraftRegistryKey + "\")");

        for (Field declaredField : groupingClass.getDeclaredFields()) {
            if (!Modifier.isPublic(declaredField.getModifiers())) {
                continue;
            }
            if (!Modifier.isStatic(declaredField.getModifiers())) {
                continue;
            }
            if (!Modifier.isFinal(declaredField.getModifiers())) {
                continue;
            }

            Type fieldType = declaredField.getGenericType();
            Object fieldValue = declaredField.get(null);
            if (isForbiddenType(fieldType)) {
                Logger.getLogger(TypedKeyCollectionBuilder.class.getSimpleName()).warning("Type was found that has no wrapper yet in MCC. (" + CapturedType.from(fieldType) + ")");
                continue;
            }
            String fieldName = declaredField.getName();
            CapturedParameterizedType resultFieldType;
            Type nativeType;

            try {
                if (ResourceKey.class.isAssignableFrom(declaredField.getType()) ||
                        Holder.class.isAssignableFrom(declaredField.getType()) ||
                        TagKey.class.isAssignableFrom(declaredField.getType())) {
                    ParameterizedType type = (ParameterizedType) declaredField.getGenericType();
                    nativeType = type.getActualTypeArguments()[0];

                    String key;
                    if (fieldValue instanceof ResourceKey<?> resourceKey) {
                        key = resourceKey.location().getPath();
                        resultFieldType = setupTypedKeyField(classBuilder, key, fieldName, nativeType);
                    } else if (fieldValue instanceof Holder<?> holder) {
                        key = holder.unwrapKey().get().location().getPath();
                        resultFieldType = setupReferenceField(classBuilder, key, fieldName, nativeType);
                    } else if (fieldValue instanceof TagKey<?> tagKey) {
                        key = tagKey.location().getPath();
                        resultFieldType = setupTagKeyField(classBuilder, key, fieldName, nativeType);
                    } else {
                        throw new IllegalStateException("Could not create field for " + fieldName + " with type " + declaredField.getGenericType());
                    }
                } else if (typeToGroup.getRawType().isAssignableFrom(declaredField.getType())) {
                    Registry<F> registry = findRegistry(registryKey);
                    if (registry == null) {
                        LOGGER.warning("Could not find registry with key " + registryKey);
                        break;
                    }

                    ResourceLocation resourceLocation = registry.getKey((F) fieldValue);
                    if (resourceLocation == null) {
                        LOGGER.warning("Could not find key of field " + fieldName + " with type " + typeToGroup + ". We are stopping here because the registry lookup code cannot find these types in general.");
                        break;
                    }
                    String key = resourceLocation.getPath();
                    nativeType = fieldType;
                    resultFieldType = setupReferenceField(classBuilder, key, fieldName, fieldType);
                } else {
                    throw new IllegalStateException();
                }
            } catch (Throwable e) {
                Logger.getLogger(TypedKeyCollectionBuilder.class.getSimpleName()).warning("Exceptionally (" + e.getCause() + ") skipping field " + fieldName + "(" + fieldType + ")" + " of collection class " + groupClassName);
                continue;
            }

            if (isForbiddenType(nativeType)) {
                Logger.getLogger(TypedKeyCollectionBuilder.class.getSimpleName()).warning("Skipping forbidden field " + fieldName + "(" + fieldType + ")" + " of collection class " + groupClassName);
                continue;
            }

            classBuilder.includeImport(CapturedType.from(Key.class));
            classBuilder.includeImport(CapturedType.from(TypeToken.class));
            classBuilder.includeImport(CapturedType.from(MCCPlatform.class));
            list.add(new Result(groupingClass, declaredField.getName(), CapturedType.from(fieldType), fieldName, resultFieldType));
        }
        if (list.isEmpty()) {
            Logger.getLogger(TypedKeyCollectionBuilder.class.getSimpleName()).warning("Could not generate collection class " + groupClassName + " because no fields were found.");
            return list;
        } else {
            classBuilder.writeClassFile(apiSrcDir);
        }
        return list;
    }

    public record Result(Class<?> groupingClass, String nmsFieldName, CapturedType<?, ?> nmsFieldType,
                         String apiFieldName, CapturedParameterizedType apiFieldType) {}

    private CapturedParameterizedType setupTypedKeyField(ClassBuilder classBuilder, String key, String fieldName, Type nativeType) {
        CapturedType<?, ?> swappedNativeType = CapturedType.swap(nativeType);
        CapturedParameterizedType resultFieldType = CapturedParameterizedType.from(MCCTypedKey.class).withExplicitType(swappedNativeType);

        String initValue = "MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key(\"minecraft\", \"" + key + "\"), VANILLA_REGISTRY_KEY, new TypeToken<>(){})";
        classBuilder.withField("public static final", resultFieldType, fieldName, initValue);
        classBuilder.includeImport(swappedNativeType);
        classBuilder.includeImport(resultFieldType);

        return resultFieldType;
    }

    private CapturedParameterizedType setupReferenceField(ClassBuilder classBuilder, String key, String fieldName, Type nativeType) {
        CapturedType<?, ?> swappedNativeType = CapturedType.swap(nativeType);
        CapturedParameterizedType resultFieldType = CapturedParameterizedType.from(MCCReference.class).withExplicitType(swappedNativeType);

        String initValue = "MCCPlatform.getInstance().getTypedKeyFactory().getKey(Key.key(\"minecraft\", \"" + key + "\"), VANILLA_REGISTRY_KEY, new TypeToken<" + swappedNativeType.toString() + ">(){}).getAsReference()";
        classBuilder.withField("public static final", resultFieldType, fieldName, initValue);
        classBuilder.includeImport(swappedNativeType);
        classBuilder.includeImport(resultFieldType);

        return resultFieldType;
    }

    private CapturedParameterizedType setupTagKeyField(ClassBuilder classBuilder, String key, String fieldName, Type nativeType) {
        CapturedType<?, ?> swappedNativeType = CapturedType.swap(nativeType);
        CapturedParameterizedType resultFieldType = CapturedParameterizedType.from(MCCTag.class).withExplicitType(swappedNativeType);

        String initValue = "MCCPlatform.getInstance().getTypedKeyFactory().createTag(Key.key(\"minecraft\", \"" + key + "\"), VANILLA_REGISTRY_KEY, new TypeToken<>(){})";
        classBuilder.withField("public static final", resultFieldType, fieldName, initValue);
        classBuilder.includeImport(swappedNativeType);
        classBuilder.includeImport(resultFieldType);

        return resultFieldType;
    }

    public static <T> Registry<T> findRegistry(ResourceKey<? extends Registry<T>> registryKey) {
        Registry<T> found = MinecraftServer.getServer().registries().compositeAccess().get(registryKey).map(Holder.Reference::value).orElse(null);
        if(found != null){
            return found;
        }
        found = MinecraftServer.getServer().registryAccess().get(registryKey).map(Holder.Reference::value).orElse(null);
        if(found != null){
            return found;
        }
        found = MinecraftServer.getServer().reloadableRegistries().lookup().get(registryKey).map(Holder.Reference::value).orElse(null);
        if(found != null){
            return found;
        }
        found = (Registry<T>) BuiltInRegistries.REGISTRY.get(registryKey.location()).map(Holder.Reference::value).orElse(null);
        if(found != null){
            return found;
        }
        return null;
    }
}
