package de.verdox.mccreativelab.wrapper.platform;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.Singleton;
import de.verdox.mccreativelab.advancement.MCCAdvancementBuilder;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.data.MCCDataPackInterceptor;
import de.verdox.mccreativelab.data.MCCVanillaRegistryManipulator;
import de.verdox.mccreativelab.wrapper.block.settings.MCCBlockHardnessSettings;
import de.verdox.mccreativelab.wrapper.block.settings.MCCBlockSoundSettings;
import de.verdox.mccreativelab.wrapper.block.settings.MCCFurnaceSettings;
import de.verdox.mccreativelab.wrapper.component.GameComponentRegistry;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.inventory.factory.MCCContainerFactory;
import de.verdox.mccreativelab.wrapper.platform.cached.signal.Signal;
import de.verdox.mccreativelab.wrapper.platform.factory.MCCElementFactory;
import de.verdox.mccreativelab.wrapper.platform.factory.TypedKeyFactory;
import de.verdox.mccreativelab.wrapper.platform.properties.MCCServerProperties;
import de.verdox.mccreativelab.wrapper.platform.serialization.MCCSerializers;
import de.verdox.mccreativelab.wrapper.registry.MCCRegistryStorage;
import de.verdox.mccreativelab.wrapper.util.MCCTicking;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.vserializer.generic.SerializationContext;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * The main entrance point for the MCC-Wrapper Library.
 */
public interface MCCPlatform extends MCCTicking {
    Logger LOGGER = Logger.getLogger(MCCPlatform.class.getSimpleName());

    /**
     * The respective server software implements this interface and sets the singleton accordingly
     */
    Singleton<MCCPlatform> INSTANCE = new Singleton<>(MCCPlatform.class);

    /**
     * Returns the active platform instance
     *
     * @return the platform
     */
    static MCCPlatform getInstance() {
        return INSTANCE.get();
    }

    /**
     * Returns the container factory of this platform
     *
     * @return the container factory
     */
    @NotNull MCCContainerFactory getContainerFactory();

    /**
     * Returns the task manager of this platform
     *
     * @return the task manager
     */
    @NotNull MCCTaskManager getTaskManager();

    /**
     * Returns the typed key factory of this platform
     *
     * @return the factory
     */
    @NotNull TypedKeyFactory getTypedKeyFactory();

    /**
     * Returns the conversion service of this platform
     *
     * @return the conversion service
     */
    @NotNull ConversionService getConversionService();

    /**
     * Returns all worlds that are loaded on this minecraft platform
     *
     * @return the list of worlds loaded
     */
    @NotNull List<MCCWorld> getWorlds();

    /**
     * Gets a world by an uuid or null
     * @param uuid the uuid
     */
    @Nullable
    default MCCWorld getWorld(UUID uuid) {
        return getWorlds().stream().filter(world -> world.getUUID().equals(uuid)).findAny().orElse(null);
    }

    /**
     * Gets a world by a key or null
     * @param key the key
     */
    @Nullable MCCWorld getWorld(Key key);


    /**
     * Gets an online player by his uuid. Returns null if the server is not online.
     *
     * @param uuid the uuid of the player
     * @return the player or null
     */
    @Nullable MCCPlayer getOnlinePlayer(@NotNull UUID uuid);

    /**
     * Returns a list of all online players
     *
     * @return the list of online players
     */
    @NotNull List<MCCPlayer> getOnlinePlayers();

    /**
     * Sets the server resource pack on this server
     *
     * @param resourcePack the server resource pack
     */
    void setServerResourcePack(@NotNull MCCResourcePack resourcePack);

    /**
     * Must be called after instantiating the platform object. Else the platform might not work as expected
     */
    void init();

    /**
     * Returns the block hardness settings of this platform
     *
     * @return the block hardness settings
     */
    @NotNull MCCBlockHardnessSettings getBlockHardnessSettings();

    /**
     * Returns the block sound settings of this platform
     *
     * @return the block sound settings
     */
    @NotNull MCCBlockSoundSettings getBlockSoundSettings();

    /**
     * Returns the furnace settings of this platform
     *
     * @return the furnace settings
     */
    @NotNull MCCFurnaceSettings getFurnaceSettings();

    /**
     * Gets the public tick of the platform
     * @return the public tick
     */
    int getPublicTick();

    /**
     * Gets the server properties of the server
     * @return the server properties
     */
    MCCServerProperties getServerProperties();

    /**
     * Shuts down the server platform
     */
    void shutdown();

    /**
     * Gets the platform registry storage
     * @return the registry storage
     */
    MCCRegistryStorage getRegistryStorage();

    /**
     * Returns the {@link MCCLifecycleTrigger} of this platform
     * @return the lifecycle trigger
     */
    MCCLifecycleTrigger getLifecycleTrigger();

    /**
     * Triggers a platform lifecycle event
     * @param lifecycle the lifecycle state
     */
    default void triggerLifecycleEvent(Lifecycle lifecycle) {
        lifecycle.lifecycleFunction.accept(getLifecycleTrigger());
    }

    /**
     * Returns an element factory that is used to create specific minecraft elements
     * @return the element factory
     */
    MCCElementFactory getElementFactory();

    /**
     * Returns an object that holds all platform serializers
     * @return the platform serializers
     */
    MCCSerializers getPlatformSerializers();

    /**
     * Returns the platform game component registry
     */
    GameComponentRegistry getGameComponentRegistry();

    enum Lifecycle {
        BOOTSTRAP(MCCLifecycleTrigger::bootstrap),
        BEFORE_WORLD_LOAD(MCCLifecycleTrigger::beforeWorldLoad),
        AFTER_WORLD_LOAD(MCCLifecycleTrigger::afterWorldLoad),
        SERVER_STARTUP_COMPLETE(MCCLifecycleTrigger::onServerStartupComplete);
        private final Consumer<MCCLifecycleTrigger> lifecycleFunction;

        Lifecycle(Consumer<MCCLifecycleTrigger> lifecycleFunction) {
            this.lifecycleFunction = lifecycleFunction;
        }
    }

    <API, VALUE> Signal<VALUE> createSignal(Key key, API apiObject, Supplier<Sinks.Many<VALUE>> sinkCreator);

    default <API, VALUE> Signal<VALUE> createSignal(Key key, API apiObject) {
        return createSignal(key, apiObject, () -> Sinks.many().multicast().directBestEffort());
    }

    default <API, VALUE> Signal<VALUE> createSignal(Key key, API apiObject, TypeToken<VALUE> typeToken) {
        return createSignal(key, apiObject, () -> Sinks.many().multicast().directBestEffort());
    }

    /**
     * Returns the nbt serialization context
     */
    SerializationContext getNBTSerializationContext();

    /**
     * Returns the {@link MCCDataPackInterceptor}
     * @return the data pack interceptor
     */
    @NotNull MCCDataPackInterceptor getDataPackInterceptor();

    /**
     * Returns the {@link MCCVanillaRegistryManipulator}
     * @return the vanilla registry manipulator
     */
    @NotNull MCCVanillaRegistryManipulator getRegistryManipulator();

    /**
     * Returns the {@link @NotNull AdvancementBuilder}
     * @return the advancement builder
     */
    @NotNull MCCAdvancementBuilder createAdvancement();
}
