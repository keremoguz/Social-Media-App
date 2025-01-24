
/**
 * A generic implementation of a max-heap for elements of type {@code Post} that implement the {@code Comparable} interface.
 *
 * @param <Post> The type of elements in the heap, which must implement {@code Comparable<Post>}.
 */
public class MyMaxHeap<Post extends Comparable<Post>> {
    private Post[] heap;
    private int size;
    private int capacity;
    /**
     * Constructs a max-heap with the specified initial capacity.
     *
     * @param capacity The initial capacity of the heap.
     */
    @SuppressWarnings("unchecked")
    public MyMaxHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = (Post[]) new Comparable[capacity + 1]; // Index 0 is unused for easier parent-child calculations
    }

    private int parent(int index) {
        return index / 2;
    }

    private int leftChild(int index) {
        return 2 * index;
    }

    private int rightChild(int index) {
        return 2 * index + 1;
    }


    /**
     * Retrieves and removes the maximum element (the root) from the heap.
     *
     * @return The maximum element in the heap.
     * @throws IllegalStateException If the heap is empty.
     */
    public Post getMaxLiked() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        Post max = heap[1]; // The root of the heap is the maximum element
        heap[1] = heap[size--]; // Replace the root with the last element in the heap
        percolateDown(1); // Move the new root down the tree
        return max;
    }
    /**
     * Inserts a new element into the heap and maintains the max-heap property.
     *
     * @param value The element to insert.
     */
    public void insert(Post value) {
        ensureCapacity();
        heap[++size] = value; // Insert the new element at the end of the heap
        percolateUp(size); // Move the new element up the tree
    }

    /**
     * Swaps two elements in the heap at the given indices.
     *
     * @param index1 The index of the first element.
     * @param index2 The index of the second element.
     */
    private void swap(int index1, int index2) {
        Post temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }
    /**
     * Ensures the heap has enough capacity to insert new elements.
     * If the heap is full, its capacity is doubled.
     */
    private void ensureCapacity() {
        if (size == capacity) {
            capacity *= 2;
            @SuppressWarnings("unchecked")
            Post[] newHeap = (Post[]) new Comparable[capacity + 1];
            System.arraycopy(heap, 1, newHeap, 1, size);
            heap = newHeap;
        }
    }

    /**
     * Restores the max-heap property by moving an element up the tree.
     *
     * @param index The index of the element to move up.
     */
    private void percolateUp(int index) {
        // Compare the element with its parent, and swap if the parent is smaller
        while (index > 1 && heap[parent(index)].compareTo(heap[index]) < 0) {
            swap(index, parent(index));
            index = parent(index);
        }
    }
    /**
     * Restores the max-heap property by moving an element down the tree.
     *
     * @param index The index of the element to move down.
     */
    private void percolateDown(int index) {
        int largest = index;
        int left = leftChild(index);
        int right = rightChild(index);
        // Check if the left child exists and is greater than the current largest
        if (left <= size && heap[left].compareTo(heap[largest]) > 0) {
            largest = left;
        }
        // Check if the right child exists and is greater than the current largest
        if (right <= size && heap[right].compareTo(heap[largest]) > 0) {
            largest = right;
        }
        // If the largest is not the current index, swap and continue percolating down
        if (largest != index) {
            swap(index, largest);
            percolateDown(largest);
        }
    }



    public boolean isEmpty() {
        return size == 0;
    }

}