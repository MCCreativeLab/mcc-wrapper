package de.verdox.mccreativelab.behaviour;

import org.jetbrains.annotations.NotNull;

public interface MCCBehaviour {
    @NotNull
    default MCCBehaviourResult.Void voidResult(@NotNull MCCBehaviourResult.Void.Type type) {
        return new MCCBehaviourResult.Void(type);
    }

    @NotNull
    default MCCBehaviourResult.Void voidResult() {
        return voidResult(MCCBehaviourResult.Void.Type.REPLACE_VANILLA);
    }

    @NotNull
    default MCCBehaviourResult.Callback done() {
        return new MCCBehaviourResult.Callback();
    }

    @NotNull
    default MCCBehaviourResult.Bool bool(boolean value, @NotNull MCCBehaviourResult.Bool.Type type) {
        return new MCCBehaviourResult.Bool(value, type);
    }

    @NotNull
    default MCCBehaviourResult.Bool bool(boolean value) {
        return bool(value, MCCBehaviourResult.Bool.Type.REPLACE_VANILLA);
    }

    @NotNull
    default <T> MCCBehaviourResult.Object<T> result(@NotNull T result, @NotNull MCCBehaviourResult.Object.Type type){
        return new MCCBehaviourResult.Object<>(result, type);
    }

    @NotNull
    default <T> MCCBehaviourResult.Object<T> result(@NotNull T result){
        return result(result, MCCBehaviourResult.Object.Type.REPLACE_VANILLA);
    }
}