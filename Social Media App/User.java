import java.util.ArrayList;

public class User {
    String userId;

    // Stores the followers, the following, seen posts and posts of the user
    MyHashSet<String> followers = new MyHashSet<>();
    MyHashSet<String> following = new MyHashSet<>();
    MyHashSet<String> seenPosts = new MyHashSet<>();
    ArrayList<Post> posts = new ArrayList<>();

    User(String userId) {
        this.userId = userId;
    }
}
