package de.verdox.mccreativelab.impl.vanilla.misc;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.misc.MCCBlockHitResult;
import de.verdox.mccreativelab.wrapper.misc.MCCDirection;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.world.coordinates.MCBlockPos;
import net.minecraft.world.phys.BlockHitResult;

public class NMSBlockHitResult extends NMSHitResult<BlockHitResult> implements MCCBlockHitResult {
    public static final MCCConverter<BlockHitResult, NMSBlockHitResult> CONVERTER = converter(NMSBlockHitResult.class, BlockHitResult.class, NMSBlockHitResult::new, MCCHandle::getHandle);

    public NMSBlockHitResult(BlockHitResult handle) {
        super(handle);
    }

    @Override
    public MCCDirection getDirection() {
        return conversionService.wrap(handle.getDirection(), new TypeToken<>() {});
    }

    @Override
    public MCBlockPos getBlockPos() {
        return new MCBlockPos(handle.getBlockPos().getX(), handle.getBlockPos().getY(), handle.getBlockPos().getZ());
    }

    @Override
    public boolean isInside() {
        return handle.isInside();
    }

    @Override
    public boolean isWorldBorderHit() {
        return handle.isWorldBorderHit();
    }
}
