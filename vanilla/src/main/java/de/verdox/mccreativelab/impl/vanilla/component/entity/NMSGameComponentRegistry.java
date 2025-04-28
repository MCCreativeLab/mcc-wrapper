package de.verdox.mccreativelab.impl.vanilla.component.entity;

import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.wrapper.component.GameComponent;
import de.verdox.mccreativelab.wrapper.component.GameComponentRegistry;
import de.verdox.mccreativelab.wrapper.component.entity.MCCEffectTarget;
import de.verdox.mccreativelab.wrapper.component.entity.MCCRideable;
import de.verdox.mccreativelab.wrapper.component.entity.MCCRider;

import java.util.HashMap;
import java.util.Map;

public class NMSGameComponentRegistry implements GameComponentRegistry {
    private final NMSPlatform nmsPlatform;
    private final Map<Class<?>, GameComponentCreator<?, ?>> creators = new HashMap<>();

    public NMSGameComponentRegistry(NMSPlatform nmsPlatform) {
        this.nmsPlatform = nmsPlatform;

        register(MCCEffectTarget.class, NMSEffectTarget::new);
        register(MCCRideable.class, NMSRideable::new);
        register(MCCRider.class, NMSRider::new);
    }

    @Override
    public <OWNER, T extends GameComponent<OWNER>> T create(OWNER owner, Class<T> componentType) {

        if (!creators.containsKey(componentType)) {
            throw new IllegalArgumentException("Trying to create the component type " + componentType+". However the component type is not implemented on this platform.");
        }
        GameComponentCreator<OWNER, T> creator = (GameComponentCreator<OWNER, T>) creators.get(componentType);
        return creator.create(owner);
    }

    @Override
    public <OWNER, T extends GameComponent<OWNER>> void register(Class<T> componentType, GameComponentCreator<OWNER, T> creator) {
        creators.put(componentType, creator);
    }
}
