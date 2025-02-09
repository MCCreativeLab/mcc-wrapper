package de.verdox.mccreativelab.classgenerator.codegen.expressions.buildingblocks;

import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.CodeExpression;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.StringExpression;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedType;

public class Field implements CodeExpression {
    private String modifier;
    private CapturedType<?, ?> type;
    private String fieldName;
    private CodeExpression initValue;

    public Field(String modifier, String fieldName, CapturedType<?, ?> type) {
        this.modifier = modifier;
        this.fieldName = fieldName;
        this.type = type;
    }

    public Field withInitValue(CodeExpression initValue) {
        this.initValue = initValue;
        return this;
    }

    public Field withInitValue(String initValue) {
        this.initValue = new StringExpression(initValue);
        return this;
    }

    public String getModifier() {
        return modifier;
    }

    public CapturedType<?, ?> getType() {
        return type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public CodeExpression getInitValue() {
        return initValue;
    }

    @Override
    public void write(CodeLineBuilder code) {
        code.increaseDepth(1);
        code.append(modifier).append(" ").append(type).append(" ").append(fieldName);
        if (initValue != null) {
            code.append(" ").append(" = ").append(initValue);
        }
        code.append(";\n");
        code.increaseDepth(-1);
    }
}
