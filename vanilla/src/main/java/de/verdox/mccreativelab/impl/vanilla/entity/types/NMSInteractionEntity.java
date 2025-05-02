package de.verdox.mccreativelab.impl.vanilla.entity.types;

import com.google.common.reflect.TypeToken;
import com.mojang.math.Transformation;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.entity.NMSEntity;
import de.verdox.mccreativelab.impl.vanilla.util.field.EntityDataField;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.entity.types.MCCInteractionEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCLivingEntity;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.util.VanillaField;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Interaction;
import org.jetbrains.annotations.Nullable;

public class NMSInteractionEntity extends NMSEntity<Interaction> implements MCCInteractionEntity {
    private static final EntityDataAccessor<Float> DATA_WIDTH_ID = ReflectionUtils.readStaticFieldFromClass(Interaction.class, "DATA_WIDTH_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Float> DATA_HEIGHT_ID = ReflectionUtils.readStaticFieldFromClass(Interaction.class, "DATA_HEIGHT_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Boolean> DATA_RESPONSE_ID = ReflectionUtils.readStaticFieldFromClass(Interaction.class, "DATA_RESPONSE_ID", new TypeToken<>() {});
    public static final MCCConverter<Interaction, NMSInteractionEntity> CONVERTER = converter(NMSInteractionEntity.class, Interaction.class, NMSInteractionEntity::new, MCCHandle::getHandle);


    public NMSInteractionEntity(Interaction handle) {
        super(handle);
    }

    @Override
    public VanillaField<Float> width() {
        return new EntityDataField<>(this::getHandle, DATA_WIDTH_ID);
    }

    @Override
    public VanillaField<Float> height() {
        return new EntityDataField<>(this::getHandle, DATA_HEIGHT_ID);
    }

    @Override
    public VanillaField<Boolean> response() {
        return new EntityDataField<>(this::getHandle, DATA_RESPONSE_ID);
    }

    @Override
    public @Nullable MCCLivingEntity getLastAttacker() {
        return conversionService.wrap(handle.getLastAttacker(), MCCLivingEntity.class);
    }

    @Override
    public @Nullable MCCLivingEntity getLastInteraction() {
        return conversionService.wrap(handle.getTarget(), MCCLivingEntity.class);
    }
}
