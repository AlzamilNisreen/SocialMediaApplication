package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
    static String url = "jdbc:mysql://localhost:3306/javaDatabase";
    static String username = "root";
    static String password = "Nisreen_1234";

    private static ConnectionSingleton instance = null;
    private Connection connection = null;

    private ConnectionSingleton() {

    }

    public static ConnectionSingleton getInstance() {
        if (instance == null)
            instance = new ConnectionSingleton();
        return instance;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.out.println("Date de conectare este invalide");
            }
        }
        return connection;
    }
}
