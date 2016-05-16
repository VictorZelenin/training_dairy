package data;

import data.dao.ExercisesInTrainingDAO;
import data.dao.TrainingDAO;
import data.data_sets.Exercise;
import data.data_sets.Training;
import database_service.database.Database;
import database_service.executor.Executor;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by victor on 16.05.16.
 */
public class DataInfo {
    private static final String SELECT_BY_LOGIN_AND_PASSWORD = "select id from client where login = ?" +
            "and password = ?";


    public static boolean isThisClientInDatabase(Connection connection, String login, String password) {
        boolean result;

        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();

            result = resultSet.next();

            resultSet.close();

        } catch (SQLException e) {
            System.err.println("Bad login or register");
            e.printStackTrace();
            return false;
        }

        return result;
    }
}
