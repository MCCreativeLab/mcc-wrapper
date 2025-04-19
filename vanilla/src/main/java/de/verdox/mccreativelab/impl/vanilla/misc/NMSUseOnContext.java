package de.verdox.mccreativelab.impl.vanilla.misc;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.entity.player.MCCInteractionHand;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.misc.MCCBlockHitResult;
import de.verdox.mccreativelab.wrapper.misc.MCCUseOnContext;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class NMSUseOnContext extends MCCHandle<UseOnContext> implements MCCUseOnContext {
    public static final MCCConverter<UseOnContext, NMSUseOnContext> CONVERTER = converter(NMSUseOnContext.class, UseOnContext.class, NMSUseOnContext::new, MCCHandle::getHandle);

    public NMSUseOnContext(UseOnContext handle) {
        super(handle);
    }

    @Override
    public @Nullable MCCPlayer getPlayer() {
        return conversionService.wrap(handle.getPlayer());
    }

    @Override
    public MCCInteractionHand getHand() {
        return conversionService.wrap(handle.getHand());
    }

    @Override
    public MCCBlockHitResult getHit() {
        return conversionService.wrap(ReflectionUtils.readFieldFromClass(handle, "hitResult", new TypeToken<BlockHitResult>() {}));
    }

    @Override
    public MCCWorld getWorld() {
        return conversionService.wrap(handle.getLevel());
    }

    @Override
    public MCCItemStack getItemStack() {
        return conversionService.wrap(handle.getItemInHand());
    }
}
