package de.verdox.mccreativelab.wrapper.event.server;

import java.net.InetAddress;
import net.kyori.adventure.text.Component;
import java.lang.String;

/**
 *  Called when a server list ping is coming in. Displayed players can be
 *  checked and removed by {@link #iterator() iterating} over this event.
 *  <br>
 *  <b>Note:</b> The players in {@link #iterator()} will not be shown in the
 *  server info if {@link Bukkit#getHideOnlinePlayers()} is true.
 */

public class MCCServerListPingEvent extends MCCServerEvent  {

	private final String hostname;

	private final InetAddress address;

	private Component motd;

	private final int numPlayers;

	private int maxPlayers;

	public MCCServerListPingEvent(String hostname, InetAddress address, Component motd, int numPlayers, int maxPlayers){
		this.hostname = hostname;
		this.address = address;
		this.motd = motd;
		this.numPlayers = numPlayers;
		this.maxPlayers = maxPlayers;
	}

	/**
	 *      Gets the hostname that the player used to connect to the server, or
	 *      blank if unknown
	 *      *
	 *      @return The hostname
	 */
	public String getHostname(){
		return hostname;
	}

	/**
	 *      Get the address the ping is coming from.
	 *      *
	 *      @return the address
	 */
	public InetAddress getAddress(){
		return address;
	}

	/**
	 *      Get the message of the day message.
	 *      *
	 *      @return the message of the day
	 *      @deprecated in favour of {@link #motd()}
	 */
	public Component getMotd(){
		return motd;
	}

	/**
	 *      Change the message of the day message.
	 *      *
	 *      @param motd the message of the day
	 */
	public void motd(Component motd){
		this.motd = motd;
	}

	/**
	 *      Get the number of players sent.
	 *      *
	 *      @return the number of players
	 */
	public int getNumPlayers(){
		return numPlayers;
	}

	/**
	 *      Get the maximum number of players sent.
	 *      *
	 *      @return the maximum number of players
	 */
	public int getMaxPlayers(){
		return maxPlayers;
	}

	/**
	 *      Set the maximum number of players sent.
	 *      *
	 *      @param maxPlayers the maximum number of player
	 */
	public void setMaxPlayers(int maxPlayers){
		this.maxPlayers = maxPlayers;
	}

}
