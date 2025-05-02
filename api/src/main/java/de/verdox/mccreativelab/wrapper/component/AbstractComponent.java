package de.verdox.mccreativelab.wrapper.component;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

public class AbstractComponent<NATIVE_OWNER, API_OWNER> implements GameComponent<API_OWNER> {
    private final API_OWNER apiOwner;
    protected final NATIVE_OWNER handle;
    protected final ConversionService conversionService = MCCPlatform.getInstance().getConversionService();

    public AbstractComponent(API_OWNER apiOwner, TypeToken<NATIVE_OWNER> apiType) {
        this.apiOwner = apiOwner;
        this.handle = MCCPlatform.getInstance().getConversionService().unwrap(apiOwner, apiType);
    }

    @Override
    public API_OWNER getOwner() {
        return apiOwner;
    }

    public NATIVE_OWNER getHandle() {
        return handle;
    }
}
