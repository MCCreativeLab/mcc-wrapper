package de.verdox.mccreativelab.impl.vanilla.platform;

import de.verdox.mccreativelab.wrapper.platform.MCCLifecycleTrigger;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class NMSLifecycleTrigger implements MCCLifecycleTrigger {
    private final NMSPlatform platform;

    public NMSLifecycleTrigger(NMSPlatform platform) {
        this.platform = platform;
    }

    @Override
    public void bootstrap() {

    }

    @Override
    public void beforeWorldLoad() {
        LOGGER.info("Freezing custom registries");
        MCCPlatform.getInstance().getRegistryStorage().freezeCustomRegistries();
        //initBlockCache();
    }

    @Override
    public void afterWorldLoad() {

    }

    @Override
    public void onServerStartupComplete() {
        platform.getResourcePackManager().buildPack();
        LOGGER.info("Platform startup complete");
    }

    /**
     * Used to rebuild the block state caches since we changed some attributes of blocks with custom block proxies
     */
    private static void initBlockCache() {
        for (Block block : BuiltInRegistries.BLOCK) {
            for (BlockState blockState : block.getStateDefinition().getPossibleStates()) {
                blockState.initCache();
            }
        }
    }
}
