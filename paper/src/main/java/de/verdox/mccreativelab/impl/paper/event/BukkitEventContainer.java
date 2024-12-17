package de.verdox.mccreativelab.impl.paper.event;

import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class BukkitEventContainer<T extends Event> {

    public static void main(String[] args) {
        BlockBreakEvent blockBreakEvent = null;
        MCCPlayer player = BukkitEventContainer.of(blockBreakEvent).get(BlockBreakEvent::getPlayer);
    }

    private final T event;

    public static <T extends Event> BukkitEventContainer<T> of(T event) {
        return new BukkitEventContainer<>(event);
    }

    private BukkitEventContainer(T event) {
        this.event = event;
    }

    public <S1, S2> void set(BiConsumer<T, S1> setter, S2 data) {
        try {
            S1 dataToSet = (S1) data;
            setter.accept(event, dataToSet);
        } catch (ClassCastException e) {
            setter.accept(event, BukkitAdapter.unwrap(data));
        }
    }

    public <R1, R2> R2 get(Function<T, R1> getter) {
        R1 result = getter.apply(event);
        try {
            return (R2) result;
        } catch (ClassCastException e) {
            return BukkitAdapter.unwrap(result);
        }
    }
}
