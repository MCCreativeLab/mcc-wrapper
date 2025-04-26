package de.verdox.mccreativelab.wrapper.util;

/**
 * Used as a wrapper to access fields of vanilla objects to reduce boiler plate
 * @param <FIELD_TYPE> the api field type
 */
public abstract class VanillaField<FIELD_TYPE> {
    /**
     * Sets the vield value
     * @param value the new value
     */
    public abstract void set(FIELD_TYPE newValue);

    /**
     * @return the field value
     */
    public abstract FIELD_TYPE get();
}
