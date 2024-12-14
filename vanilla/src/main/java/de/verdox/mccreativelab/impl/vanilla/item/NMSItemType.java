package de.verdox.mccreativelab.impl.vanilla.item;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSDataComponentMap;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NMSItemType extends MCCHandle<Item> implements MCCItemType {
    public static final MCCConverter<Item, NMSItemType> CONVERTER = converter(NMSItemType.class, Item.class, NMSItemType::new, MCCHandle::getHandle);

    public NMSItemType(Item handle) {
        super(handle);
    }

    @Override
    public @NotNull MCCItemStack createItem() {
        return conversionService.wrap(new ItemStack(this.handle), new TypeToken<>() {});
    }

    @Override
    public MCCDataComponentMap getItemStandardComponentMap() {
        return new NMSDataComponentMap(handle.components());
    }

    @Override
    public boolean isEmpty() {
        return handle.equals(Items.AIR);
    }

    @Override
    public @NotNull Key key() {
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(handle);
        return Key.key(resourceLocation.getNamespace(), resourceLocation.getPath());
    }
}
