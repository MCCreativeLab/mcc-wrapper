package de.verdox.mccreativelab.impl.vanilla.item;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.item.components.NMSDataComponentMap;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.types.MCCEnchantment;
import net.kyori.adventure.text.Component;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class NMSItemStack extends MCCHandle<ItemStack> implements MCCItemStack {
    public static final MCCConverter<ItemStack, NMSItemStack> CONVERTER = converter(NMSItemStack.class, ItemStack.class, NMSItemStack::new, MCCHandle::getHandle);

    public NMSItemStack(ItemStack handle) {
        super(handle);
    }

    @Override
    public MCCDataComponentMap components() {
        return new NMSDataComponentMap(handle.getComponents());
    }

    @Override
    public boolean hasDataComponentType(MCCDataComponentType<?> type) {
        return handle.has(conversionService.unwrap(type, new TypeToken<>() {}));
    }

    @Override
    public int getAmount() {
        return handle.getCount();
    }

    @Override
    public void setAmount(int amount) {
        handle.setCount(amount);
    }

    @Override
    public int getMaxStackSize() {
        return getHandle().getMaxStackSize();
    }

    @Override
    public boolean isSimilar(MCCItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (stack == this) {
            return true;
        }
        if(!(stack instanceof NMSItemStack that)){
            return false;
        }
        if (this.handle == that.handle) {
            return true;
        }
        if (this.handle == null || that.handle == null) {
            return false;
        }
        return net.minecraft.world.item.ItemStack.isSameItemSameComponents(this.handle, that.handle);

    }

    @Override
    public MCCItemStack withAmount(int amount) {
        var copy1 = this.handle.copy();
        copy1.setCount(amount);

        return new NMSItemStack(copy1);
    }

    @Override
    public Component name() {
        return MCCPlatform.getInstance().getConversionService().wrap(handle.get(DataComponents.ITEM_NAME), new TypeToken<>() {});
    }

    @Override
    public void name(Component name) {
        handle.set(DataComponents.ITEM_NAME, MCCPlatform.getInstance().getConversionService().unwrap(name, net.minecraft.network.chat.Component.class));
    }

    @Override
    public Component customName() {
        return MCCPlatform.getInstance().getConversionService().wrap(handle.get(DataComponents.CUSTOM_NAME), new TypeToken<>() {});
    }

    @Override
    public void customName(Component name) {
        handle.set(DataComponents.CUSTOM_NAME, MCCPlatform.getInstance().getConversionService().unwrap(name, net.minecraft.network.chat.Component.class));
    }

    @Override
    public MCCItemType getType() {
        return new NMSItemType(this.handle.getItem());
    }

    @Override
    public MCCItemStack copy() {
        return new NMSItemStack(this.handle.copy());
    }

    @Override
    public boolean isCorrectToolForDrops(MCCBlockState blockState) {
        BlockState nmsBlockState = conversionService.unwrap(blockState, new TypeToken<>() {});
        return handle.isCorrectToolForDrops(nmsBlockState);
    }

    @Override
    public float getDestroySpeed(MCCBlockState blockState) {
        BlockState nmsBlockState = conversionService.unwrap(blockState, new TypeToken<>() {});
        return handle.getDestroySpeed(nmsBlockState);
    }

    @Override
    public boolean isEmpty() {
        return getHandle().isEmpty();
    }

    @Override
    public int getEnchantmentLevel(MCCEnchantment mccEnchantment) {
        return getHandle().getEnchantments().getLevel(
                Holder.direct(MCCPlatform.getInstance().getConversionService().unwrap(mccEnchantment, new TypeToken<>() {})) // TODO: check if this is the direct way to get the holder of the enchantment
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NMSItemStack that = (NMSItemStack) o;
        if (this.handle == that.handle) return true;
        else if (this.handle == null || that.handle == null) return false;
        else if (this.handle.isEmpty() && that.handle.isEmpty()) return true;
        else return net.minecraft.world.item.ItemStack.matches(this.handle, that.handle);
    }

    @Override
    public int hashCode() {
        if (this.handle == null || this.handle.isEmpty()) {
            return net.minecraft.world.item.ItemStack.EMPTY.hashCode();
        } else {
            int hash = net.minecraft.world.item.ItemStack.hashItemAndComponents(this.handle);
            hash = hash * 31 + this.handle.getCount();
            return hash;
        }
    }
}
