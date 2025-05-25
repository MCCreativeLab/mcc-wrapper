package de.verdox.mccreativelab.impl.vanilla.block.properties;

import de.verdox.mccreativelab.gamefactory.block.properties.MCCBlockStateProperty;
import de.verdox.mccreativelab.gamefactory.block.properties.MCCBlockStatePropertyFactory;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.List;

public class NMSBlockStatePropertyFactory implements MCCBlockStatePropertyFactory {
    @Override
    public <T extends Enum<T>> MCCBlockStateProperty<T> createEnum(String name, List<T> enumValues) {
        if (enumValues.isEmpty()) {
            throw new IllegalArgumentException("Provided list cannot be empty");
        }
        return create(name, enumValues);
    }

    @Override
    public MCCBlockStateProperty<Boolean> createBoolean(String name) {
        return new NMSBlockStateProperty<>(BooleanProperty.create(name));
    }

    @Override
    public MCCBlockStateProperty<Integer> createInteger(String name, int min, int max) {
        return new NMSBlockStateProperty<>(IntegerProperty.create(name, min, max));
    }

    private <F extends Enum<F> & StringRepresentable, T extends Enum<T>> NMSBlockStateProperty<F, T> create(String name, List<T> apiValues) {
        Class<F> type = (Class<F>) apiValues.getFirst().getClass();
        List<F> converted = MCCPlatform.getInstance().getConversionService().unwrap(apiValues);
        return new NMSBlockStateProperty<>(EnumProperty.create(name, type, converted));
    }
}
