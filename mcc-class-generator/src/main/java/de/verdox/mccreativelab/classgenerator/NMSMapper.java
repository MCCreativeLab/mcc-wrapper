package de.verdox.mccreativelab.classgenerator;

import de.verdox.mccreativelab.classgenerator.codegen.ClassBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.*;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.MutableClassType;
import de.verdox.mccreativelab.conversion.SwapMap;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.ItemLike;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.File;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Logger;

public class NMSMapper {
    public static final Logger LOGGER = Logger.getLogger(NMSMapper.class.getSimpleName());
    private static final String swapCacheVersion = "1.21.1-R0.1-SNAPSHOT";
    private static final SwapMap swapMap = SwapMap.loadFromFile(new File("../../versionSwapMaps/" + swapCacheVersion + "/swap_map.json"));

    private static final Map<ClassType<?>, ClassSwap> SWAP_MAP = new HashMap<>();

    static {
        swapMap.getNativeToApiClasses().forEach((description, description2) -> {
            //LOGGER.info("Adding swap from " + description + " to " + description2 + " to swap map");


            Class<?> foundClass = findClass(description);
            if (foundClass == null) {
                LOGGER.warning("Could not find " + description.packageName() + "." + description.className() + " via reflection");
                return;
            }

            SWAP_MAP.put(ClassType.from(foundClass), new ClassSwap(from(description2)));
        });
    }

    private static MutableClassType from(SwapMap.Description description) {
        if (description == null) {
            return null;
        }
        MutableClassType mutableClassType = MutableClassType.create(description.className(), description.packageName());
        if (description.declaringClass() != null) {
            mutableClassType.setDeclaringClass(CapturedParameterizedType.from(from(description.declaringClass())));
        }
        return mutableClassType;
    }

    private static Class<?> findClass(SwapMap.Description description) {
        // Initialisierung der Reflections-Bibliothek

        List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        FilterBuilder filterBuilder = new FilterBuilder();
        filterBuilder.includePackage(description.packageName());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), Scanners.Resources)
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(filterBuilder));

        // Alle Klassen im Paket suchen
        Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);
        allClasses.addAll(reflections.getSubTypesOf(Record.class));
        // Nach der gewünschten Klasse filtern
        for (Class<?> clazz : allClasses) {
            if (clazz.getSimpleName().equals(description.className())) {
                return clazz;
            }
        }
        return null;
    }

    public static void register(ClassType<?> from, ClassType<?> to) {
        if (from == null || to == null) {
            return;
        }
        ClassSwap classSwap = new ClassSwap(to);
        SWAP_MAP.put(from, classSwap);
    }

    public static void register(Class<?> from, ClassType<?> to) {
        register(ClassType.from(from), to);
    }

    public static void register(ClassType<?> from, Class<?> to) {
        register(from, ClassType.from(to));
    }

    public static void register(Class<?> from, Class<?> to) {
        register(ClassType.from(from), ClassType.from(to));
    }

    static {
        //TODO: Disabled -> We are using the platform converter now in the NMSMapper
        //useSwapCacheFromPlatform();
        // NMS to adventure
        register(Component.class, net.kyori.adventure.text.Component.class);
        register(MutableComponent.class, net.kyori.adventure.text.Component.class);

        // NMS to MCC
        register(GlobalPos.class, MCCLocation.class);
        register(ItemLike.class, MCCItemStack.class);
        register(DataComponentType.class, MCCDataComponentType.class);

        // NMS to java
        register(Void.class, ClassType.from(void.class));
        //register(NonNullList.class, List.class, true);
        //register(IntList.class, IntList.class, true);
    }

    public static boolean isSwapped(Type type) {
        return isSwapped(CapturedType.from(type));
    }

    public static boolean isSwapped(CapturedType<?, ?> type) {
        return swap(type) != null;
    }

    public record ClassSwap(ClassType<?> newType) {

    }

    public static <T extends CapturedType<?, ?>> T swap(T typeToSwap) {
        if (typeToSwap instanceof ClassType<?> classType) {
            if (!SWAP_MAP.containsKey(classType)) {
                return typeToSwap;
            }
            return (T) SWAP_MAP.get(classType).newType();
        } else if (typeToSwap instanceof CapturedParameterizedType capturedParameterizedType) {
            ClassType<?> swappedRawType = swap(capturedParameterizedType.getRawType());
            return (T) CapturedParameterizedType.from(swappedRawType)
                    .withOwner(swap(capturedParameterizedType.getOwner()))
                    .withExplicitTypes(swap(capturedParameterizedType.getTypeArguments()), true);
        } else if (typeToSwap instanceof CapturedTypeVariable capturedTypeVariable) {
            return (T) CapturedTypeVariable.create(capturedTypeVariable.getName())
                    .withUpperBounds(swap(capturedTypeVariable.getUpperBounds()), true);
        } else if (typeToSwap instanceof CapturedWildcardType capturedWildcardType) {
            return (T) CapturedWildcardType.create()
                    .withUpperBounds(swap(capturedWildcardType.getUpperBounds()), true)
                    .withLowerBounds(swap(capturedWildcardType.getLowerBounds()), true);
        }

        return typeToSwap;
    }

    public static <T extends CapturedType<?, ?>> List<T> swap(List<T> listToSwap) {
        return listToSwap.stream().map(NMSMapper::swap).toList();
    }
}
