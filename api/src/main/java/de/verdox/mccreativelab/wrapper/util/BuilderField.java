package de.verdox.mccreativelab.wrapper.util;

/**
 * A helper class for immutable types that use the builder pattern to create a new state
 * @param <FIELD_TYPE> the field type
 * @param <OWNER> the owner of the builder field
 */
public abstract class BuilderField<FIELD_TYPE, OWNER> {
    protected final OWNER owner;

    public BuilderField(OWNER owner) {
        this.owner = owner;
    }

    public abstract OWNER with(FIELD_TYPE value);

    public abstract FIELD_TYPE get();
}
