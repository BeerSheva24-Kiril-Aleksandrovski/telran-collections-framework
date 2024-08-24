package telran.util;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public abstract class CollectionTest {
    protected Collection<Integer> collection;
    Integer[] array = {3, -10, 0, 1, 10, 8, 100, 17};

    void setUp() {
        Arrays.stream(array).forEach(collection::add); 
    }

    protected void runTest(Integer[] expected) {
        assertArrayEquals(expected, collection.stream().toArray(Integer[]::new));
        assertEquals(expected.length, collection.size());
    }
    
    @Test
    void addTest() {
        assertTrue(collection.add(200));
        assertTrue(collection.add(17));
        assertEquals(array.length + 2, collection.size());
    }
    @Test
    void removeTest() {
        assertTrue(collection.remove(100));
        assertTrue(collection.remove(17));
        assertFalse(collection.remove(12));
        assertEquals(array.length - 2, collection.size());
    }

    @Test
    void sizeTest() {
        assertEquals(array.length, collection.size());
    }
    
    @Test
    void containsTestTrue() {
        assertTrue(collection.contains(-10));
        assertTrue(collection.contains(10));
        assertTrue(collection.contains(17));  
    }

    @Test
    void containsTestFalse() {
        assertFalse(collection.contains(-104));
        assertFalse(collection.contains(130));
        assertFalse(collection.contains(111));  
    }

    @Test
    void isEmptyTest() {
        assertFalse(collection.isEmpty());
        clear(); 
        assertTrue(collection.isEmpty());
    }

    private void clear() {
        Arrays.stream(array).forEach(n -> collection.remove(n));
    }

    @Test
    void iteratorTest() {
        Integer[] actual = new Integer[array.length];
        int index = 0;
        Iterator<Integer> it = collection.iterator();
        while(it.hasNext()) {
            actual[index++] = it.next();
        }
        assertArrayEquals(array, actual);
        assertThrowsExactly(NoSuchElementException.class, it::next );
    }
}
