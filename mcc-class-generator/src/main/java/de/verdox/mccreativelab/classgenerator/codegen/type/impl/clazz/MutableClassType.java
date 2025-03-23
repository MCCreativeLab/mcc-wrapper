package de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz;

import de.verdox.mccreativelab.classgenerator.codegen.ClassBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedParameterizedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedTypeVariable;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MutableClassType extends ClassType<Void> {
    protected String className;
    protected String packageName;
    @Nullable
    protected CapturedParameterizedType declaringClass;
    @Nullable
    protected CapturedParameterizedType superClass;
    protected List<CapturedParameterizedType> interfaces = new LinkedList<>();
    protected List<CapturedTypeVariable> typeVariables = new LinkedList<>();

    protected boolean isSynthetic;
    protected boolean isPrimitive;
    protected boolean isEnum;
    protected boolean isRecord;
    protected boolean isAnonymous;
    protected boolean isArray;
    protected boolean isInterface;

    protected MutableClassType(String className, String packageName) {
        super(null);
        this.className = className;
        this.packageName = packageName;
    }

    protected MutableClassType(ClassType<?> classType) {
        super(null);
        this.className = classType.getClassName();
        this.packageName = classType.getPackageName();
        this.declaringClass = classType.getDeclaringClass();
        this.superClass = classType.getSuperClass();
        this.interfaces.addAll(classType.getInterfaces());
        this.typeVariables.addAll(classType.getTypeVariables());
        this.isSynthetic = classType.isSynthetic();
        this.isPrimitive = classType.isPrimitive();
        this.isEnum = classType.isEnum();
        this.isRecord = classType.isRecord();
        this.isAnonymous = classType.isAnonymous();
        this.isArray = classType.isArray();
        this.isInterface = classType.isInterface();
    }

    public void setDeclaringClass(@Nullable CapturedParameterizedType type){
        this.declaringClass = type;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Nullable
    @Override
    public CapturedParameterizedType getDeclaringClass() {
        return declaringClass;
    }

    @Nullable
    @Override
    public CapturedParameterizedType getSuperClass() {
        return superClass;
    }

    @Override
    public List<CapturedTypeVariable> getTypeVariables() {
        return typeVariables;
    }

    @Override
    public List<CapturedParameterizedType> getInterfaces() {
        return interfaces;
    }

    @Override
    public boolean isSynthetic() {
        return isSynthetic;
    }

    @Override
    public boolean isPrimitive() {
        return isPrimitive;
    }

    @Override
    public boolean isEnum() {
        return isEnum;
    }

    @Override
    public boolean isRecord() {
        return isRecord;
    }

    @Override
    public boolean isAnonymous() {
        return isAnonymous;
    }

    @Override
    public boolean isArray() {
        return isArray;
    }

    @Override
    public boolean isInterface() {
        return isInterface;
    }

    @Override
    protected ClassType<Void> copy(boolean mutable) {
        MutableClassType other = new MutableClassType(this.className, this.packageName);
        other.declaringClass = copyType(ClassType::getDeclaringClass, mutable);
        other.superClass = copyType(ClassType::getSuperClass, mutable);
        other.typeVariables = copyCollection(ClassType::getTypeVariables, mutable);
        other.interfaces = copyCollection(ClassType::getInterfaces, mutable);

        other.isSynthetic = this.isSynthetic;
        other.isPrimitive = this.isPrimitive;
        other.isEnum = this.isEnum;
        other.isRecord = this.isRecord;
        other.isAnonymous = this.isAnonymous;
        other.isArray = this.isArray;
        other.isInterface = this.isInterface;
        return other;
    }

    @Override
    protected ClassType<Void> createImmutableClone() {
        return new MutableClassType(this);
    }

    public MutableClassType withClassName(String name) {
        this.className = name;
        return this;
    }

    public MutableClassType withPackageName(String name) {
        this.packageName = name;
        return this;
    }

    public MutableClassType withNoTypeVariables() {
        typeVariables.clear();
        return this;
    }

    public MutableClassType withTypeVariables(List<CapturedTypeVariable> typeVariables, boolean clear) {
        if (clear) {
            this.typeVariables.clear();
        }
        this.typeVariables.addAll(typeVariables);
        return this;
    }

    public MutableClassType withTypeVariable(int index, CapturedTypeVariable capturedTypeVariable) {
        typeVariables.set(index, capturedTypeVariable);
        return this;
    }

    public MutableClassType withTypeVariable(CapturedTypeVariable capturedTypeVariable) {
        this.typeVariables.add(capturedTypeVariable);
        return this;
    }

    public MutableClassType withNoInterfaces() {
        this.interfaces.clear();
        return this;
    }

    public MutableClassType withInterface(int index, ClassType<?> classType) {
        interfaces.set(index, CapturedParameterizedType.from(classType));
        return this;
    }

    public MutableClassType withInterface(ClassType<?> classType) {
        interfaces.add(CapturedParameterizedType.from(classType));
        return this;
    }

    public MutableClassType withSuperClass(CapturedParameterizedType capturedTypeVariable) {
        superClass = capturedTypeVariable;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MutableClassType that = (MutableClassType) o;
        return isSynthetic == that.isSynthetic && isPrimitive == that.isPrimitive && isEnum == that.isEnum && isRecord == that.isRecord && isAnonymous == that.isAnonymous && isArray == that.isArray && isInterface == that.isInterface && Objects.equals(className, that.className) && Objects.equals(packageName, that.packageName) && Objects.equals(declaringClass, that.declaringClass) && Objects.equals(superClass, that.superClass) && Objects.equals(interfaces, that.interfaces) && Objects.equals(typeVariables, that.typeVariables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), className, packageName, declaringClass, superClass, interfaces, typeVariables, isSynthetic, isPrimitive, isEnum, isRecord, isAnonymous, isArray, isInterface);
    }
}
