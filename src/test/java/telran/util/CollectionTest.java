package telran.util;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public abstract class CollectionTest {
    protected Collection<Integer> collection;
    Integer[] array = {3, -10, 0, 1, 10, 8, 100, 17};

    void setUp() {
        Arrays.stream(array).forEach(collection::add); 
    }

    @Test
    void addTest() {
        collection.add(200);
        collection.add(17);
        assertEquals(array.length + 2, collection.size());
    }
    @Test
    void removeTest() {
        assertEquals(collection, array);
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
        while (!collection.isEmpty()) {
            collection.remove(collection.iterator().next());
        } 
        assertTrue(collection.isEmpty());
    }
}
