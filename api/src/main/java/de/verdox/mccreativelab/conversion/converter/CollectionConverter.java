package de.verdox.mccreativelab.conversion.converter;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * A converter used for collections. The converter will try to iterate through the collection and apply its logic to every element of the collection. It will always create a new collection with a provided constructor method.
 * @param <C> the generic collection type
 * @param <T> the specific collection type
 */
public class CollectionConverter <C extends Collection, T extends C> extends ContainerConverter<C, C> {
    private final Supplier<T> constructor;
    private final Class<C> collectionType;

    public CollectionConverter(Supplier<T> constructor, Class<C> collectionType) {
        this.constructor = constructor;
        this.collectionType = collectionType;
    }

    @Override
    public ConversionResult<C> wrap(C nativeType) {

        C newCollection = constructor.get();
        for (Object o : nativeType) {
            newCollection.add(MCCPlatform.getInstance().getConversionService().wrap(o));
        }

        return done(newCollection);
    }

    @Override
    public ConversionResult<C> unwrap(C platformImplType) {
        C newCollection = constructor.get();
        for (Object o : platformImplType) {
            newCollection.add(MCCPlatform.getInstance().getConversionService().unwrap(o));
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
}
