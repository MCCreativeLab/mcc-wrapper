package de.verdox.mccreativelab.impl.vanilla.types;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlotGroup;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.registry.MCCReferenceSet;
import de.verdox.mccreativelab.wrapper.types.MCCEnchantment;
import net.kyori.adventure.text.Component;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;
import java.util.Optional;

public class NMSEnchantment extends MCCHandle<Enchantment> implements MCCEnchantment {
    public static final MCCConverter<Enchantment, NMSEnchantment> CONVERTER = converter(NMSEnchantment.class, Enchantment.class, NMSEnchantment::new, MCCHandle::getHandle);

    public NMSEnchantment(Enchantment handle) {
        super(handle);
    }

    @Override
    public Component getDescription() {
        return conversionService.wrap(handle.description());
    }

    @Override
    public MCCDataComponentMap getEffects() {
        return conversionService.wrap(handle.effects());
    }

    @Override
    public MCCReferenceSet<MCCEnchantment> getExclusiveSet() {
        return conversionService.wrap(handle.exclusiveSet());
    }

    @Override
    public Definition getDefinition() {
        return conversionService.wrap(handle.definition());
    }

    public static class NMSDefinition extends MCCHandle<Enchantment.EnchantmentDefinition> implements Definition {
        public static final MCCConverter<Enchantment.EnchantmentDefinition, NMSDefinition> CONVERTER = converter(NMSDefinition.class, Enchantment.EnchantmentDefinition.class, NMSDefinition::new, MCCHandle::getHandle);

        public NMSDefinition(Enchantment.EnchantmentDefinition handle) {
            super(handle);
        }

        @Override
        public MCCReferenceSet<MCCItemType> getSupportedItems() {
            return conversionService.wrap(handle.supportedItems());
        }

        @Override
        public Optional<MCCReferenceSet<MCCItemType>> getPrimaryItems() {
            return conversionService.wrap(handle.primaryItems());
        }

        @Override
        public int getWeight() {
            return handle.weight();
        }

        @Override
        public int getMaxLevel() {
            return handle.maxLevel();
        }

        @Override
        public Cost getMinCost() {
            return conversionService.wrap(handle.minCost());
        }

        @Override
        public Cost getMaxCost() {
            return conversionService.wrap(handle.maxCost());
        }

        @Override
        public int getAnvilCost() {
            return handle.anvilCost();
        }

        @Override
        public List<MCCEquipmentSlotGroup> getSlots() {
            return conversionService.wrap(handle.slots());
        }
    }

    public static class NMSCost extends MCCHandle<Enchantment.Cost> implements Cost {
        public static final MCCConverter<Enchantment.Cost, NMSCost> CONVERTER = converter(NMSCost.class, Enchantment.Cost.class, NMSCost::new, MCCHandle::getHandle);

        public NMSCost(Enchantment.Cost handle) {
            super(handle);
        }

        @Override
        public int getBase() {
            return handle.base();
        }

        @Override
        public int getPerLevelAboveFirst() {
            return handle.perLevelAboveFirst();
        }
    }
}
