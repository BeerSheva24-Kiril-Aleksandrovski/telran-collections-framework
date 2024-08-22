package telran.util;

public abstract class ListTest extends CollectionTest {
    List<Integer> list;
    @Override
    void setUp() {
        super.setUp();
        list = (List<Integer>)collection;
    } 
    //TODO
    //specific tests for list
    //where list is the reference to collectiion being filled in the method setUp of CollectionTest
}