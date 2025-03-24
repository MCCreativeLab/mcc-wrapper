package de.verdox.mccreativelab.impl.paper.events;

import de.verdox.mccreativelab.wrapper.event.server.MCCServerListPingEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerEvents implements Listener {
    // without MCCServerEvent

    @EventHandler
    public void handle(ServerListPingEvent event) {
        MCCServerListPingEvent mccServerListPingEvent = new MCCServerListPingEvent(
                event.getHostname(),
                event.getAddress(),
                event.motd(),
                event.getNumPlayers(),
                event.getMaxPlayers()
        );

        mccServerListPingEvent.callEvent();
    }
}
