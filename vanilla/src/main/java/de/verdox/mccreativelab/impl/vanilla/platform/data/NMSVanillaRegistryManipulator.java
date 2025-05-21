package de.verdox.mccreativelab.impl.vanilla.platform.data;

import de.verdox.mccreativelab.wrapper.platform.data.MCCVanillaRegistryManipulator;

import java.util.LinkedList;
import java.util.List;

public class NMSVanillaRegistryManipulator implements MCCVanillaRegistryManipulator {
    public static List<Runnable> CUSTOM_BOOTSTRAPPERS = new LinkedList<>();

    @Override
    public void injectRegistryManipulationCode(Runnable runnable) {
        CUSTOM_BOOTSTRAPPERS.add(runnable);
    }
}