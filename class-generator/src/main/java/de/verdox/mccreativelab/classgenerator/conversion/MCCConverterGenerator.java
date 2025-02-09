package de.verdox.mccreativelab.classgenerator.conversion;

import de.verdox.mccreativelab.classgenerator.AbstractClassGenerator;
import de.verdox.mccreativelab.classgenerator.codegen.ClassBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.Method;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.DynamicType;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.CodeExpression;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.MethodCall;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.Parameter;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MCCConverterGenerator extends AbstractClassGenerator {

    private final String className;
    private final String classPackage;
    private final DynamicType from;
    private final DynamicType to;
    private final WrappingMethodBuilder wrappingMethodBuilder;
    private static final Set<CodeExpression> registerExpressions = new HashSet<>();
    private static final ClassBuilder converterImportClass = new ClassBuilder();

    static {
        converterImportClass.withPackage("de.verdox.mccreativelab.impl.vanilla.platform.converter");
        converterImportClass.withHeader("public", ClassBuilder.ClassHeader.CLASS, "GeneratedConverters", "");
    }

    public MCCConverterGenerator(File srcDir, File implSrcDir, String className, String classPackage, DynamicType from, DynamicType to, WrappingMethodBuilder wrappingMethodBuilder, List<Class<?>> excludedTypes, List<String> excludedPackages) {
        super(srcDir, implSrcDir, "", "", excludedTypes, excludedPackages);
        this.className = className;
        this.classPackage = classPackage;
        this.from = from;
        this.to = to;
        this.wrappingMethodBuilder = wrappingMethodBuilder;
    }

    public void create() throws IOException {
        ClassBuilder classBuilder = new ClassBuilder();
        classBuilder.withPackage(classPackage);
        classBuilder.withHeader("public", ClassBuilder.ClassHeader.CLASS, className, "");

        DynamicType apiType = DynamicType.of(from.getRawType(), true);

        DynamicType converterType = DynamicType.of(MCCConverter.class, false).withAddedGeneric(from).withAddedGeneric(to);

        classBuilder.implementsClasses(converterType);

        classBuilder.withMethod(
                new Method()
                        .name("wrap")
                        .type(DynamicType.of(MCCConverter.ConversionResult.class, false).withAddedGeneric(to))
                        .parameter(new Parameter(from, "nativeType"))
                        .code(code -> wrappingMethodBuilder.wrapFunction(this, "nativeType", from, to, code))
        );

        classBuilder.withMethod(
                new Method()
                        .name("unwrap")
                        .type(DynamicType.of(MCCConverter.ConversionResult.class, false).withAddedGeneric(from))
                        .parameter(new Parameter(to, "platformImplType"))
                        .code(code -> wrappingMethodBuilder.unwrapFunction(this, "platformImplType", to, from, code))
        );

        classBuilder.withMethod(
                new Method()
                        .name("apiImplementationClass")
                        .type(DynamicType.of(Class.class, false).withAddedGeneric(to))
                        .code(code -> code.append("return ").append(to.getTypeName()).append(".class;"))
        );

        classBuilder.withMethod(
                new Method()
                        .name("nativeMinecraftType")
                        .type(DynamicType.of(Class.class, false).withAddedGeneric(from))
                        .code(code -> code.append("return ").append(from.getTypeName()).append(".class;"))
        );

/*        classBuilder.withMethod("public", "wrap", DynamicType.of(MCCConverter.ConversionResult.class, false).withAddedGeneric(to),
            methodCode -> wrappingMethodBuilder.wrapFunction(this, "nativeType", from, to, methodCode), new Parameter(from, "nativeType"));*/

/*
        classBuilder.withMethod("public", "unwrap", DynamicType.of(MCCConverter.ConversionResult.class, false).withAddedGeneric(from),
            methodCode -> wrappingMethodBuilder.unwrapFunction(this, "platformImplType", to, from, methodCode), new Parameter(to, "platformImplType"));
*/

/*        classBuilder.withMethod("public", "apiImplementationClass", DynamicType.of(Class.class, false).withAddedGeneric(to),
            methodCode -> methodCode.append("return ").append(to.getTypeName()).append(".class;"));*/

/*        classBuilder.withMethod("public", "nativeMinecraftType", DynamicType.of(Class.class, false).withAddedGeneric(from),
            methodCode -> methodCode.append("return ").append(from.getTypeName()).append(".class;"));*/

        //converterImportClass.includeImport(from);
        converterImportClass.includeImport(to);
        converterImportClass.includeImport(apiType);

        CodeExpression register = CodeExpression.create().with("conversionService.registerPlatformType(").with(apiType).with(".class, new ").with(classBuilder.getClassDescription().getTypeName()).with("());");
        registerExpressions.add(register);

        classBuilder.writeClassFile(implSrcDir);
    }

    public static void createNMSHandleConverter(ClassBuilder classBuilder, DynamicType nmsType, DynamicType implType, DynamicType apiType) {
        DynamicType converterType = DynamicType.of(MCCConverter.class, false).withAddedGeneric(nmsType).withAddedGeneric(implType);
        MethodCall methodCall = new MethodCall("converter", code -> code.append(implType + ".class"), code -> code.append(nmsType).append(".class"), code -> code.append(implType).append("::new"), code -> code.append("MCCHandle::getHandle"));
        classBuilder.withField("public static final", converterType, "CONVERTER", methodCall);
        //converterImportClass.includeImport(nmsType);
        converterImportClass.includeImport(implType);
        converterImportClass.includeImport(apiType);
        CodeExpression register = CodeExpression.create().with("conversionService.registerPlatformType(").with(apiType).with(".class, ").with(implType).with(".CONVERTER);");
        registerExpressions.add(register);
    }

    public static void createGeneratedConvertersClass(File srcDir) throws IOException {
        converterImportClass.includeImport(DynamicType.of(MCCPlatform.class, false));

        converterImportClass.withMethod(
                new Method()
                        .modifier("public static")
                        .name("init")
                        .code(code -> {
                            for (CodeExpression registerExpression : registerExpressions) {
                                registerExpression.write(code);
                                code.appendAndNewLine("");
                            }
                        })
                        .parameter(new Parameter(DynamicType.of(ConversionService.class), "conversionService"))
        );
        converterImportClass.writeClassFile(srcDir);
    }

    public interface WrappingMethodBuilder {
        void wrapFunction(AbstractClassGenerator abstractClassGenerator, String parameterName, DynamicType nativeType, DynamicType typeToWrapInto, CodeLineBuilder codeLineBuilder);

        void unwrapFunction(AbstractClassGenerator abstractClassGenerator, String parameterName, DynamicType apiType, DynamicType nativeTypeToUnwrapTo, CodeLineBuilder codeLineBuilder);
    }
}
