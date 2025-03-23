package de.verdox.mccreativelab.impl.vanilla.types;

import java.util.Optional;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.decoration.PaintingVariant;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.types.MCCPaintingVariant;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.resources.ResourceLocation;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.key.Key;

public class NMSPaintingVariant extends MCCHandle<PaintingVariant> implements MCCPaintingVariant {
    public static final MCCConverter<PaintingVariant, NMSPaintingVariant> CONVERTER = converter(NMSPaintingVariant.class, PaintingVariant.class, NMSPaintingVariant::new, MCCHandle::getHandle);

    public NMSPaintingVariant(PaintingVariant handle) {
        super(handle);
    }

    public int getWidth() {
        var nms = getWidthFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Integer>() {});
    }

    private int getWidthFromImpl() {
        return handle == null ? 0 : handle.width();
    }

    public int getHeight() {
        var nms = getHeightFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Integer>() {});
    }

    private int getHeightFromImpl() {
        return handle == null ? 0 : handle.height();
    }

    public Key getAssetId() {
        var nms = getAssetIdFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Key>() {});
    }

    private ResourceLocation getAssetIdFromImpl() {
        return handle == null ? null : handle.assetId();
    }

    public Optional<Component> getTitle() {
        var nms = getTitleFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Optional<Component>>() {});
    }

    private Optional<net.minecraft.network.chat.Component> getTitleFromImpl() {
        return handle == null ? null : handle.title();
    }

    public Optional<Component> getAuthor() {
        var nms = getAuthorFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Optional<Component>>() {});
    }

    private Optional<net.minecraft.network.chat.Component> getAuthorFromImpl() {
        return handle == null ? null : handle.author();
    }
}