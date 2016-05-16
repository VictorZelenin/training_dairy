package data.dao;

import data.data_sets.Exercise;
import database_service.executor.Executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 15.05.16.
 */
public class ExerciseDAO extends DAO<Exercise> {

    private static final String SELECT_ALL = "select * from exercise";
    private static final String SELECT_BY_ID = "select * from exercise where id = ";
    private static final String INSERT = "insert into client(`name`) values(?)";
    private static final String DELETE_BY_ID = "delete from exercise where id = ";
    private static final String DELETE_ALL = "delete from exercise";


    public ExerciseDAO(Connection connection) {
        super(connection);
    }


    @Override
    public Exercise get(long id) {

        return Executor.executeQuery(connection, SELECT_BY_ID + id, result -> {
            result.next();

            return new Exercise(result.getLong(1), result.getString(2));
        });
    }

    @Override
    public List<Exercise> getAll() {
        List<Exercise> exercises = new ArrayList<>();

        Executor.executeQuery(connection, SELECT_ALL, result -> {

            while (result.next()) {
                exercises.add(new Exercise(result.getLong(1), result.getString(2)));
            }

            return exercises;
        });

        return exercises;
    }

    @Override
    public int save(Exercise object) {
        int updated = 0;

        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {

            statement.setString(1, object.getExerciseName());
            statement.execute();

            updated = statement.getUpdateCount();

        } catch (SQLException e) {
            System.err.println("Can not add: " + object);
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
