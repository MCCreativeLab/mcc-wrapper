package de.verdox.mccreativelab.impl.vanilla.entity.types;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.entity.NMSEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCMarkerEntity;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.entity.Marker;

public class NMSMarkerEntity extends NMSEntity<Marker> implements MCCMarkerEntity {
    public static final MCCConverter<Marker, NMSMarkerEntity> CONVERTER = MCCHandle.converter(NMSMarkerEntity.class, Marker.class, NMSMarkerEntity::new, nms -> nms.handle);

    public NMSMarkerEntity(Marker handle) {
        super(handle);
    }
}
