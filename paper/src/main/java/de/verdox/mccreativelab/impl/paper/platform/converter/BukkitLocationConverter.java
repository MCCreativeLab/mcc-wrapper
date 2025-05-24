package de.verdox.mccreativelab.impl.paper.platform.converter;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;

class BukkitLocationConverter implements MCCConverter<Location, MCCLocation> {
    @Override
    public ConversionResult<MCCLocation> wrap(Location nativeType) {
        CraftWorld world = (CraftWorld) nativeType.getWorld();
        return done(new MCCLocation(BukkitAdapter.toMcc(world), nativeType.x(), nativeType.y(), nativeType.z(), nativeType.getYaw(), nativeType.getPitch()));
    }

    @Override
    public ConversionResult<Location> unwrap(MCCLocation platformImplType) {
        org.bukkit.entity.Entity livingEntity;

        return done(new Location(BukkitAdapter.toBukkit(platformImplType.world()), platformImplType.x(), platformImplType.y(), platformImplType.z(), platformImplType.yaw(), platformImplType.pitch()));
    }

    @Override
    public Class<MCCLocation> apiImplementationClass() {
        return MCCLocation.class;
    }

    @Override
    public Class<Location> nativeMinecraftType() {
        return Location.class;
    }
}
