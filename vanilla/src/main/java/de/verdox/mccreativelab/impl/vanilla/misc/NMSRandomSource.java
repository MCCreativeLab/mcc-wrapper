package de.verdox.mccreativelab.impl.vanilla.misc;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.misc.MCCRandomSource;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.util.RandomSource;

public class NMSRandomSource extends MCCHandle<RandomSource> implements MCCRandomSource {
    public static final MCCConverter<RandomSource, NMSRandomSource> CONVERTER = converter(NMSRandomSource.class, RandomSource.class, NMSRandomSource::new, MCCHandle::getHandle);

    public NMSRandomSource(RandomSource handle) {
        super(handle);
    }

    @Override
    public void setSeed(long seed) {
        handle.setSeed(seed);
    }

    @Override
    public int nextInt() {
        return handle.nextInt();
    }

    @Override
    public int nextInt(int bound) {
        return handle.nextInt(bound);
    }

    @Override
    public long nextLong() {
        return handle.nextLong();
    }

    @Override
    public boolean nextBoolean() {
        return handle.nextBoolean();
    }

    @Override
    public float nextFloat() {
        return handle.nextFloat();
    }

    @Override
    public double nextDouble() {
        return handle.nextDouble();
    }

    @Override
    public double nextGaussian() {
        return handle.nextGaussian();
    }
}
