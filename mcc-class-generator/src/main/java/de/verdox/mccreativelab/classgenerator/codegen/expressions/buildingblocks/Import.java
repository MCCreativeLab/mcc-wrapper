package de.verdox.mccreativelab.classgenerator.codegen.expressions.buildingblocks;

import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.CodeExpression;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;

import java.util.Objects;

public record Import(ClassType<?> classDescription) implements CodeExpression {

    @Override
    public void write(CodeLineBuilder code) {
        if (classDescription.getDeclaringClass() != null || !classDescription.getClassName().equals(classDescription.getFullClassName())) {
            return;
        }

        if (classDescription.getPackageName().equals("de.verdox.mccreativelab.wrapper.types") && classDescription.getClassName().equals("Cost")) {
            System.out.println("IMPORT " + classDescription.debugString() + " (" + classDescription.getDeclaringClass() + ")");
        }

        code.appendAndNewLine("import " + classDescription.getPackageName() + "." + classDescription.getClassName() + ";");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Import anImport = (Import) o;
        return Objects.equals(classDescription.toString(), anImport.classDescription.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(classDescription.toString());
    }
}
