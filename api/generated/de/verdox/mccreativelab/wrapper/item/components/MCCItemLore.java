package de.verdox.mccreativelab.wrapper.item.components;

import net.kyori.adventure.text.Component;

import java.util.List;

public interface MCCItemLore extends MCCItemComponent  {

	public List<Component> getLines();

	public MCCItemLore withLines(List<Component> lines);

	public List<Component> getStyledLines();

	public MCCItemLore withStyledLines(List<Component> styledLines);

}
