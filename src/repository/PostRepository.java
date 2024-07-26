package repository;

import model.Post;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PostRepository {

    private Post extractPostFromResultSet(ResultSet rs) {
        try {
            return new Post(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("mesaj"),
                    LocalDateTime.of(
                            rs.getInt("an"),
                            rs.getInt("luna"),
                            rs.getInt("zi"),
                            rs.getInt("ora"),
                            rs.getInt("minut")
                    ),
                    new ArrayList<>()
            );
        } catch (SQLException e) {
            return null;
        }
    }


    public ArrayList<Post> readPostsFromUserWithId(int userId) {

        ArrayList<Post> postarileUserului = new ArrayList<>();
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM Post WHERE user_id =" + userId);
            while (rs.next()) {
                postarileUserului.add(extractPostFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return postarileUserului;
    }


    public ArrayList<Post> readAll() {

        ArrayList<Post> allPosts = new ArrayList<>();
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM Post;");
            while (rs.next()) {
                allPosts.add(extractPostFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allPosts;
    }


    public Post readById(int postIdDorit) {

        Post postCitit = null;
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM Post WHERE id = " + postIdDorit);
            rs.next();
            postCitit = extractPostFromResultSet(rs);
        } catch (SQLException e) {
            return null;
        }
        return postCitit;
    }


    public int creat(int user_id, String mesaj, LocalDateTime createdAt) {

        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "INSERT INTO Post (user_id , mesaj , an , luna , zi , ora , minut) " +
                    "VALUES ( %d ,'%s', %d , %d , %d , %d , %d)";
            return st.executeUpdate(String.format(template,
                    user_id,
                    mesaj,
                    createdAt.getYear(),
                    createdAt.getMonthValue(),
                    createdAt.getDayOfMonth(),
                    createdAt.getHour(),
                    createdAt.getMinute())
            );
        } catch (SQLException e) {
            return 0;
        }
    }


    public int update(int postId, String mesajNou) {

        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "UPDATE Post SET mesaj = '%s' WHERE id = %d;";
            return st.executeUpdate(String.format(template, mesajNou, postId));
        } catch (SQLException e) {
            return 0;
        }
    }

    public int delete(int idDorit) {

        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "DELETE FROM Post WHERE id = %d;";
            return st.executeUpdate(String.format(template, idDorit));
        } catch (SQLException e) {
            return 0;
        }
    }

}
