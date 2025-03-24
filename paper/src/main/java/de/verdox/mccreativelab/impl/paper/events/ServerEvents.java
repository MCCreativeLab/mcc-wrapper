package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.server.MCCServerListPingEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerEvents extends EventBase {
    // TODO: without MCCServerEvent

    @EventHandler(ignoreCancelled = true)
    public void handle(ServerListPingEvent event) {
        callEvent(event, new MCCServerListPingEvent(
                event.getHostname(),
                event.getAddress(),
                event.motd(),
                event.getNumPlayers(),
                event.getMaxPlayers()
        ));
    }
}
