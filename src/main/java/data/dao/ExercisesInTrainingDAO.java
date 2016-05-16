package data.dao;

import data.data_sets.Exercise;
import data.data_sets.Training;
import database_service.executor.Executor;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by victor on 16.05.16.
 */
public class ExercisesInTrainingDAO {

    private static final String INSERT = "insert into `exercises_in_training`" +
            "values(?, ?)";

    private static final String SELECT_BY_TRAINING = "select `exercise_id` from exercises_in_training" +
            " where `training_id` = ";

    private static final String DELETE_BY_ID = "delete from exercises_in_training where `training_id` = ";
    private static final String DELETE_ALL = "delete from exercises_in_training";

    private Connection connection;

    public ExercisesInTrainingDAO(Connection connection) {
        this.connection = connection;
    }

    public int addExercisesInTraining(Training training, List<Exercise> exercises) {
        int updated = 0;

        for (Exercise exercise : exercises) {

            try (PreparedStatement statement = connection.prepareStatement(INSERT)) {

                statement.setLong(1, training.getId());
                statement.setLong(2, exercise.getId());

                statement.execute();

                updated += statement.getUpdateCount();

            } catch (SQLException e) {
                System.err.println("Can not add exercise: " + exercise);
                e.printStackTrace();
            }

        }

        return updated;
    }

    public List<Exercise> getExercisesInTraining(Training training) {
        List<Exercise> exercises = new ArrayList<>();
        ExerciseDAO exerciseDAO = new ExerciseDAO(connection);

        Executor.executeQuery(connection, SELECT_BY_TRAINING + training.getId(), result -> {

            while (result.next()) {
                exercises.add(exerciseDAO.get(result.getLong("exercise_id")));
            }

            return exercises;
        });

        return exercises;
    }


    public Map<Training, List<Exercise>> getExercisesInTrainings() {

        Map<Training, List<Exercise>> exercisesInTraining = new HashMap<>();

        TrainingDAO trainingDAO = new TrainingDAO(connection);
        ExercisesInTrainingDAO exercisesInTrainingDAO = new ExercisesInTrainingDAO(connection);

        List<Training> trainings = trainingDAO.getAll();

        for (Training training : trainings) {
            List<Exercise> exercises = exercisesInTrainingDAO.getExercisesInTraining(training);

            exercisesInTraining.put(training, exercises);
        }

        return exercisesInTraining;
    }

    public int delete(long id) {
        return Executor.executeUpdate(connection, DELETE_BY_ID + id);
    }

    public int deleteAll() {
        return Executor.executeUpdate(connection, DELETE_ALL);
    }
}
