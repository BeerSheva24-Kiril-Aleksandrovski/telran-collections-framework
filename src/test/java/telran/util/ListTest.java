package telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public abstract class ListTest extends CollectionTest {
    List<Integer> list;
    @Override
    void setUp() {
        super.setUp();
        list = (List<Integer>)collection;
    } 
    //Integer[] array = {3, -10, 0, 1, 10, 8, 100, 17};
    @Test
    void addIndexTest() {
        Integer[] expected = {3, -100, -10, 0, 1, null, 10, 8, 100, 17};
        list.add(4, null);
        list.add(1, -100);
        runTest(expected);
        wrongIndicesTest(() -> list.add(list.size() + 1, 1));
        wrongIndicesTest(() -> list.add(-1, 1)); 
    }
    
    @Test
    void removeIndexTest() {
        Integer[] expected = {3, 1, 8, 100, 17};
        list.remove(1);
        list.remove(1);
        list.remove(2);
        runTest(expected);
        wrongIndicesTest(() -> list.remove(list.size() + 1));
        wrongIndicesTest(() -> list.remove(-1)); 

    }
    
    @Test
    void indexOfTest() {
        assertEquals(list.size() - 1, list.indexOf(17));
        assertEquals(1, list.indexOf(-10));
        assertEquals(4, list.indexOf(10));
    }

    @Test
    void getTest() {
        assertEquals(3, list.get(0));
        assertEquals(10, list.get(4));
        assertEquals(17, list.get(list.size() - 1));
    }

    @Test
    void lastIndexOfTest() {
        // {3, 3, -10, 0, 1, 10, 8, 8, 100, 17, 17};
        list.add(1, 3);
        list.add(17);
        list.add(6, 8);
        assertEquals(list.size() - 1, list.lastIndexOf(17));
        assertEquals(1, list.lastIndexOf(3));
        assertEquals(7, list.lastIndexOf(8));
    } 

    private void wrongIndicesTest(Executable method) {
    assertThrowsExactly(IndexOutOfBoundsException.class, method);
    }
}