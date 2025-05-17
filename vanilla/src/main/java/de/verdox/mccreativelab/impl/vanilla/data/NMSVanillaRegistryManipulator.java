package de.verdox.mccreativelab.impl.vanilla.data;

import de.verdox.mccreativelab.data.MCCVanillaRegistryManipulator;

import java.util.LinkedList;
import java.util.List;

public class NMSVanillaRegistryManipulator implements MCCVanillaRegistryManipulator {
    public static List<Runnable> CUSTOM_BOOTSTRAPPERS = new LinkedList<>();

    @Override
    public void injectRegistryManipulationCode(Runnable runnable) {
        CUSTOM_BOOTSTRAPPERS.add(runnable);
    }
}