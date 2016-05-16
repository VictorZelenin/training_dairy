package database_service.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by victor on 16.05.16.
 */
public class Database {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/training_dairy";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Database instance;

    private Connection connection;

    private Database() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Can not create a connection with DB!");
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        return instance == null ? new Database() : instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void shutDownDatabase() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Can not close database!");
            e.printStackTrace();
        }
    }
}
