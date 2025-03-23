package de.verdox.mccreativelab.classgenerator.wrapper;

import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;
import org.jetbrains.annotations.Nullable;

public record WrappedClass(ClassType<?> classToWrap, ClassType<?> apiInterface, @Nullable ClassType<?> implementation) {
}
