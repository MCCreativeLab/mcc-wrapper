package de.verdox.mccreativelab.gamefactory.block;

import de.verdox.mccreativelab.wrapper.block.MCCBlockSoundGroup;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public class MCCCustomBlockTypeBuilder {
    private MCCBlockSoundGroup soundGroup /* TODO: = MCCBlockSoundGroups.STONE*/;
    private float explosionResistance;
    private float destroyTime;
    private boolean requiresCorrectToolForDrops;
    private boolean isRandomlyTicking;
    private float friction = 0.6f;
    private float speedFactor = 1f;
    private float jumpFactor = 1f;
    private boolean ignitedByLava;
    /*TODO: private MCCPistonPushReaction pushReaction;*/
    /*TODO: MCCNoteBlockInstrument noteBlockInstrument;*/
    private boolean replaceable;
    private int lightEmission;

    public MCCCustomBlockTypeBuilder setSoundGroup(MCCBlockSoundGroup soundGroup) {
        this.soundGroup = soundGroup;
        return this;
    }

    public MCCCustomBlockTypeBuilder getLightEmission(int emission) {
        lightEmission = emission;
        return this;
    }

    public MCCCustomBlockTypeBuilder explosionResistance(float explosionResistance) {
        this.explosionResistance = explosionResistance;
        return this;
    }

    public MCCCustomBlockTypeBuilder destroyTime(float destroyTime) {
        this.destroyTime = destroyTime;
        return this;
    }

    public MCCCustomBlockTypeBuilder requiresCorrectToolForDrops() {
        this.requiresCorrectToolForDrops = true;
        return this;
    }

    public MCCCustomBlockTypeBuilder instabreak() {
        return this.strength(0.0F);
    }

    public MCCCustomBlockTypeBuilder strength(float strength) {
        this.strength(strength, strength);
        return this;
    }

    public MCCCustomBlockTypeBuilder strength(float destroyTime, float explosionResistance) {
        return this.destroyTime(destroyTime).explosionResistance(explosionResistance);
    }

    public MCCCustomBlockTypeBuilder randomTicks() {
        this.isRandomlyTicking = true;
        return this;
    }

    public MCCCustomBlockTypeBuilder friction(float friction) {
        this.friction = friction;
        return this;
    }

    public MCCCustomBlockTypeBuilder speedFactor(float speedFactor) {
        this.speedFactor = speedFactor;
        return this;
    }

    public MCCCustomBlockTypeBuilder jumpFactor(float jumpFactor) {
        this.jumpFactor = jumpFactor;
        return this;
    }

    public MCCCustomBlockTypeBuilder ignitedByLava() {
        this.ignitedByLava = true;
        return this;
    }

    public MCCCustomBlockTypeBuilder replaceable() {
        this.replaceable = true;
        return this;
    }
}
