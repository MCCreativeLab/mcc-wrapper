package de.verdox.mccreativelab.wrapper.event.player;

import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import java.util.Locale;
import java.lang.String;

/**
 *  Called when a player changes their locale in the client settings.
 */

public class MCCPlayerLocaleChangeEvent extends MCCPlayerEvent  {

	private final String locale;

	private final Locale adventure$locale;

	public MCCPlayerLocaleChangeEvent(MCCEntity player, String locale, Locale adventure$locale){
		super(player);
		this.locale = locale;
		this.adventure$locale = adventure$locale;
	}

	/**
	 *      @return the player's new locale
	 *      @see Player#getLocale()
	 *      @deprecated in favour of {@link #locale()}
	 */
	public String getLocale(){
		return locale;
	}

	/**
	 *      @see Player#locale()
	 *      *
	 *      @return the player's new locale
	 */
	public Locale locale(){
		return adventure$locale;
	}

}
