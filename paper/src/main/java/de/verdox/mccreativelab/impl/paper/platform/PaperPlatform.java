package de.verdox.mccreativelab.impl.paper.platform;

import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.conversion.ConversionServiceImpl;
import de.verdox.mccreativelab.conversion.converter.EnumConverter;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.paper.block.settings.PaperBlockHardnessSettings;
import de.verdox.mccreativelab.impl.paper.block.settings.PaperBlockSoundSettings;
import de.verdox.mccreativelab.impl.paper.block.settings.PaperFurnaceSettings;
import de.verdox.mccreativelab.impl.paper.entity.PaperAttributeInstance;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.impl.paper.platform.converter.CraftBlockStateConverter;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.wrapper.block.MCCBlockFace;
import de.verdox.mccreativelab.wrapper.block.MCCCapturedBlockState;
import de.verdox.mccreativelab.wrapper.block.settings.MCCBlockHardnessSettings;
import de.verdox.mccreativelab.wrapper.block.settings.MCCBlockSoundSettings;
import de.verdox.mccreativelab.wrapper.block.settings.MCCFurnaceSettings;
import de.verdox.mccreativelab.wrapper.entity.*;
import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class PaperPlatform extends NMSPlatform {
    private static final Logger LOGGER = Logger.getLogger(PaperPlatform.class.getSimpleName());


    protected final JavaPlugin javaPlugin;
    private final PaperBlockSoundSettings blockSoundSettings = new PaperBlockSoundSettings();
    private final PaperBlockHardnessSettings paperBlockHardnessSettings = new PaperBlockHardnessSettings();
    private final PaperFurnaceSettings paperFurnaceSettings = new PaperFurnaceSettings();
    private final ConversionService bukkitConversionService = new ConversionServiceImpl();

    public PaperPlatform(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        LOGGER.info("Setting up Paper Platform");
    }

    @Override
    public void init() {
        super.init();
        //CraftBukkitConverters.init();
        //BukkitToMCCConverters.init();
        BukkitAdapter.init();
        conversionService.registerConverterForNewImplType(MCCAttributeInstance.class, PaperAttributeInstance.CONVERTER);
        conversionService.registerConverterForNewImplType(Component.class, new MCCConverter<net.minecraft.network.chat.Component, Component>() {
            @Override
            public ConversionResult<Component> wrap(net.minecraft.network.chat.Component nativeType) {
                return done(PaperAdventure.asAdventure(nativeType));
            }

            @Override
            public ConversionResult<net.minecraft.network.chat.Component> unwrap(Component platformImplType) {
                return done(PaperAdventure.asVanilla(platformImplType));
            }

            @Override
            public Class<Component> apiImplementationClass() {
                return Component.class;
            }

            @Override
            public Class<net.minecraft.network.chat.Component> nativeMinecraftType() {
                return net.minecraft.network.chat.Component.class;
            }
        });
        LOGGER.info("Paper Platform initialized");
    }

    public void enableListeners() {
        paperFurnaceSettings.initVanillaBurnDurations();
        Bukkit.getPluginManager().registerEvents(paperBlockHardnessSettings, javaPlugin);
        Bukkit.getPluginManager().registerEvents(paperFurnaceSettings, javaPlugin);
        Bukkit.getPluginManager().registerEvents(blockSoundSettings, javaPlugin);
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
}
