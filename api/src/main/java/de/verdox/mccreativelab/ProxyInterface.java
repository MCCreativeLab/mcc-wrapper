package de.verdox.mccreativelab;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * A proxy interface provider that merges various implementations of the same interface to one proxy instance.
 *
 * @param <T>
 */
public class ProxyInterface<T> {
    private final Map<Method, T> methodToImplementationCache = new HashMap<>();
    private final Class<? extends T> type;
    private final T defaultImplementation;
    private final T proxy;

    public ProxyInterface(@NotNull Class<? extends T> type, @NotNull T defaultImplementation) {
        this.type = type;
        this.defaultImplementation = defaultImplementation;
        this.proxy = createProxy();
    }

    @NotNull
    public T getImplementation() {
        return this.proxy;
    }

    public void addImplementation(@NotNull T implementation, @NotNull BiFunction<T, Method, Boolean> replaceExistingMethodImplementation) {
        for (Method declaredMethod : implementation.getClass().getMethods()) {
            if(!Modifier.isPublic(declaredMethod.getModifiers()))
                continue;
            MethodOriginFinder methodOriginFinder = new MethodOriginFinder();
            methodOriginFinder.startFindingMethod(declaredMethod);
            Method overridenMethod = methodOriginFinder.getOriginalMethod();
            if (overridenMethod == null)
                continue;
            if (methodToImplementationCache.containsKey(overridenMethod) && (replaceExistingMethodImplementation == null || !replaceExistingMethodImplementation.apply(methodToImplementationCache.get(overridenMethod), overridenMethod)))
                continue;
            methodToImplementationCache.put(overridenMethod, implementation);
        }
    }

    public boolean isImplemented() {
        return !methodToImplementationCache.isEmpty();
    }

    public void addImplementation(@NotNull T implementation) {
        addImplementation(implementation, null);
    }

    @NotNull
    private T createProxy() {
        return type.cast(Proxy.newProxyInstance(
                type.getClassLoader(),
                new Class<?>[]{type},
                (proxy, method, args) -> {

                    T implementation = defaultImplementation;

                    if (methodToImplementationCache.containsKey(method)) {
                        implementation = methodToImplementationCache.get(method);
                    }

                    return method.invoke(implementation, args);
                }
        ));
    }

    public static class MethodOriginFinder {
        private Class<?> result;
        private Method foundMethod;

        public void startFindingMethod(@NotNull Method method) {
            startFindingMethod(method.getDeclaringClass(), method.getName(), method.getParameterTypes());
        }

        public synchronized void startFindingMethod(@NotNull Class<?> currentClass, @NotNull String methodName, @NotNull Class<?>... parameterTypes) {
            result = null;
            foundMethod = null;
            search(currentClass, methodName, parameterTypes);
        }

        @NotNull
        public Class<?> getClassDeclaringMethod() {
            return result;
        }

        @NotNull
        public Method getOriginalMethod() {
            return foundMethod;
        }

        private boolean search(@NotNull Class<?> currentClass, @NotNull String methodName, @NotNull Class<?>... parameterTypes) {
            if (result != null)
                return false;
            boolean foundInSuperClass = false;
            if (currentClass.getSuperclass() != null && !currentClass.getSuperclass().equals(Object.class)) {
                var found = search(currentClass.getSuperclass(), methodName, parameterTypes);
                if (found)
                    foundInSuperClass = true;
            }

            boolean foundInAnyInterface = false;
            for (Class<?> anInterface : currentClass.getInterfaces()) {
                var foundMethodInInterface = search(anInterface, methodName, parameterTypes);
                if (foundMethodInInterface)
                    foundInAnyInterface = true;
            }


            try {
                Method method = currentClass.getMethod(methodName, parameterTypes);

                if (!foundInSuperClass && !foundInAnyInterface && !currentClass.equals(Object.class)) {
                    result = currentClass;
                    foundMethod = method;
                }

                return true;
            } catch (NoSuchMethodException e) {
                return false;
            }
        }
    }
}