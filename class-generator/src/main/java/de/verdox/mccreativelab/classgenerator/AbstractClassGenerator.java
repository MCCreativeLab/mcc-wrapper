package de.verdox.mccreativelab.classgenerator;

import de.verdox.mccreativelab.classgenerator.codegen.type.impl.DynamicType;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

public class AbstractClassGenerator {
    public static final Logger LOGGER = Logger.getLogger(AbstractClassGenerator.class.getName());
    protected final String suffix;
    protected final List<Class<?>> excludedTypes;
    protected final List<String> excludedPackages;
    protected final File apiSrcDir;
    protected final File implSrcDir;
    protected final String prefix;

    public AbstractClassGenerator(File apiSrcDir, File implSrcDir, String prefix, String suffix, List<Class<?>> excludedTypes, List<String> excludedPackages) {
        this.apiSrcDir = apiSrcDir;
        this.implSrcDir = implSrcDir;
        this.prefix = prefix;
        this.suffix = suffix;
        this.excludedTypes = excludedTypes;
        this.excludedPackages = excludedPackages;
    }

    public boolean isForbiddenType(DynamicType dynamicType) {
        for (DynamicType genericType : dynamicType.getGenericTypes()) {
            if (isForbiddenType(genericType)) {
                return true;
            }
        }

        if (dynamicType.getClassDescription().getPackageName().contains("de.verdox.mccreativelab")) {
            return false;
        }
        if (dynamicType.getClassDescription().getPackageName().contains("net.minecraft") && !NMSMapper.isSwapped(dynamicType)) {
            return true;
        }

        if (dynamicType.getRawType() != null && excludedTypes.contains(dynamicType.getRawType())) {
            return true;
        }
        return false;
    }

    public boolean isForbiddenType(Type type) {
        return isForbiddenType(DynamicType.of(type, false));
    }
}
