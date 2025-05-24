package de.verdox.mccreativelab.conversion.converter;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * A converter used for collections. The converter will try to iterate through the collection and apply its logic to every element of the collection. It will always create a new collection with a provided constructor method.
 * @param <C> the generic collection type
 * @param <T> the specific collection type
 */
public class CollectionConverter <C extends Collection, T extends C> extends ContainerConverter<C, C> {
    private final Supplier<T> constructor;
    private final Class<C> collectionType;

    public CollectionConverter(ConversionService conversionService, Supplier<T> constructor, Class<C> collectionType) {
        super(conversionService);
        this.constructor = constructor;
        this.collectionType = collectionType;
    }

    @Override
    public ConversionResult<C> wrap(C nativeType) {

        C newCollection = constructor.get();
        for (Object o : nativeType) {
            newCollection.add(getConversionService().wrap(o));
        }

        return done(newCollection);
    }

    @Override
    public ConversionResult<C> unwrap(C platformImplType) {
        C newCollection = constructor.get();
        for (Object o : platformImplType) {
            newCollection.add(getConversionService().unwrap(o));
        }
        return done(newCollection);
    }

    @Override
    public Class<C> apiImplementationClass() {
        return collectionType;
    }

    @Override
    public Class<C> nativeMinecraftType() {
        return collectionType;
    }

    @Override
    public ConversionResult<C> wrap(C nativeType, TypeToken<C> typeToken) {
        // Extrahiere den Elementtyp aus dem Collection-Typ
        List<TypeToken<?>> typeArguments = getTypeArguments(typeToken);
        TypeToken<?> elementType = typeArguments.isEmpty() ? TypeToken.of(Object.class) : typeArguments.get(0);

        C newCollection = constructor.get();
        for (Object element : nativeType) {
            Object wrapped = getConversionService().wrap(element, elementType);
            newCollection.add(wrapped);
        }
        return done(newCollection);
    }

    @Override
    public ConversionResult<C> unwrap(C platformImplType, TypeToken<C> typeToken) {
        List<TypeToken<?>> typeArguments = getTypeArguments(typeToken);
        TypeToken<?> elementType = typeArguments.isEmpty() ? TypeToken.of(Object.class) : typeArguments.get(0);

        C newCollection = constructor.get();
        for (Object element : platformImplType) {
            Object unwrapped = getConversionService().unwrap(element, elementType);
            newCollection.add(unwrapped);
        }
        return done(newCollection);
    }
}
