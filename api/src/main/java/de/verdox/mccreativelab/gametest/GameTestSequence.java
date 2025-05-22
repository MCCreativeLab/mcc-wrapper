package de.verdox.mccreativelab.gametest;

import java.util.function.Consumer;

public class GameTestSequence {

    public static GameTestSequence create() {
        return new GameTestSequence();
    }

    private GameTestSequence() {

    }

    public <R> GameTestSequence withTest(GameTest<R> test) {
        return this;
    }
}
