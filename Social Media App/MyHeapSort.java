import java.util.ArrayList;

/**
 * Sorts a list of posts in descending order of likes using a max-heap.
 * If two posts have the same number of likes, they are sorted lexicographically by their post ID.
 *
 * @return A new ArrayList of {@link Post} objects sorted in descending order of likes.
 * Posts with the same number of likes are further sorted by post ID lexicographically.
 *
 * The method constructs a max-heap using the input list and then extracts the elements in sorted order.
 */
public class MyHeapSort {
    public static ArrayList<Post> heapSort(ArrayList<Post> posts) {
        // Created a max-heap with the size of the input ArrayList
        MyMaxHeap<Post> maxHeap = new MyMaxHeap<>(posts.size());

        // Insert all elements of the ArrayList into the heap
        for (Post post : posts) {
            maxHeap.insert(post);
        }

        // Extract elements from the heap and add them directly to the result list
        ArrayList<Post> sortedList = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            sortedList.add(maxHeap.getMaxLiked());
        }
        return sortedList;
    }
}
