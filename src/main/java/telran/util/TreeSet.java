package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;


@SuppressWarnings("unchecked")
public class TreeSet<T> implements SortedSet<T> {
    private static class Node<T> {
        T obj;
        Node<T> parent;
        Node<T> left;
        Node<T> right;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class TreeSetIterator implements Iterator<T> {
        Node<T> current = null;
        Node<T> prev = null;

        TreeSetIterator() {
            current = getMinNode(root);
        }

        private void setNextCurrent() {
            current = getNextCurrent(current);
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            prev = current;
            T obj = current.obj;
            setNextCurrent();

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

    private Node<T> root;
    private Comparator<T> comparator;
    int size;

    public TreeSet(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public TreeSet() {
        this((Comparator<T>) Comparator.naturalOrder());
    }

    @Override
    public boolean add(T obj) {
        boolean res = false;
        if (!contains(obj)) {
            res = true;
            Node<T> node = new Node<>(obj);
            if (root == null) {
                addRoot(node);
            } else {
                addAfterParent(node);
            }
            size++;
        }
        return res;
    }

    private void addAfterParent(Node<T> node) {
        Node<T> parent = getParent(node.obj);
        if (comparator.compare(node.obj, parent.obj) > 0) {
            parent.right = node;
        } else {
            parent.left = node;
        }
        node.parent = parent;
    }

    private void addRoot(Node<T> node) {
        root = node;
    }

    private Node<T> getGreaterParent(Node<T> node) {
        Node<T> res = node;
        while (res.parent != null && res.parent.right == res) {
            res = res.parent;
        }
        return res.parent;

    }

    private Node<T> getLowerParent(Node<T> node) {
        Node<T> res = node;
        while (res.parent != null && res.parent.left == res) {
            res = res.parent;
        }
        return res.parent;

    }

    @Override
    public boolean remove(T pattern) {
        boolean res = false;
        Node<T> node = getNode(pattern);
        if (node != null) {
            res = true;
            removeNode(node);
        }
        return res;
    }

    private void removeNode(Node<T> node) {
        if (node.left == null || node.right == null) {
            removeNonJunction(node);
        } else {
            removeJunction(node);
        }
        size--;
    }

    private void removeNonJunction(Node<T> node) {
        Node<T> child = node.left == null ? node.right : node.left;
        if (node == root) {
            addRoot(child);
        } else {
            if (node.parent.left == node) {
                node.parent.left = child;
            } else {
                node.parent.right = child;
            }
        }

        if (child != null) {
            child.parent = node.parent;
        }

    }

    private void removeJunction(Node<T> node) {
        Node<T> maxNode = getMaxNode(node.left);
        node.obj = maxNode.obj;
        removeNonJunction(maxNode);
    }

    private Node<T> getMinNode(Node<T> node) {
        Node<T> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node<T> getMaxNode(Node<T> node) {
        Node<T> current = node;
        while (current.right != null) {
            current = current.right;
        }
        return current;
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
    public boolean contains(T obj) {
        Node<T> res = getNode(obj);
        return res != null;
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeSetIterator();
    }

    @Override
    public T get(Object pattern) {
        T res = null;
        T tpattern = (T) pattern;
        if (contains(tpattern)) {
            res = getNode(tpattern).obj;
        }
        return res;
    }

    private Node<T> getParentOrNode(T pattern) {
        Node<T> current = root;
        Node<T> parent = null;
        int compRes = 0;
        while (current != null && (compRes = comparator.compare(pattern, current.obj)) != 0) {
            parent = current;
            current = compRes > 0 ? current.right : current.left;
        }
        return current == null ? parent : current;
    }

    private Node<T> getNode(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        if (res != null) {
            int compRes = comparator.compare(pattern, res.obj);
            res = compRes == 0 ? res : null;
        }

        return res;
    }

    private Node<T> getParent(T pattern) {
        Node<T> res = getParentOrNode(pattern);
        int compRes = comparator.compare(pattern, res.obj);
        return compRes == 0 ? null : res;
    }

    @Override
    public T first() {
        return getMinNode(root).obj;
    }

    @Override
    public T last() {
        return getMaxNode(root).obj;

    }

    @Override
    public T floor(T key) {
        Node<T> node = floorNode(key);
        return node == null ? null : node.obj;
    }

    private Node<T> floorNode(T key) {
        Node<T> node = getParentOrNode(key);
        if (node != null && comparator.compare(key, node.obj) < 0) {
            node = getLowerParent(node);
        }
        return node;
    }

    @Override
    public T ceiling(T key) {
        Node<T> node = ceilingNode(key);
        return node == null ? null : node.obj;
    }

    private Node<T> ceilingNode(T key) {
        Node<T> node = getParentOrNode(key);
        if (node != null && comparator.compare(key, node.obj) > 0) {
            node = getGreaterParent(node);
        }
        return node;
    }

    @Override
    public SortedSet<T> subSet(T keyFrom, T keyTo) {
        if (comparator.compare(keyFrom, keyTo) > 0) {
            throw new IllegalArgumentException();
        }

        SortedSet<T> subSet = new TreeSet<>(comparator);
        Node<T> node = ceilingNode(keyFrom);
        while (node != null && comparator.compare(node.obj, keyTo) < 0) {
            subSet.add(node.obj);
            node = getNextCurrent(node);
        }
        return subSet;
    }

    private Node<T> getNextCurrent(Node<T> current) {
        return current.right == null ?
            getGreaterParent(current) :
            getMinNode(current.right);
    }
}
