package telran.util;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public abstract class CollectionTest {
    private static final int N_ELEMENTS = 2_000_000;
    protected Collection<Integer> collection;
    Random random = new Random();
    Integer[] array = {3, -10, 0, 1, 10, 8, 100, 17};
    
    void setUp() {
        Arrays.stream(array).forEach(collection::add); 
    }

    protected void runTest(Integer[] expected) {
        assertArrayEquals(expected, collection.stream().toArray(Integer[]::new));
        assertEquals(expected.length, collection.size());
    }

    @Test
    void addNonExsistingTest() {
        assertTrue(collection.add(200));
        assertEquals(array.length + 1, collection.size());
        runTest(new Integer[]{3, -10, 20, 1, 10, 8, 100 , 17, 200});
    }

    @Test
    void addExsistingTest() {
        assertTrue(collection.add(17));
        runTest(new Integer[]{3, -10, 20, 1, 10, 8, 100 , 17, 17});
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
        assertThrowsExactly(IllegalStateException.class, () -> it.remove());
        while(it.hasNext()) {
                actual[index++] = it.next();
        }
        assertArrayEquals(array, actual);
        assertThrowsExactly(NoSuchElementException.class, it::next );
    }
    @Test
    void removeInIteratorTest() {
        Iterator<Integer> it = collection.iterator();
        assertThrowsExactly(IllegalStateException.class, () -> it.remove());
        Integer n = it.next();
        it.remove();
        it.next();
        it.next();
        it.remove();;
        assertFalse(collection.contains(n));
        assertThrows(IllegalStateException.class, () -> it.remove());
    }

    @Test
    void removeIfTest() {
        assertTrue(collection.removeIf(n -> n % 2 == 0));   //removing even numbers 
        assertFalse(collection.removeIf(n -> n % 2 == 0));
        assertTrue(collection.stream().allMatch(n -> n % 2 != 0)); 
    }
    
    @Test
    void clearTest () {
        collection.clear();
        assertTrue(collection.isEmpty());
    }
    //TODO see all required parameters in 
    @Test
    void perfomanceTest() {
        collection.clear();
        IntStream.range(0, N_ELEMENTS).forEach(i -> collection.add(random.nextInt()));
        collection.removeIf(n -> n % 2 == 0);
        assertTrue(collection.stream().allMatch(n -> n % 2 != 0));
        collection.clear();
        assertTrue(collection.isEmpty());
    }
}