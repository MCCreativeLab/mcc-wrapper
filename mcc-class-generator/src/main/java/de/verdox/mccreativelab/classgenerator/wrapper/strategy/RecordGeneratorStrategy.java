package de.verdox.mccreativelab.classgenerator.wrapper.strategy;

import de.verdox.mccreativelab.classgenerator.codegen.ClassBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedParameterizedType;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.CodeExpression;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.ConstructorCall;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.LocalVariableAssignment;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RecordGeneratorStrategy implements WrapperGeneratorStrategy {

    @Override
    public List<ParameterOrRecord> collectParametersForGettersAndSetters(Class<?> nmsClass) {
        List<ParameterOrRecord> parameters = new LinkedList<>();
        Arrays.stream(nmsClass.getRecordComponents()).forEach(recordComponent -> parameters.add(new ParameterOrRecord(recordComponent)));
        return parameters;
    }

    @Override
    public CodeExpression createInstantiationExpression(Class<?> nmsClass, ClassBuilder interfaceBuilder, ClassBuilder implBuilder, Map<ParameterOrRecord, LocalVariableAssignment> parameters) {
        CapturedParameterizedType implType = CapturedParameterizedType.from(implBuilder.asCapturedClassType());

        return new ConstructorCall(implType, new ConstructorCall(CapturedParameterizedType.from(nmsClass), parameters.values().stream().map(localVariableAssignment -> CodeExpression.create().with(localVariableAssignment.variableName())).toArray(CodeExpression[]::new)));
    }
}
