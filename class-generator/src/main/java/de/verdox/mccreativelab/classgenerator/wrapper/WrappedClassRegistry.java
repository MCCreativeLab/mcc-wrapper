package de.verdox.mccreativelab.classgenerator.wrapper;

import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class WrappedClassRegistry {
    public static final WrappedClassRegistry INSTANCE = new WrappedClassRegistry();

    private final Map<ClassType<?>, WrappedClass> wrappedClassesCache = new HashMap<>();
    private final Map<ClassType<?>, WrappedClass> apiToWrapped = new HashMap<>();
    private final Map<ClassType<?>, WrappedClass> implToWrapped = new HashMap<>();

    private WrappedClassRegistry(){}

    public void register(WrappedClass wrappedClass) {
        wrappedClassesCache.put(wrappedClass.classToWrap(), wrappedClass);
        apiToWrapped.put(wrappedClass.apiInterface(), wrappedClass);
        implToWrapped.put(wrappedClass.implementation(), wrappedClass);
    }

    @Nullable
    public WrappedClass getWrappingInformationByWrappedClass(Class<?> classThatWasWrapped) {
        return wrappedClassesCache.getOrDefault(ClassType.from(classThatWasWrapped), null);
    }

    @Nullable
    public WrappedClass getWrappingInformationByWrappedClass(ClassType<?> classThatWasWrapped) {
        return wrappedClassesCache.getOrDefault(classThatWasWrapped, null);
    }

    @Nullable
    public WrappedClass getWrappingInformationByApiInterface(ClassType<?> apiInterface) {
        return apiToWrapped.getOrDefault(apiInterface, null);
    }

    @Nullable
    public WrappedClass getWrappingInformationByApiName(String name){
        return apiToWrapped.keySet().stream().filter(classDescription -> classDescription.getClassName().equals(name)).map(apiToWrapped::get).findAny().orElse(null);
    }

    @Nullable
    public WrappedClass getWrappingInformationByImplInterface(ClassType<?> implInterface) {
        return implToWrapped.getOrDefault(implInterface, null);
    }

    @Nullable
    public WrappedClass findWrappingInformationByGeneratedClass(ClassType<?> classDescription) {
        if(apiToWrapped.containsKey(classDescription)) {
            return apiToWrapped.get(classDescription);
        }
        else if(implToWrapped.containsKey(classDescription)) {
            return implToWrapped.get(classDescription);
        }
        return null;
    }
}
