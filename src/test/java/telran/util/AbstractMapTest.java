package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public abstract class AbstractMapTest {
    Map<Integer, String> map;
    Integer[] array = { 3, -10, 20, 1, 10, 8, 100, 17 };

    void setUp() {
        Arrays.stream(array).forEach(i -> map.put(i, addValue(i)));
    }

    private String addValue(Integer i) {
        return i % 2 == 0 ? "even" : "odd";
    }

    abstract <T> void runTest(T[] expected, T[] actual);

    @Test
    void getTest() {
        assertEquals(addValue(array[0]), map.get(array[0]));
        assertEquals(addValue(array[1]), map.get(array[1]));
        assertEquals(addValue(array[7]), map.get(array[7]));
        assertNull(map.get(200));
    }

    @Test
    void putTest() {
        Integer key = 200;
        String val = "hello world";
        map.put(key, val);
        assertEquals(val, map.get(key));
        String newVal = addValue(key);
        assertEquals(val, map.put(key, newVal));
        assertEquals(newVal, map.get(key));
    }

    @Test
    void containsKeyTrueTest() {
        assertTrue(map.containsKey(array[0]));
        assertTrue(map.containsKey(array[1]));
    }

    @Test
    void containsKeyFalseTest() {
        assertFalse(map.containsKey(200));
        assertFalse(map.containsKey(1000));   
    }

    @Test
    void containsValueTrueTest() {
        assertTrue(map.containsValue(addValue(array[0])));
        assertTrue(map.containsValue(addValue(array[1])));
    }
    
    @Test
    void containsValueFalseTest() {
        assertFalse(map.containsValue("hello world"));
        assertFalse(map.containsValue("go, Diego!"));
    }

    @Test
        void sizeTest() {
        assertEquals(array.length, map.size());
    }

    @Test
        void isEmptyTest() {
        assertFalse(map.isEmpty());
    }

    @Test
    void keySetTest() {
        Integer[] keys = map.keySet().stream().toArray(Integer[]::new);
        runTest(array, keys);
    }

    @Test
    void valuesTest() {
        String[] expected = Arrays.stream(array).map(this::addValue).toArray(String[]::new);
        String[] values = map.values().stream().toArray(String[]::new);
        runTest(expected, values);
    }
}