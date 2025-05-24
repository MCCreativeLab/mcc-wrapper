package de.verdox.mccreativelab.gamefactory.block;

import de.verdox.mccreativelab.gamefactory.block.properties.MCCBlockStateProperty;
import de.verdox.mccreativelab.generator.Asset;
import de.verdox.mccreativelab.generator.resourcepack.*;
import de.verdox.mccreativelab.generator.resourcepack.types.ItemTextureData;
import de.verdox.vserializer.util.gson.JsonObjectBuilder;
import net.kyori.adventure.key.Key;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BlockVisuals extends ResourcePackResource {
    public BlockVisuals(Key key) {
        super(key);
    }

    public BlockVisuals withTextureForProperties(String identifier, Asset<CustomResourcePack> blockTexture, ItemTextureData.ModelType modelType, MCCBlockStateProperty<?>... properties) {
        return null;
    }

    @Override
    public void installResourceToPack(CustomResourcePack customResourcePack) throws IOException {

    }

    @Override
    public void beforeResourceInstallation(CustomResourcePack customPack) throws IOException {
/*        Map<String, NamespacedKey> textureNamesToKeyMapping = new HashMap<>();
        textures.forEach((s, customResourcePackAsset) -> {
            NamespacedKey assetKey = new NamespacedKey(key().namespace(), "block/" + key().value() + "/" + s);
            customPack.register(new AssetBasedResourcePackResource(assetKey, customResourcePackAsset, ResourcePackAssetTypes.TEXTURES, "png"));
            textureNamesToKeyMapping.put(s, assetKey);
        });
        ItemTextureData.ModelType modifiedModelType = ItemTextureData.ModelType.modifyExistingModelType(modelType, (namespacedKey, jsonObject) -> {
            JsonObjectBuilder.create(jsonObject).getOrCreateJsonObject("textures", jsonObjectBuilder -> {
                textureNamesToKeyMapping.forEach((s, namespacedKey1) -> jsonObjectBuilder.add(s, namespacedKey1.toString()));
            });
        });
        ItemTextureData itemTextureData = new ItemTextureData(getKeyOfFullDisplay(key()), getModelMaterial(), drawNewModelID(), null, modifiedModelType);
        customPack.register(itemTextureData);
        customPack.register(new AlternateBlockStateModel(BukkitAdapter.wrap(hitBox.getBlockData()), getKeyOfFullDisplay(key())));*/
    }

    public static class BlockStateVisuals {
        private final Map<String, Asset<CustomResourcePack>> textures = new HashMap<>();
        private ItemTextureData.ModelType modelType;

        public BlockStateVisuals withTexture(String textureName, Asset<CustomResourcePack> textureAsset) {
            textures.put(textureName, textureAsset);
            return this;
        }

        public BlockStateVisuals withModel(ModelBuilder builder) {
            this.modelType = modelType;
            return this;
        }
    }
}
