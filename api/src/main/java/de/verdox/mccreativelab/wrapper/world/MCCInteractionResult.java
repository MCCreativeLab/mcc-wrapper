package de.verdox.mccreativelab.wrapper.world;

/**
 * Represents an interaction result of a player with the game world
 */
public class MCCInteractionResult {
    public static final MCCInteractionResult SUCCESS = new MCCInteractionResult();
    public static final MCCInteractionResult SUCCESS_SERVER = new MCCInteractionResult();
    public static final MCCInteractionResult CONSUME = new MCCInteractionResult();
    public static final MCCInteractionResult FAIL = new MCCInteractionResult();
    public static final MCCInteractionResult PASS = new MCCInteractionResult();
    public static final MCCInteractionResult TRY_WITH_EMPTY_HAND = new MCCInteractionResult();

    private MCCInteractionResult() {}
}
