package de.verdox.mccreativelab.impl.vanilla.types;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.entity.MCCEffect;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.types.MCCPotion;
import net.minecraft.world.item.alchemy.Potion;

import java.util.List;

public class NMSPotion extends MCCHandle<Potion> implements MCCPotion {
    public static final MCCConverter<Potion, NMSPotion> CONVERTER = converter(NMSPotion.class, Potion.class, NMSPotion::new, MCCHandle::getHandle);

    public NMSPotion(Potion handle) {
        super(handle);
    }

    @Override
    public String getName() {
        return handle.name();
    }

    @Override
    public List<MCCEffect> getEffects() {
        return conversionService.wrap(handle.getEffects(), new TypeToken<>() {});
    }

    @Override
    public boolean hasInstantEffects() {
        return handle.hasInstantEffects();
    }
}
