package telran.util;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ArrayList<T> implements List<T>{
    private static final int DEFAULT_CAPACITY = 16;
    private Object [] array;
    private int size = 0;
    public ArrayList(int capacity){
        array = new Object[capacity];
    }
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }
    
    private void reallocationIfNeeded() {
        if(size == array.length) {
            reallocate();
        }
    }

    private void reallocate() {
        array = Arrays.copyOf(array, array.length * 2);
    }
    
    @Override
    public boolean add(T obj) {
        reallocationIfNeeded();
        array[size++] = obj;
        return true;
    }

    @Override
    public void add(int index, T obj) {
        checkIndex(index, false);
        reallocationIfNeeded();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = obj;
        size++;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        T obj = (T)array[index];
        size--;
        System.arraycopy(array, index + 1, array, index, size - index);
        array[size] = null;
        return obj;
    }

    @Override
    public int size() {
       return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T pattern) {
        return indexOf(pattern) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
       return new ArrayListIterator();
    }
    
    @Override
    public T get(int index) {
        checkIndex(index, false);
        return (T) array[index];
    }

    @Override
    public int indexOf(T pattern) {
        int index = 0;
        while (index < size && !pattern.equals(array[index])) {
            index++;
        }
        return index >= size ? -1 : index;
    }

    @Override
    public int lastIndexOf(T pattern) {
        int index = size - 1;
        while (index >= 0 && !pattern.equals(array[index])) {
            index--;
        }
        return index;
    }

    @Override
    public boolean removeIf(Predicate <T> predicate) {
        int indexTo = 0;
        Predicate <T> negPred = predicate.negate();             //not to apply "!" operator on ecach iterationm
        for(int currentIndex = 0; currentIndex < size; currentIndex++){
            T current = (T)array[currentIndex];
            if (negPred.test(current)){
                array[indexTo++] = current;
            }
        }
        Arrays.fill(array, indexTo, size, null);
        boolean res = indexTo < size;
        size = indexTo;
        return res;
    }

    private class ArrayListIterator implements Iterator<T> {
        private int index = 0;
        private  boolean flNext = false;
        @Override
        public boolean hasNext() {
            flNext = true;
            return index < size;
        }
    
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T)array[index++];
        }
    
        @Override
        public void remove() {
            if (!flNext) {
                throw new IllegalStateException();
            }
            ArrayList.this.remove(--index);
            flNext = false;
        }
    }
}
