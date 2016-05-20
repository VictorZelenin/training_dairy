package data.dao;

import data.data_sets.Training;
import database_service.database.Database;
import database_service.executor.Executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 15.05.16.
 */
public class TrainingDAO extends DAO<Training> {

    private static final String SELECT_ALL = "select * from training";
    private static final String SELECT_BY_ID = "select * from training where id = ";
    private static final String INSERT = "insert into training(`training_name`) values(?)";
    private static final String DELETE_BY_ID = "delete from training where id = ";
    private static final String DELETE_ALL = "delete from training";

    public TrainingDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Training get(long id) {
        ClientDAO client = new ClientDAO(connection);

        return Executor.executeQuery(connection, SELECT_BY_ID + id, result -> {
            result.next();
            return new Training(result.getLong("id"), result.getString("training_name"));
        });
    }

    public Training getByName(String name) {

        StringBuilder query = new StringBuilder();
        query.append("select * from training where training_name = '")
                .append(name)
                .append("'");

        return Executor.executeQuery(connection, query.toString(), result -> {
            result.next();
            return new Training(result.getLong(1), result.getString(2));
        });
    }

    @Override
    public List<Training> getAll() {
        List<Training> trainings = new ArrayList<>();
        ClientDAO client = new ClientDAO(connection);

        Executor.executeQuery(connection, SELECT_ALL, resultSet -> {

            while (resultSet.next()) {
                trainings.add(new Training(resultSet.getLong("id"), resultSet.getString("training_name")));
            }

            return trainings;
        });

        return trainings;
    }

    @Override
    public int save(Training object) {
        int updated = 0;

        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {

            statement.setString(1, object.getTrainingName());

            statement.execute();

            updated = statement.getUpdateCount();

        } catch (SQLException e) {
            System.err.println("Can not add " + object);
            e.printStackTrace();
        }

        return updated;
    }

    @Override
    public int delete(long id) {
        return Executor.executeUpdate(connection, DELETE_BY_ID + id);
    }

    @Override
    public int deleteAll() {
        return Executor.executeUpdate(connection, DELETE_ALL);
    }
}
