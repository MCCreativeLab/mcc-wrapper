package de.verdox.mccreativelab.classgenerator.codegen.expressions;

import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.StandardValuesForTypes;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;

import java.lang.reflect.Parameter;

/**
 * Used to build reflection code that reads a field of an object and declares a variable with the respective type.
 *
 * @param variableName The name of the variable the data is put into
 * @param classToReflect the class to do reflection on
 * @param parameter the reflection parameter
 */
public record ReflectiveFieldGetter(String variableName, Class<?> classToReflect, Parameter parameter) implements CodeExpression {
    @Override
    public void write(CodeLineBuilder codeLineBuilder) {

        codeLineBuilder.appendAndNewLine(CapturedType.from(parameter.getParameterizedType())+" "+variableName+";");
        codeLineBuilder.append("if(handle==null) return ");
        codeLineBuilder.append(StandardValuesForTypes.getDefaultValueForType(parameter.getType()));
        codeLineBuilder.appendAndNewLine(";");
        codeLineBuilder.appendAndNewLine("try {");
        codeLineBuilder.increaseDepth(+1);
        codeLineBuilder.append("Field nmsField = ").append(ClassType.from(classToReflect).getFullClassName()).append(".class").append(".getDeclaredField(\"").append(parameter.getName()).appendAndNewLine("\");");
        codeLineBuilder.appendAndNewLine("nmsField.setAccessible(true);");
        codeLineBuilder.appendAndNewLine(variableName+" = (" + CapturedType.from(parameter.getParameterizedType()) + ") nmsField.get(handle);");
        codeLineBuilder.increaseDepth(-1);
        codeLineBuilder.appendAndNewLine("} catch (Throwable e) { throw new RuntimeException(e); }");
    }
}
