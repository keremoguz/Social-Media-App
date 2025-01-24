import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;


public class InputOutputOperations {
    static ArrayList<String> logMessages = new ArrayList<>();
    public static void takeInput(String line, MyHashMap<String, User> users, MyHashMap<String, Post> posts) {
        // splits the input line into two parts:
        // the first word is the action
        // the rest of the line is the argument
        String[] parts = line.split(" ",2);
        String action = parts[0];
        if(action.equals("create_user")){
            UserOperations.createUser(parts[1],users);
        }
        else if (action.equals("follow_user")){
            UserOperations.followUser(parts[1],users);
        }
        else if (action.equals("unfollow_user")){
            UserOperations.unfollowUser(parts[1],users);
        }
        else if (action.equals("create_post")){
            PostOperations.createPost(parts[1],users,posts);
        }
        else if(action.equals("toggle_like")){
            PostOperations.toggleLike(parts[1],true, users, posts);
        }
        else if(action.equals("see_post")){
            PostOperations.seePost(parts[1],users,posts);
        }
        else if(action.equals("see_all_posts_from_user")){
            PostOperations.seeAllPostsFromUser(parts[1],users);
        }
        else if(action.equals("generate_feed")){
            FeedOperations.generateFeed(parts[1],users);
        }
        else if(action.equals("scroll_through_feed")){
            FeedOperations.scrollThroughFeed(parts[1],users,posts);
        }
        else if(action.equals("sort_posts")){
            PostOperations.sortPosts(parts[1],users);
        }
        else{
            System.out.println("Invalid action");
        }
    }

    // All log messages are stored in the logMessages ArrayList for later output
    public static void log(String message) {
        logMessages.add(message);
    }
    public static void logError(String operation) {
        log("Some error occurred in " + operation + ".");
    }
    /**
     * Processes an input file containing commands and writes the results to an output file.
     *
     * @param inputFile The path to the input file containing commands.
     * @param outputFile The path to the output file where results will be written.
     * @param users A hashmap containing all users in the network.
     * @param posts A hashmap containing all posts in the network.
     * @throws Exception If an error occurs during file reading or writing.
     *
     * Reads commands line by line from the input file and executes them using the
     * {@link #takeInput(String, MyHashMap, MyHashMap)} method. All log messages
     * are written to the output file at the end.
     */
    public static void processInput(String inputFile,String outputFile, MyHashMap<String, User> users, MyHashMap<String, Post> posts) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                takeInput(line.trim(),users,posts);
            }
            for (String log : logMessages) {
                writer.println(log);
            }
        }
    }

}
