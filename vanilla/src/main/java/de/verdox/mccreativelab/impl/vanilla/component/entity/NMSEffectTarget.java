package de.verdox.mccreativelab.impl.vanilla.component.entity;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.component.AbstractComponent;
import de.verdox.mccreativelab.wrapper.component.entity.MCCEffectTarget;
import de.verdox.mccreativelab.wrapper.entity.MCCEffect;
import de.verdox.mccreativelab.wrapper.entity.types.MCCLivingEntity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;

public class NMSEffectTarget extends AbstractComponent<LivingEntity, MCCLivingEntity> implements MCCEffectTarget {
    public NMSEffectTarget(MCCLivingEntity livingEntity) {
        super(livingEntity, new TypeToken<>() {});
    }

    @Override
    public Collection<MCCEffect> getActiveEffects() {
        return conversionService.wrap(handle.getActiveEffects(), new TypeToken<>() {});
    }

    @Override
    public boolean removeAllEffects() {
        return handle.removeAllEffects();
    }
}
