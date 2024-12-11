package de.verdox.mccreativelab.conversion;

import com.google.common.reflect.TypeToken;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeUtil {

    @Nullable
    public static TypeToken<?> extractGeneric(TypeToken<?> genericTypeToken, int indexOfGeneric) {
        Type type = genericTypeToken.getType();
        if (!(type instanceof ParameterizedType parameterizedType)) {
            return null;
        }
        Type[] generics = parameterizedType.getActualTypeArguments();
        if (indexOfGeneric < 0 || indexOfGeneric >= generics.length) {
            return null;
        }
        return TypeToken.of(generics[indexOfGeneric]);
    }

}
