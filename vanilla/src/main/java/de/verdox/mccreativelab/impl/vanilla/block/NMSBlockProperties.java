package de.verdox.mccreativelab.impl.vanilla.block;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.block.MCCBlockProperties;
import de.verdox.mccreativelab.wrapper.block.MCCBlockSoundGroup;
import de.verdox.mccreativelab.wrapper.misc.MCCNoteBlockInstrument;
import de.verdox.mccreativelab.wrapper.misc.MCCPushReaction;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class NMSBlockProperties extends MCCHandle<BlockBehaviour.Properties> implements MCCBlockProperties {
    public static final MCCConverter<BlockBehaviour.Properties, NMSBlockProperties> CONVERTER = converter(NMSBlockProperties.class, BlockBehaviour.Properties.class, NMSBlockProperties::new, MCCHandle::getHandle);

    public NMSBlockProperties(BlockBehaviour.Properties handle) {
        super(handle);
    }

    @Override
    @MCCReflective
    public boolean isRandomlyTicking() {
        return ReflectionUtils.readFieldFromClass(handle, "isRandomlyTicking", new TypeToken<>() {});
    }

    @Override
    @MCCReflective
    public boolean requiresCorrectToolForDrops() {
        return ReflectionUtils.readFieldFromClass(handle, "requiresCorrectToolForDrops", new TypeToken<>() {});
    }

    @Override
    @MCCReflective
    public float getBlockHardness() {
        return ReflectionUtils.readFieldFromClass(handle, "destroyTime", new TypeToken<>() {});
    }

    @Override
    @MCCReflective
    public MCCNoteBlockInstrument instrument() {
        return conversionService.wrap(ReflectionUtils.readFieldFromClass(handle, "instrument", new TypeToken<>() {}), new TypeToken<>() {});
    }

    @Override
    @MCCReflective
    public MCCBlockSoundGroup soundType() {
        return conversionService.wrap(ReflectionUtils.readFieldFromClass(handle, "soundType", new TypeToken<>() {}), new TypeToken<>() {});
    }

    @Override
    @MCCReflective
    public float explosionResistance() {
        return ReflectionUtils.readFieldFromClass(handle, "explosionResistance", new TypeToken<>() {});
    }

    @Override
    @MCCReflective
    public float friction() {
        return ReflectionUtils.readFieldFromClass(handle, "friction", new TypeToken<>() {});
    }

    @Override
    @MCCReflective
    public float speedFactor() {
        return ReflectionUtils.readFieldFromClass(handle, "speedFactor", new TypeToken<>() {});
    }

    @Override
    @MCCReflective
    public float jumpFactor() {
        return ReflectionUtils.readFieldFromClass(handle, "jumpFactor", new TypeToken<>() {});
    }

    @Override
    @MCCReflective
    public boolean ignitedByLava() {
        return ReflectionUtils.readFieldFromClass(handle, "ignitedByLava", new TypeToken<>() {});
    }

    @Override
    @MCCReflective
    public boolean replaceable() {
        return ReflectionUtils.readFieldFromClass(handle, "replaceable", new TypeToken<>() {});
    }

    @Override
    @MCCReflective
    public MCCPushReaction pushReaction() {
        return conversionService.wrap(ReflectionUtils.readFieldFromClass(handle, "pushReaction", new TypeToken<>() {}),  new TypeToken<>() {});
    }
}
