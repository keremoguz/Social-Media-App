import java.util.Iterator;

public class MyHashSet<E> implements Iterable<E> {
    private MyHashMap<E, Object> map;
    private static final Object  PRESENT = new Object();

    public MyHashSet() {
        map = new MyHashMap<>();
    }

    // Adds an element to the set
    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    // Removes an element from the set, if it is present returns true
    public boolean remove(E e) {
        return map.remove(e) != null;
    }

    // Checks if an element is present in the set
    public boolean contains(E e) {
        return map.containsKey(e);
    }


    // Returns an iterator over the elements
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            // Create an internal iterator for the underlying map
            private Iterator<MyHashMap.Entry<E, Object>> it = map.iterator();

            // Check if the underlying map iterator has more elements
            public boolean hasNext() {
                return it.hasNext();
            }
            // Return the key from the next map entry
            public E next() {
                return it.next().key;
            }
        };
    }
}



