package de.verdox.mccreativelab.classgenerator.wrapper.strategy;

import de.verdox.mccreativelab.classgenerator.codegen.ClassBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedParameterizedType;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ClassGeneratorStrategy implements WrapperGeneratorStrategy {
    @Override
    public List<ParameterOrRecord> collectParametersForGettersAndSetters(Class<?> nmsClass) {
        List<ParameterOrRecord> parameters = new LinkedList<>();

        java.lang.reflect.Parameter[] foundParameters = null;
        java.lang.reflect.Constructor<?> foundConstructor = findConstructorWithMostNumberOfArguments(nmsClass);
        java.lang.reflect.Method foundFactoryMethod = findFactoryMethodWithMostNumberOfArguments(nmsClass);
        if (foundConstructor != null)
            foundParameters = foundConstructor.getParameters();
        else if (foundFactoryMethod != null)
            foundParameters = foundFactoryMethod.getParameters();

        if (foundParameters != null) {
            Arrays.stream(foundParameters).forEach(parameter -> parameters.add(new ParameterOrRecord(parameter)));
        }
        return parameters;
    }

    @Override
    public CodeExpression createInstantiationExpression(Class<?> nmsClass, ClassBuilder interfaceBuilder, ClassBuilder implBuilder, Map<ParameterOrRecord, LocalVariableAssignment> parameters) {
        CapturedParameterizedType implType = CapturedParameterizedType.from(implBuilder.asCapturedClassType());
        CapturedParameterizedType nmsType = CapturedParameterizedType.from(nmsClass);

        java.lang.reflect.Constructor<?> foundConstructor = findConstructorWithMostNumberOfArguments(nmsClass);
        java.lang.reflect.Method foundFactoryMethod = findFactoryMethodWithMostNumberOfArguments(nmsClass);

        if(foundConstructor != null){
            return new ConstructorCall(implType, new ConstructorCall(nmsType, parameters.values().stream().map(localVariableAssignment -> CodeExpression.create().with(localVariableAssignment.variableName())).toArray(CodeExpression[]::new)));
        }
        else if(foundFactoryMethod != null){
            CodeExpression methodCall = CodeExpression.create().with(nmsType).with(".").with(new MethodCall(foundFactoryMethod.getName(), parameters.values().stream().map(localVariableAssignment -> CodeExpression.create().with(localVariableAssignment.variableName())).toArray(CodeExpression[]::new)));
            return new ConstructorCall(implType, methodCall);
        }
        throw new UnsupportedOperationException("This error is only thrown when parameters are collected neither from a foundConstructor nor from a foundFactoryMethod. Please recheck "+ClassGeneratorStrategy.class+" if you think this is a bug!");
    }
}
