package de.verdox.mccreativelab.classgenerator.wrapper.strategy;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.classgenerator.NMSMapper;
import de.verdox.mccreativelab.classgenerator.codegen.ClassBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.StandardValuesForTypes;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.buildingblocks.Method;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.buildingblocks.Parameter;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedParameterizedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedType;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.*;
import de.verdox.mccreativelab.classgenerator.util.FieldNameUtil;

import de.verdox.mccreativelab.classgenerator.wrapper.WrapperInterfaceGenerator;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.util.*;

public interface WrapperGeneratorStrategy {

    List<ParameterOrRecord> collectParametersForGettersAndSetters(Class<?> nmsClass);

    CodeExpression createInstantiationExpression(Class<?> nmsClass, ClassBuilder interfaceBuilder, ClassBuilder implBuilder, Map<ParameterOrRecord, LocalVariableAssignment> parameters);

    default String getApiGetterMethodName(ParameterOrRecord parameter) {
        String capitalizedParameterName = FieldNameUtil.capitalize(parameter.getName());
        return "get" + capitalizedParameterName;
    }

    default String getApiSetterMethodName(ParameterOrRecord parameter) {
        String capitalizedParameterName = FieldNameUtil.capitalize(parameter.getName());
        return "with" + capitalizedParameterName;
    }

    default String getImplGetterMethodName(ParameterOrRecord parameter) {
        return getApiGetterMethodName(parameter) + "FromImpl";
    }

    default void createGetterAndSetter(WrapperInterfaceGenerator wrapperInterfaceGenerator, Class<?> nmsClass, ClassBuilder interfaceBuilder, ClassBuilder implBuilder, boolean withSetters) {
        List<ParameterOrRecord> parameters = collectParametersForGettersAndSetters(nmsClass);
        implBuilder.includeImport(CapturedType.from(MCCPlatform.class));
        implBuilder.includeImport(CapturedType.from(TypeToken.class));
/*        implBuilder.includeImport(CapturedType.from(List.class));
        implBuilder.includeImport(CapturedType.from(ArrayList.class));
        implBuilder.includeImport(CapturedType.from(Set.class));
        implBuilder.includeImport(CapturedType.from(HashSet.class));*/

        //TODO: Create a private utility function to gather those information
        Map<ParameterOrRecord, String> parameterToGetter = new HashMap<>();
        for (ParameterOrRecord parameter : parameters) {
            String apiGetterMethodName = "get" + FieldNameUtil.capitalize(parameter.getName());
            String nmsGetterMethodName = apiGetterMethodName + "NMS";
            if (NMSMapper.isSwapped(parameter.getGenericType()))
                parameterToGetter.put(parameter, nmsGetterMethodName);
            else
                parameterToGetter.put(parameter, apiGetterMethodName);
        }

        CapturedParameterizedType interfaceType = CapturedParameterizedType.from(interfaceBuilder.asCapturedClassType());

        for (ParameterOrRecord parameter : parameters) {
            boolean isParameterSwapped = NMSMapper.isSwapped(parameter.getGenericType());

            CapturedType<?, ?> parameterTypeSwapped = CapturedType.swap(parameter.getGenericType());
            CapturedType<?, ?> parameterTypeNotSwapped = CapturedType.from(parameter.getGenericType());

            //TODO: Class where we collect standard values for types
            CodeExpression notSwappedStandardValue = StandardValuesForTypes.getDefaultValueForType(parameter.getType());


            String apiGetterMethodName = getApiGetterMethodName(parameter);
            String apiSetterMethodName = getApiSetterMethodName(parameter);

            String nmsGetterMethodName = getImplGetterMethodName(parameter);

            if (!wrapperInterfaceGenerator.isForbiddenType(parameterTypeSwapped)) {
                interfaceBuilder.withMethod(
                        new Method()
                                .name(apiGetterMethodName)
                                .type(parameterTypeSwapped)
                );


                if (withSetters) {
                    interfaceBuilder.withMethod(
                            new Method()
                                    .name(apiSetterMethodName)
                                    .type(interfaceType)
                                    .parameter(new Parameter(parameterTypeSwapped, parameter.getName()))
                    );
                }
            } else {
                //TODO: Append TODO into generated code so we know there are setters / getters missing
            }

            boolean generateGetterImpl = true;

            if (!nmsClass.isRecord()) {
                Field fieldOfParameter = findNonStaticFieldInClassForParameter(nmsClass, parameter.parameter());
                // If we cannot find an attribute of the nmsClass that saves our parameter we cannot create getter for it.
                if (fieldOfParameter == null)
                    generateGetterImpl = false;
                implBuilder.includeImport(parameter.getGenericType());
                implBuilder.includeImport(Field.class);
            }

            if (generateGetterImpl) {
                // Build public getter method

                implBuilder.withMethod(
                        new Method()
                                .name(apiGetterMethodName)
                                .type(parameterTypeSwapped)
                                .code(code -> {
                                    code.appendAndNewLine("var nms = " + nmsGetterMethodName + "();");
                                    if (!isParameterSwapped) {
                                        code.appendAndNewLine("return nms;");
                                        return;
                                    }

                                    code.append("return ");
                                    //converterGenerator.requireGeneratorFunction(parameterTypeSwapped, parameterTypeNotSwapped);
                                    CodeExpression converterExpression = createImplToApiExpression("nms", parameterTypeNotSwapped, parameterTypeSwapped);
                                    code.append(converterExpression);
                                    code.appendAndNewLine(";");
                                })
                );

                implBuilder.withMethod(
                        new Method()
                                .modifier("private")
                                .name(nmsGetterMethodName)
                                .type(CapturedType.from(parameter.getGenericType()))
                                .code(code -> {
                                    if (nmsClass.isRecord()) {
                                        code.append("return handle == null ? ");
                                        code.append(notSwappedStandardValue);
                                        code.append(" : handle.");
                                        code.append(parameter.getName()).appendAndNewLine("();");
                                    } else {
                                        new ReflectiveFieldGetter("nms", nmsClass, parameter.parameter()).write(code);
                                        code.appendAndNewLine("return nms;");
                                    }
                                })
                );
            }

            if (withSetters) {

                implBuilder.withMethod(
                        new Method()
                                .name(apiSetterMethodName)
                                .type(interfaceType)
                                .parameter(new Parameter(parameterTypeSwapped, parameter.getName()))
                                .code(code -> {
                                    int counter = 0;
                                    Map<ParameterOrRecord, LocalVariableAssignment> variables = new LinkedHashMap<>();

                                    for (ParameterOrRecord param : parameters) {
                                        if (!param.equals(parameter)) {


                                            variables.put(param, new LocalVariableAssignment("param" + (counter++), CodeExpression.create().with(getImplGetterMethodName(param) + "()")));
                                            variables.get(param).write(code);
                                            continue;
                                        }


                                        if (isParameterSwapped) {
                                            //converterGenerator.requireGeneratorFunction(parameterTypeSwapped, parameterTypeNotSwapped);
                                            CodeExpression conversionExpression = createApiToImplExpression(parameter.getName(), parameterTypeSwapped, parameterTypeNotSwapped);
                                            variables.put(param, new LocalVariableAssignment("param" + (counter++), conversionExpression));
                                        } else {
                                            variables.put(param, new LocalVariableAssignment("param" + (counter++), CodeExpression.create().with(parameter.getName())));
                                        }
                                        variables.get(param).write(code);
                                    }
                                    code.append("return ");
                                    createInstantiationExpression(nmsClass, interfaceBuilder, implBuilder, variables).write(code);
                                    code.append(";");
                                })

                );
            }
            implBuilder.includeImport(parameter.getGenericType());
        }
    }

    private static @Nullable Field findNonStaticFieldInClassForParameter(Class<?> nmsClass, java.lang.reflect.Parameter parameter) {
        Field fieldOfParameter = null;
        for (Field declaredField : nmsClass.getDeclaredFields()) {
            if (declaredField.getGenericType().equals(parameter.getParameterizedType()))
                fieldOfParameter = declaredField;
        }
        return fieldOfParameter;
    }

    private static CodeExpression createApiToImplExpression(String variableToConvert, CapturedType<?, ?> api, CapturedType<?, ?> impl) {
        return CodeExpression.create().with("MCCPlatform.getInstance().getConversionService().unwrap(").with(variableToConvert).with(", ").withTypeToken(impl).with(")");
    }

    private static CodeExpression createImplToApiExpression(String variableToConvert, CapturedType<?, ?> impl, CapturedType<?, ?> api) {
        return CodeExpression.create().with("MCCPlatform.getInstance().getConversionService().wrap(").with(variableToConvert).with(", ").withTypeToken(api).with(")");
    }

    default @Nullable java.lang.reflect.Constructor<?> findConstructorWithMostNumberOfArguments(Class<?> nmsClass) {
        int amountParameters = 0;
        java.lang.reflect.Constructor<?> foundConstructor = null;
        for (java.lang.reflect.Constructor<?> declaredConstructor : nmsClass.getDeclaredConstructors()) {
            if (!Modifier.isPublic(declaredConstructor.getModifiers()))
                continue;
            if (declaredConstructor.getParameterCount() > amountParameters) {
                amountParameters = declaredConstructor.getParameterCount();
                foundConstructor = declaredConstructor;
            }
        }
        return foundConstructor;
    }

    default @Nullable java.lang.reflect.Method findFactoryMethodWithMostNumberOfArguments(Class<?> nmsClass) {
        int amountParameters = 0;
        java.lang.reflect.Method foundMethod = null;
        for (java.lang.reflect.Method declaredMethod : nmsClass.getMethods()) {
            if (!Modifier.isPublic(declaredMethod.getModifiers()))
                continue;

            if (!Modifier.isStatic(declaredMethod.getModifiers()))
                continue;
            if (!declaredMethod.getReturnType().equals(nmsClass))
                continue;

            if (declaredMethod.getParameterCount() > amountParameters) {
                amountParameters = declaredMethod.getParameterCount();
                foundMethod = declaredMethod;
            }
        }
        return foundMethod;
    }

    record ParameterOrRecord(java.lang.reflect.Parameter parameter, RecordComponent recordComponent) {
        public ParameterOrRecord(java.lang.reflect.Parameter parameter) {
            this(parameter, null);
        }

        public ParameterOrRecord(RecordComponent recordComponent) {
            this(null, recordComponent);
        }

        public String getName() {
            return parameter != null ? parameter.getName() : recordComponent.getName();
        }

        public Type getGenericType() {
            return parameter != null ? parameter.getParameterizedType() : recordComponent.getGenericType();
        }

        public Class<?> getType() {
            return parameter != null ? parameter.getType() : recordComponent.getType();
        }
    }
}
