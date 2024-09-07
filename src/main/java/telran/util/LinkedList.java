package telran.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Iterator;

public class LinkedList<T> implements List<T> {
    static class Node<T> {
        T obj;
        Node<T> next;
        Node<T> prev;

        Node(T obj) {
            this.obj = obj;
        }
    }

    class LinkedListIterator implements Iterator<T> {
        Node<T> curr = head;
        Node<T> prev = null;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T obj = curr.obj;
            prev = curr;
            curr = curr.next;
            return obj;
        }

        @Override
        public void remove() {
            if (prev == null) {
                throw new IllegalStateException();
            }
            removeNode(prev);
            prev = null;
        }
    }

    Node<T> head;
    Node<T> tail;
    int size = 0;

    Node<T> getNode(int index) {
        return index < size / 2 ? getNodeFromHead(index) : getNodeFromTail(index);
    }

    private Node<T> getNodeFromTail(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private Node<T> getNodeFromHead(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    void addNode(Node<T> node, int index) {
        if (index == 0) {
            addHead(node);
        } else if (index == size) {
            addTail(node);
        } else {
            addMiddle(node, index);
        }
        size++;
    }

    private void addMiddle(Node<T> nodeToInsert, int index) {
        Node<T> nodeBefore = getNode(index);
        Node<T> nodeAfter = nodeBefore.prev;
        nodeToInsert.next = nodeBefore;
        nodeToInsert.prev = nodeAfter;
        nodeBefore.prev = nodeToInsert;
        nodeAfter.next = nodeToInsert;
    }

    private void addTail(Node<T> node) {
        tail.next = node;
        node.prev = tail;
        tail = node;
    }

    private void addHead(Node<T> node) {
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    @Override
    public boolean add(T obj) {
        Node<T> node = new Node<>(obj);
        addNode(node, size);
        return true;
    }

    @Override
    public void add(int index, T obj) {
        checkIndex(index, true);
        Node<T> node = new Node<>(obj);
        addNode(node, index);
    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        int index = indexOf(pattern);
        if (index >= 0) {
            remove(index);
            res = true;
        }
        return res;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        Node<T> node = getNode(index);
        removeNode(node);
        return node.obj;
    }

    void removeNode(Node<T> node) {
        if (node == head) {
            removeHead();
        } else if (node == tail) {
            removeTail();
        } else {
            removeMiddle(node);
        }
        size--;
        clearReferences(node);
    }

    private void clearReferences(Node<T> node) {
        node.next = null;
        node.obj = null;
        node.prev = null;
    }

    private void removeTail() {
        tail = tail.prev;
        tail.next = null;
    }

    private void removeHead() {
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;

        }
    }

    private void removeMiddle(Node<T> node) {
        Node<T> nodeAfter = node.next;
        Node<T> nodeBefore = node.prev;
        nodeBefore.next = node.next;
        nodeAfter.prev = node.prev;
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
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        return getNode(index).obj;
    }

    @Override
    public int indexOf(T pattern) {
        int index = 0;
        Node<T> curr = head;
        while (index < size && !Objects.equals(pattern, curr.obj)) {
            index++;
            curr = curr.next;
        }
        return index >= size ? -1 : index;
    }

    @Override
    public int lastIndexOf(T pattern) {
        Node<T> curr = tail;
        int index = size - 1;
        while (index >= 0 && !Objects.equals(pattern, curr.obj)) {
            index--;
            curr = curr.prev;
        }
        return index;
    }

}