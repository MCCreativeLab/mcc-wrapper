package de.verdox.mccreativelab.impl.vanilla.component.entity;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.component.AbstractComponent;
import de.verdox.mccreativelab.wrapper.component.entity.MCCEffectTarget;
import de.verdox.mccreativelab.wrapper.entity.MCCEffect;
import de.verdox.mccreativelab.wrapper.entity.MCCEffectType;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCLivingEntity;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class NMSEffectTarget extends AbstractComponent<LivingEntity, MCCLivingEntity> implements MCCEffectTarget {
    public NMSEffectTarget(MCCLivingEntity livingEntity) {
        super(livingEntity, new TypeToken<>() {});
    }

    @Override
    public Collection<MCCEffect> getActiveEffects() {
        return conversionService.wrap(handle.getActiveEffects());
    }

    @Override
    public boolean removeAllEffects() {
        return handle.removeAllEffects();
    }

    @Override
    public boolean hasEffect(MCCReference<MCCEffectType> effect) {
        return handle.hasEffect(conversionService.unwrap(effect, new TypeToken<>() {}));
    }

    @Override
    public boolean addEffect(MCCEffect effect, @Nullable MCCEntity cause) {
        return handle.addEffect(conversionService.unwrap(effect, new TypeToken<>() {}), conversionService.unwrap(cause, new TypeToken<>() {}));
    }

    @Override
    public boolean canBeAffected(MCCEffect effect) {
        return handle.canBeAffected(conversionService.unwrap(effect, new TypeToken<>() {}));
    }

    @Override
    public boolean removeEffect(MCCReference<MCCEffectType> effect) {
        return handle.removeEffect(conversionService.unwrap(effect, new TypeToken<>() {}));
    }
}
