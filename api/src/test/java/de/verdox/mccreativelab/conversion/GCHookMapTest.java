package de.verdox.mccreativelab.conversion;

import de.verdox.mccreativelab.wrapper.util.GCHookMap;
import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class GCHookMapTest {

    record DummyKey(String name) {

        @Override
        public String toString() {
            return "DummyKey{" + name + '}';
        }
    }


    @Test
    void testEntrySurvivesWhileReferenced() {
        GCHookMap<DummyKey, String> map = new GCHookMap<>();
        DummyKey key = new DummyKey("test1");

        map.put(key, "value");

        assertEquals("value", map.get(key));
    }

    @Test
    void testEntryRemovedAfterGC() throws InterruptedException {
        GCHookMap<DummyKey, String> map = new GCHookMap<>();
        DummyKey key = new DummyKey("test2");
        WeakReference<DummyKey> weakRef = new WeakReference<>(key);

        map.put(key, "value");

        // Dereferenzieren
        key = null;

        // Versuchen, GC auszulösen
        for (int i = 0; i < 10 && weakRef.get() != null; i++) {
            System.gc();
            Thread.sleep(100);
        }

        assertNull(weakRef.get(), "Key sollte vom GC eingesammelt worden sein");

        // Cleanup aufrufen, um verwaiste Einträge zu entfernen
        map.cleanup();

        // Nachprüfen, ob Eintrag entfernt wurde
        assertThrows(NullPointerException.class, () -> map.get(new DummyKey("test2")));
    }

    @Test
    void testCleanupHookIsCalled() throws InterruptedException {
        GCHookMap<DummyKey, String> map = new GCHookMap<>();
        AtomicBoolean cleanupCalled = new AtomicBoolean(false);

        DummyKey key = new DummyKey("test3");
        WeakReference<DummyKey> weakRef = new WeakReference<>(key);

        map.put(key, "value", val -> cleanupCalled.set(true));

        key = null;

        // GC forcieren
        for (int i = 0; i < 10 && weakRef.get() != null; i++) {
            System.gc();
            Thread.sleep(100);
        }

        assertNull(weakRef.get(), "Key sollte vom GC eingesammelt worden sein");

        // Cleanup manuell aufrufen
        map.cleanup();

        assertTrue(cleanupCalled.get(), "Cleanup-Hook sollte aufgerufen worden sein");
    }

    @Test
    void testContainsKey() {
        GCHookMap<DummyKey, String> map = new GCHookMap<>();
        DummyKey key = new DummyKey("test4");

        assertFalse(map.containsKey(key));

        map.put(key, "value");
        assertTrue(map.containsKey(key));

        map.cleanup(); // sollte keinen Einfluss haben, da key noch lebt
        assertTrue(map.containsKey(key));
    }

    @Test
    void testContainsKeyAfterGC() throws InterruptedException {
        GCHookMap<DummyKey, String> map = new GCHookMap<>();
        DummyKey key = new DummyKey("test5");
        WeakReference<DummyKey> weakRef = new WeakReference<>(key);

        map.put(key, "value");
        assertTrue(map.containsKey(key));

        key = null;

        for (int i = 0; i < 10 && weakRef.get() != null; i++) {
            System.gc();
            Thread.sleep(100);
        }

        map.cleanup();
        assertFalse(map.containsKey(new DummyKey("test5"))); // andere Instanz → false
    }
}
