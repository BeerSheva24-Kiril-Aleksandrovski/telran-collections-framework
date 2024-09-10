package telran.util;

import java.util.Iterator;

import telran.util.LinkedList.Node;

public class LinkedHashSet<T> implements Set<T> {
    private LinkedList<T> list = new LinkedList<>();
    HashMap<T, Node<T>> map = new HashMap<>();

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            res = true;
            Node<T> node = new Node<>(obj);
            list.addNode(node, list.size());
            map.put(obj, node);
            }
        return res;
    }

    @Override
    public boolean remove(T pattern) {
        Node<T> node = map.get(pattern);
        boolean res = false;
        if (node != null) {
            res = true;
            list.removeNode(node);
            map.remove(pattern);
        }
        return res;
    }

    @Override
    public int size() {
        return list.size;
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(T obj) {
        return map.containsKey(obj);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedHashSetIterator();
        
    }

    class LinkedHashSetIterator implements Iterator<T> {
        LinkedList<T>.LinkedListIterator iterator = (LinkedList<T>.LinkedListIterator) list.iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
           return iterator.next();
        }

        @Override
        public void remove() {
            T prev = iterator.getPrev();
            iterator.remove();
            map.remove(prev);
        }
    }

    @Override
    public T get(Object pattern) {
        Node<T> res = map.get(pattern);
        return res != null ?  res.obj : null;
    }

}
