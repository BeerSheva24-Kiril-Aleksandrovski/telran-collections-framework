package telran.util;

import java.util.Objects;

@SuppressWarnings("unchecked")
public abstract class AbstractMap<K, V> implements Map<K, V> {
    protected Set<Entry<K, V>> set;

    protected abstract Set<K> getEmptyKeySet();

    @Override
    public V get(Object key) {
        Entry<K, V> pattern = new Entry<>((K) key, null);
        Entry<K, V> entry = set.get(pattern);
        V res = null;
        if (entry != null) {
            res = entry.getValue();
        }
        return res;
    }

    @Override
    public V put(K key, V value) {
        Entry<K, V> pattern = new Entry<>((K)key, null);
        Entry<K, V> entry = set.get(pattern);
        V res = null;
    
        if (entry == null) {
            set.add(new Entry<>(key, value));
        } else {
            res = entry.getValue();
            entry.setValue(value);
        }

        return res;
    }

    @Override
    public boolean containsKey(Object key) {
        return set.contains(new Entry<K, V>((K) key, null));
    }

    @Override
    public boolean containsValue(Object value) {
        return set.stream().anyMatch(i -> Objects.equals(value, i.getValue()));
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = getEmptyKeySet();
        set.forEach(i -> keySet.add(i.getKey()));
        return keySet;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return set;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> res = new ArrayList<>();
        set.forEach(i -> res.add(i.getValue()));
        return res;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }
    
    @Override
    public V remove(K key) {
        Entry<K, V> pattern = new Entry<>((K) key, null);
        Entry<K,V> entry = set.get(pattern); 
        V res = null;
        if(entry != null){
            set.remove(entry);
            res = entry.getValue();
        }
        return res;
    }
}
