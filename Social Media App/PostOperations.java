import java.util.ArrayList;

public class PostOperations {

    /**
     * Method to create a new post in the network.
     *
     * @param params A string containing the user ID, post ID, and content of the post, separated by spaces.
     * @param users A hashmap containing all users in the network.
     * @param posts A hashmap containing all posts in the network.
     *
     * Logs an error if the user does not exist or if the post ID already exists.
     * Adds the post to the user's post list and the global post network if successful.
     */
    public static void createPost(String params, MyHashMap<String, User> users, MyHashMap<String, Post> posts) {
        // split the input string into parts:
        // the first part is the user who created the post
        // the second part is the post ID
        // the third part is the content of the post
        String[] parts = params.split(" ", 3);

        // if the user does not exist in the network or the post already exists, log an error
        if (!users.containsKey(parts[0]) || posts.containsKey(parts[1])) {
            InputOutputOperations.logError("create_post");
            return;
        }
        String userId = parts[0];
        String postId = parts[1];
        String content = parts[2];
        Post post = new Post(postId, userId, content);

        // added the post to the whole post network and the user's post list
        posts.put(postId, post);
        users.get(userId).posts.add(post);
        InputOutputOperations.log(userId + " created a post with Id " + postId + ".");
    }
    /**
     * Marks a post as seen by a user.
     *
     * @param params A string containing the user ID and post ID, separated by a space.
     * @param users A hashmap containing all users in the network.
     * @param posts A hashmap containing all posts in the network.
     *
     * Adds the post to the user's seen posts list if successful.
     */
    public static void seePost(String params, MyHashMap<String, User> users, MyHashMap<String, Post> posts) {
        String[] parts = params.split(" ");
        // if the user or the post does not exist in the network, log an error
        if (!users.containsKey(parts[0]) || !posts.containsKey(parts[1])) {
            InputOutputOperations.logError("see_post");
            return;
        }
        User user = users.get(parts[0]);
        // added the post to the user's seen posts for checks in feed operations
        user.seenPosts.add(parts[1]);
        InputOutputOperations.log(parts[0] + " saw " + parts[1] + ".");
    }
    /**
     * Marks all posts from one user as seen by another user.
     *
     * @param params A string containing the viewer's user ID and the viewed user's user ID, separated by a space.
     * @param users A hashmap containing all users in the network.
     *
     * Adds all the viewed user's posts to the viewer's seen posts list if successful.
     */
    public static void seeAllPostsFromUser(String params,MyHashMap<String, User> users) {
        String[] parts = params.split(" ");
        // if any of the users do not exist in the network, log an error
        if (!users.containsKey(parts[0]) || !users.containsKey(parts[1])) {
            InputOutputOperations.logError("see_all_posts_from_user");
            return;
        }
        User viewer = users.get(parts[0]);
        User viewed = users.get(parts[1]);
        for (Post post : viewed.posts) {
            // added all the posts of the user to the viewer's seen posts for checks in feed operations
            viewer.seenPosts.add(post.postId);
        }
        InputOutputOperations.log(parts[0] + " saw all posts of " + parts[1] + ".");
    }
    /**
     * Toggles the like status of a post by a user.
     *
     * @param params A string containing the user ID and post ID, separated by a space.
     * @param isLogged A boolean indicating whether the action should be logged.
     * @param users A hashmap containing all users in the network.
     * @param posts A hashmap containing all posts in the network.
     *
     * Adds the post to the user's seen posts list if not already seen. Toggles like/unlike status and logs the action if required.
     */
    public static void toggleLike(String params, boolean isLogged, MyHashMap<String, User> users, MyHashMap<String, Post> posts) {
        String[] parts = params.split(" ");
        // if the user or the post does not exist in the network, log an error
        if (!users.containsKey(parts[0]) || !posts.containsKey(parts[1])) {
            InputOutputOperations.logError("toggle_like");
            return;
        }
        String userId = parts[0];
        String postId = parts[1];
        User user = users.get(userId);
        Post post = posts.get(postId);

        // liking a post means it is seen by the user so, add to seen posts
        user.seenPosts.add(postId);

        // if the user already liked the post, unlike it, otherwise like it
        // isLogged is used to prevent logging the action when it is called from scrollThroughFeed
        if (post.peopleLikedPost.contains(userId)) {
            post.peopleLikedPost.remove(userId);
            post.likes--;
            if (isLogged) {
                InputOutputOperations.log(userId + " unliked " + postId + ".");
            }
        } else {
            post.peopleLikedPost.add(userId);
            post.likes++;
            if (isLogged) {
                InputOutputOperations.log(userId + " liked " + postId + ".");
            }
        }
    }
    /**
     * Sorts a user's posts by the number of likes in descending order
     * if likes are the same, by the ID in descending order.
     *
     * @param userId The ID of the user whose posts should be sorted.
     * @param users A hashmap containing all users in the network.
     *
     * Logs the sorted posts if the user has any, otherwise logs that no posts exist.
     */
    public static void sortPosts(String userId, MyHashMap<String, User> users) {
        // if the user does not exist in the network, log an error
        if (!users.containsKey(userId)) {
            InputOutputOperations.logError("sort_posts");
            return;
        }
        User user = users.get(userId);
        // if there is no post from the user, log the appropriate message
        if (user.posts.isEmpty()) {
            InputOutputOperations.log("No posts from " + userId + ".");
            return;
        }
        InputOutputOperations.log("Sorting " + userId + "'s posts:");
        // used heap sort to sort the posts in descending order of likes (the heap is max heap)
        ArrayList<Post> sortedPosts = MyHeapSort.heapSort(user.posts);
        for (Post post : sortedPosts) {
            InputOutputOperations.log(post.postId + ", Likes: " + post.likes);
        }
    }

}
