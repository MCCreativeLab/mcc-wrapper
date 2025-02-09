package de.verdox.mccreativelab.impl.vanilla.platform;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.conversion.ConversionServiceImpl;
import de.verdox.mccreativelab.conversion.converter.EnumConverter;
import de.verdox.mccreativelab.impl.vanilla.block.NMSBlockSoundGroup;
import de.verdox.mccreativelab.impl.vanilla.block.NMSBlockState;
import de.verdox.mccreativelab.impl.vanilla.block.NMSBlockType;
import de.verdox.mccreativelab.impl.vanilla.entity.*;
import de.verdox.mccreativelab.impl.vanilla.entity.types.NMSItemEntity;
import de.verdox.mccreativelab.impl.vanilla.entity.types.NMSLivingEntity;
import de.verdox.mccreativelab.impl.vanilla.entity.types.NMSPlayer;
import de.verdox.mccreativelab.impl.vanilla.inventory.NMSContainer;
import de.verdox.mccreativelab.impl.vanilla.inventory.factory.NMSContainerFactory;
import de.verdox.mccreativelab.impl.vanilla.inventory.types.container.NMSPlayerInventory;
import de.verdox.mccreativelab.impl.vanilla.inventory.types.menu.*;
import de.verdox.mccreativelab.impl.vanilla.item.NMSItemStack;
import de.verdox.mccreativelab.impl.vanilla.item.NMSItemType;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSConsumeEffect;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSDataComponentMap;
import de.verdox.mccreativelab.impl.vanilla.platform.converter.*;
import de.verdox.mccreativelab.impl.vanilla.platform.factory.NMSTypedKeyFactory;
import de.verdox.mccreativelab.impl.vanilla.registry.*;
import de.verdox.mccreativelab.impl.vanilla.types.*;
import de.verdox.mccreativelab.impl.vanilla.world.NMSWorld;
import de.verdox.mccreativelab.impl.vanilla.world.chunk.NMSChunk;
import de.verdox.mccreativelab.impl.vanilla.world.level.biome.NMSBiome;
import de.verdox.mccreativelab.wrapper.block.MCCBlockSoundGroup;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.block.settings.MCCBlockHardnessSettings;
import de.verdox.mccreativelab.wrapper.block.settings.MCCBlockSoundSettings;
import de.verdox.mccreativelab.wrapper.block.settings.MCCFurnaceSettings;
import de.verdox.mccreativelab.wrapper.entity.*;
import de.verdox.mccreativelab.wrapper.entity.player.MCCGameMode;
import de.verdox.mccreativelab.wrapper.entity.types.MCCItemEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCLivingEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.exceptions.OperationNotPossibleOnNMS;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainer;
import de.verdox.mccreativelab.wrapper.inventory.MCCMenuType;
import de.verdox.mccreativelab.wrapper.inventory.factory.MCCContainerFactory;
import de.verdox.mccreativelab.wrapper.inventory.types.container.MCCPlayerInventory;
import de.verdox.mccreativelab.wrapper.inventory.types.menu.*;
import de.verdox.mccreativelab.wrapper.item.MCCAttributeModifier;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.item.components.MCCConsumeEffect;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.platform.MCCLifecycleTrigger;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.platform.MCCResourcePack;
import de.verdox.mccreativelab.wrapper.platform.MCCTaskManager;
import de.verdox.mccreativelab.wrapper.platform.factory.TypedKeyFactory;
import de.verdox.mccreativelab.wrapper.platform.properties.MCCPropertyKey;
import de.verdox.mccreativelab.wrapper.platform.properties.MCCServerProperties;
import de.verdox.mccreativelab.wrapper.registry.*;
import de.verdox.mccreativelab.wrapper.types.*;
import de.verdox.mccreativelab.wrapper.world.MCCDifficulty;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import de.verdox.mccreativelab.wrapper.world.level.biome.MCCBiome;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.level.GameType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class NMSPlatform implements MCCPlatform {
    protected final NMSTypedKeyFactory typedKeyFactory;
    protected final ConversionService conversionService;
    protected final MCCContainerFactory containerFactory;
    protected final NMSRegistryStorage registryStorage;
    protected final NMSLifecycleTrigger lifecycleTrigger;
    private final boolean useGeneratedConverters;

    public NMSPlatform(boolean useGeneratedConverters) {
        this.useGeneratedConverters = useGeneratedConverters;
        this.typedKeyFactory = new NMSTypedKeyFactory();
        this.conversionService = new ConversionServiceImpl();
        this.containerFactory = new NMSContainerFactory(this);
        this.registryStorage = new NMSRegistryStorage();
        this.lifecycleTrigger = new NMSLifecycleTrigger();
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
        this.lifecycleTrigger = new NMSLifecycleTrigger();
        this.useGeneratedConverters = true;
    }

    @Override
    public void init() {
        conversionService.registerConverterForNewImplType(MCCBlockState.class, NMSBlockState.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCBlockSoundGroup.class, NMSBlockSoundGroup.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCBlockType.class, NMSBlockType.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCAttribute.class, NMSAttribute.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCEntityType.class, NMSEntityType.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCItemStack.class, NMSItemStack.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCDataComponentMap.class, NMSDataComponentMap.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCItemType.class, NMSItemType.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCWorld.class, NMSWorld.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCBiome.class, NMSBiome.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCEffectType.class, NMSEffectType.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCEffect.class, NMSEffect.CONVERTER);

        conversionService.registerConverterForNewImplType(MCCAttributeMap.class, NMSAttributeMap.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCChunk.class, NMSChunk.CONVERTER);

        conversionService.registerConverterForNewImplType(Key.class, new ResourceLocationConverter());
        conversionService.registerConverterForNewImplType(Sound.class, new SoundConverter());
        conversionService.registerConverterForNewImplType(MCCAttributeModifier.class, new AttributeModifierConverter());
        conversionService.registerConverterForNewImplType(MCCMenuType.class, new MenuTypeConverter());
        conversionService.registerConverterForNewImplType(MCCTypedKey.class, NMSTypedKey.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCReference.class, NMSReference.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCTag.class, NMSTag.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCReferenceSet.class, NMSReferenceSet.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCEitherReference.class, NMSEitherReference.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCRegistry.class, NMSRegistry.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCRegistry.class, NMSRegistryLookup.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCContainer.class, NMSContainer.CONVERTER);

        conversionService.registerConverterForNewImplType(MCCGameEvent.class, NMSGameEvent.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCJukeboxSong.class, NMSJukeboxSong.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCLootTable.class, NMSLootTable.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCPaintingVariant.class, NMSPaintingVariant.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCPoiType.class, NMSPoiType.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCPotion.class, NMSPotion.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCTrimPattern.class, NMSTrimPattern.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCVillagerProfession.class, NMSVillagerProfession.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCDamageType.class, NMSDamageType.CONVERTER);

        conversionService.registerConverterForNewImplType(MCCEnchantment.class, NMSEnchantment.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCEnchantment.Definition.class, NMSEnchantment.NMSDefinition.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCEnchantment.Cost.class, NMSEnchantment.NMSCost.CONVERTER);

        conversionService.registerConverterForNewImplType(MCCConsumeEffect.class, NMSConsumeEffect.CONVERTER);

        registerMenuTypes();
        registerContainerTypes();
        registerEnumConverters();
        registerEntityClasses();
        if (useGeneratedConverters) {
            GeneratedConverters.init(conversionService);
        }
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
        return MinecraftServer.currentTick;
    }

    @Override
    public MCCServerProperties getServerProperties() {
        DedicatedServer dedicatedServer = (DedicatedServer) MinecraftServer.getServer();
        return new MCCServerProperties(dedicatedServer.getProperties().properties, () -> dedicatedServer.settings.forceSave());
    }

    @Override
    public void shutdown() {
        MinecraftServer.getServer().halt(false);
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
    public @NotNull MCCContainerFactory getContainerFactory() {
        return containerFactory;
    }

    @Override
    public @NotNull MCCTaskManager getTaskManager() {
        //TODO
        return null;
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
        MinecraftServer.getServer().getAllLevels().forEach(serverLevel -> {
            worlds.add(getConversionService().wrap(serverLevel, new TypeToken<>() {}));
        });
        return worlds;
    }

    @Override
    public @Nullable MCCPlayer getOnlinePlayer(@NotNull UUID uuid) {
        return conversionService.wrap(MinecraftServer.getServer().getPlayerList().getPlayer(uuid));
    }

    @Override
    public @NotNull List<MCCPlayer> getOnlinePlayers() {
        return MinecraftServer.getServer().getPlayerList().getPlayers().stream().map(serverPlayer -> getConversionService().wrap(serverPlayer, new TypeToken<MCCPlayer>() {})).toList();
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

    private void registerMenuTypes() {
        conversionService.registerConverterForNewImplType(MCCAnvilContainerMenu.class, NMSAnvilContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCBeaconContainerMenu.class, NMSBeaconContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCBrewingStandContainerMenu.class, NMSBrewingStandContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCCartographyTableContainerMenu.class, NMSCartographyTableContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCChestContainerMenu.class, NMSChestContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCCrafterContainerMenu.class, NMSCrafterContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCDispenserContainerMenu.class, NMSDispenserContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCEnchantingTableContainerMenu.class, NMSEnchantingTableContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCFurnaceContainerMenu.class, NMSFurnaceContainerMenu.Furnace.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCFurnaceContainerMenu.class, NMSFurnaceContainerMenu.BlastFurnace.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCFurnaceContainerMenu.class, NMSFurnaceContainerMenu.Smoker.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCLoomContainerMenu.class, NMSLoomContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCGrindstoneContainerMenu.class, NMSGrindstoneContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCLecternContainerMenu.class, NMSLecternContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCMerchantContainerMenu.class, NMSMerchantContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCWorkBenchContainerMenu.class, NMSWorkBenchContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCHopperContainerMenu.class, NMSHopperContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCShulkerContainerMenu.class, NMSShulkerContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCStonecutterContainerMenu.class, NMSStoneCutterContainerMenu.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCSmithingContainerMenu.class, NMSSmithingContainerMenu.CONVERTER);
    }

    private void registerEntityClasses() {
        conversionService.registerConverterForNewImplType(MCCEntity.class, NMSEntity.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCLivingEntity.class, NMSLivingEntity.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCItemEntity.class, NMSItemEntity.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCPlayer.class, NMSPlayer.CONVERTER);
    }

    private void registerContainerTypes() {
        conversionService.registerConverterForNewImplType(MCCPlayerInventory.class, NMSPlayerInventory.CONVERTER);
    }

    private void registerEnumConverters() {
        conversionService.registerConverterForNewImplType(MCCEquipmentSlot.class, new EnumConverter<>(EquipmentSlot.class, MCCEquipmentSlot.class));
        conversionService.registerConverterForNewImplType(MCCEquipmentSlotGroup.class, new EnumConverter<>(EquipmentSlotGroup.class, MCCEquipmentSlotGroup.class));
        conversionService.registerConverterForNewImplType(MCCGameMode.class, new EnumConverter<>(GameType.class, MCCGameMode.class));
        conversionService.registerConverterForNewImplType(MCCDifficulty.class, new EnumConverter<>(Difficulty.class, MCCDifficulty.class));
    }
}
