package de.verdox.mccreativelab.wrapper.misc;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.entity.player.MCCInteractionHand;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import org.jetbrains.annotations.Nullable;

public interface MCCUseOnContext extends MCCWrapped {
    /**
     * @return the involved player
     */
    @Nullable MCCPlayer getPlayer();

    /**
     * @return the involved hand
     */
    MCCInteractionHand getHand();

    /**
     * @return the hit
     */
    MCCBlockHitResult getHit();

    /**
     * @return the world
     */
    MCCWorld getWorld();

    /**
     * @return the item stack
     */
    MCCItemStack getItemStack();
}
