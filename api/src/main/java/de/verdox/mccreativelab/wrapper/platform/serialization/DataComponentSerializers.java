package de.verdox.mccreativelab.wrapper.platform.serialization;

import de.verdox.mccreativelab.wrapper.item.components.*;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.vserializer.generic.Serializer;
import de.verdox.vserializer.generic.SerializerBuilder;

import java.util.ArrayList;

public interface DataComponentSerializers {
    Serializer<MCCUnbreakable> UNBREAKABLE = prepare(MCCUnbreakable.class)
            .withBuilderField("showInTooltip", Serializer.Primitive.BOOLEAN, MCCUnbreakable::getShowInTooltip, MCCUnbreakable::withShowInTooltip)
            .build();

    Serializer<MCCItemLore> ITEM_LORE = prepare(MCCItemLore.class)
            .withBuilderField("lines", Serializer.Collection.create(MCCSerializers.COMPONENT, ArrayList::new), MCCItemLore::getLines, MCCItemLore::withLines)
            .build();

    Serializer<MCCCustomModelData> CUSTOM_MODEL_DATA = prepare(MCCCustomModelData.class)
            .withBuilderField("floats", Serializer.Collection.create(Serializer.Primitive.FLOAT, ArrayList::new), MCCCustomModelData::getFloats, MCCCustomModelData::withFloats)
            .withBuilderField("strings", Serializer.Collection.create(Serializer.Primitive.STRING, ArrayList::new), MCCCustomModelData::getStrings, MCCCustomModelData::withStrings)
            .withBuilderField("colors", Serializer.Collection.create(Serializer.Primitive.INTEGER, ArrayList::new), MCCCustomModelData::getColors, MCCCustomModelData::withColors)
            .withBuilderField("flags", Serializer.Collection.create(Serializer.Primitive.BOOLEAN, ArrayList::new), MCCCustomModelData::getFlags, MCCCustomModelData::withFlags)
            .build();

    Serializer<MCCFoodProperties> FOOD_PROPERTIES = prepare(MCCFoodProperties.class)
            .withBuilderField("nutrition", Serializer.Primitive.INTEGER, MCCFoodProperties::getNutrition, MCCFoodProperties::withNutrition)
            .withBuilderField("saturation", Serializer.Primitive.FLOAT, MCCFoodProperties::getSaturation, MCCFoodProperties::withSaturation)
            .withBuilderField("canAlwaysEat", Serializer.Primitive.BOOLEAN, MCCFoodProperties::getCanAlwaysEat, MCCFoodProperties::withCanAlwaysEat)
            .build();

    Serializer<MCCUseRemainder> USE_REMAINDER = prepare(MCCUseRemainder.class)
            .withBuilderField("convertInto", MCCSerializers.ITEM_STACK, MCCUseRemainder::getConvertInto, MCCUseRemainder::withConvertInto)
            .build();

    Serializer<MCCUseCooldown> USE_COOLDOWN = prepare(MCCUseCooldown.class)
            .withBuilderField("useSeconds", Serializer.Primitive.FLOAT, MCCUseCooldown::getSeconds, MCCUseCooldown::withSeconds)
            .withBuilderField("cooldownGroups", Serializer.Optional.create(MCCSerializers.KEY), MCCUseCooldown::getCooldownGroup, MCCUseCooldown::withCooldownGroup)
            .build();

    private static <T extends MCCItemComponent> SerializerBuilder<T> prepare(Class<T> type) {
        return SerializerBuilder.create(type.getSimpleName(), type)
                .constructor(() -> MCCPlatform.getInstance().getElementFactory().createEmptyComponent(type));
    }
}
