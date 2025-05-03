package de.verdox.mccreativelab.impl.vanilla.mixins.proxy;

import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.impl.vanilla.mixins.proxy.util.*;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

public interface GameProxy extends Proxy0, Proxy1, Proxy2, Proxy3, Proxy4, Proxy5, Proxy6, Proxy7, Proxy8 {
    default ConversionService conversionService() {
        return MCCPlatform.getInstance().getConversionService();
    }
}
