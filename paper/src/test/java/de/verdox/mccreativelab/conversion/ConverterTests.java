package de.verdox.mccreativelab.conversion;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.TestBase;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.Test;

public class ConverterTests extends TestBase {

    @Test
    public void testCraftConverters(){
        ItemStack nmsStack = new ItemStack(Items.STONE);

        MCCItemStack mccItemStack = MCCPlatform.getInstance().getConversionService().wrap(nmsStack, new TypeToken<>() {});
        org.bukkit.inventory.ItemStack toBukkit = MCCPlatform.getInstance().getConversionService().unwrap(mccItemStack, new TypeToken<>() {});
    }

}
