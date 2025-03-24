package de.verdox.mccreativelab.classgenerator.codegen.expressions;

import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedType;

public interface CodeExpression {
    void write(CodeLineBuilder codeLineBuilder);

    default CodeExpression with(CodeExpression newExpression) {
        return new ChainedExpression(this, newExpression);
    }

    default CodeExpression with(String blankCode) {
        return new ChainedExpression(this, new StringExpression(blankCode));
    }

    default CodeExpression with(CapturedType<?,?> dynamicType) {
        return new ChainedExpression(this, codeLineBuilder -> codeLineBuilder.append(dynamicType));
    }

    default CodeExpression withTypeToken(CapturedType<?,?> dynamicType) {
        return new ChainedExpression(this, codeLineBuilder -> codeLineBuilder.appendTypeToken(dynamicType));
    }

    default CodeExpression with(CapturedType<?,?> dynamicType, boolean insideGeneric) {
        return new ChainedExpression(this, codeLineBuilder -> codeLineBuilder.append(dynamicType, insideGeneric));
    }

    static CodeExpression create() {
        return new ChainedExpression();
    }
}
