package de.verdox.mccreativelab.impl.vanilla.pack;

import de.verdox.mccreativelab.generator.AssetPath;
import de.verdox.mccreativelab.generator.ResourcePackFileHoster;
import de.verdox.mccreativelab.generator.resourcepack.CustomResourcePack;
import de.verdox.mccreativelab.generator.resourcepack.types.hud.renderer.HudRendererImpl;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.platform.GeneratorPlatformHelper;
import de.verdox.mccreativelab.platform.PlatformResourcePack;
import de.verdox.vserializer.SerializableField;
import de.verdox.vserializer.exception.SerializationException;
import de.verdox.vserializer.generic.SerializationElement;
import de.verdox.vserializer.generic.Serializer;
import de.verdox.vserializer.generic.SerializerBuilder;
import de.verdox.vserializer.json.JsonSerializerContext;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourcePackManager {
    private static final Logger LOGGER = Logger.getLogger(ResourcePackManager.class.getSimpleName());

    private CustomResourcePack customResourcePack;
    private GeneratorPlatformHelper helper;
    private ResourcePackFileHoster resourcePackFileHoster;
    private ResourcePackSettings resourcePackSettings;

    public boolean init(NMSPlatform nmsPlatform) throws IOException, SerializationException {
        File templateFolder = new File("resourcePack/template/");
        File fileStorage = new File("resourcePack/settings/");
        File hostStorage = new File("resourcePack/host/");
        File settingsFile = new File("resourcePack/settings.json");

        JsonSerializerContext jsonSerializerContext = new JsonSerializerContext();
        if (!settingsFile.exists()) {
            SerializationElement element = ResourcePackSettings.SERIALIZER.serialize(jsonSerializerContext, new ResourcePackSettings("mcc"));
            jsonSerializerContext.writeToFile(element, settingsFile);
        }

        resourcePackSettings = ResourcePackSettings.SERIALIZER.deserialize(jsonSerializerContext.readFromFile(settingsFile));

        boolean setupComplete = true;
        if (!templateFolder.mkdirs() && !templateFolder.isDirectory()) {
            LOGGER.log(Level.SEVERE, "The template folder could not be created");
            setupComplete = false;
        }
        if (!fileStorage.mkdirs() && !fileStorage.isDirectory()) {
            LOGGER.log(Level.SEVERE, "The settings folder could not be created");
            setupComplete = false;
        }
        if (!hostStorage.mkdirs() && !hostStorage.isDirectory()) {
            LOGGER.log(Level.SEVERE, "The pack host folder could not be created");
            setupComplete = false;
        }

        if (!setupComplete) {
            LOGGER.log(Level.SEVERE, "Could not initialize correctly... Shutting down platform");
            nmsPlatform.shutdown();
            return false;
        }

        this.customResourcePack = new CustomResourcePack(resourcePackSettings.packName(), 18, "Generated by MCCreativeLab", AssetPath.buildPath("resourcePacks"), templateFolder, fileStorage);
        this.helper = nmsPlatform.constructPackGeneratorHelper(customResourcePack);
        this.resourcePackFileHoster = new ResourcePackFileHoster(hostStorage);
        PlatformResourcePack.INSTANCE.setup(this.customResourcePack, rp -> {
        });
        nmsPlatform.tickSignal().subscribe(tick -> ((HudRendererImpl) GeneratorPlatformHelper.INSTANCE.get().getHudRenderer()).addTickToRenderQueue(nmsPlatform.getOnlinePlayers()));
        return true;
    }

    public void buildPack() {
        try {
            customResourcePack.installPack(false);
            resourcePackFileHoster.createResourcePackZipFiles();
        } catch (IOException | SerializationException e) {
            throw new RuntimeException(e);
        }
    }

    public CustomResourcePack getResourcePack() {
        return customResourcePack;
    }

    public GeneratorPlatformHelper getHelper() {
        return helper;
    }

    public ResourcePackFileHoster getResourcePackFileHoster() {
        return resourcePackFileHoster;
    }

    private record ResourcePackSettings(String packName) {
        private static final Serializer<ResourcePackSettings> SERIALIZER = SerializerBuilder.create("settings", ResourcePackSettings.class)
                .constructor(
                        ResourcePackSettings::new,
                        serializer -> new SerializableField<>("packName", Serializer.Primitive.STRING, ResourcePackSettings::packName)
                )
                .build();
    }
}
