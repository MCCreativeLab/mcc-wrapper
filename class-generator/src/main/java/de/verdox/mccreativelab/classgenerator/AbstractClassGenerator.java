package de.verdox.mccreativelab.classgenerator;

import de.verdox.mccreativelab.classgenerator.codegen.type.impl.*;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;
import org.jetbrains.annotations.Nullable;

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

    public boolean isForbiddenType(CapturedType<?, ?> capturedType) {

        if (capturedType instanceof CapturedParameterizedType parameterizedType) {
            if (isForbiddenType(parameterizedType.getRawType())) {
                return true;
            }
            if (isForbiddenType(parameterizedType.getOwner())) {
                return true;
            }
            for (CapturedType<?, ?> typeArgument : parameterizedType.getTypeArguments()) {
                if(isForbiddenType(typeArgument)){
                    return true;
                }
            }
        }
        else if (capturedType instanceof CapturedTypeVariable capturedTypeVariable) {

            for (CapturedType<?, ?> upperBound : capturedTypeVariable.getUpperBounds()) {
                if(isForbiddenType(upperBound)){
                    return true;
                }
            }
        }
        else if (capturedType instanceof CapturedWildcardType wildcardType) {
            for (CapturedType<?, ?> upperBound : wildcardType.getUpperBounds()) {
                if(isForbiddenType(upperBound)){
                    return true;
                }
            }
            for (CapturedType<?, ?> upperBound : wildcardType.getLowerBounds()) {
                if(isForbiddenType(upperBound)){
                    return true;
                }
            }
        }
        else if (capturedType instanceof ClassType<?> classType) {
            for (CapturedTypeVariable typeVariable : classType.getTypeVariables()) {
                if (isForbiddenType(typeVariable))
                    return true;
            }
            if (isForbiddenType(classType.getDeclaringClass())) {
                return true;
            }

            if (classType.getPackageName().contains("de.verdox.mccreativelab")) {
                return false;
            }
            if (classType.getPackageName().contains("net.minecraft") && !NMSMapper.isSwapped(classType)) {
                return true;
            }

            return excludedTypes.stream().anyMatch(aClass -> ClassType.from(aClass).equals(capturedType));
        }
        return false;
    }

    public boolean isForbiddenType(Type type) {
        return isForbiddenType(CapturedType.from(type));
    }

    @Nullable
    public <T extends CapturedType<?, ?>> T swap(T typeToSwap) {
        return NMSMapper.swap(typeToSwap);
    }
}
