package de.verdox.mccreativelab.impl.vanilla.misc;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.misc.MCCHitResult;
import de.verdox.mccreativelab.wrapper.misc.MCCHitType;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.phys.HitResult;

public class NMSHitResult<T extends HitResult> extends MCCHandle<T> implements MCCHitResult {
    public static final MCCConverter<HitResult, NMSHitResult> CONVERTER = converter(NMSHitResult.class, HitResult.class, NMSHitResult::new, nmsHitResult -> (HitResult) nmsHitResult.getHandle());

    public NMSHitResult(T handle) {
        super(handle);
    }

    @Override
    public double x() {
        return handle.getLocation().x();
    }

    @Override
    public double y() {
        return handle.getLocation().y();
    }

    @Override
    public double z() {
        return handle.getLocation().z();
    }

    @Override
    public MCCHitType getType() {
        return conversionService.wrap(getType());
    }
}
