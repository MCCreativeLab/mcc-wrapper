package de.verdox.mccreativelab.classgenerator.codegen.expressions;

import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedParameterizedType;

public record ConstructorCall(CapturedParameterizedType typeToInstantiate, CodeExpression... parameterExpressions) implements CodeExpression {
    @Override
    public void write(CodeLineBuilder codeLineBuilder) {
        codeLineBuilder.append("new ");
        typeToInstantiate.write(codeLineBuilder);
        codeLineBuilder.append("(");
        for (int i = 0; i < parameterExpressions.length; i++) {
            parameterExpressions[i].write(codeLineBuilder);
            if (i < parameterExpressions.length - 1)
                codeLineBuilder.append(", ");
        }
        codeLineBuilder.append(")");
    }
}
