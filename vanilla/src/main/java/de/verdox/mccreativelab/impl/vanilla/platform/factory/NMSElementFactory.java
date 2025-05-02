package de.verdox.mccreativelab.impl.vanilla.platform.factory;

import com.mojang.math.Transformation;
import de.verdox.mccreativelab.impl.vanilla.entity.types.NMSDisplayEntity;
import de.verdox.mccreativelab.impl.vanilla.item.components.*;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.wrapper.entity.types.MCCDisplayEntity;
import de.verdox.mccreativelab.wrapper.item.components.*;
import de.verdox.mccreativelab.wrapper.platform.factory.MCCElementFactory;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NMSElementFactory implements MCCElementFactory {
    private final NMSPlatform nmsPlatform;

    private final Map<Class<? extends MCCItemComponent>, Supplier<? extends MCCItemComponent>> constructors = new HashMap<>();

    public NMSElementFactory(NMSPlatform nmsPlatform) {
        this.nmsPlatform = nmsPlatform;

        registerComponentConstructor(MCCUnbreakable.class, () -> new NMSUnbreakable(null));
        registerComponentConstructor(MCCItemLore.class, () -> new NMSItemLore(null));
        registerComponentConstructor(MCCCustomModelData.class, () -> new NMSCustomModelData(null));
        registerComponentConstructor(MCCFoodProperties.class, () -> new NMSFoodProperties(null));
        registerComponentConstructor(MCCUseRemainder.class, () -> new NMSUseRemainder(null));
        registerComponentConstructor(MCCUseCooldown.class, () -> new NMSUseCooldown(null));
        registerComponentConstructor(MCCTool.class, () -> new NMSTool(null));
        registerComponentConstructor(MCCEnchantable.class, () -> new NMSEnchantable(null));
        registerComponentConstructor(MCCRepairable.class, () -> new NMSRepairable(null));
        registerComponentConstructor(MCCDyedItemColor.class, () -> new NMSDyedItemColor(null));
        registerComponentConstructor(MCCMapItemColor.class, () -> new NMSMapItemColor(null));
        registerComponentConstructor(MCCMapId.class, () -> new NMSMapId(null));
        registerComponentConstructor(MCCChargedProjectiles.class, () -> new NMSChargedProjectiles(null));
        registerComponentConstructor(MCCSuspiciousStewEffects.class, () -> new NMSSuspiciousStewEffects(null));
        registerComponentConstructor(MCCJukeboxPlayable.class, () -> new NMSJukeboxPlayable(null));
        registerComponentConstructor(MCCBlockItemStateProperties.class, () -> new NMSBlockItemStateProperties(null));
    }

    @Override
    public MCCDataComponentMap createEmptyDataComponentMap() {
        return nmsPlatform.getConversionService().wrap(new PatchedDataComponentMap(DataComponentMap.builder().build()));
    }

    @Override
    public <T extends MCCItemComponent> T createEmptyComponent(Class<T> componentClass) {
        if (!constructors.containsKey(componentClass)) {
            throw new IllegalArgumentException("Could not create an empty variant of the data component type " + componentClass.getName());
        }
        return componentClass.cast(constructors.get(componentClass).get());
    }

    @Override
    public MCCDisplayEntity.Transformation createTransformation() {
        return new NMSDisplayEntity.NMSTransformation(Transformation.identity());
    }

    private <T extends MCCItemComponent> void registerComponentConstructor(Class<T> componentClass, Supplier<T> emptyConstructor) {
        constructors.put(componentClass, emptyConstructor);
    }
}
