package de.verdox.mccreativelab.impl.vanilla.platform;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.advancement.MCCAdvancementBuilder;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.conversion.ConversionServiceImpl;
import de.verdox.mccreativelab.conversion.converter.EnumConverter;
import de.verdox.mccreativelab.data.MCCDataPackInterceptor;
import de.verdox.mccreativelab.data.MCCVanillaRegistryManipulator;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.generator.resourcepack.CustomResourcePack;
import de.verdox.mccreativelab.impl.vanilla.block.NMSBlockSoundGroup;
import de.verdox.mccreativelab.impl.vanilla.block.NMSBlockState;
import de.verdox.mccreativelab.impl.vanilla.block.NMSBlockType;
import de.verdox.mccreativelab.impl.vanilla.component.entity.NMSGameComponentRegistry;
import de.verdox.mccreativelab.impl.vanilla.data.NMSDataPackInterceptor;
import de.verdox.mccreativelab.impl.vanilla.data.NMSVanillaRegistryManipulator;
import de.verdox.mccreativelab.impl.vanilla.entity.*;
import de.verdox.mccreativelab.impl.vanilla.entity.types.*;
import de.verdox.mccreativelab.impl.vanilla.inventory.NMSContainer;
import de.verdox.mccreativelab.impl.vanilla.inventory.factory.NMSContainerFactory;
import de.verdox.mccreativelab.impl.vanilla.inventory.types.container.NMSPlayerInventory;
import de.verdox.mccreativelab.impl.vanilla.inventory.types.menu.*;
import de.verdox.mccreativelab.impl.vanilla.item.NMSItemStack;
import de.verdox.mccreativelab.impl.vanilla.item.NMSItemType;
import de.verdox.mccreativelab.impl.vanilla.item.components.*;
import de.verdox.mccreativelab.impl.vanilla.item.equipment.NMSEquipmentAsset;
import de.verdox.mccreativelab.impl.vanilla.pack.ResourcePackManager;
import de.verdox.mccreativelab.impl.vanilla.pack.VanillaGeneratorHelper;
import de.verdox.mccreativelab.impl.vanilla.platform.converter.*;
import de.verdox.mccreativelab.impl.vanilla.platform.factory.NMSElementFactory;
import de.verdox.mccreativelab.impl.vanilla.platform.factory.NMSTypedKeyFactory;
import de.verdox.mccreativelab.impl.vanilla.platform.serialization.NMSSerializers;
import de.verdox.mccreativelab.impl.vanilla.platform.serialization.nbt.NBTSerializationContext;
import de.verdox.mccreativelab.impl.vanilla.registry.*;
import de.verdox.mccreativelab.impl.vanilla.types.*;
import de.verdox.mccreativelab.impl.vanilla.world.NMSWorld;
import de.verdox.mccreativelab.impl.vanilla.world.chunk.NMSChunk;
import de.verdox.mccreativelab.impl.vanilla.world.level.biome.NMSBiome;
import de.verdox.mccreativelab.platform.GeneratorPlatformHelper;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.block.MCCBlockSoundGroup;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.block.settings.MCCBlockHardnessSettings;
import de.verdox.mccreativelab.wrapper.block.settings.MCCBlockSoundSettings;
import de.verdox.mccreativelab.wrapper.block.settings.MCCFurnaceSettings;
import de.verdox.mccreativelab.wrapper.component.GameComponentRegistry;
import de.verdox.mccreativelab.wrapper.entity.*;
import de.verdox.mccreativelab.wrapper.entity.player.MCCGameMode;
import de.verdox.mccreativelab.wrapper.entity.types.*;
import de.verdox.mccreativelab.wrapper.exceptions.OperationNotPossibleOnNMS;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainer;
import de.verdox.mccreativelab.wrapper.inventory.MCCMenuType;
import de.verdox.mccreativelab.wrapper.inventory.factory.MCCContainerFactory;
import de.verdox.mccreativelab.wrapper.inventory.types.container.MCCPlayerInventory;
import de.verdox.mccreativelab.wrapper.inventory.types.menu.*;
import de.verdox.mccreativelab.wrapper.item.MCCAttributeModifier;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.item.components.*;
import de.verdox.mccreativelab.wrapper.item.equipment.MCCEquipmentAsset;
import de.verdox.mccreativelab.wrapper.platform.MCCLifecycleTrigger;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.platform.MCCResourcePack;
import de.verdox.mccreativelab.wrapper.platform.MCCTaskManager;
import de.verdox.mccreativelab.wrapper.platform.cached.signal.Signal;
import de.verdox.mccreativelab.wrapper.platform.cached.signal.SignalCache;
import de.verdox.mccreativelab.wrapper.platform.factory.MCCElementFactory;
import de.verdox.mccreativelab.wrapper.platform.factory.TypedKeyFactory;
import de.verdox.mccreativelab.wrapper.platform.properties.MCCPropertyKey;
import de.verdox.mccreativelab.wrapper.platform.properties.MCCServerProperties;
import de.verdox.mccreativelab.wrapper.platform.serialization.MCCSerializers;
import de.verdox.mccreativelab.wrapper.registry.*;
import de.verdox.mccreativelab.wrapper.types.*;
import de.verdox.mccreativelab.wrapper.world.MCCDifficulty;
import de.verdox.mccreativelab.wrapper.world.MCCEntitySpawnReason;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import de.verdox.mccreativelab.wrapper.world.level.biome.MCCBiome;
import de.verdox.vserializer.generic.SerializationContext;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerSettings;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.GameType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;
import reactor.core.publisher.Sinks;

import java.util.*;
import java.util.function.Supplier;
import java.util.logging.Level;

public class NMSPlatform implements MCCPlatform {
    protected final NMSTypedKeyFactory typedKeyFactory;
    protected final ConversionService conversionService;
    protected final MCCContainerFactory containerFactory;
    protected final NMSRegistryStorage registryStorage;
    protected final NMSLifecycleTrigger lifecycleTrigger;
    protected final NMSElementFactory elementFactory = new NMSElementFactory(this);
    protected final NMSSerializers serializers = new NMSSerializers();
    private final boolean useGeneratedConverters;
    private final ResourcePackManager resourcePackManager = new ResourcePackManager();
    private final GameComponentRegistry gameComponentRegistry = new NMSGameComponentRegistry(this);

    private final MCCDataPackInterceptor dataPackInterceptor = new NMSDataPackInterceptor();
    private final MCCVanillaRegistryManipulator registryManipulator = new NMSVanillaRegistryManipulator();

    public NMSPlatform(boolean useGeneratedConverters) {
        this.useGeneratedConverters = useGeneratedConverters;
        this.typedKeyFactory = new NMSTypedKeyFactory();
        this.conversionService = new ConversionServiceImpl();
        this.containerFactory = new NMSContainerFactory(this);
        this.registryStorage = new NMSRegistryStorage();
        this.lifecycleTrigger = new NMSLifecycleTrigger(this);
    }

    public NMSPlatform() {
        this(true);
    }

    /**
     * We use this constructor for testing purposes only. Our Test Suite uses a different bootstrapping mechanism that skips core parts of the server startup to make the test way faster.
     *
     * @param fullRegistryAccess   The full registry access to the builtin and vanilla registries
     * @param reloadableRegistries the registry access to the reloadable resources
     */
    @VisibleForTesting
    public NMSPlatform(RegistryAccess.Frozen fullRegistryAccess, HolderGetter.Provider reloadableRegistries) {
        this.typedKeyFactory = new NMSTypedKeyFactory();
        this.conversionService = new ConversionServiceImpl();
        this.containerFactory = new NMSContainerFactory(this);
        this.registryStorage = new NMSRegistryStorage(fullRegistryAccess, reloadableRegistries);
        this.lifecycleTrigger = new NMSLifecycleTrigger(this);
        this.useGeneratedConverters = true;
    }

    @Override
    public void load() {
        prepareConverter(MCCBlockState.class, NMSBlockState.CONVERTER);
        prepareConverter(MCCBlockSoundGroup.class, NMSBlockSoundGroup.CONVERTER);
        prepareConverter(MCCBlockType.class, NMSBlockType.CONVERTER);
        prepareConverter(MCCAttribute.class, NMSAttribute.CONVERTER);
        prepareConverter(MCCEntityType.class, NMSEntityType.CONVERTER);
        prepareConverter(MCCItemStack.class, NMSItemStack.CONVERTER);
        prepareConverter(MCCDataComponentMap.class, NMSDataComponentMap.CONVERTER);
        prepareConverter(MCCItemType.class, NMSItemType.CONVERTER);
        prepareConverter(MCCWorld.class, NMSWorld.CONVERTER);
        prepareConverter(MCCBiome.class, NMSBiome.CONVERTER);
        prepareConverter(MCCEffectType.class, NMSEffectType.CONVERTER);
        prepareConverter(MCCEffect.class, NMSEffect.CONVERTER);

        prepareConverter(MCCAttributeInstance.class, NMSAttributeInstance.CONVERTER);
        prepareConverter(MCCAttributeMap.class, NMSAttributeMap.CONVERTER);
        prepareConverter(MCCChunk.class, NMSChunk.CONVERTER);

        prepareConverter(Key.class, new ResourceLocationConverter());
        prepareConverter(Sound.class, new SoundConverter());
        prepareConverter(MCCAttributeModifier.class, new AttributeModifierConverter());
        prepareConverter(MCCMenuType.class, new MenuTypeConverter());
        prepareConverter(MCCTypedKey.class, NMSTypedKey.CONVERTER);
        prepareConverter(MCCReference.class, NMSReference.CONVERTER);
        prepareConverter(MCCTag.class, NMSTag.CONVERTER);
        prepareConverter(MCCReferenceSet.class, NMSReferenceSet.CONVERTER);
        prepareConverter(MCCEitherReference.class, NMSEitherReference.CONVERTER);
        prepareConverter(MCCRegistry.class, NMSRegistry.CONVERTER);
        prepareConverter(MCCRegistry.class, NMSRegistryLookup.CONVERTER);
        prepareConverter(MCCContainer.class, NMSContainer.CONVERTER);

        prepareConverter(MCCGameEvent.class, NMSGameEvent.CONVERTER);
        prepareConverter(MCCJukeboxSong.class, NMSJukeboxSong.CONVERTER);
        prepareConverter(MCCLootTable.class, NMSLootTable.CONVERTER);
        prepareConverter(MCCPaintingVariant.class, NMSPaintingVariant.CONVERTER);
        prepareConverter(MCCPoiType.class, NMSPoiType.CONVERTER);
        prepareConverter(MCCPotion.class, NMSPotion.CONVERTER);
        prepareConverter(MCCTrimPattern.class, NMSTrimPattern.CONVERTER);
        prepareConverter(MCCVillagerProfession.class, NMSVillagerProfession.CONVERTER);
        prepareConverter(MCCDamageType.class, NMSDamageType.CONVERTER);

        prepareConverter(MCCEnchantment.class, NMSEnchantment.CONVERTER);
        prepareConverter(MCCEnchantment.Definition.class, NMSEnchantment.NMSDefinition.CONVERTER);
        prepareConverter(MCCEnchantment.Cost.class, NMSEnchantment.NMSCost.CONVERTER);

        prepareConverter(MCCConsumeEffect.class, NMSConsumeEffect.CONVERTER);

        prepareConverter(MCCEquipmentAsset.class, NMSEquipmentAsset.CONVERTER);

        registerMenuTypes();
        registerContainerTypes();
        registerEnumConverters();
        registerEntityClasses();
        registerItemComponentConverters();
        if (useGeneratedConverters) {
            GeneratedConverters.init(conversionService);
        }
    }

    @Override
    public void setupConversionService() {
        LOGGER.info("Initializing conversion service with " + preparedConverters.size() + " converters");
        preparedConverters.forEach((aClass, preparedConverter) -> preparedConverter.register(conversionService));
    }

    @Override
    public @NotNull MCCBlockHardnessSettings getBlockHardnessSettings() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public @NotNull MCCBlockSoundSettings getBlockSoundSettings() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public @NotNull MCCFurnaceSettings getFurnaceSettings() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public int getPublicTick() {
        return getServer().getTickCount(); // TODO: check if this replaces return MinecraftServer.currentTick;
    }

    @Override
    public MCCServerProperties getServerProperties() {
        DedicatedServer dedicatedServer = (DedicatedServer) getServer();
        return new MCCServerProperties(
                ReflectionUtils.readFieldFromClass(dedicatedServer.getProperties(), "properties", new TypeToken<>() {}),
                () -> ReflectionUtils.readFieldFromClass(dedicatedServer, "settings", new TypeToken<DedicatedServerSettings>() {}).forceSave()
        );
    }

    @Override
    public void shutdown() {
        getServer().halt(false);
    }

    @Override
    public MCCRegistryStorage getRegistryStorage() {
        return registryStorage;
    }

    @Override
    public MCCLifecycleTrigger getLifecycleTrigger() {
        return lifecycleTrigger;
    }

    @Override
    public MCCElementFactory getElementFactory() {
        return elementFactory;
    }

    @Override
    public MCCSerializers getPlatformSerializers() {
        return serializers;
    }

    @Override
    public GameComponentRegistry getGameComponentRegistry() {
        return gameComponentRegistry;
    }

    @Override
    public <API, VALUE> Signal<VALUE> createSignal(Key key, API apiObject, Supplier<Sinks.Many<VALUE>> sinkCreator) {
        return SignalCache.INSTANCE.createFlux(key, conversionService.unwrap(apiObject), sinkCreator);
    }

    @Override
    public SerializationContext getNBTSerializationContext() {
        return new NBTSerializationContext();
    }

    @Override
    public @NotNull MCCDataPackInterceptor getDataPackInterceptor() {
        return dataPackInterceptor;
    }

    @Override
    public @NotNull MCCVanillaRegistryManipulator getRegistryManipulator() {
        return registryManipulator;
    }

    @Override
    public @NotNull MCCAdvancementBuilder createAdvancement() {
        return null; // TODO
    }

    @Override
    public @NotNull MCCContainerFactory getContainerFactory() {
        return containerFactory;
    }

    @Override
    public @NotNull MCCTaskManager getTaskManager() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public @NotNull TypedKeyFactory getTypedKeyFactory() {
        return typedKeyFactory;
    }

    @Override
    public @NotNull ConversionService getConversionService() {
        return conversionService;
    }

    @Override
    public @NotNull List<MCCWorld> getWorlds() {
        List<MCCWorld> worlds = new LinkedList<>();
        getServer().getAllLevels().forEach(serverLevel -> {
            worlds.add(getConversionService().wrap(serverLevel, new TypeToken<>() {}));
        });
        return worlds;
    }

    @Override
    public @Nullable MCCWorld getWorld(Key key) {
        return conversionService.wrap(getServer().getLevel(ResourceKey.create(Registries.DIMENSION, conversionService.unwrap(key, ResourceLocation.class))), MCCWorld.class);
    }

    @Override
    public @Nullable MCCPlayer getOnlinePlayer(@NotNull UUID uuid) {
        return conversionService.wrap(getServer().getPlayerList().getPlayer(uuid));
    }

    @Override
    public @NotNull List<MCCPlayer> getOnlinePlayers() {
        return getServer().getPlayerList().getPlayers().stream().map(serverPlayer -> getConversionService().wrap(serverPlayer, new TypeToken<MCCPlayer>() {})).toList();
    }

    @Override
    public void setServerResourcePack(@NotNull MCCResourcePack resourcePack) {
        String downloadUrl = getServerProperties().read(MCCPropertyKey.RESOURCE_PACK);
        String packIdString = getServerProperties().read(MCCPropertyKey.RESOURCE_PACK_ID);

        LOGGER.info("Found server resource pack settings:");
        LOGGER.info("Download url: " + downloadUrl);

        UUID packID = null;
        try {
            packID = UUID.fromString(packIdString);
            LOGGER.info("Pack id: " + packID);
        } catch (Throwable e) {
            LOGGER.warning("The resource pack id " + packIdString + " is not a valid uuid.");
        }


        if (resourcePack.getUUID().equals(packID) && resourcePack.url().equals(downloadUrl)) {
            return;
        }
        Component component = conversionService.unwrap(resourcePack.prompt() != null ? resourcePack.prompt() : net.kyori.adventure.text.Component.empty());

        getServerProperties().write(MCCPropertyKey.RESOURCE_PACK, resourcePack.url());
        getServerProperties().write(MCCPropertyKey.RESOURCE_PACK_ID, resourcePack.getUUID().toString());
        getServerProperties().write(MCCPropertyKey.RESOURCE_PACK_PROMPT, component.getString());
        getServerProperties().write(MCCPropertyKey.RESOURCE_PACK_SHA1, resourcePack.hash());
        getServerProperties().write(MCCPropertyKey.RESOURCE_PACK_REQUIRE, resourcePack.isRequired());
        LOGGER.info("Restarting the platform to write the resource pack settings to the server.properties file.");
        LOGGER.info(MCCPropertyKey.RESOURCE_PACK.id() + " = " + resourcePack.url());
        LOGGER.info(MCCPropertyKey.RESOURCE_PACK_ID.id() + " = " + resourcePack.getUUID());
        LOGGER.info(MCCPropertyKey.RESOURCE_PACK_PROMPT.id() + " = " + component.getString());
        LOGGER.info(MCCPropertyKey.RESOURCE_PACK_SHA1.id() + " = " + resourcePack.hash());
        LOGGER.info(MCCPropertyKey.RESOURCE_PACK_REQUIRE.id() + " = " + resourcePack.isRequired());
        LOGGER.info("...");
        getServerProperties().saveToFile();
        shutdown();
    }

    public MinecraftServer getServer() {
        return null;
    }

    private void registerMenuTypes() {
        prepareConverter(MCCAnvilContainerMenu.class, NMSAnvilContainerMenu.CONVERTER);
        prepareConverter(MCCBeaconContainerMenu.class, NMSBeaconContainerMenu.CONVERTER);
        prepareConverter(MCCBrewingStandContainerMenu.class, NMSBrewingStandContainerMenu.CONVERTER);
        prepareConverter(MCCCartographyTableContainerMenu.class, NMSCartographyTableContainerMenu.CONVERTER);
        prepareConverter(MCCChestContainerMenu.class, NMSChestContainerMenu.CONVERTER);
        prepareConverter(MCCCrafterContainerMenu.class, NMSCrafterContainerMenu.CONVERTER);
        prepareConverter(MCCDispenserContainerMenu.class, NMSDispenserContainerMenu.CONVERTER);
        prepareConverter(MCCEnchantingTableContainerMenu.class, NMSEnchantingTableContainerMenu.CONVERTER);
        prepareConverter(MCCFurnaceContainerMenu.class, NMSFurnaceContainerMenu.Furnace.CONVERTER);
        prepareConverter(MCCFurnaceContainerMenu.class, NMSFurnaceContainerMenu.BlastFurnace.CONVERTER);
        prepareConverter(MCCFurnaceContainerMenu.class, NMSFurnaceContainerMenu.Smoker.CONVERTER);
        prepareConverter(MCCLoomContainerMenu.class, NMSLoomContainerMenu.CONVERTER);
        prepareConverter(MCCGrindstoneContainerMenu.class, NMSGrindstoneContainerMenu.CONVERTER);
        prepareConverter(MCCLecternContainerMenu.class, NMSLecternContainerMenu.CONVERTER);
        prepareConverter(MCCMerchantContainerMenu.class, NMSMerchantContainerMenu.CONVERTER);
        prepareConverter(MCCWorkBenchContainerMenu.class, NMSWorkBenchContainerMenu.CONVERTER);
        prepareConverter(MCCHopperContainerMenu.class, NMSHopperContainerMenu.CONVERTER);
        prepareConverter(MCCShulkerContainerMenu.class, NMSShulkerContainerMenu.CONVERTER);
        prepareConverter(MCCStonecutterContainerMenu.class, NMSStoneCutterContainerMenu.CONVERTER);
        prepareConverter(MCCSmithingContainerMenu.class, NMSSmithingContainerMenu.CONVERTER);
    }

    private void registerEntityClasses() {
        prepareConverter(MCCEntity.class, NMSEntity.CONVERTER);
        prepareConverter(MCCLivingEntity.class, NMSLivingEntity.CONVERTER);
        prepareConverter(MCCItemEntity.class, NMSItemEntity.CONVERTER);
        prepareConverter(MCCMarkerEntity.class, NMSMarkerEntity.CONVERTER);
        prepareConverter(MCCInteractionEntity.class, NMSInteractionEntity.CONVERTER);
        prepareConverter(MCCPlayer.class, NMSPlayer.CONVERTER);
        registerDisplayEntity();
    }

    private void registerDisplayEntity() {
        prepareConverter(MCCDisplayEntity.Item.class, NMSDisplayEntity.Item.CONVERTER);
        prepareConverter(MCCDisplayEntity.Block.class, NMSDisplayEntity.Block.CONVERTER);
        prepareConverter(MCCDisplayEntity.Text.class, NMSDisplayEntity.Text.CONVERTER);

        prepareConverter(MCCDisplayEntity.Transformation.class, NMSDisplayEntity.NMSTransformation.CONVERTER);
        prepareConverter(MCCDisplayEntity.Text.Alignment.class, new EnumConverter<>(Display.TextDisplay.Align.class, MCCDisplayEntity.Text.Alignment.class));
        prepareConverter(MCCDisplayEntity.Item.Display.class, new EnumConverter<>(ItemDisplayContext.class, MCCDisplayEntity.Item.Display.class));
    }

    private void registerContainerTypes() {
        prepareConverter(MCCPlayerInventory.class, NMSPlayerInventory.CONVERTER);
    }

    private void registerEnumConverters() {
        prepareConverter(MCCEquipmentSlot.class, new EnumConverter<>(EquipmentSlot.class, MCCEquipmentSlot.class));
        prepareConverter(MCCEquipmentSlotGroup.class, new EnumConverter<>(EquipmentSlotGroup.class, MCCEquipmentSlotGroup.class));
        prepareConverter(MCCGameMode.class, new EnumConverter<>(GameType.class, MCCGameMode.class));
        prepareConverter(MCCDifficulty.class, new EnumConverter<>(Difficulty.class, MCCDifficulty.class));
        prepareConverter(MCCEntitySpawnReason.class, new EnumConverter<>(EntitySpawnReason.class, MCCEntitySpawnReason.class));
    }

    private void registerItemComponentConverters() {
        prepareConverter(MCCDyedItemColor.class, NMSDyedItemColor.CONVERTER);
        prepareConverter(MCCTool.class, NMSTool.CONVERTER);
        prepareConverter(MCCUnbreakable.class, NMSUnbreakable.CONVERTER);
        prepareConverter(MCCMapId.class, NMSMapId.CONVERTER);
        prepareConverter(MCCRepairable.class, NMSRepairable.CONVERTER);
        prepareConverter(MCCCustomModelData.class, NMSCustomModelData.CONVERTER);
        prepareConverter(MCCLodestoneTracker.class, NMSLodestoneTracker.CONVERTER);
        prepareConverter(MCCTool.Rule.class, NMSTool.NMSRule.CONVERTER);
        prepareConverter(MCCUseRemainder.class, NMSUseRemainder.CONVERTER);
        prepareConverter(MCCJukeboxPlayable.class, NMSJukeboxPlayable.CONVERTER);
        prepareConverter(MCCSuspiciousStewEffects.class, NMSSuspiciousStewEffects.CONVERTER);
        prepareConverter(MCCEnchantable.class, NMSEnchantable.CONVERTER);
        prepareConverter(MCCEquippable.class, NMSEquippable.CONVERTER);
        prepareConverter(MCCMapItemColor.class, NMSMapItemColor.CONVERTER);
        prepareConverter(MCCItemLore.class, NMSItemLore.CONVERTER);
        prepareConverter(MCCSuspiciousStewEffects.Entry.class, NMSSuspiciousStewEffects.NMSEntry.CONVERTER);
        prepareConverter(MCCUseCooldown.class, NMSUseCooldown.CONVERTER);
        prepareConverter(MCCBlockItemStateProperties.class, NMSBlockItemStateProperties.CONVERTER);
        prepareConverter(MCCChargedProjectiles.class, NMSChargedProjectiles.CONVERTER);
        prepareConverter(MCCFoodProperties.class, NMSFoodProperties.CONVERTER);
        prepareConverter(MCCDataComponentType.class, new DataComponentTypeConverter());
    }

    public ResourcePackManager getResourcePackManager() {
        return resourcePackManager;
    }

    public GeneratorPlatformHelper constructPackGeneratorHelper(CustomResourcePack customResourcePack) {
        return new VanillaGeneratorHelper(customResourcePack);
    }

    private static final Map<Class<?>, PreparedConverter<?, ?, ?>> preparedConverters = new HashMap<>();

    /**
     * We prepare converters so other platforms can override prepared converters.
     * A native minecraft class type can only have one prepared converter.
     */
    protected <A, T extends A, F> void prepareConverter(Class<A> apiType, MCCConverter<F, T> converter) {
        var preparedConverter = new PreparedConverter<>(apiType, converter);
        if (preparedConverters.containsKey(converter.nativeMinecraftType())) {
            var oldApiImplClass = preparedConverters.get(converter.nativeMinecraftType()).converter.apiImplementationClass();
            var newApiImplClass = converter.apiImplementationClass();
            if (oldApiImplClass.equals(newApiImplClass)) {
                LOGGER.log(Level.SEVERE, apiType.getSimpleName() + " is already mapped to impl type " + newApiImplClass.getSimpleName());
                return;
            }

            LOGGER.info(apiType.getSimpleName() + ": replacing " + oldApiImplClass.getSimpleName() + " with " + newApiImplClass.getSimpleName());
        }
        preparedConverters.put(converter.nativeMinecraftType(), preparedConverter);
    }

    private record PreparedConverter<A, T extends A, F>(Class<A> apiType, MCCConverter<F, T> converter) {
        void register(ConversionService conversionService) {
            conversionService.registerConverterForNewImplType(apiType, converter);
        }
    }
}
