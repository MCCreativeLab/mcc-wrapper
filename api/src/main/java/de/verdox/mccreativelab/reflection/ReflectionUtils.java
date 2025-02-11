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
            Field field = clazz.getClass().getDeclaredField(fieldName);
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
            Field field = clazz.getClass().getDeclaredField(fieldName);
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
}
