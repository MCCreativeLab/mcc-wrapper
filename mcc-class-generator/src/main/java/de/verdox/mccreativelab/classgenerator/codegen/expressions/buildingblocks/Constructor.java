package de.verdox.mccreativelab.classgenerator.codegen.expressions.buildingblocks;

import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.JavaDocExpression;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedParameterizedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedTypeVariable;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;

import java.util.Objects;
import java.util.function.Consumer;

public class Constructor extends Method {

    public Constructor(){

    }

    @Override
    public Constructor generic(CapturedTypeVariable... declarations) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Constructor name(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Constructor type(CapturedType<?,?> type) {
        super.type(type);
        return this;
    }

    @Override
    public Constructor modifier(String modifier) {
        super.modifier(modifier);
        return this;
    }

    @Override
    public Constructor javaDoc(JavaDocExpression javaDoc) {
        super.javaDoc(javaDoc);
        return this;
    }

    @Override
    public Constructor code(Consumer<CodeLineBuilder> content) {
        super.code(content);
        return this;
    }

    @Override
    public Constructor parameter(Parameter... parameters) {
        super.parameter(parameters);
        return this;
    }
    @Override
    protected void writeType(CodeLineBuilder code) {
        Objects.requireNonNull(type());
        if(type() instanceof ClassType<?> classType){
            code.append(classType.getClassName());
        }
        else if(type() instanceof CapturedParameterizedType classType){
            code.append(classType.getRawType().getClassName());
        }
        else {
            code.append(type());
        }
    }
}
