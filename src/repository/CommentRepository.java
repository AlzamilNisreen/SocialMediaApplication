package repository;

import model.Comment;
import model.Post;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CommentRepository {

    private Comment extractCommentFromResultSet(ResultSet rs) {
        try {
            return new Comment(
                    rs.getInt("id"),
                    rs.getInt("post_id"),
                    rs.getInt("user_id"),
                    rs.getString("mesaj"),
                    LocalDateTime.of(
                            rs.getInt("an"),
                            rs.getInt("luna"),
                            rs.getInt("zi"),
                            rs.getInt("ora"),
                            rs.getInt("minut")
                    )
            );
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<Comment> readCommentsFromPostWithId(int postId) {

        ArrayList<Comment> comentariilePostarii = new ArrayList<>();
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM Comment WHERE post_id =" + postId);
            while (rs.next()) {
                comentariilePostarii.add(extractCommentFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comentariilePostarii;
    }

    public Comment readById(int commentIdDorit) {

        Comment commentCitit = null;
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM Comment WHERE id = " + commentIdDorit);
            rs.next();
            commentCitit = extractCommentFromResultSet(rs);
        } catch (SQLException e) {
            return null;
        }
        return commentCitit;
    }

    public int creat(int post_id, int user_id, String mesaj, LocalDateTime createdAt) {

        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "INSERT INTO Comment (post_id , user_id , mesaj , an , luna , zi , ora , minut) " +
                    "VALUES ( %d , %d ,'%s', %d , %d , %d , %d , %d)";
            return st.executeUpdate(String.format(template,
                    post_id,
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

    public int update(int commentId, String mesajNou) {

        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "UPDATE Comment SET mesaj = '%s' WHERE id = %d;";
            return st.executeUpdate(String.format(template, mesajNou, commentId));
        } catch (SQLException e) {
            return 0;
        }
    }

    public int delete(int idDorit) {

        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "DELETE FROM Comment WHERE id = %d;";
            return st.executeUpdate(String.format(template, idDorit));
        } catch (SQLException e) {
            return 0;
        }
    }

}
