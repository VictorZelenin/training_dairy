package data.dao;

import data.data_sets.Client;
import data.data_sets.Training;
import data.data_sets.TrainingsResult;
import database_service.database.Database;
import database_service.executor.Executor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 15.05.16.
 */
public class TrainingResultsDAO extends DAO<TrainingsResult> {

    private static final String SELECT_ALL = "select * from result";
    private static final String SELECT_BY_ID = "select * from result where id = ";
    private static final String INSERT = "insert into result(`date`, `client_id`, `exercise_id`, " +
            "`approaches`, `repeatings`, `expected_training_weight`, `real_training_weight`) " +
            "values(?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_CLIENT = "select * from result where client_id = ";

    private static final String DELETE_BY_ID = "delete from exercise where id = ";
    private static final String DELETE_ALL = "delete from exercise";


    public TrainingResultsDAO(Connection connection) {
        super(connection);
    }


    @Override
    public TrainingsResult get(long id) {
        ClientDAO client = new ClientDAO(connection);
        ExerciseDAO exercise = new ExerciseDAO(connection);

        return Executor.executeQuery(connection, SELECT_BY_ID + id, result -> {
            result.next();


            return new TrainingsResult(result.getLong(1), result.getDate(2), client.get(result.getLong(3)),
                    exercise.get(result.getLong(4)), result.getInt(5), result.getInt(6),
                    result.getInt(7), result.getInt(8));
        });
    }

    public List<TrainingsResult> getList(long id) {
        List<TrainingsResult> results = new ArrayList<>();

        ClientDAO client = new ClientDAO(connection);
        ExerciseDAO exercise = new ExerciseDAO(connection);
        System.out.println(id);
        return Executor.executeQuery(connection, SELECT_BY_CLIENT + id, result -> {
            while (result.next()) {
                System.out.println(result.getLong(1));
                results.add(new TrainingsResult(result.getLong(1), result.getDate(2), client.get(result.getLong(3)),
                        exercise.get(result.getLong(4)), result.getInt(5), result.getInt(6),
                        result.getInt(7), result.getInt(8)));
            }
            return results;
        });
    }

    public List<TrainingsResult> getByClient(Client client) {
        List<TrainingsResult> results = new ArrayList<>();
        ClientDAO clientDAO = new ClientDAO(connection);
        ExerciseDAO exercise = new ExerciseDAO(connection);

        return Executor.executeQuery(connection, SELECT_BY_CLIENT + client.getId(),
                result -> {
                    while (result.next()) {
                        new TrainingsResult(result.getLong(1), result.getDate(2),
                                clientDAO.get(result.getLong(3)),
                                exercise.get(result.getLong(4)), result.getInt(5), result.getInt(6),
                                result.getInt(7), result.getInt(8));
                    }
                    return results;

                });
    }

    @Override
    public List<TrainingsResult> getAll() {
        List<TrainingsResult> results = new ArrayList<>();

        ClientDAO clientDAO = new ClientDAO(connection);
        ExerciseDAO exerciseDAO = new ExerciseDAO(connection);

        Executor.executeQuery(connection, SELECT_ALL, resultSet -> {

            while (resultSet.next()) {
                results.add(new TrainingsResult(resultSet.getLong(1), resultSet.getDate(2),
                        clientDAO.get(resultSet.getLong(3)), exerciseDAO.get(resultSet.getLong(4)),
                        resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8)));
            }

            return results;
        });

        return results;
    }

    @Override
    public int save(TrainingsResult object) {
        int updated = 0;

        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {

            statement.setDate(1, object.getDate());
            statement.setLong(2, object.getClient().getId());
            statement.setLong(3, object.getExercise().getId());
            statement.setInt(4, object.getApproaches());
            statement.setInt(5, object.getRepetitions());
            statement.setInt(6, object.getExpectedTrainingWeight());
            statement.setInt(7, object.getRealTrainingWeight());

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


    public static void main(String[] args) {
        Database database = Database.getInstance();

        TrainingResultsDAO dao = new TrainingResultsDAO(database.getConnection());

        System.out.println(dao.getList(15));
    }
}
