package de.verdox.mccreativelab.wrapper.item.components;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.types.MCCLootTable;

/**
 * Defines the loot table present in a container item.
 */
public interface MCCSeededContainerLoot extends MCCItemComponent {
	public MCCTypedKey<MCCLootTable> getLootTable();

	public MCCSeededContainerLoot withLootTable(MCCTypedKey<MCCLootTable> lootTable);

	public long getSeed();

	public MCCSeededContainerLoot withSeed(long seed);
}