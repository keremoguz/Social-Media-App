
public class Post implements Comparable<Post> {
    String postId;
    String authorId;
    String content;
    int likes = 0;

    // Set of people who liked the post
    MyHashSet<String> peopleLikedPost = new MyHashSet<>();

    Post(String postId, String authorId, String content) {
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
    }
    // Overriding the compareTo method to compare posts,
    // first by likes descending, then by postId descending
    @Override
    public int compareTo(Post other) {
        // First compare by likes
        if (this.likes > other.likes) {
            return 1;
        } else if (this.likes < other.likes) {
            return -1;
        }
        // If likes are equal, compare by postId
        return this.postId.compareTo(other.postId); // Descending order for postId
    }
}
