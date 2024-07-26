package service;

import model.Post;
import model.User;
import repository.CommentRepository;
import repository.PostRepository;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class PostService {

    private UserRepository userRepository = new UserRepository();
    private PostRepository postRepository = new PostRepository();
    private CommentRepository commentRepository = new CommentRepository();

    private Scanner scText = new Scanner(System.in);
    private Scanner scNumere = new Scanner(System.in);

    public void readAll() {
        // returnam din tabel toate postarile
        // printam postarile returnate

        ArrayList<Post> allPosts = postRepository.readAll();
        for (Post post : allPosts)
            post.comments = commentRepository.readCommentsFromPostWithId(post.id);
        allPosts.forEach(post -> System.out.println(post));
    }

    public void readUserPosts() {
        // Introducem idul userului al caror postari dorim sa fie printate
        // Verificam daca userul exista (facem un request catre UserRepository)
        // daca utilizatorul exista, atunci returnam toate posatrile acestuia si daca nu returnam un mesaj corespunzator

        System.out.println("Introduceti idul userului pentru a-i returna postarile:");
        int userId_Introdus = scNumere.nextInt();

        User userCitit = userRepository.readById(userId_Introdus);

        if (userCitit == null)
            System.out.println("Nu exista nici un user cu idul " + userId_Introdus);
        else {
            ArrayList<Post> postarileUserului = postRepository.readPostsFromUserWithId(userId_Introdus);
            if (postarileUserului.isEmpty())
                System.out.println("Userul nu are nici o postare");
            else {
                for (Post post : postarileUserului)
                    post.comments = commentRepository.readCommentsFromPostWithId(post.id);
                postarileUserului.forEach(post -> System.out.println(post));
            }
        }
    }

    public void create() {
        // Introducem idul userului al caror dorim sa adaugati postari
        // Verificam daca userul exista (facem un request catre UserRepository)
        // daca utilizatorul nu exista trimitem un mesaj corespunzator
        // si daca utilizatorul exista, atunci introducem toate datele necesare: mesaj
        // trimitem un request cu informatiile catre PostRepository pentru a creea o noua postare in BD

        System.out.println("Introduceti idul userului al caror doriti sa adaugati postri:");
        int userId_Introdus = scNumere.nextInt();

        User userCitit = userRepository.readById(userId_Introdus);

        if (userCitit == null)
            System.out.println("Nu exista nici un user cu idul " + userId_Introdus);
        else {
            System.out.println("Introduceti noul mesajul:");
            String mesajIntrodus = scText.nextLine();

            LocalDateTime createdAt = LocalDateTime.now();
            int affectedRows = postRepository.creat(userId_Introdus, mesajIntrodus, createdAt);
            System.out.println(affectedRows > 0 ? "Postarea a fost adaugata cu succes" : "Postarea nu a fost adaugata, a aparut o eroare");
        }
    }

    public void update() {
        // Introducem idul postarii care dorim sa fie modificata
        // verificam daca acea postare exista in BD:
        // daca nu printam un mesaj corespunzator
        // si daca da, cerem utilizatorului sa introduca noul mesaj
        // vom face un request catre BD

        System.out.println("Introduceti idul postarii pe care doriti sa o modificati:");
        int postId_introdus = scNumere.nextInt();
        Post postCitit = postRepository.readById(postId_introdus);

        if (postCitit == null)
            System.out.println("Nu exista nici o postare cu idul " + postId_introdus);
        else {
            System.out.println("Introduceti noul mesaj:");
            String mesajNou = scText.nextLine();
            int affectedRows = postRepository.update(postId_introdus, mesajNou);
            System.out.println(affectedRows > 0 ? "Postarea a fost modificata cu succes" : "Postare nu a putut fi modificata, a aparut o eroare");
        }
    }

    public void delete() {
        // Introducem idul postarii pe care dorim sa o stergem
        // verificam daca postare exista in BD
        // daca exista, o stergem din BD si daca nu trimitem un mesaj corespunzator

        System.out.println("Introduceti idul postarii pe care doriti sa o stergeti:");
        int postId_Dorit = scNumere.nextInt();

        Post postCitit = postRepository.readById(postId_Dorit);
        if (postCitit == null)
            System.out.println("Nu exista nici o postare cu idul " + postId_Dorit);
        else {
            int affectedRows = postRepository.delete(postId_Dorit);
            System.out.println(affectedRows > 0 ? "Postarea a fost stearsa cu succes" : "Postare nu a fost stearsa, a aparut o eroare");
        }
    }

}
