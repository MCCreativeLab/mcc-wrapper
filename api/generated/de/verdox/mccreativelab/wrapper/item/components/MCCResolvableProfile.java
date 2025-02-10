package de.verdox.mccreativelab.wrapper.item.components;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.PropertyMap;

import java.util.Optional;
import java.util.UUID;

public interface MCCResolvableProfile extends MCCItemComponent  {

	public Optional<String> getName();

	public MCCResolvableProfile withName(Optional<String> name);

	public Optional<UUID> getId();

	public MCCResolvableProfile withId(Optional<UUID> id);

	public PropertyMap getProperties();

	public MCCResolvableProfile withProperties(PropertyMap properties);

	public GameProfile getGameProfile();

	public MCCResolvableProfile withGameProfile(GameProfile gameProfile);

}
