import java.util.ArrayList;
import java.util.List;

/**
 * Custom HashMap implementation for efficient user management.
 * Uses separate chaining for collision resolution.
 */
public class SimpleHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Entry<K, V>[] table;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public SimpleHashMap() {
        this.capacity = DEFAULT_CAPACITY;
        this.table = new Entry[capacity];
        this.size = 0;
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), capacity);
    }

    /**
     * Put a key-value pair into the map
     */
    public synchronized void put(K key, V value) {
        if (key == null) return;

        int index = hash(key);
        Entry<K, V> entry = table[index];

        // Check if key already exists
        while (entry != null) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
            entry = entry.next;
        }

        // Add new entry
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;

        // Resize if needed
        if (size > capacity * LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Get value by key
     */
    public synchronized V get(K key) {
        if (key == null) return null;

        int index = hash(key);
        Entry<K, V> entry = table[index];

        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }

        return null;
    }

    /**
     * Remove a key-value pair
     */
    public synchronized V remove(K key) {
        if (key == null) return null;

        int index = hash(key);
        Entry<K, V> entry = table[index];
        Entry<K, V> prev = null;

        while (entry != null) {
            if (entry.key.equals(key)) {
                if (prev == null) {
                    table[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;
                return entry.value;
            }
            prev = entry;
            entry = entry.next;
        }

        return null;
    }

    /**
     * Check if key exists
     */
    public synchronized boolean containsKey(K key) {
        if (key == null) return false;

        int index = hash(key);
        Entry<K, V> entry = table[index];

        while (entry != null) {
            if (entry.key.equals(key)) {
                return true;
            }
            entry = entry.next;
        }

        return false;
    }

    /**
     * Get all keys
     */
    public synchronized List<K> keySet() {
        List<K> keys = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            Entry<K, V> current = entry;
            while (current != null) {
                keys.add(current.key);
                current = current.next;
            }
        }
        return keys;
    }

    /**
     * Get all values
     */
    public synchronized List<V> values() {
        List<V> vals = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            Entry<K, V> current = entry;
            while (current != null) {
                vals.add(current.value);
                current = current.next;
            }
        }
        return vals;
    }

    /**
     * Get size
     */
    public synchronized int size() {
        return size;
    }

    /**
     * Check if empty
     */
    public synchronized boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clear all entries
     */
    @SuppressWarnings("unchecked")
    public synchronized void clear() {
        table = new Entry[capacity];
        size = 0;
    }

    /**
     * Resize the table when load factor is exceeded
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        Entry<K, V>[] oldTable = table;
        table = new Entry[newCapacity];
        capacity = newCapacity;
        size = 0;

        for (Entry<K, V> entry : oldTable) {
            Entry<K, V> current = entry;
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }
}