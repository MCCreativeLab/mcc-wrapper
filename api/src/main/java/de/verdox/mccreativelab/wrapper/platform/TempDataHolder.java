package de.verdox.mccreativelab.wrapper.platform;

import de.verdox.mccreativelab.wrapper.platform.cached.TempData;

/**
 * An element that can hold temporary data that is not saved persistently
 */
public interface TempDataHolder {
    /**
     * Returns the temp data container
     * @return the temp data container
     */
    TempData getTempData();
}
