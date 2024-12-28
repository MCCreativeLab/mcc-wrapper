package de.verdox.mccreativelab;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class Singleton<T> {
    private static final Logger LOGGER = Logger.getLogger(Singleton.class.getSimpleName());

    private final Class<T> type;
    private T singleton;

    public Singleton(Class<T> type) {
        this.type = type;
    }

    public void setup(T singleton, Consumer<T> callback) {
        if (this.singleton != null)
            throw new IllegalStateException("The singleton " + type + " (" + this + ") is already set up");
        this.singleton = singleton;
        callback.accept(this.singleton);
        LOGGER.info("Setting up singleton " + type + " (" + this + ")");
    }

    public T get() {
        if (this.singleton == null)
            throw new IllegalStateException("The singleton " + type + " (" + this + ") was not setup by the running server platform. Please provide a valid implementation by using the setup function.");
        return singleton;
    }

    public boolean isSetup() {
        return this.singleton != null;
    }
}
