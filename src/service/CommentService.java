package service;

import model.Comment;
import model.Post;
import model.User;
import repository.CommentRepository;
import repository.PostRepository;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class CommentService {

    private UserRepository userRepository = new UserRepository();
    private PostRepository postRepository = new PostRepository();
    private CommentRepository commentRepository = new CommentRepository();

    private Scanner scText = new Scanner(System.in);
    private Scanner scNumere = new Scanner(System.in);

    public void readPostComments() {

        // Introdecum idul postarii al caror comentarii dorim sa fie printate
        // Verificam daca postarea exista in BD (facem un request catre PostRepository)
        // daca postarea exista, atunci returnam toate comentariile acestuia si daca nu returnam un mesaj corespunzator

        System.out.println("Introduceti idul postarii pentru a-i returna comentarii:");
        int postId_Introdus = scNumere.nextInt();

        Post postCitit = postRepository.readById(postId_Introdus);

        if (postCitit == null)
            System.out.println("Nu exista nici un post cu idul " + postId_Introdus);
        else {
            ArrayList<Comment> comentariilePostarii = commentRepository.readCommentsFromPostWithId(postId_Introdus);
            if (comentariilePostarii.isEmpty())
                System.out.println("Postarea nu are nici un comentariu");
            else
                comentariilePostarii.forEach(comment -> System.out.println(comment));
        }
    }

    public void create() {

        // Introducem idul postarii la care vrem sa adaugam un comentariu
        // Verificam daca postare exista in BD (facem un request catre PostRepository)
        // daca postarea nu exista returnam un mesaj corespunzator
        // si daca postarea exista, introducem idul userului care dorim sa comneteze la acea postare
        // Verificam daca userul exista IN BD (facem un request catre UserRepository)
        // daca userul nu exista returnam un mesaj corespunzator
        // si daca userul exista, atunci introducem mesajul comentariului
        // trimitem un request cu informatiile catre CommentRepository pentru a creea o noua comment in BD

        System.out.println("Introduceti idul postarii la care doriti sa adaugati un comentariu:");
        int postId_Introdus = scNumere.nextInt();

        Post postCitit = postRepository.readById(postId_Introdus);

        if (postCitit == null)
            System.out.println("Nu exista nici un post cu idul " + postId_Introdus);
        else {
            System.out.println("Introduceti idul userului al caror doriti sa comneteze la acea postare:");
            int userId_Introdus = scNumere.nextInt();

            User userCitit = userRepository.readById(userId_Introdus);

            if (userCitit == null)
                System.out.println("Nu exista nici un user cu idul " + userId_Introdus);
            else {
                System.out.println("Introduceti mesajul comentariului:");
                String mesajIntrodus = scText.nextLine();

                LocalDateTime createdAt = LocalDateTime.now();
                int affectedRows = commentRepository.creat(postId_Introdus, userId_Introdus, mesajIntrodus, createdAt);
                System.out.println(affectedRows > 0 ? "Commentariul a fost adaugat cu succes" : "Commentariul nu a fost adaugat, a aparut o eroare");
            }
        }
    }

    public void update() {
        // Introducem idul comentariului pe care dorim sa fie modificat
        // verificam daca comentariul exista IN BD (facem un request catre CommentRepository)
        // daca nu exista returnam un mesaj corespunzator
        // daca exista, cerem utilizatorului sa introduca noul mesaj
        // trimitem un request catre CommentRepository pentru a modifica comentariul in BD

        System.out.println("Introduceti idul comentariului pe care doriti sa il modificati:");
        int commentId_introdus = scNumere.nextInt();
        Comment commentCitit = commentRepository.readById(commentId_introdus);

        if (commentCitit == null)
            System.out.println("Nu exista nici un comentariu cu idul " + commentId_introdus);
        else {
            System.out.println("Introduceti noul mesaj:");
            String mesajNou = scText.nextLine();
            int affectedRows = commentRepository.update(commentId_introdus, mesajNou);
            System.out.println(affectedRows > 0 ? "Comentariul a fost modificat cu succes" : "Commentariul nu a putut fi modificat, a aparut o eroare");
        }
    }

    public void delete() {

        // introducem idul comentariului pe care dorim sa il stergem
        // verificam daca comentariul exista in BD (facem un request catre CommentRepository)
        // daca exista, il stergem din BD si daca nu trimitem un mesaj corespunzator

        System.out.println("Introduceti idul Comentariului pe care doriti sa il stergeti:");
        int commentId_Dorit = scNumere.nextInt();

        Comment commentCitit = commentRepository.readById(commentId_Dorit);
        if (commentCitit == null)
            System.out.println("Nu exista nici un comentariu cu idul " + commentId_Dorit);
        else {
            int affectedRows = commentRepository.delete(commentId_Dorit);
            System.out.println(affectedRows > 0 ? "Comentariul a fost sters cu succes" : "Comentariul nu a fost sters, a aparut o eroare");
        }
    }

}
