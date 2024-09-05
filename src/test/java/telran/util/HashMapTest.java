package telran.util;

import org.junit.jupiter.api.BeforeEach;

public class HashMapTest extends AbstractMapTest {

    @Override
    <T> void runTest(T[] expected, T[] actual) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'runTest'");
    }
    @Override
    @BeforeEach
    void setUp() {
        map = new HashMap<>();
        super.setUp();
    }
}
