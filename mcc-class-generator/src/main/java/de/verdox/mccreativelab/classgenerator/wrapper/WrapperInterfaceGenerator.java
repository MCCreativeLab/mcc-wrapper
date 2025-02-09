package de.verdox.mccreativelab.classgenerator.wrapper;

import de.verdox.mccreativelab.classgenerator.AbstractClassGenerator;
import de.verdox.mccreativelab.classgenerator.NMSMapper;
import de.verdox.mccreativelab.classgenerator.codegen.ClassBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.buildingblocks.Constructor;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.buildingblocks.Method;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.buildingblocks.Parameter;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedParameterizedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;
import de.verdox.mccreativelab.classgenerator.conversion.MCCConverterGenerator;
import de.verdox.mccreativelab.classgenerator.wrapper.strategy.ClassGeneratorStrategy;
import de.verdox.mccreativelab.classgenerator.wrapper.strategy.RecordGeneratorStrategy;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.Logger;

public class WrapperInterfaceGenerator extends AbstractClassGenerator {
    public static final Logger LOGGER = Logger.getLogger(WrapperInterfaceGenerator.class.getSimpleName());

    private final String implPrefix;

    public WrapperInterfaceGenerator(File srcDir, File implSrcDir, String wrapperPrefix, String wrapperSuffix, String implPrefix, List<Class<?>> excludedTypes, List<String> excludedPackages) {
        super(srcDir, implSrcDir, wrapperPrefix, wrapperSuffix, excludedTypes, excludedPackages);
        this.implPrefix = implPrefix;
    }

    @Nullable
    public WrappedClass generateWrapper(Class<?> nmsClass, String apiPackage, String implPackage, ClassType<?> wrapperParentClass, boolean withSetters) {
        if (!isTypeAllowedToGenerateInterface(nmsClass))
            return null;
        ClassBuilder interfaceBuilder = new ClassBuilder();
        ClassBuilder implBuilder = new ClassBuilder();
        WrappedClass wrappedClass = generateWrapper(nmsClass, apiPackage, implPackage, false, interfaceBuilder, implBuilder, wrapperParentClass, withSetters);
        try {
            interfaceBuilder.writeClassFile(apiSrcDir.getPath());
            if (!interfaceBuilder.asCapturedClassType().isEnum())
                implBuilder.writeClassFile(implSrcDir.getPath());
            LOGGER.info("Generated wrapper interface: " + interfaceBuilder.getClassName());
        } catch (IOException e) {
            LOGGER.warning("Error writing interface file: " + e.getMessage());
        }
        return wrappedClass;
    }

    private void setupInterfaceHeader(Class<?> nmsClass, String apiPackage, boolean isInnerClass, ClassBuilder interfaceBuilder, ClassType<?> wrapperParentClass) {
        String interfaceName = isInnerClass ? nmsClass.getSimpleName() : prefix + nmsClass.getSimpleName();
        interfaceBuilder.withPackage(apiPackage);
        if (wrapperParentClass != null && !isInnerClass) {
            interfaceBuilder.includeImport(wrapperParentClass);
        }

        ClassBuilder.ClassHeader header = ClassBuilder.ClassHeader.INTERFACE;
        if (nmsClass.isEnum()) {
            header = ClassBuilder.ClassHeader.ENUM;
        }
        interfaceBuilder.withHeader(isInnerClass ? "public static" : "public", header, interfaceName, suffix);
    }

    private WrappedClass generateWrapper(Class<?> nmsClass, String apiPackage, String implPackage, boolean isInnerClass, ClassBuilder interfaceBuilder, ClassBuilder implBuilder, ClassType<?> wrapperParentClass, boolean withSetters) {
        setupInterfaceHeader(nmsClass, apiPackage, isInnerClass, interfaceBuilder, wrapperParentClass);
        String interfaceName = isInnerClass ? nmsClass.getSimpleName() : prefix + nmsClass.getSimpleName();
        String implName = implPrefix + nmsClass.getSimpleName();

        if (nmsClass.isEnum()) {
            if (wrapperParentClass != null) {
                if (wrapperParentClass.isInterface()) {
                    interfaceBuilder.implementsClasses(CapturedParameterizedType.from(wrapperParentClass));
                } else {
                    interfaceBuilder.withSuperType(CapturedParameterizedType.from(wrapperParentClass));
                }
            }
        } else {
            if (wrapperParentClass != null) {
                if (wrapperParentClass.isInterface()) {
                    interfaceBuilder.withSuperType(CapturedParameterizedType.from(wrapperParentClass));
                }
            }
            setupImplHeader(nmsClass, implPackage, isInnerClass, interfaceBuilder, implBuilder, implName, interfaceName);
        }

        NMSMapper.register(nmsClass, interfaceBuilder.asCapturedClassType());

        for (Class<?> innerClass : nmsClass.getDeclaredClasses()) {
            if (excludedTypes.contains(innerClass))
                continue;
            if (Modifier.isPublic(innerClass.getModifiers())) {

                ClassBuilder interfaceChild = new ClassBuilder();
                setupInterfaceHeader(innerClass, apiPackage, true, interfaceChild, null);
                interfaceBuilder.includeInnerClass(interfaceChild);

                if(innerClass.isEnum()){
                    continue;
                }

                ClassBuilder implChild = new ClassBuilder();
                if (!innerClass.isEnum()) {
                    implChild.withHeader(isInnerClass ? "public static" : "public", ClassBuilder.ClassHeader.CLASS, implPrefix + innerClass.getSimpleName(), "");
                    implChild.withPackage(implPackage);
                    implBuilder.includeInnerClass(implChild);
                }

                var childWrappedClass = generateWrapper(innerClass, apiPackage, implPackage, true, interfaceChild, implChild, null, withSetters);

                if (interfaceChild.asCapturedClassType().isEnum()) continue;
                if (withSetters) {

                    interfaceBuilder.withMethod(
                            new Method()
                                    .name("create" + interfaceChild.asCapturedClassType().getClassName())
                                    .type(CapturedParameterizedType.from(interfaceChild.asCapturedClassType()))
                    );
                    implBuilder.withMethod(
                            new Method()
                                    .name("create" + interfaceChild.asCapturedClassType().getClassName())
                                    .type(CapturedParameterizedType.from(interfaceChild.asCapturedClassType()))
                                    .code(code -> {
                                        code.append("return new " + childWrappedClass.implementation().getClassName() + "(null);");
                                    })
                    );
                }
            }
        }

        if (nmsClass.isRecord()) {
            new RecordGeneratorStrategy().createGetterAndSetter(this, nmsClass, interfaceBuilder, implBuilder, withSetters);
        } else if (nmsClass.isEnum()) {
            for (Object enumConstant : nmsClass.getEnumConstants())
                interfaceBuilder.withEnumEntry(enumConstant.toString());
        } else {
            new ClassGeneratorStrategy().createGetterAndSetter(this, nmsClass, interfaceBuilder, implBuilder, withSetters);
        }



        if (!isInnerClass) {
            try {
                interfaceBuilder.writeClassFile(apiSrcDir.getPath());
                if (!interfaceBuilder.asCapturedClassType().isEnum())
                    implBuilder.writeClassFile(implSrcDir.getPath());
            } catch (IOException e) {
                LOGGER.warning("Error writing interface file: " + e.getMessage());
            }
        }

        WrappedClass wrappedClass = new WrappedClass(ClassType.from(nmsClass), interfaceBuilder.asCapturedClassType(), /*implBuilder.asCapturedClassType().getClassName() == null || implBuilder.asCapturedClassType().getPackageName() == null ? null : */implBuilder.asCapturedClassType());
        WrappedClassRegistry.INSTANCE.register(wrappedClass);
        return wrappedClass;
    }

    private boolean isTypeAllowedToGenerateInterface(Class<?> type) {
        for (String excludedPackage : excludedPackages) {
            if (type.getPackageName().contains(excludedPackage)) {
                return false;
            }
        }
        for (Class<?> excludedType : excludedTypes) {
            if (type.equals(excludedType)) {
                return false;
            }
        }
        return true;
    }

    private static void setupImplHeader(Class<?> nmsClass, String implPackage, boolean isInnerClass, ClassBuilder interfaceBuilder, ClassBuilder implBuilder, String implName, String interfaceName) {
        implBuilder.withHeader(isInnerClass ? "public static" : "public", ClassBuilder.ClassHeader.CLASS, implName, "");
        implBuilder.withPackage(implPackage);
        implBuilder.withSuperType(CapturedParameterizedType.from(MCCHandle.class).withExplicitType(CapturedParameterizedType.from(nmsClass)));
        implBuilder.implementsClasses(CapturedParameterizedType.from(interfaceBuilder.asCapturedClassType()));
        if (!isInnerClass) {
            implBuilder.includeImport(nmsClass);
            implBuilder.includeImport(CapturedParameterizedType.from(MCCHandle.class));
        }

        CapturedParameterizedType implType = CapturedParameterizedType.from(implBuilder.asCapturedClassType());

        MCCConverterGenerator.createNMSHandleConverter(implBuilder, CapturedParameterizedType.from(nmsClass), implType, CapturedParameterizedType.from(interfaceBuilder.asCapturedClassType()));

        //implBuilder.withField("private final", DynamicType.of(nmsClass, false), "handle", null);

        implBuilder.withConstructor(
                new Constructor()
                        .parameter(new Parameter(CapturedParameterizedType.from(nmsClass), "handle")).code(code -> {
                            code.append("super(handle);");
                        }));
    }
}
