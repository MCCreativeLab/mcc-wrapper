package de.verdox.mccreativelab.impl.vanilla.types;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.types.MCCGameEvent;
import net.minecraft.world.level.gameevent.GameEvent;

public class NMSGameEvent extends MCCHandle<GameEvent> implements MCCGameEvent {
    public static final MCCConverter<GameEvent, NMSGameEvent> CONVERTER = converter(NMSGameEvent.class, GameEvent.class, NMSGameEvent::new, MCCHandle::getHandle);

    public NMSGameEvent(GameEvent handle) {
        super(handle);
    }

    @Override
    public int getNotificationRadius() {
        return handle.notificationRadius();
    }
}
