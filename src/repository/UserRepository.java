package repository;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserRepository {

    private static User extractUserFromResultSet(ResultSet rs) throws SQLException {

        return new User(
                rs.getInt("id"),
                rs.getString("nume"),
                rs.getString("prenume"),
                rs.getString("email"),
                rs.getString("numarTelefon"),
                new ArrayList<>()
        );
    }

    public ArrayList<User> readAll() {

        ArrayList<User> allUsers = new ArrayList<>();
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM User;");
            while (rs.next()) {
                allUsers.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    public User readById(int idDorit) {

        User userCitit = null;
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM User WHERE id = " + idDorit);
            rs.next();
            userCitit = extractUserFromResultSet(rs);
        } catch (SQLException e) {
            return null;
        }
        return userCitit;
    }

    public int creat(String nume, String prenume, String email, String numarTelefon) {

        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "INSERT INTO User (nume,prenume,email,numarTelefon) VALUES ('%s','%s','%s','%s')";
            return st.executeUpdate(String.format(template, nume, prenume, email, numarTelefon));
        } catch (SQLException e) {
            return 0;
        }
    }

    private static void modifyColumn(String columnName, int idDorit, String valoareNoua) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "UPDATE User SET %s = '%s' WHERE id = %d;";
            int affectedRows = st.executeUpdate(String.format(template, columnName, valoareNoua, idDorit));
            System.out.println(columnName + (affectedRows > 0 ? " modificat" : " nemodificat"));
        } catch (SQLException e) {
            System.out.println("Coloana nu a putut fi modificata");
        }
    }

    public void modifyNume(int idDorit, String numeNou) {
        modifyColumn("nume", idDorit, numeNou);
    }

    public void modifyPrenume(int idDorit, String prenumeNou) {
        modifyColumn("prenume", idDorit, prenumeNou);
    }

    public void modifyEmail(int idDorit, String emailNou) {
        modifyColumn("email", idDorit, emailNou);
    }

    public void modifyNumarDeTelefon(int idDorit, String nrTelefonNou) {
        modifyColumn("numarTelefon", idDorit, nrTelefonNou);
    }

    public int delete(int idDorit) {

        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "DELETE FROM User WHERE id = %d;";
            return st.executeUpdate(String.format(template, idDorit));
        } catch (SQLException e) {
            return 0;
        }
    }

}


