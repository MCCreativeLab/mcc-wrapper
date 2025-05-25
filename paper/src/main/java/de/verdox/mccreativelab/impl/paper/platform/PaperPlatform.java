package de.verdox.mccreativelab.impl.paper.platform;

import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.conversion.ConversionServiceImpl;
import de.verdox.mccreativelab.gamefactory.MCCGameFactory;
import de.verdox.mccreativelab.generator.resourcepack.CustomResourcePack;
import de.verdox.mccreativelab.impl.paper.block.settings.PaperBlockHardnessSettings;
import de.verdox.mccreativelab.impl.paper.block.settings.PaperBlockSoundSettings;
import de.verdox.mccreativelab.impl.paper.block.settings.PaperFurnaceSettings;
import de.verdox.mccreativelab.impl.paper.component.entity.PaperEntityHiding;
import de.verdox.mccreativelab.impl.paper.component.entity.PaperPersistent;
import de.verdox.mccreativelab.impl.paper.component.entity.PaperPluginMessenger;
import de.verdox.mccreativelab.impl.paper.entity.PaperAttributeInstance;
import de.verdox.mccreativelab.impl.paper.entity.types.PaperPlayer;
import de.verdox.mccreativelab.impl.paper.gamefactory.PaperGameFactory;
import de.verdox.mccreativelab.impl.paper.pack.PaperGeneratorHelper;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.impl.paper.platform.converter.ComponentConverter;
import de.verdox.mccreativelab.impl.paper.platform.task.PaperTaskScheduler;
import de.verdox.mccreativelab.impl.paper.test.GameFactoryTestData;
import de.verdox.mccreativelab.impl.paper.world.PaperWorld;
import de.verdox.mccreativelab.impl.paper.world.chunk.PaperChunk;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.impl.vanilla.registry.NMSRegistryStorage;
import de.verdox.mccreativelab.platform.GeneratorPlatformHelper;
import de.verdox.mccreativelab.wrapper.block.settings.MCCBlockHardnessSettings;
import de.verdox.mccreativelab.wrapper.block.settings.MCCBlockSoundSettings;
import de.verdox.mccreativelab.wrapper.block.settings.MCCFurnaceSettings;
import de.verdox.mccreativelab.wrapper.component.entity.MCCEntityHiding;
import de.verdox.mccreativelab.wrapper.component.entity.MCCPersistent;
import de.verdox.mccreativelab.wrapper.component.entity.MCCPluginMessenger;
import de.verdox.mccreativelab.wrapper.entity.MCCAttributeInstance;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.platform.MCCTaskManager;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import net.kyori.adventure.text.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.RecipeManager;
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

    public PaperPlatform(RegistryAccess.Frozen fullRegistryAccess, HolderGetter.Provider reloadableRegistries, RecipeManager recipeManager) {
        super(fullRegistryAccess, reloadableRegistries, recipeManager);
    }

    @Override
    public void load() {
        super.load();
        //CraftBukkitConverters.init();
        //BukkitToMCCConverters.init();

        prepareConverter(MCCPlayer.class, PaperPlayer.CONVERTER);
        prepareConverter(MCCWorld.class, PaperWorld.CONVERTER);

        prepareConverter(MCCAttributeInstance.class, PaperAttributeInstance.CONVERTER);
        prepareConverter(Component.class, new ComponentConverter());
        prepareConverter(MCCChunk.class, PaperChunk.CONVERTER);

        getGameComponentRegistry().register(MCCPersistent.class, PaperPersistent::new);
        getGameComponentRegistry().register(MCCPluginMessenger.class, PaperPluginMessenger::new);
        getGameComponentRegistry().register(MCCEntityHiding.class, PaperEntityHiding::new);

        BukkitAdapter.init();
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
        GameFactoryTestData.init();
        //PlatformEvents.init(javaPlugin);
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

    @Override
    protected MCCGameFactory constructGameFactory() {
        return new PaperGameFactory(this);
    }
}
