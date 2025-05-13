package de.verdox.mccreativelab;

import org.jetbrains.annotations.NotNull;

public enum MCCItemInteractionResult {
    SUCCESS,
    CONSUME,
    CONSUME_PARTIAL,
    PASS_TO_DEFAULT_BLOCK_INTERACTION,
    SKIP_DEFAULT_BLOCK_INTERACTION,
    FAIL;

    public boolean consumesAction() {
        return this.result().consumesAction();
    }

    @NotNull public static MCCItemInteractionResult sidedSuccess(boolean swingHand) {
        return swingHand ? SUCCESS : CONSUME;
    }

    @NotNull
    public MCCInteractionResult result() {
        return switch (this) {
            case SUCCESS -> MCCInteractionResult.SUCCESS;
            case CONSUME -> MCCInteractionResult.CONSUME;
            case CONSUME_PARTIAL -> MCCInteractionResult.CONSUME_PARTIAL;
            case PASS_TO_DEFAULT_BLOCK_INTERACTION, SKIP_DEFAULT_BLOCK_INTERACTION -> MCCInteractionResult.PASS;
            case FAIL -> MCCInteractionResult.FAIL;
        };
    }
}