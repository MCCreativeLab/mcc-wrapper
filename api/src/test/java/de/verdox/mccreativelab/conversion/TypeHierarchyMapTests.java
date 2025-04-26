package de.verdox.mccreativelab.conversion;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TypeHierarchyMapTests {

    /**
     * Tests the basic functionality of the put and get methods.
     * <p>
     * The test adds two entries to the map: "Number" for the class `Number.class` and "Integer" for `Integer.class`.
     * It then verifies that the correct values are returned when these classes are queried.
     * </p>
     * Expected outcomes:
     * - `map.get(Number.class)` should return "Number".
     * - `map.get(Integer.class)` should return "Integer".
     */
    @Test
    void testPutAndGet() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");
        map.put(Integer.class, "Integer");

        assertEquals("Number", map.get(Number.class));
        assertEquals("Integer", map.get(Integer.class));
    }

    /**
     * Tests the entries of the map by printing them out.
     * <p>
     * This test adds two entries to the map and prints each entry. It is mostly useful for manual inspection
     * and debugging, as no assertions are made here.
     * </p>
     */
    @Test
    void test() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");
        map.put(Integer.class, "Integer");

        for (Map.Entry<Class<?>, String> classStringEntry : map.entrySet()) {
            System.out.println(classStringEntry);
        }
    }

    /**
     * Tests the resolution of the hierarchy and inheritance in the map.
     * <p>
     * This test ensures that the map resolves the correct hierarchy. It checks that the class `Double`,
     * which extends `Number`, is resolved to the value associated with `Number.class`. It also verifies
     * that the direct match for `Integer.class` is correct.
     * </p>
     * Expected outcomes:
     * - `map.get(Double.class)` should return "Number" (because Double extends Number).
     * - `map.get(Integer.class)` should return "Integer".
     */
    @Test
    void testHierarchyResolution() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");
        map.put(Integer.class, "Integer");

        assertEquals("Number", map.get(Double.class)); // Double extends Number
        assertEquals("Integer", map.get(Integer.class));
    }

    /**
     * Tests that the value associated with a key can be updated.
     * <p>
     * The test verifies that calling `put` with the same key multiple times will override the existing value.
     * After putting `"Number"` for `Number.class`, it is overridden by `"Updated Number"`.
     * </p>
     * Expected outcome:
     * - `map.get(Number.class)` should return "Updated Number" after the override.
     */
    @Test
    void testPutOverride() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");
        map.put(Number.class, "Updated Number");

        assertEquals("Updated Number", map.get(Number.class));
    }

    /**
     * Tests if the `containsKey` method works correctly.
     * <p>
     * This test checks if the map correctly identifies whether it contains a key. It verifies both direct
     * and indirect containment (assignability) for `Number.class` and `Integer.class`.
     * </p>
     * Expected outcomes:
     * - `map.containsKey(Number.class)` and `map.containsKey(Integer.class)` should return true.
     * - `map.containsKey(Double.class)` should return false (Double is assignable from Number, but not directly present).
     * - `map.containsKey(String.class)` should return false.
     */
    @Test
    void testContainsKey() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");
        map.put(Integer.class, "Integer");

        assertTrue(map.containsKey(Number.class));
        assertTrue(map.containsKey(Integer.class));
        assertFalse(map.containsKey(Double.class)); // Double is assignable from Number
        assertFalse(map.containsKey(String.class));
    }

    /**
     * Tests the removal of a key from the map.
     * <p>
     * This test ensures that when a key is removed (in this case, `Integer.class`), the other values in the map
     * remain unaffected. It verifies that `Number.class` remains in the map after removing `Integer.class`.
     * </p>
     * Expected outcome:
     * - After removing `Integer.class`, the map should still return "Number" for `Number.class`.
     * - `Integer.class` should no longer exist in the map.
     */
    @Test
    void testRemove() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");
        map.put(Integer.class, "Integer");

        map.remove(Integer.class);
        assertEquals("Number",map.get(Integer.class));
        assertEquals("Number", map.get(Number.class));
    }

    /**
     * Tests the removal of a non-existent key.
     * <p>
     * This test ensures that attempting to remove a key that does not exist in the map will return `null`
     * instead of throwing an exception.
     * </p>
     * Expected outcome:
     * - `map.remove(String.class)` should return `null` because `String.class` is not in the map.
     */
    @Test
    void testRemoveNonExistentKey() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");

        assertNull(map.remove(String.class)); // String is not in the map
    }

    /**
     * Tests the `keySet` method to ensure it returns the correct set of keys.
     * <p>
     * This test checks if the `keySet` method returns a set that contains the keys for all inserted classes.
     * It verifies that classes not in the map (e.g., `String.class`) are not included.
     * </p>
     * Expected outcomes:
     * - `map.keySet()` should contain `Number.class` and `Integer.class`.
     * - `map.keySet()` should not contain `String.class`.
     */
    @Test
    void testKeySet() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");
        map.put(Integer.class, "Integer");

        assertTrue(map.keySet().contains(Number.class));
        assertTrue(map.keySet().contains(Integer.class));
        assertFalse(map.keySet().contains(String.class));
    }

    /**
     * Tests the `values` method to ensure it returns the correct set of values.
     * <p>
     * This test checks if the `values` method returns a collection of all values inserted into the map.
     * It verifies that classes not present in the map (e.g., `String.class`) do not appear in the values collection.
     * </p>
     * Expected outcomes:
     * - `map.values()` should contain "Number" and "Integer".
     * - `map.values()` should not contain "String".
     */
    @Test
    void testValues() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");
        map.put(Integer.class, "Integer");

        assertTrue(map.values().contains("Number"));
        assertTrue(map.values().contains("Integer"));
        assertFalse(map.values().contains("String"));
    }

    /**
     * Tests the `entrySet` method to ensure it returns the correct set of entries.
     * <p>
     * This test ensures that the `entrySet` method correctly returns the key-value pairs in the map.
     * It verifies that the size of the entry set corresponds to the number of entries in the map.
     * </p>
     * Expected outcome:
     * - `map.entrySet().size()` should return 2 if two entries are added.
     */
    @Test
    void testEntrySet() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");
        map.put(Integer.class, "Integer");

        assertEquals(2, map.entrySet().size());
    }

    /**
     * Tests the `size` method to ensure it returns the correct number of entries in the map.
     * <p>
     * This test ensures that the `size` method accurately reflects the number of entries in the map.
     * </p>
     * Expected outcome:
     * - `map.size()` should return 2 after inserting two entries.
     */
    @Test
    void testSize() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");
        map.put(Integer.class, "Integer");

        assertEquals(2, map.size());
    }

    /**
     * Tests the `isEmpty` method to check if the map is empty.
     * <p>
     * This test checks that the `isEmpty` method correctly identifies an empty map and a non-empty map.
     * </p>
     * Expected outcomes:
     * - The map should be empty initially.
     * - After adding an entry, the map should no longer be empty.
     */
    @Test
    void testIsEmpty() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();

        assertTrue(map.isEmpty());

        map.put(Number.class, "Number");
        assertFalse(map.isEmpty());
    }

    /**
     * Tests the `clear` method to ensure that it empties the map.
     * <p>
     * This test checks that after calling `clear()`, the map is empty, and all keys return `null`.
     * </p>
     * Expected outcome:
     * - `map.isEmpty()` should return `true` after calling `clear()`.
     * - `map.get(Number.class)` and `map.get(Integer.class)` should return `null` after clearing the map.
     */
    @Test
    void testClear() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");
        map.put(Integer.class, "Integer");

        map.clear();
        assertTrue(map.isEmpty());
        assertNull(map.get(Number.class));
        assertNull(map.get(Integer.class));
    }

    /**
     * Tests the `putAll` method to ensure multiple entries can be added from another map.
     * <p>
     * This test ensures that the `putAll` method works correctly by adding entries from another map
     * into the `TypeHierarchyMap`.
     * </p>
     * Expected outcomes:
     * - After calling `putAll`, `map.get(Number.class)` should return "Number".
     * - The `map.get(Integer.class)` test is commented out due to an identified issue.
     */
    @Test
    void testPutAll() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        Map<Class<?>, String> otherMap = Map.of(Number.class, "Number", Integer.class, "Integer");

        map.putAll(otherMap);

        assertEquals("Number", map.get(Number.class));
        //assertEquals("Integer", map.get(Integer.class)); //TODO: Fix?
    }

    /**
     * Tests the behavior when trying to add an invalid key.
     * <p>
     * This test ensures that attempting to use `Object.class` as a key in the map will throw an `IllegalArgumentException`.
     * </p>
     * Expected outcome:
     * - The exception message should be "Cannot use Object.class as a key".
     */
    @Test
    void testInvalidPutKey() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> map.put(Object.class, "Root"));
        assertEquals("Cannot use Object.class as a key", exception.getMessage());
    }

    /**
     * Tests the behavior when trying to remove an invalid key.
     * <p>
     * This test ensures that attempting to remove `Object.class` from the map will throw an `IllegalArgumentException`.
     * </p>
     * Expected outcome:
     * - The exception message should be "Cannot remove Object.class from the hierarchy".
     */
    @Test
    void testInvalidRemoveKey() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> map.remove(Object.class));
        assertEquals("Cannot remove Object.class from the hierarchy", exception.getMessage());
    }

    /**
     * Tests the cache invalidation behavior after an insert.
     * <p>
     * This test ensures that when a new value is inserted, the cache for `Integer.class` is invalidated,
     * and the map returns the updated value.
     * </p>
     * Expected outcome:
     * - `map.get(Integer.class)` should return the updated value after inserting a new value for `Integer.class`.
     */
    @Test
    void testCacheInvalidationAfterInsert() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");

        // Initial Lookup => cached
        assertEquals("Number", map.get(Integer.class));

        // Must invalidate cache
        map.put(Integer.class, "Integer");

        assertEquals("Integer", map.get(Integer.class));
    }

    /**
     * Tests if reorganizing the hierarchy shifts the children correctly.
     * <p>
     * This test ensures that after a new entry is added to the hierarchy, the children are reorganized properly.
     * </p>
     * Expected outcome:
     * - After adding `Double.class`, it should return "Double" for `Double.class`,
     * - and retain "Integer" for `Integer.class` and "Number" for `Number.class`.
     */
    @Test
    void testReorganizationShiftsChildren() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");
        map.put(Integer.class, "Integer");

        map.put(Double.class, "Double");

        assertEquals("Double", map.get(Double.class));
        assertEquals("Integer", map.get(Integer.class));
        assertEquals("Number", map.get(Number.class));
    }

    /**
     * Tests the method that checks if a relative class exists in the hierarchy.
     * <p>
     * This test ensures that the method `canFindRelative` correctly identifies whether a class is in the hierarchy.
     * </p>
     * Expected outcomes:
     * - `canFindRelative(Integer.class)` should return true.
     * - `canFindRelative(Number.class)` should return true.
     * - `canFindRelative(String.class)` should return false.
     */
    @Test
    void testCanFindRelative() {
        TypeHierarchyMap<String> map = new TypeHierarchyMap<>();
        map.put(Number.class, "Number");

        assertTrue(map.canFindRelative(Integer.class));
        assertTrue(map.canFindRelative(Number.class));
        assertFalse(map.canFindRelative(String.class));
    }
}

