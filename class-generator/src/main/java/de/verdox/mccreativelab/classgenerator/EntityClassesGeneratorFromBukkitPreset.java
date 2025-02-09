package de.verdox.mccreativelab.classgenerator;

import de.verdox.mccreativelab.classgenerator.codegen.ClassBuilder;
import org.bukkit.event.Event;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityClassesGeneratorFromBukkitPreset extends AbstractClassGenerator {
    private final Map<Class<?>, ClassBuilder> DONE = new HashMap<>();

    public EntityClassesGeneratorFromBukkitPreset(File apiSrcDir, File implSrcDir, String prefix, String suffix, List<Class<?>> excludedTypes, List<String> excludedPackages) {
        super(apiSrcDir, implSrcDir, prefix, suffix, excludedTypes, excludedPackages);
    }

    public void generateEventWrappers() throws IOException {
        for (Class<? extends Event> subtype : new Reflections("org.bukkit.entity").getSubTypesOf(Event.class)) {
            generateWrapper(subtype);
        }
        for (Class<? extends Event> subtype : new Reflections("org.bukkit.entity.boat").getSubTypesOf(Event.class)) {
            generateWrapper(subtype);
        }
        for (Class<? extends Event> subtype : new Reflections("org.bukkit.entity.minecart").getSubTypesOf(Event.class)) {
            generateWrapper(subtype);
        }
    }

    private ClassBuilder generateWrapper(Class<? extends Event> bukkitEventClass) throws IOException {
        if (DONE.containsKey(bukkitEventClass) || bukkitEventClass.isAnnotationPresent(Deprecated.class)) {
            return DONE.get(bukkitEventClass);
        }
        return null;
    }
}
