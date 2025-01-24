import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyHashMap<K, V> implements Iterable<MyHashMap.Entry<K, V>> {

    // Entry class representing a key-value pair in the map
    public static class Entry<K, V> {
        public K key;
        public V value;
        Entry<K, V> next;
        Entry(K key, V value, Entry<K, V> next) {
            this.key   = key;
            this.value = value;
            this.next  = next;
        }
    }

    private Entry<K, V>[] table;
    private int capacity = 16;
    private int size = 0;
    private static final float LOAD_FACTOR = 0.50f;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        table = new Entry[capacity];
    }

    // Inserts a key-value pair into the map
    public V put(K key, V value) {
        int index = indexFor(hash(key.hashCode()));
        Entry<K, V> e = table[index];

        // handle collision by separate chaining,
        // if the bucket is already occupied, add the new entry to the front
        while (e != null) {
            // if the key already exists, do not update the value, just return the old value
            if (e.key.equals(key)) {
                return e.value;
            }
            e = e.next;
        }

        addEntry(key, value, index);
        return null;
    }

    // Retrieves a value associated with the key from the map
    public V get(K key) {
        int index = indexFor(hash(key.hashCode()));
        Entry<K, V> e = table[index];
        while (e != null) {
            if (e.key.equals(key)) {
                return e.value;
            }
            e = e.next;
        }
        return null;
    }

    // Removes a key-value pair from the map and returns the value
    public V remove(K key) {
        int index = indexFor(hash(key.hashCode()));
        Entry<K, V> prev = null;
        Entry<K, V> e = table[index];
        while (e != null) {
            if (e.key.equals(key)) {
                if (prev == null) {
                    table[index] = e.next; // Update the head of the bucket if the match is at the first node
                } else {
                    prev.next = e.next; // Skip the matched node
                }
                size--;
                return e.value; // Return the value of the removed node
            }
            prev = e;
            e = e.next;
        }

        return null;
    }

    // Checks if a key is present in the map or not
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // Returns an iterator over the entries in the map
    public Iterator<Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    // Internal helper methods

    // Adds a new entry to the map at the specified index,
    // if space is available, otherwise resizes the map
    private void addEntry(K key, V value, int index) {
        Entry<K, V> e = table[index];
        table[index] = new Entry<>(key, value, e);
        size++;
        if (size >= capacity * LOAD_FACTOR) {
            resize(2 * capacity);
        }
    }

    // Resizes the map to the new capacity and rehashes all entries
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Entry<K, V>[] oldTable = table;
        table = new Entry[newCapacity];
        capacity = newCapacity;
        size = 0;
        for (Entry<K, V> e : oldTable) {
            while (e != null) {
                put(e.key, e.value);
                e = e.next;
            }
        }
    }

    private int hash(int hashCode) {
        return Math.abs(hashCode * 31); // Spread the hash code using prime multiplier;
    }

    private int indexFor(int hash) {
        return hash % capacity; // Use modulo to find the correct bucket
    }

    // Iterator implementation for the HashMap to iterate over the entries
    private class HashMapIterator implements Iterator<Entry<K, V>> {
        int index = 0;
        Entry<K, V> next;

        // Constructor initializes the iterator and moves to the first non-null bucket
        HashMapIterator() {
            moveToNext();
        }

         // Advances the iterator to the next non-null bucket in the hash table.
         // Sets the `next` reference to the first entry in the next non-empty bucket.
        private void moveToNext() {
            while (index < table.length) {
                next = table[index++];
                if (next != null) {
                    break; // Exit the loop if a non-null bucket is found
                }
            }
        }

         // Checks if there are more elements to iterate over in the hash table.
         // return true if there is at least one more entry, false otherwise.
        public boolean hasNext() {
            return next != null;
        }


         // Returns the next entry in the hash table.
         // @return The next  Entry<K, V> in the iteration.
        public Entry<K, V> next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Entry<K, V> e = next;
            next = e.next;
            if (next == null)
                moveToNext();
            return e;
        }
    }
}
