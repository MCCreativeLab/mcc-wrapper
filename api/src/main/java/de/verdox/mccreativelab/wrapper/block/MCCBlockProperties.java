package de.verdox.mccreativelab.wrapper.block;

import de.verdox.mccreativelab.wrapper.MCCWrapped;

public interface MCCBlockProperties extends MCCWrapped {

    boolean isRandomlyTicking();

    boolean requiresCorrectToolForDrops();

    float getBlockHardness();

    NoteBlockInstrument instrument();

    MCCBlockSoundGroup soundType();

    float explosionResistance();

    float friction();

    float speedFactor();

    float jumpFactor();

    boolean ignitedByLava();

    boolean replaceable();

    PushReaction pushReaction();
}
