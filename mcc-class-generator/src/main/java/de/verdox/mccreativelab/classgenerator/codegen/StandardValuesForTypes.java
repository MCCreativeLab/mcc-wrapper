package de.verdox.mccreativelab.classgenerator.codegen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.CodeExpression;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.StringExpression;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.shorts.ShortList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StandardValuesForTypes {
    public static final Map<Class<?>, CodeExpression> defaultValues = new HashMap<>();

    static {
        register(boolean.class, "false");
        register(char.class, "0");
        register(byte.class, "0");
        register(short.class, "0");
        register(int.class, "0");
        register(long.class, "0");
        register(float.class, "0");
        register(double.class, "0");
        register(ImmutableMap.class, "ImmutableMap.of()");
        register(ImmutableList.class, "ImmutableList.of()");
        register(ImmutableSet.class, "ImmutableSet.of()");
        register(Set.class, "Set.of()");
        register(Map.class, "Map.of()");
        register(List.class, "List.of()");

        register(ByteList.class, "ByteList.of()");
        register(ShortList.class, "ShortList.of()");
        register(IntList.class, "IntList.of()");
        register(LongList.class, "LongList.of()");
        register(FloatList.class, "FloatList.of()");
        register(DoubleList.class, "DoubleList.of()");
    }

    public static CodeExpression getDefaultValueForType(Class<?> type) {
        if (!defaultValues.containsKey(type)) {
            return defaultValues.keySet().stream().filter(clazz -> clazz.isAssignableFrom(type)).map(defaultValues::get).findAny().orElse(new StringExpression("null"));
        }
        return defaultValues.getOrDefault(type, new StringExpression("null"));
    }

    private static void register(Class<?> type, CodeExpression value) {
        defaultValues.put(type, value);
    }

    private static void register(Class<?> type, String value) {
        defaultValues.put(type, new StringExpression(value));
    }
}
