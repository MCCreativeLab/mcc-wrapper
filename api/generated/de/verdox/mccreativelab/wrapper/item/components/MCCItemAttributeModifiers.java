package de.verdox.mccreativelab.wrapper.item.components;

import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlot;
import de.verdox.mccreativelab.wrapper.entity.MCCEquipmentSlotGroup;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.item.MCCAttributeModifier;
import de.verdox.mccreativelab.wrapper.types.MCCAttribute;

import java.util.List;
import java.lang.Object;

public interface MCCItemAttributeModifiers {
    public MCCItemAttributeModifiers.Builder createBuilder();

    public MCCItemAttributeModifiers.Entry createEntry();

    public List<MCCItemAttributeModifiers.Entry> getModifiers();

    public MCCItemAttributeModifiers withModifiers(List<MCCItemAttributeModifiers.Entry> modifiers);

    public boolean getShowInTooltip();

    public MCCItemAttributeModifiers withShowInTooltip(boolean showInTooltip);

    public static interface Entry {
        public MCCReference<MCCAttribute> getAttribute();

        public MCCItemAttributeModifiers.Entry withAttribute(MCCReference<MCCAttribute> attribute);

        public MCCAttributeModifier getModifier();

        public MCCItemAttributeModifiers.Entry withModifier(MCCAttributeModifier modifier);

        public MCCEquipmentSlotGroup getSlot();

        public MCCItemAttributeModifiers.Entry withSlot(MCCEquipmentSlotGroup slot);
    }

    public static interface Builder {
    }
}