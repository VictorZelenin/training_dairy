package executor;

import java.sql.*;

/**
 * Created by victor on 15.05.16.
 */
public class Executor {

    public static int executeUpdate(Connection connection, String query) {
//        Statement statement = null;
        int updated = 0;

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            updated = statement.getUpdateCount();

        } catch (SQLException e) {
            System.err.println("Executor:executeUpdate can not execute query: " + query);
            e.printStackTrace();
        }

        return updated;
    }


    public static <T> T executeQuery(Connection connection, String query, ResultHandler<T> handler) {

        T value = null;

        try (Statement statement = connection.createStatement()) {

            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();

            value = handler.handle(resultSet);

        } catch (SQLException e) {
            System.err.println("Can not execute query: " + query);
            e.printStackTrace();
        }

        return value;
    }

}
