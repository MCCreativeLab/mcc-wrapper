package de.verdox.mccreativelab.gametest;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class GameTest<R> {
    public static <R> GameTest<R> create(String name, String category, TypeToken<R> typeToken) {
        return new GameTest<>(name, category);
    }

    private Runnable setup = () -> {
    };
    private Runnable teardown = () -> {
    };

    private final String name;
    private final String category;
    private final List<Condition<? extends R>> conditions = new ArrayList<>();
    private Supplier<CompletableFuture<? extends R>> codeRunner = () -> CompletableFuture.completedFuture(null);
    private long timeout = 50;
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private String failureMessage;

    private GameTest(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public GameTest<R> withAsyncCode(Supplier<CompletableFuture<? extends R>> codeRunner) {
        this.codeRunner = codeRunner;
        return this;
    }

    public GameTest<R> setup(Runnable setup) {
        this.setup = setup;
        return this;
    }

    public GameTest<R> teardown(Runnable teardown) {
        this.teardown = teardown;
        return this;
    }

    public GameTest<R> withSyncCode(Supplier<? extends R> codeRunner) {
        return withAsyncCode(() -> CompletableFuture.completedFuture(codeRunner.get()));
    }

    public GameTest<R> timeout(long timeout, TimeUnit timeUnit) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        return this;
    }

    public GameTest<R> addPostCondition(Predicate<R> condition, String message) {
        this.conditions.add(new Condition<>(condition, message));
        return this;
    }

    public CompletableFuture<GameTestResult<R>> runTest() {
        GameTestResult<R> gameTestResult = new GameTestResult<>(this);
        setup.run();

        try {
            var test = this.codeRunner.get()
                    .thenApply(r -> {
                        for (Condition<R> condition : conditions) {
                            try {
                                var testResult = condition.condition().test(r);
                                gameTestResult.result(condition, testResult, null);
                            } catch (Throwable e) {
                                gameTestResult.result(condition, false, e);
                            }
                        }
                        return r;
                    })
                    .orTimeout(timeout, timeUnit)
                    .whenComplete((unused, throwable) -> {
                        if (throwable != null) {
                            gameTestResult.throwableInGameCode(throwable);
                        }
                        teardown.run();
                    });
            return CompletableFuture.allOf(test).thenApply(unused -> gameTestResult);
        } catch (Throwable throwable) {
            return CompletableFuture.completedFuture(gameTestResult.throwableInGameCode(throwable));
        }
    }

    public static class GameTestResult<R> {
        private final GameTest<R> gameTest;
        private final Map<Condition<R>, ConditionResult> results = new HashMap<>();
        private Throwable throwableInGameCode;

        private GameTestResult(GameTest<R> gameTest) {
            this.gameTest = gameTest;
        }

        public GameTestResult<R> result(Condition<R> condition, boolean result, Throwable throwable) {
            results.put(condition, new ConditionResult(result, throwable));
            return this;
        }

        public GameTestResult<R> throwableInGameCode(Throwable throwable) {
            this.throwableInGameCode = throwable;
            return this;
        }

        public GameTest<R> getGameTest() {
            return gameTest;
        }
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public long getTimeout() {
        return timeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public record Condition<R>(Predicate<? extends R> condition, String failureMessage) {}

    public record ConditionResult(boolean result, Throwable exception) {}
}
