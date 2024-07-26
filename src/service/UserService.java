package service;

import model.Post;
import model.User;
import repository.CommentRepository;
import repository.PostRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    private UserRepository userRepository = new UserRepository();
    private PostRepository postRepository = new PostRepository();
    private CommentRepository commentRepository = new CommentRepository();

    private Scanner scText = new Scanner(System.in);
    private Scanner scNumere = new Scanner(System.in);
    private Scanner scBoolean = new Scanner(System.in);

    public void readAll() {
        // returnam din tabel toti utilizatorii
        // printam utilizatorii returnati

        ArrayList<User> allUsers = userRepository.readAll();
        if (allUsers.isEmpty())
            System.out.println("Nu exista nici un User");
        else {
            for (User user : allUsers) {
                user.postari = postRepository.readPostsFromUserWithId(user.id);
                for (Post post : user.postari)
                    post.comments = commentRepository.readCommentsFromPostWithId(post.id);
            }
            allUsers.forEach(user -> System.out.println(user));
        }
    }

    public void readById() {
        // Introducem idul userului dorit
        // Verificam daca userul exista (facem un request catre UserRepository)
        // daca utilizatorul exista, atunci il returnam si daca nu returnam un mesaj corespunzator

        System.out.println("Introduceti idul userului dorit:");
        int idDorit = scNumere.nextInt();

        User userCitit = userRepository.readById(idDorit);

        if (userCitit == null)
            System.out.println("Nu exista nici un user cu idul " + idDorit);
        else {
            userCitit.postari = postRepository.readPostsFromUserWithId(idDorit);
            for (Post post : userCitit.postari)
                post.comments = commentRepository.readCommentsFromPostWithId(post.id);
            System.out.println(userCitit);
        }
    }

    public void create() {
        // Introducem datele necesare
        // trimitem un request cu informatiile pentru a salva in baza de date userul nou

        System.out.println("Introduceti numele:");
        String numeIntrodusa = scText.nextLine();

        System.out.println("Introduceti prenumele:");
        String prenumeIntrodusa = scText.nextLine();

        System.out.println("Introduceti emailul:");
        String emailIntrodus = scText.nextLine();

        System.out.println("Introduceti numarul de telefon:");
        String numarTelefonIntrodus = scText.nextLine();

        int affecetedRows = userRepository.creat(numeIntrodusa, prenumeIntrodusa, emailIntrodus, numarTelefonIntrodus);
        System.out.println(affecetedRows > 0 ? "Userul a fost salvat cu succes" : "Userul nu a fost salvat, a aparut o eroare");
    }

    public void update() {
        // Introducem idul userului
        // verificam daca userul exista in BD,
        // daca userul nu exista trimitem un mesaj corespunzator
        // si daca userul exista atunci:
        // doriti sa modificati campul X?
        // Introducem noile valori
        // pentru fiecare modificare vom face un request catre DB

        System.out.println("Introduceti idul userului pe care doriti sa il modificati:");
        int idDorit = scNumere.nextInt();
        User userCitit = userRepository.readById(idDorit);

        if (userCitit == null)
            System.out.println("Nu exista nici un user cu idul " + idDorit);
        else {
            userCitit.postari = postRepository.readPostsFromUserWithId(idDorit);
            for (Post post : userCitit.postari)
                post.comments = commentRepository.readCommentsFromPostWithId(post.id);
            System.out.println(userCitit);
            boolean modificamNumele = modificamProprietate("Nume");
            boolean modificamPreumele = modificamProprietate("Prenume");
            boolean modificamEmailul = modificamProprietate("Email");
            boolean modificamNumarDeTelefon = modificamProprietate("Numar de Telefon");

            if (modificamNumele) {
                System.out.println("Introduceti noul nume:");
                String numeNou = scText.nextLine();
                userRepository.modifyNume(idDorit, numeNou);
            }

            if (modificamPreumele) {
                System.out.println("Introduceti noul prenume:");
                String prenumeNou = scText.nextLine();
                userRepository.modifyPrenume(idDorit, prenumeNou);
            }

            if (modificamEmailul) {
                System.out.println("Introduceti noul Email:");
                String emailNou = scText.nextLine();
                userRepository.modifyEmail(idDorit, emailNou);
            }

            if (modificamNumarDeTelefon) {
                System.out.println("Introduceti noul Numar de Telefon:");
                String nrTelefonNou = scText.nextLine();
                userRepository.modifyNumarDeTelefon(idDorit, nrTelefonNou);
            }
        }
    }

    public boolean modificamProprietate(String proprietate) {
        System.out.println("Doriti sa modificati " + proprietate + " ? (true / false)");
        return scBoolean.nextBoolean();
    }

    public void delete() {
        // Introducem idul userului
        // verificam daca userul exista in BD
        // daca exista userul, il stergem din BD si daca nu trimitem un mesaj corespunzator

        System.out.println("Introduceti idul userului pe care doriti sa il stergeti:");
        int idDorit = scNumere.nextInt();

        User userCitit = userRepository.readById(idDorit);
        if (userCitit == null)
            System.out.println("Nu exista nici un user cu idul " + idDorit);
        else {
            int affectedRows = userRepository.delete(idDorit);
            System.out.println(affectedRows > 0 ? "Userul a fost sters cu succes" : "Userul nu a fost sters, a aparut o eroare");
        }
    }

}

