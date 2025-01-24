/**
 * This class is the main class of the project. It creates a social network with users and posts.
 * The main method reads the input file and processes the input line by line.
 * @author Kerem Oguz ID : 2022400270
 * @since 24.11.2024
 */

public class Main {
    // created a social network with users and posts
    MyHashMap<String, User> users = new MyHashMap<>();
    MyHashMap<String, Post> posts = new MyHashMap<>();


    public static void main(String[] args) throws Exception {
        Main socialNetwork = new Main();
        // process the input file, read line by line
        InputOutputOperations.processInput(args[0], args[1], socialNetwork.users, socialNetwork.posts);
    }
}