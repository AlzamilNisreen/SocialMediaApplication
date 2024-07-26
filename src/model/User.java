package model;

import java.util.ArrayList;

public class User {
    public int id;
    public String nume;
    public String prenume;
    public String email;
    public String numarTelefon;
    public ArrayList<Post> postari;

    public User(int id, String nume, String prenume, String email, String numarTelefon, ArrayList<Post> postari) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.numarTelefon = numarTelefon;
        this.postari = postari;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", email='" + email + '\'' +
                ", numarTelefon='" + numarTelefon + '\'' +
                ", postari=" + postari +
                "}\n";
    }
}
