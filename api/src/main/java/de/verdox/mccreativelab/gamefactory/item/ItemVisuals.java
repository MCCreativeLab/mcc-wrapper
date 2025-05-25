package de.verdox.mccreativelab.gamefactory.item;

import de.verdox.mccreativelab.generator.Asset;
import de.verdox.mccreativelab.generator.resourcepack.CustomResourcePack;
import de.verdox.mccreativelab.generator.resourcepack.types.ItemTextureData;
import de.verdox.mccreativelab.generator.resourcepack.types.lang.Translatable;
import de.verdox.mccreativelab.wrapper.annotations.MCCRequireVanillaElement;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.typed.MCCItems;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class ItemVisuals {
    @Nullable
    private Asset<CustomResourcePack> pngFile;
    @Nullable
    private ItemTextureData.ModelType modelType = ItemTextureData.ModelType.GENERATED_ITEM;
    private MCCReference<MCCItemType> mccReference = MCCItems.STICK;
    private Consumer<Translatable> nameSetup = translatable -> {
    };
    private boolean sealed;

    public ItemVisuals withTexture(@Nullable Asset<CustomResourcePack> pngFile) {
        if (sealed) {
            throw new IllegalStateException("ItemVisuals sealed");
        }
        this.pngFile = pngFile;
        return this;
    }

    public ItemVisuals withDisguisedVanillaItem(@MCCRequireVanillaElement MCCReference<MCCItemType> mccReference) {
        if (sealed) {
            throw new IllegalStateException("ItemVisuals sealed");
        }
        mccReference.requireVanilla();
        this.mccReference = mccReference;
        return this;
    }

    public ItemVisuals withModelType(ItemTextureData.ModelType modelType) {
        if (sealed) {
            throw new IllegalStateException("ItemVisuals sealed");
        }
        this.modelType = modelType;
        return this;
    }

    public ItemVisuals withName(Consumer<Translatable> translatableConsumer) {
        if (sealed) {
            throw new IllegalStateException("ItemVisuals sealed");
        }
        Objects.requireNonNull(translatableConsumer);
        this.nameSetup = translatableConsumer;
        return this;
    }

    public Translatable getName(Key key) {
        Translatable translatable = new Translatable("item." + key.namespace() + "." + key.value());
        nameSetup.accept(translatable);
        return translatable;
    }

    public ItemTextureData asTextureData(Key key) {
        return new ItemTextureData(key, mccReference.get(), pngFile, modelType, false);
    }

    void seal() {
        this.sealed = true;
    }
}
