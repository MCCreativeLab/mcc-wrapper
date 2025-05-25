package de.verdox.mccreativelab.wrapper.block;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.misc.MCCNoteBlockInstrument;
import de.verdox.mccreativelab.wrapper.misc.MCCPushReaction;

/**
 * Represents properties of a block typ
 */
public interface MCCBlockProperties extends MCCWrapped {
    /**
     * Whether this block is randomly ticking
     */
    boolean isRandomlyTicking();

    /**
     * Whether this block requires a correct tool for drops
     */
    boolean requiresCorrectToolForDrops();

    /**
     * The block hardness (defaults to 1.0)
     */
    float getBlockHardness();

    /**
     * The note block instrument of this block
     */
    MCCNoteBlockInstrument instrument();

    /**
     * The sound type of this block
     * @return the sound type
     */
    MCCBlockSoundGroup soundType();

    /**
     * The explosion resistance of this block
     */
    float explosionResistance();

    /**
     * The friction of this block
     */
    float friction();

    /**
     * The speed factor of this block
     */
    float speedFactor();

    /**
     * The jump boost factor of this block
     */
    float jumpFactor();

    /**
     * Whether this block is ignitable by lava
     */
    boolean ignitedByLava();

    /**
     * Whether this block is replaceable
     */
    boolean replaceable();

    /**
     * The push piston reaction of this block
     * @return
     */
    MCCPushReaction pushReaction();
}
