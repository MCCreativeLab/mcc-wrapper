package de.verdox.mccreativelab.classgenerator.conversion;

import de.verdox.mccreativelab.classgenerator.AbstractClassGenerator;
import de.verdox.mccreativelab.classgenerator.codegen.ClassBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.buildingblocks.Method;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.CodeExpression;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.MethodCall;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.buildingblocks.Parameter;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedParameterizedType;
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
    private final CapturedParameterizedType from;
    private final CapturedParameterizedType to;
    private final WrappingMethodBuilder wrappingMethodBuilder;
    private static final Set<CodeExpression> registerExpressions = new HashSet<>();
    private static final ClassBuilder converterImportClass = new ClassBuilder();

    static {
        converterImportClass.withPackage("de.verdox.mccreativelab.impl.vanilla.platform.converter");
        converterImportClass.withHeader("public", ClassBuilder.ClassHeader.CLASS, "GeneratedConverters", "");
    }

    public MCCConverterGenerator(File srcDir, File implSrcDir, String className, String classPackage, CapturedParameterizedType from, CapturedParameterizedType to, WrappingMethodBuilder wrappingMethodBuilder, List<Class<?>> excludedTypes, List<String> excludedPackages) {
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

        CapturedParameterizedType apiType = swap(CapturedParameterizedType.from(from.getRawType()));
        if(apiType == null){
            LOGGER.warning("Could not create converter generator "+classBuilder.asCapturedClassType().getFullClassName()+" because "+from.getRawType()+" could not be swapped.");
            return;
        }

        CapturedParameterizedType converterType = CapturedParameterizedType.from(MCCConverter.class).withExplicitType(from).withExplicitType(to);

        classBuilder.implementsClasses(converterType);

        classBuilder.withMethod(
                new Method()
                        .name("wrap")
                        .type(CapturedParameterizedType.from(MCCConverter.ConversionResult.class).withExplicitType(to))
                        .parameter(new Parameter(from, "nativeType"))
                        .code(code -> wrappingMethodBuilder.wrapFunction(this, "nativeType", from, to, code))
        );

        classBuilder.withMethod(
                new Method()
                        .name("unwrap")
                        .type(CapturedParameterizedType.from(MCCConverter.ConversionResult.class).withExplicitType(from))
                        .parameter(new Parameter(to, "platformImplType"))
                        .code(code -> wrappingMethodBuilder.unwrapFunction(this, "platformImplType", to, from, code))
        );

        classBuilder.withMethod(
                new Method()
                        .name("apiImplementationClass")
                        .type(CapturedParameterizedType.from(Class.class).withExplicitType(to))
                        .code(code -> code.append("return ").append(to.getRawType().getClassName()).append(".class;"))
        );

        classBuilder.withMethod(
                new Method()
                        .name("nativeMinecraftType")
                        .type(CapturedParameterizedType.from(Class.class).withExplicitType(from))
                        .code(code -> code.append("return ").append(from.getRawType().getClassName()).append(".class;"))
        );

        converterImportClass.includeImport(to);
        converterImportClass.includeImport(apiType);

        CodeExpression register = CodeExpression.create().with("conversionService.registerConverterForNewImplType(").with(apiType).with(".class, new ").with(classBuilder.asCapturedClassType().getFullClassName()).with("());");
        registerExpressions.add(register);

        classBuilder.writeClassFile(implSrcDir);
    }

    public static void createNMSHandleConverter(ClassBuilder classBuilder, CapturedParameterizedType nmsType, CapturedParameterizedType implType, CapturedParameterizedType apiType) {
        CapturedParameterizedType converterType = CapturedParameterizedType.from(MCCConverter.class).withExplicitType(nmsType).withExplicitType(implType);
        MethodCall methodCall = new MethodCall("converter", code -> code.append(implType + ".class"), code -> code.append(nmsType).append(".class"), code -> code.append(implType).append("::new"), code -> code.append("MCCHandle::getHandle"));
        classBuilder.withField("public static final", converterType, "CONVERTER", methodCall);
        //converterImportClass.includeImport(nmsType);
        converterImportClass.includeImport(implType);
        converterImportClass.includeImport(apiType);
        CodeExpression register = CodeExpression.create().with("conversionService.registerConverterForNewImplType(").with(apiType).with(".class, ").with(implType).with(".CONVERTER);");
        registerExpressions.add(register);
    }

    public static void createGeneratedConvertersClass(File srcDir) throws IOException {
        converterImportClass.includeImport(CapturedParameterizedType.from(MCCPlatform.class));

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
                        .parameter(new Parameter(CapturedParameterizedType.from(ConversionService.class), "conversionService"))
        );
        converterImportClass.writeClassFile(srcDir);
    }

    public interface WrappingMethodBuilder {
        void wrapFunction(AbstractClassGenerator abstractClassGenerator, String parameterName, CapturedParameterizedType nativeType, CapturedParameterizedType typeToWrapInto, CodeLineBuilder codeLineBuilder);

        void unwrapFunction(AbstractClassGenerator abstractClassGenerator, String parameterName, CapturedParameterizedType apiType, CapturedParameterizedType nativeTypeToUnwrapTo, CodeLineBuilder codeLineBuilder);
    }
}
