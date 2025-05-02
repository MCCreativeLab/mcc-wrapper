package de.verdox.mccreativelab.wrapper.platform.cached;

import de.verdox.mccreativelab.wrapper.util.GCHookMap;
public class TempCache {
    private static final GCHookMap<Object, TempData> weak = new GCHookMap<>();

    public static TempData get(Object o){
        if(!weak.containsKey(o)){
            weak.put(o, new TempData());
        }
        return weak.get(o);
    }
}
