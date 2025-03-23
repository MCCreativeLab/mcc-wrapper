package de.verdox.mccreativelab.classgenerator.codegen.expressions.buildingblocks;

import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.CodeExpression;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Parameter implements CodeExpression {

    private final CapturedType<?, ?> type;
    private final String name;

    public Parameter(@NotNull CapturedType<?, ?> type, @NotNull String name) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(type, "Parameter with name " + name + " cannot have type null!");
        this.type = type;
        this.name = name;
    }

    public CapturedType<?, ?> getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public void write(CodeLineBuilder code) {
        code.append(type).append(" ").append(name);
    }

    public void writeWithoutTypeName(CodeLineBuilder code) {
        code.append(name);
    }
}
