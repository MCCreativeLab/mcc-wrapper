package de.verdox.mccreativelab.impl.vanilla.platform.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import de.verdox.mccreativelab.wrapper.platform.data.MCCDataPackInterceptor;
import net.kyori.adventure.key.Key;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.IoSupplier;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class NMSDataPackInterceptor implements MCCDataPackInterceptor {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Set<PackAssetType> excludedTypes = new HashSet<>();
    private final Set<String> stringPatternExcludes = new HashSet<>();
    private final Map<PackAssetType, Set<Key>> exclusions = new HashMap<>();
    private final Map<PackAssetType, Map<Key, List<Function<DataPackAsset, Boolean>>>> modifies = new HashMap<>();
    private final Set<Function<DataPackAsset, Boolean>> globalModifiers = new HashSet<>();
    private Consumer<DataPackAsset> installCallback;

    @Override
    public @NotNull MCCDataPackInterceptor exclude(@NotNull String pathContains) {
        stringPatternExcludes.add(pathContains);
        return this;
    }

    @Override
    public @NotNull MCCDataPackInterceptor exclude(@NotNull PackAssetType packAssetType) {
        excludedTypes.add(packAssetType);
        return this;
    }

    @Override
    public @NotNull MCCDataPackInterceptor modify(@NotNull PackAssetType packAssetType, @NotNull Key key, @NotNull Function<DataPackAsset, Boolean> consumer) {
        modifies.computeIfAbsent(packAssetType, packAssetType1 -> new HashMap<>()).computeIfAbsent(key, key1 -> new LinkedList<>()).add(consumer);
        return this;
    }

    @Override
    public @NotNull MCCDataPackInterceptor modify(@NotNull Function<DataPackAsset, Boolean> modifier) {
        globalModifiers.add(modifier);
        return this;
    }


    @Override
    public @NotNull MCCDataPackInterceptor exclude(@NotNull PackAssetType packAssetType, @NotNull Key key) {
        exclusions.computeIfAbsent(packAssetType, packAssetType1 -> new HashSet<>()).add(key);
        return this;
    }

    @Override
    public @NotNull MCCDataPackInterceptor onInstall(@NotNull Consumer<DataPackAsset> installCallback) {
        this.installCallback = installCallback;
        return this;
    }

    @Nullable
    public IoSupplier<InputStream> evaluate(ResourceLocation resourceLocation, Path assetPath, IoSupplier<InputStream> ioSupplier) {

        String assetTypeName = resourceLocation.toString().split("/")[0];
        int indexOfStartKey = assetTypeName.indexOf(":");
        assetTypeName = assetTypeName.substring(indexOfStartKey + 1);
        PackAssetType packAssetType = new PackAssetType(assetTypeName);
        Key key = Key.key(resourceLocation.getNamespace(), FilenameUtils.removeExtension(resourceLocation.getPath().replace(assetTypeName + "/", "")));

        if (excludedTypes.contains(packAssetType) || stringPatternExcludes.stream().anyMatch(s -> resourceLocation.toString().contains(s)) || (exclusions.containsKey(packAssetType) && exclusions.get(packAssetType).contains(key))) {
            LOGGER.info("Removing {}: {}", packAssetType.parentFolder(), key);
            return null;
        }

        try (InputStream inputStream = ioSupplier.get()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            String jsonString = byteArrayOutputStream.toString(StandardCharsets.UTF_8);

            JsonObject jsonObject;
            try {
                jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            } catch (Throwable e) {
                return ioSupplier;
            }


            DataPackAsset dataPackAsset = new DataPackAsset(packAssetType, key, jsonObject);

            boolean shouldLoad = true;
            for (Function<DataPackAsset, Boolean> globalModifier : globalModifiers) {
                if (!globalModifier.apply(dataPackAsset)) shouldLoad = false;
            }

            if (modifies.containsKey(packAssetType)) {
                Map<Key, List<Function<DataPackAsset, Boolean>>> map = modifies.get(packAssetType);
                if (map.containsKey(key)) {
                    List<Function<DataPackAsset, Boolean>> modifiers = map.get(key);
                    LOGGER.info("Patching {}: {}", packAssetType.parentFolder(), key);

                    for (Function<DataPackAsset, Boolean> modifier : modifiers) {
                        if (!modifier.apply(dataPackAsset)) shouldLoad = false;
                    }

                }
            }

            if (!shouldLoad)
                return null;
            if (this.installCallback != null)
                this.installCallback.accept(dataPackAsset);

            inputStream.close();

            byteArrayOutputStream.close();
            return () -> new ByteArrayInputStream(jsonObject.toString().getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            LOGGER.error("Error while manipulating DataPack resource installation", e);
            return ioSupplier;
        }
    }
}