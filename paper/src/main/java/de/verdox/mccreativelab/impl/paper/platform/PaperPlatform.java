package de.verdox.mccreativelab.impl.paper.platform;

import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.conversion.ConversionServiceImpl;
import de.verdox.mccreativelab.generator.resourcepack.CustomResourcePack;
import de.verdox.mccreativelab.impl.paper.block.settings.PaperBlockHardnessSettings;
import de.verdox.mccreativelab.impl.paper.block.settings.PaperBlockSoundSettings;
import de.verdox.mccreativelab.impl.paper.block.settings.PaperFurnaceSettings;
import de.verdox.mccreativelab.impl.paper.entity.PaperAttributeInstance;
import de.verdox.mccreativelab.impl.paper.entity.types.PaperPlayer;
import de.verdox.mccreativelab.impl.paper.events.PlatformEvents;
import de.verdox.mccreativelab.impl.paper.pack.PaperGeneratorHelper;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.impl.paper.platform.converter.ComponentConverter;
import de.verdox.mccreativelab.impl.paper.platform.task.PaperTaskScheduler;
import de.verdox.mccreativelab.impl.paper.world.PaperWorld;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.platform.GeneratorPlatformHelper;
import de.verdox.mccreativelab.wrapper.block.settings.MCCBlockHardnessSettings;
import de.verdox.mccreativelab.wrapper.block.settings.MCCBlockSoundSettings;
import de.verdox.mccreativelab.wrapper.block.settings.MCCFurnaceSettings;
import de.verdox.mccreativelab.wrapper.entity.MCCAttributeInstance;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.platform.MCCTaskManager;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import net.kyori.adventure.text.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistryAccess;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Logger;

public class PaperPlatform extends NMSPlatform {
    private static final Logger LOGGER = Logger.getLogger(PaperPlatform.class.getSimpleName());

    private final PaperBlockSoundSettings blockSoundSettings = new PaperBlockSoundSettings();
    private final PaperBlockHardnessSettings paperBlockHardnessSettings = new PaperBlockHardnessSettings();
    private final PaperFurnaceSettings paperFurnaceSettings = new PaperFurnaceSettings();
    private final ConversionService bukkitConversionService = new ConversionServiceImpl();
    private PaperTaskScheduler paperTaskScheduler;

    public PaperPlatform() {
        LOGGER.info("Setting up Paper Platform");
    }

    public PaperPlatform(boolean useGeneratedConverters) {
        super(useGeneratedConverters);
    }

    public PaperPlatform(RegistryAccess.Frozen fullRegistryAccess, HolderGetter.Provider reloadableRegistries) {
        super(fullRegistryAccess, reloadableRegistries);
    }

    @Override
    public void init() {
        super.init();
        //CraftBukkitConverters.init();
        //BukkitToMCCConverters.init();

        conversionService.registerConverterForNewImplType(MCCPlayer.class, PaperPlayer.CONVERTER);
        conversionService.registerConverterForNewImplType(MCCWorld.class, PaperWorld.CONVERTER);
        BukkitAdapter.init();
        conversionService.registerConverterForNewImplType(MCCAttributeInstance.class, PaperAttributeInstance.CONVERTER);
        conversionService.registerConverterForNewImplType(Component.class, new ComponentConverter());

        LOGGER.info("Paper Platform initialized");
    }

    @Override
    public MinecraftServer getServer() {
        return Objects.requireNonNull(MinecraftServer.getServer(), "The Minecraft server is not initialized at this point.");
    }

    public void enableListeners(JavaPlugin javaPlugin) {
        paperFurnaceSettings.initVanillaBurnDurations();
        this.paperTaskScheduler = new PaperTaskScheduler(javaPlugin);
        Bukkit.getPluginManager().registerEvents(paperBlockHardnessSettings, javaPlugin);
        Bukkit.getPluginManager().registerEvents(paperFurnaceSettings, javaPlugin);
        Bukkit.getPluginManager().registerEvents(blockSoundSettings, javaPlugin);

        PlatformEvents.init(javaPlugin);
    }

    public ConversionService getBukkitConversionService() {
        return bukkitConversionService;
    }

    @Override
    public @NotNull MCCFurnaceSettings getFurnaceSettings() {
        return paperFurnaceSettings;
    }

    @Override
    public @NotNull MCCBlockHardnessSettings getBlockHardnessSettings() {
        return paperBlockHardnessSettings;
    }


    @Override
    public @NotNull MCCBlockSoundSettings getBlockSoundSettings() {
        return blockSoundSettings;
    }

    @Override
    public @NotNull MCCTaskManager getTaskManager() {
        return paperTaskScheduler;
    }

    @Override
    public GeneratorPlatformHelper constructPackGeneratorHelper(CustomResourcePack customResourcePack) {
        return new PaperGeneratorHelper(customResourcePack);
    }
}
