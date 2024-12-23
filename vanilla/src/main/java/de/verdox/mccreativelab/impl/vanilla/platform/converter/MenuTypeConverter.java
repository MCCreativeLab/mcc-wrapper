package de.verdox.mccreativelab.impl.vanilla.platform.converter;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.inventory.MCCMenuType;
import de.verdox.mccreativelab.wrapper.inventory.MCCMenuTypes;
import net.minecraft.world.inventory.MenuType;

public class MenuTypeConverter implements MCCConverter<MenuType, MCCMenuType> {
    @Override
    public ConversionResult<MCCMenuType> wrap(MenuType nativeType) {
        if (nativeType.equals(MenuType.ANVIL)) {
            return done(MCCMenuTypes.ANVIL);
        } else if (nativeType.equals(MenuType.GENERIC_9x1)) {
            return done(MCCMenuTypes.GENERIC_9x1);
        } else if (nativeType.equals(MenuType.GENERIC_9x2)) {
            return done(MCCMenuTypes.GENERIC_9x2);
        } else if (nativeType.equals(MenuType.GENERIC_9x3)) {
            return done(MCCMenuTypes.GENERIC_9x3);
        } else if (nativeType.equals(MenuType.GENERIC_9x4)) {
            return done(MCCMenuTypes.GENERIC_9x4);
        } else if (nativeType.equals(MenuType.GENERIC_9x5)) {
            return done(MCCMenuTypes.GENERIC_9x5);
        } else if (nativeType.equals(MenuType.GENERIC_9x6)) {
            return done(MCCMenuTypes.GENERIC_9x6);
        } else if (nativeType.equals(MenuType.GENERIC_3x3)) {
            return done(MCCMenuTypes.GENERIC_3x3);
        } else if (nativeType.equals(MenuType.CRAFTER_3x3)) {
            return done(MCCMenuTypes.CRAFTER_3x3);
        } else if (nativeType.equals(MenuType.BEACON)) {
            return done(MCCMenuTypes.BEACON);
        } else if (nativeType.equals(MenuType.BLAST_FURNACE)) {
            return done(MCCMenuTypes.BLAST_FURNACE);
        } else if (nativeType.equals(MenuType.BREWING_STAND)) {
            return done(MCCMenuTypes.BREWING_STAND);
        } else if (nativeType.equals(MenuType.CRAFTING)) {
            return done(MCCMenuTypes.CRAFTING);
        } else if (nativeType.equals(MenuType.ENCHANTMENT)) {
            return done(MCCMenuTypes.ENCHANTMENT);
        } else if (nativeType.equals(MenuType.FURNACE)) {
            return done(MCCMenuTypes.FURNACE);
        } else if (nativeType.equals(MenuType.GRINDSTONE)) {
            return done(MCCMenuTypes.GRINDSTONE);
        } else if (nativeType.equals(MenuType.HOPPER)) {
            return done(MCCMenuTypes.HOPPER);
        } else if (nativeType.equals(MenuType.LECTERN)) {
            return done(MCCMenuTypes.LECTERN);
        } else if (nativeType.equals(MenuType.LOOM)) {
            return done(MCCMenuTypes.LOOM);
        } else if (nativeType.equals(MenuType.MERCHANT)) {
            return done(MCCMenuTypes.MERCHANT);
        } else if (nativeType.equals(MenuType.SHULKER_BOX)) {
            return done(MCCMenuTypes.SHULKER_BOX);
        } else if (nativeType.equals(MenuType.SMITHING)) {
            return done(MCCMenuTypes.SMITHING);
        } else if (nativeType.equals(MenuType.SMOKER)) {
            return done(MCCMenuTypes.SMOKER);
        } else if (nativeType.equals(MenuType.CARTOGRAPHY_TABLE)) {
            return done(MCCMenuTypes.CARTOGRAPHY_TABLE);
        } else if (nativeType.equals(MenuType.STONECUTTER)) {
            return done(MCCMenuTypes.STONECUTTER);
        }

        return null;

    }

    @Override
    public ConversionResult<MenuType> unwrap(MCCMenuType platformImplType) {
        if (platformImplType.equals(MCCMenuTypes.ANVIL)) {
            return done(MenuType.ANVIL);
        } else if (platformImplType.equals(MCCMenuTypes.GENERIC_9x1)) {
            return done(MenuType.GENERIC_9x1);
        } else if (platformImplType.equals(MCCMenuTypes.GENERIC_9x2)) {
            return done(MenuType.GENERIC_9x2);
        } else if (platformImplType.equals(MCCMenuTypes.GENERIC_9x3)) {
            return done(MenuType.GENERIC_9x3);
        } else if (platformImplType.equals(MCCMenuTypes.GENERIC_9x4)) {
            return done(MenuType.GENERIC_9x4);
        } else if (platformImplType.equals(MCCMenuTypes.GENERIC_9x5)) {
            return done(MenuType.GENERIC_9x5);
        } else if (platformImplType.equals(MCCMenuTypes.GENERIC_9x6)) {
            return done(MenuType.GENERIC_9x6);
        } else if (platformImplType.equals(MCCMenuTypes.GENERIC_3x3)) {
            return done(MenuType.GENERIC_3x3);
        } else if (platformImplType.equals(MCCMenuTypes.CRAFTER_3x3)) {
            return done(MenuType.CRAFTER_3x3);
        } else if (platformImplType.equals(MCCMenuTypes.BEACON)) {
            return done(MenuType.BEACON);
        } else if (platformImplType.equals(MCCMenuTypes.BLAST_FURNACE)) {
            return done(MenuType.BLAST_FURNACE);
        } else if (platformImplType.equals(MCCMenuTypes.BREWING_STAND)) {
            return done(MenuType.BREWING_STAND);
        } else if (platformImplType.equals(MCCMenuTypes.CRAFTING)) {
            return done(MenuType.CRAFTING);
        } else if (platformImplType.equals(MCCMenuTypes.ENCHANTMENT)) {
            return done(MenuType.ENCHANTMENT);
        } else if (platformImplType.equals(MCCMenuTypes.FURNACE)) {
            return done(MenuType.FURNACE);
        } else if (platformImplType.equals(MCCMenuTypes.GRINDSTONE)) {
            return done(MenuType.GRINDSTONE);
        } else if (platformImplType.equals(MCCMenuTypes.HOPPER)) {
            return done(MenuType.HOPPER);
        } else if (platformImplType.equals(MCCMenuTypes.LECTERN)) {
            return done(MenuType.LECTERN);
        } else if (platformImplType.equals(MCCMenuTypes.LOOM)) {
            return done(MenuType.LOOM);
        } else if (platformImplType.equals(MCCMenuTypes.MERCHANT)) {
            return done(MenuType.MERCHANT);
        } else if (platformImplType.equals(MCCMenuTypes.SHULKER_BOX)) {
            return done(MenuType.SHULKER_BOX);
        } else if (platformImplType.equals(MCCMenuTypes.SMITHING)) {
            return done(MenuType.SMITHING);
        } else if (platformImplType.equals(MCCMenuTypes.SMOKER)) {
            return done(MenuType.SMOKER);
        } else if (platformImplType.equals(MCCMenuTypes.CARTOGRAPHY_TABLE)) {
            return done(MenuType.CARTOGRAPHY_TABLE);
        } else if (platformImplType.equals(MCCMenuTypes.STONECUTTER)) {
            return done(MenuType.STONECUTTER);
        }

        return null;
    }

    @Override
    public Class<MCCMenuType> apiImplementationClass() {
        return MCCMenuType.class;
    }

    @Override
    public Class<MenuType> nativeMinecraftType() {
        return MenuType.class;
    }
}
