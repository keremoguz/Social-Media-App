
public class UserOperations {

    /**
     * Creates a new user in the network.
     *
     * @param userId The ID of the user to be created.
     * @param users A hashmap containing all users in the network.
     *
     * If the user is created successfully, it is added to the `users` hashmap, and a success message is logged.
     */
    public static void createUser(String userId, MyHashMap<String, User> users) {
        // if the user already exists in the network, log an error
        if (users.containsKey(userId)) {
            InputOutputOperations.logError("create_user");
        } else {
            users.put(userId, new User(userId));
            InputOutputOperations.log("Created user with Id " + userId + ".");
        }
    }
    /**
     * Allows one user to follow another user.
     *
     * @param params A string containing the follower's user ID and the followed user's user ID, separated by a space.
     * @param users A hashmap containing all users in the network.
     *
     * Updates the follower's and followed user's lists and logs a success message.
     */
    public static void followUser(String params, MyHashMap<String, User> users) {
        String[] userIds = params.split(" ");
        // if one of the users do not exist in the network, log an error
        if (!users.containsKey(userIds[0]) || !users.containsKey(userIds[1])) {
            InputOutputOperations.logError("follow_user");
            return;
        }
        // a user cannot follow himself/herself, so log an error
        if (userIds[0].equals(userIds[1])) {
            InputOutputOperations.logError("follow_user");
            return;
        }
        User follower = users.get(userIds[0]);
        User followedUser = users.get(userIds[1]);
        boolean isAlreadyFollowing = follower.following.contains(userIds[1]);

        // if the follower is already following the user2, it couldn't be added, log an error
        if (!isAlreadyFollowing){
            follower.following.add(userIds[1]);
            followedUser.followers.add(userIds[0]);
            InputOutputOperations.log(userIds[0] + " followed " + userIds[1] + ".");
        } else {
            InputOutputOperations.logError("follow_user");
        }
    }
    /**
     * Allows one user to unfollow another user.
     *
     * @param params A string containing the follower's user ID and the unfollowed user's user ID, separated by a space.
     * @param users A hashmap containing all users in the network.
     *
     * Updates the follower's and unfollowed user's lists and logs a success message.
     */
    public static void unfollowUser(String params, MyHashMap<String, User> users) {
        String[] userIds = params.split(" ");
        // if one of the users do not exist in the network, log an error
        if (!users.containsKey(userIds[0]) || !users.containsKey(userIds[1])) {
            InputOutputOperations.logError("unfollow_user");
            return;
        }
        // a user cannot unfollow himself/herself, so log an error
        if (userIds[0].equals(userIds[1])) {
            InputOutputOperations.logError("unfollow_user");
            return;
        }
        User follower = users.get(userIds[0]);
        User unfollowedUser = users.get(userIds[1]);
        boolean isAlreadyFollowing = follower.following.contains(userIds[1]);
        // if and only if a follower is following the user2, he/she can unfollow that user
        if (isAlreadyFollowing) {
            follower.following.remove(userIds[1]);
            unfollowedUser.followers.remove(userIds[0]);
            InputOutputOperations.log(userIds[0] + " unfollowed " + userIds[1] + ".");
        } else {
            InputOutputOperations.logError("unfollow_user");
        }
    }

}
