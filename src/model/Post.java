package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Post {
    public int id;
    public int user_id;
    public String mesaj;
    public LocalDateTime createdAt;
    public ArrayList<Comment> comments;

    public Post(int id, int user_id, String mesaj, LocalDateTime createdAt, ArrayList<Comment> comments) {
        this.id = id;
        this.user_id = user_id;
        this.mesaj = mesaj;
        this.createdAt = createdAt;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "\nPost{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", mesaj='" + mesaj + '\'' +
                ", createdAt=" + createdAt +
                ", comments=" + comments +
                "}\n";
    }
}
