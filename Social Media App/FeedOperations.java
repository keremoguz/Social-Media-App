import java.util.ArrayList;

public class FeedOperations {

    /**
     * Generates a feed of posts for a user.
     *
     * @param params A string containing the user ID and the number of posts to display, separated by a space.
     * @param users A hashmap containing all users in the network.
     *
     * Generates a feed consisting of posts from users the user follows that have not been seen yet.
     * The feed is sorted by likes (descending) and then lexicographically by post ID.
     * Displays up to the requested number of posts and logs a message if no more posts are available.
     */
    public static void generateFeed(String params, MyHashMap<String, User> users) {
        String[] parts = params.split(" ");
        String userId = parts[0];
        int num = Integer.parseInt(parts[1]);

        // if the user does not exist in the network, log an error
        if (!users.containsKey(userId)) {
            InputOutputOperations.logError("generate_feed");
            return;
        }
        User user = users.get(userId);
        // Generate the list of posts for the user's feed,
        // this feed consists of posts from the users that the user follows and has not seen yet
        ArrayList<Post> feed = new ArrayList<>();
        for (String followedId : user.following) {
            User followedUser = users.get(followedId);
            for (Post post : followedUser.posts) {
                if (!user.seenPosts.contains(post.postId)) {
                    feed.add(post);
                }
            }
        }
        // Sort by likes (descending), then lexicographically by post ID using heap sort
        ArrayList<Post> sortedFeed = MyHeapSort.heapSort(feed);

        InputOutputOperations.log("Feed for " + userId + ":");
        int count = 0; // tracks the number of posts retrieved
        for (Post post : sortedFeed) {
            if (count >= num) {
                break;
            }
            InputOutputOperations.log("Post ID: " + post.postId + ", Author: " + post.authorId + ", Likes: " + post.likes);
            count++;
        }
        // If fewer posts are available than requested, log the "No more posts" message
        if (count < num) {
            InputOutputOperations.log("No more posts available for " + userId + ".");
        }
    }
    /**
     * Generates a feed of posts for scrolling purposes.
     *
     * @param user The user for whom the feed is being generated.
     * @param users A hashmap containing all users in the network.
     * @return An ArrayList of posts, sorted by likes (descending) and then lexicographically by post ID.
     */
    private static ArrayList<Post> generateFeedForScroll(User user, MyHashMap<String, User> users) {
        ArrayList<Post> feed = new ArrayList<>();
        for (String followedId : user.following) {
            User followedUser = users.get(followedId);
            for (Post post : followedUser.posts) {
                if (!user.seenPosts.contains(post.postId)) {
                    feed.add(post);
                }
            }
        }
        // Sort by likes (descending), then lexicographically by post ID
        return MyHeapSort.heapSort(feed);
    }
    /**
     * Allows a user to scroll through their feed and interact with posts.
     *
     * @param params A string containing the user ID, the number of posts to scroll through, and a sequence of actions (0 for "saw," 1 for "like").
     * @param users A hashmap containing all users in the network.
     * @param posts A hashmap containing all posts in the network.
     *
     * For each post in the feed, logs whether the user saw or liked the post.
     */
    public static void scrollThroughFeed(String params, MyHashMap<String, User> users, MyHashMap<String, Post> posts) {
        String[] parts = params.split(" ");
        String userId = parts[0];
        int num = Integer.parseInt(parts[1]);
        // if the user does not exist in the network, log an error
        if (!users.containsKey(userId)) {
            InputOutputOperations.logError("scroll_through_feed");
            return;
        }

        User user = users.get(userId);

        // Generate the list of posts for the user's feed.
        ArrayList<Post> feed = generateFeedForScroll(user, users);

        InputOutputOperations.log(userId + " is scrolling through feed:");

        boolean notEnoughPosts = false;

        for (int i = 0; i < num; i++) {
            // check if there are enough posts in the feed
            if (i >= feed.size()) {
                notEnoughPosts = true;
                break;
            }
            // gets the post and the action for the current post
            Post post = feed.get(i);
            int action = Integer.parseInt(parts[2 + i]); // Action for the current post

            if (action == 1) {
                // Like the post and log appropriately, there will be no another log in toggleLike since isLogged is false
                PostOperations.toggleLike(userId + " " + post.postId, false,users, posts);
                InputOutputOperations.log(userId + " saw " + post.postId + " while scrolling and clicked the like button.");
            } else {
                // Log only "saw" for action 0
                InputOutputOperations.log(userId + " saw " + post.postId + " while scrolling.");
                user.seenPosts.add(post.postId);
            }
        }

        // if the num is bigger than feed size, log the appropriate message
        if (notEnoughPosts) {
            InputOutputOperations.log("No more posts in feed.");
        }
    }


}
