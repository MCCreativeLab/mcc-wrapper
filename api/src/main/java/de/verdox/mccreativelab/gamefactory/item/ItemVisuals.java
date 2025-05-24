package de.verdox.mccreativelab.gamefactory.item;

import de.verdox.mccreativelab.generator.Asset;
import de.verdox.mccreativelab.generator.resourcepack.CustomResourcePack;
import de.verdox.mccreativelab.generator.resourcepack.types.ItemTextureData;
import de.verdox.mccreativelab.wrapper.annotations.MCCRequireVanillaElement;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.typed.MCCItems;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;

public class ItemVisuals {
    @Nullable
    private Asset<CustomResourcePack> pngFile;
    @Nullable
    private ItemTextureData.ModelType modelType = ItemTextureData.ModelType.GENERATED_ITEM;
    private MCCReference<MCCItemType> mccReference = MCCItems.STICK;

    public ItemVisuals withTexture(@Nullable Asset<CustomResourcePack> pngFile) {
        this.pngFile = pngFile;
        return this;
    }

    public ItemVisuals withDisguisedVanillaItem(@MCCRequireVanillaElement MCCReference<MCCItemType> mccReference) {
        mccReference.requireVanilla();
        this.mccReference = mccReference;
        return this;
    }

    public ItemVisuals withModelType(ItemTextureData.ModelType modelType) {
        this.modelType = modelType;
        return this;
    }


    public ItemTextureData asTextureData(Key key) {
        return new ItemTextureData(key, mccReference.get(), pngFile, modelType, pngFile == null);
    }
}
