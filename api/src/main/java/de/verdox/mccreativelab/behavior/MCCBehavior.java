package de.verdox.mccreativelab.behavior;

import org.jetbrains.annotations.NotNull;

public interface MCCBehavior {
    @NotNull
    default MCCBehaviorResult.Void voidResult(@NotNull MCCBehaviorResult.Void.Type type) {
        return new MCCBehaviorResult.Void(type);
    }

    @NotNull
    default MCCBehaviorResult.Void voidResult() {
        return voidResult(MCCBehaviorResult.Void.Type.REPLACE_VANILLA);
    }

    @NotNull
    default MCCBehaviorResult.Callback done() {
        return new MCCBehaviorResult.Callback();
    }

    @NotNull
    default MCCBehaviorResult.Bool bool(boolean value, @NotNull MCCBehaviorResult.Bool.Type type) {
        return new MCCBehaviorResult.Bool(value, type);
    }

    @NotNull
    default MCCBehaviorResult.Bool bool(boolean value) {
        return bool(value, MCCBehaviorResult.Bool.Type.REPLACE_VANILLA);
    }

    @NotNull
    default <T> MCCBehaviorResult.Object<T> result(@NotNull T result, @NotNull MCCBehaviorResult.Object.Type type){
        return new MCCBehaviorResult.Object<>(result, type);
    }

    @NotNull
    default <T> MCCBehaviorResult.Object<T> result(@NotNull T result){
        return result(result, MCCBehaviorResult.Object.Type.REPLACE_VANILLA);
    }
}