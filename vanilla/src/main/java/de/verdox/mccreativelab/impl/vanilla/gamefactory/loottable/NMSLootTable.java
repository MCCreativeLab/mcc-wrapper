package de.verdox.mccreativelab.impl.vanilla.gamefactory.loottable;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.gamefactory.loottable.MCCLootTable;
import net.minecraft.world.level.storage.loot.LootTable;

public class NMSLootTable extends MCCHandle<LootTable> implements MCCLootTable {
    public static final MCCConverter<LootTable, NMSLootTable> CONVERTER = converter(NMSLootTable.class, LootTable.class, NMSLootTable::new, MCCHandle::getHandle);

    public NMSLootTable(LootTable handle) {
        super(handle);
    }
}
