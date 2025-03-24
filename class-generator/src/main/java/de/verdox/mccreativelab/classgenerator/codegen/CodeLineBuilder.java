package de.verdox.mccreativelab.classgenerator.codegen;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.CodeExpression;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.buildingblocks.Parameter;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class CodeLineBuilder {
    private final ClassBuilder classBuilder;
    private int depth;
    private final List<CodeLine> lines = new LinkedList<>();
    private boolean lineBreak;

    public CodeLineBuilder(ClassBuilder classBuilder, int depth) {
        this.classBuilder = classBuilder;
        this.depth = depth;
    }

    public ClassBuilder getClassBuilder() {
        return classBuilder;
    }

    public CodeLineBuilder append(String line) {
        if (lineBreak || lines.isEmpty())
            lines.add(new CodeLine(depth, new StringBuilder().append(line)));
        else
            lines.getLast().content.append(line);
        lineBreak = false;
        return this;
    }

    public CodeLineBuilder append(CodeExpression codeExpression) {
        codeExpression.write(this);
        return this;
    }

    public CodeLineBuilder add(CodeLineBuilder codeLineBuilder) {
        for (CodeLine line : codeLineBuilder.lines) {
            this.lines.add(new CodeLine(depth, line.content()));
        }
        return this;
    }

    public CodeLineBuilder appendAndNewLine(CodeExpression codeExpression) {
        codeExpression.write(this);
        newLine();
        return this;
    }

    public CodeLineBuilder appendParametersInBrackets(Parameter... parameters) {
        append("(");
        for (int i = 0; i < parameters.length; i++) {
            parameters[i].writeWithoutTypeName(this);
            if (i < parameters.length - 1)
                append(", ");
        }
        append(")");
        return this;
    }

    public CodeLineBuilder appendVariablesInBrackets(CodeExpression... variableNames) {
        append("(");
        for (int i = 0; i < variableNames.length; i++) {
            append(variableNames[i]);
            if (i < variableNames.length - 1)
                append(", ");
        }
        append(")");
        return this;
    }

    public CodeLineBuilder appendAndNewLine(String line) {
        append(line);
        return newLine();
    }

    private CodeLineBuilder newLine() {
        lineBreak = true;
        return this;
    }

    public CodeLineBuilder append(CapturedType<?, ?> dynamicType) {
        if (dynamicType.getRawType().getClassName() == null || dynamicType.getRawType().getPackageName() == null) {
            Logger.getLogger(CodeLineBuilder.class.getSimpleName()).warning("Trying to append a class that has either no name or no package name: " + dynamicType);
            return this;
        }
        dynamicType.write(this);
        return this;
    }

    public CodeLineBuilder append(CapturedType<?, ?> dynamicType, boolean insideGeneric) {
        if (dynamicType.getRawType().isPrimitive() && insideGeneric) {
            if (dynamicType.getRawType().equals(ClassType.from(boolean.class))) {
                append(ClassType.from(Boolean.class));
            } else if (dynamicType.getRawType().equals(ClassType.from(char.class))) {
                append(ClassType.from(Character.class));
            } else if (dynamicType.getRawType().equals(ClassType.from(byte.class))) {
                append(ClassType.from(Byte.class));
            } else if (dynamicType.getRawType().equals(ClassType.from(short.class))) {
                append(ClassType.from(Short.class));
            } else if (dynamicType.getRawType().equals(ClassType.from(int.class))) {
                append(ClassType.from(Integer.class));
            } else if (dynamicType.getRawType().equals(ClassType.from(long.class))) {
                append(ClassType.from(Long.class));
            } else if (dynamicType.getRawType().equals(ClassType.from(float.class))) {
                append(ClassType.from(Float.class));
            } else if (dynamicType.getRawType().equals(ClassType.from(double.class))) {
                append(ClassType.from(Double.class));
            } else if (dynamicType.getRawType().equals(ClassType.from(void.class))) {
                append(ClassType.from(Void.class));
            }
        } else {
            dynamicType.write(this);
        }
        return this;
    }

    public CodeLineBuilder appendTypeToken(CapturedType<?, ?> dynamicType) {
        getClassBuilder().includeImport(ClassType.from(TypeToken.class));

        append("new TypeToken<");
        append(dynamicType, true);
        append(">(){}");
        return this;
    }

    public CodeLineBuilder increaseDepth(int increase) {
        this.depth += increase;
        this.depth = Math.max(0, this.depth);
        newLine();
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            CodeLine line = lines.get(i);
            if (line.depth > 0)
                stringBuilder.append("\t".repeat(line.depth));
            stringBuilder.append(line.content);
            if (i < lines.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public boolean isEmpty() {
        return this.lines.isEmpty();
    }

    private record CodeLine(int depth, StringBuilder content) {
    }
}
