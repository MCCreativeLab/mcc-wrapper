package de.verdox.mccreativelab.wrapper.component;

/**
 * A central point to create and manage game components.
 * Game components are used to manage certain parts of platform elements.
 * They serve as an alternative for the inheritance pattern in OOP languages which create boiler plated classes.
 */
public interface GameComponentRegistry {
    /**
     * Used internally to create game components
     * @param owner the owner of the component
     * @param componentType the component type
     * @return the component
     * @param <OWNER> the generic component owner
     * @param <T> the generic component type
     */
    <OWNER, T extends GameComponent<OWNER>> T create(OWNER owner, Class<T> componentType);

    <OWNER, T extends GameComponent<OWNER>> void register(Class<T> componentType, GameComponentCreator<OWNER, T> creator);


    interface GameComponentCreator<OWNER, COMPONENT extends GameComponent<OWNER>> {
        COMPONENT create(OWNER owner);
    }
}
