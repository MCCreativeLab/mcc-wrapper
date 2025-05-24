package de.verdox.mccreativelab.impl.vanilla.entity.types;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.entity.NMSEntity;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCProjectileEntity;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.entity.projectile.Projectile;
import org.jetbrains.annotations.Nullable;

public class NMSProjectileEntity extends NMSEntity<Projectile> implements MCCProjectileEntity {
    public static final MCCConverter<Projectile, NMSProjectileEntity> CONVERTER = MCCHandle.converter(NMSProjectileEntity.class, Projectile.class, NMSProjectileEntity::new, MCCHandle::getHandle);

    public NMSProjectileEntity(Projectile handle) {
        super(handle);
    }

    @Override
    public void setOwner(@Nullable MCCEntity owner) {
        handle.setOwner(conversionService.unwrap(owner));
    }

    @Override
    public @Nullable MCCEntity getOwner() {
        return conversionService.wrap(handle.getOwner(), new TypeToken<>() {});
    }

    @Override
    public @Nullable MCCEntity getEffectSource() {
        return conversionService.wrap(handle.getEffectSource(), new TypeToken<>() {});
    }
}
