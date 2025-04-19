package de.verdox.mccreativelab.reflection;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection utilities
 */
public class ReflectionUtils {

    /**
     * Read a field from a class
     *
     * @param clazz     Class to read from
     * @param fieldName Field name
     * @param type      Type of the field
     * @param <C>       Class type
     * @param <R>       Field type
     * @return Field value
     */
    @SuppressWarnings("unchecked")
    public static <C, R> R readFieldFromClass(C clazz, String fieldName, TypeToken<R> type) {
        try {
            Field field = findFieldInHierarchy(clazz.getClass(), fieldName);
            field.setAccessible(true);
            return (R) field.get(clazz);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Write a field in a class
     *
     * @param clazz     Class to write to
     * @param fieldName Field name
     * @param value     Value to write
     * @param <C>       Class type
     * @param <R>       Field type
     */
    public static <C, R> void writeFieldInClass(C clazz, String fieldName, R value) {
        try {
            Field field = findFieldInHierarchy(clazz.getClass(), fieldName);
            field.setAccessible(true);
            field.set(clazz, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invoke a method in a class
     *
     * @param clazz      Class to invoke the method in
     * @param methodName Method name
     * @param args       Arguments
     * @param <C>        Class type
     * @param <R>        Return type
     * @return Method return value
     */
    @SuppressWarnings("unchecked")
    public static <C, R> R invokeMethodInClass(C clazz, String methodName, Object... args) {
        try {
            Method method = clazz.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            return (R) method.invoke(clazz, args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Find a field by name in the class hierarchy (includes private and inherited fields)
     *
     * @param clazz     Class to search
     * @param fieldName Field name
     * @return The Field object
     * @throws NoSuchFieldException if the field does not exist in the class hierarchy
     */
    public static Field findFieldInHierarchy(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> current = clazz;
        while (current != null) {
            try {
                Field field = current.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass(); // weiter nach oben in der Hierarchie
            }
        }
        throw new NoSuchFieldException("Field '" + fieldName + "' not found in class hierarchy of " + clazz.getName());
    }

    /**
     * Find a method by name and parameter types in the class hierarchy
     *
     * @param clazz          Class to search
     * @param methodName     Method name
     * @param parameterTypes Method parameter types
     * @return The Method object
     * @throws NoSuchMethodException if the method does not exist in the class hierarchy
     */
    public static Method findMethodInHierarchy(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?> current = clazz;
        while (current != null) {
            try {
                Method method = current.getDeclaredMethod(methodName, parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                current = current.getSuperclass(); // weiter nach oben in der Hierarchie
            }
        }
        throw new NoSuchMethodException("Method '" + methodName + "' not found in class hierarchy of " + clazz.getName());
    }
}
