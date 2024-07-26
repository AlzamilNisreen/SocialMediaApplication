import repository.UserRepository;
import service.CommentService;
import service.PostService;
import service.UserService;

import java.util.Scanner;

public class Controller {
    static Scanner scText = new Scanner(System.in);

    private static UserService userService = new UserService();
    private static PostService postService = new PostService();
    private static CommentService commentService = new CommentService();

    public static void main(String[] args) {

        while (true) {
            System.out.println("Introduceti flowul dorit (user/post/comment):");
            String response = scText.nextLine();

            switch (response.toLowerCase()) {
                case "user":
                    startUserFlow();
                    break;
                case "post":
                    startPostFlow();
                    break;
                case "comment":
                    startCommentFlow();
                    break;
                default:
                    System.out.println("Acest flow nu exista");
            }
        }
    }

    private static void startUserFlow() {

        String opratiaDorita = chooseOperation("RA / RBI / C / U / D");

        switch (opratiaDorita.toUpperCase()) {
            case "RA":
                userService.readAll();
                break;
            case "RBI":
                userService.readById();
                break;
            case "C":
                userService.create();
                break;
            case "U":
                userService.update();
                break;
            case "D":
                userService.delete();
                break;
            default:
                System.out.println("Operatia invalida");
        }
    }


    private static void startPostFlow() {

        String opratiaDorita = chooseOperation("RA / RUP / C / U / D");

        switch (opratiaDorita.toUpperCase()) {
            case "RA":
                postService.readAll();
                break;
            case "RUP":
                postService.readUserPosts();
                break;
            case "C":
                postService.create();
                break;
            case "U":
                postService.update();
                break;
            case "D":
                postService.delete();
                break;
            default:
                System.out.println("Operatia invalida");
        }
    }

    private static void startCommentFlow() {

        String opratiaDorita = chooseOperation("RPC / C / U / D");

        switch (opratiaDorita.toUpperCase()) {
            case "RPC":
                commentService.readPostComments();
                break;
            case "C":
                commentService.create();
                break;
            case "U":
                commentService.update();
                break;
            case "D":
                commentService.delete();
                break;
            default:
                System.out.println("Operatia invalida");
        }
    }

    private static String chooseOperation(String operations) {
        System.out.println("Introduceti operatia dorita (" + operations + ")");
        String operatiaDorita = scText.nextLine();
        return operatiaDorita;
    }

}
