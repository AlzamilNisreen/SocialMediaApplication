package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Comment {
    public int id;
    public int post_id;
    public int user_id;
    public String mesaj;
    public LocalDateTime createdAt;

    public Comment(int id, int post_id, int user_id, String mesaj, LocalDateTime createdAt) {
        this.id = id;
        this.post_id = post_id;
        this.user_id = user_id;
        this.mesaj = mesaj;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "\nComment{" +
                "id=" + id +
                ", post_id=" + post_id +
                ", user_id=" + user_id +
                ", mesaj='" + mesaj + '\'' +
                ", createdAt=" + createdAt +
                "}";
    }
}
