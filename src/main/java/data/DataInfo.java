package data;

import controllers.windows.main_windows.StartSceneController;
import data.dao.ClientDAO;
import data.dao.ExerciseDAO;
import data.dao.TrainingDAO;
import data.data_sets.Client;
import data.data_sets.Exercise;
import data.data_sets.Training;
import data.data_sets.TrainingsResult;
import data.enums.Gender;
import database_service.database.Database;
import database_service.executor.Executor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by victor on 16.05.16.
 */
public class DataInfo {
    private static final String SELECT_BY_LOGIN_AND_PASSWORD = "select * from client where login = ?" +
            " and password = ?";

    private static final String SELECT_FAVOURITES_TRAININGS = "select `training_id`, training_name," +
            " count(client_id) from clients_trainings join training on id = training_id" +
            " group by training_id having count(client_id) >= ";

    private static final String SELECT_CLIENT_TRAININGS = "select training_id from " +
            " clients_trainings where client_id = ";

    private static final String SELECT_RECOMMENDED_TRAININGS = "select training_id from clients_trainings" +
            " join training on id = training_id join client on client.id = client_id " +
            "where gender = ? and age between ? and ? and weight between ? and ?";

    private static final String INSERT_EXERCISES_IN_TRAINING = "insert into exercises_in_training " +
            " values(?, ?)";

    private static final String SELECT_ALL_EXERCISES_IN_RESULT_BY_CLIENT = "select exercise_id, " +
            " count(exercise_id) from result where client_id = ? group by exercise_id";

    private static final String SELECT_RESULTS_OF_TRAININGS_BY_CLIENT = "select date, real_training_weight" +
            " from result where client_id = ? and exercise_id = ? order by 1";


    public static boolean isThisClientInDatabase(Database database, String login, String password) {
        StartSceneController.CURRENT_CLIENT = getClientByLoginAndPassword(database, login, password);
        return StartSceneController.CURRENT_CLIENT != null;
    }

    public static Client getClientByLoginAndPassword(Database database, String login, String password) {
        Client client = null;

        try (PreparedStatement statement =
                     database.getConnection().prepareStatement(SELECT_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            statement.execute();

            ResultSet result = statement.getResultSet();
            result.next();

            client = new Client(result.getLong(1), result.getString(2), result.getString(3),
                    result.getString(4), getGender(result.getString(5)), result.getInt(6),
                    result.getDouble(7));

            result.close();

        } catch (SQLException e) {
            System.err.println("Bad login or need to register");

        }

        return client;
    }

    private static Gender getGender(String gender) {
        if (gender.toLowerCase().equals("man")
                || gender.toLowerCase().equals("мужской")
                || gender.toLowerCase().equals("чоловіча"))
            return Gender.MAN;
        else
            return Gender.WOMAN;
    }

    public static List<Training> getFavouritesTrainings(Database database, int numberOfClients) {
        List<Training> trainings = new ArrayList<>();
        TrainingDAO trainingDAO = new TrainingDAO(database.getConnection());

        Executor.executeQuery(database.getConnection(), SELECT_FAVOURITES_TRAININGS + numberOfClients,
                result -> {

                    while (result.next()) {
                        trainings.add(trainingDAO.get(result.getLong(1)));
                    }

                    return trainings;
                });


        return trainings;
    }

    public static List<Training> getClientTrainings(Database database, Client client) {
        List<Training> trainings = new ArrayList<>();
        TrainingDAO trainingDAO = new TrainingDAO(database.getConnection());

        Executor.executeQuery(database.getConnection(), SELECT_CLIENT_TRAININGS + client.getId(),
                result -> {

                    while (result.next()) {
                        Training training = trainingDAO.get(result.getLong(1));
                        trainings.add(training);
                    }

                    return trainings;
                });

        return trainings;
    }

    public static List<Training> getRecommendedTrainings(Database database, Client client,
                                                         int ageDifference, double weightDifference) {
        List<Training> trainings = new ArrayList<>();
        TrainingDAO trainingDAO = new TrainingDAO(database.getConnection());

        try (PreparedStatement statement = database.getConnection()
                .prepareStatement(SELECT_RECOMMENDED_TRAININGS)) {

            statement.setString(1, getSex(client.getGender()));

            statement.setInt(2, client.getAge() - ageDifference);
            statement.setInt(3, client.getAge() + ageDifference);

            statement.setDouble(4, client.getWeight() - weightDifference);
            statement.setDouble(5, client.getWeight() + weightDifference);

            statement.execute();

            ResultSet result = statement.getResultSet();

            while (result.next()) {
                Training training = trainingDAO.get(result.getLong(1));

                trainings.add(training);
            }

            result.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return trainings;
    }

    private static String getSex(Gender gender) {
        return gender == Gender.WOMAN ? "woman" : "man";
    }

    public static Map<Exercise, Integer> getTrainingFrequencyMap(Database database, Client client) {
        Map<Exercise, Integer> resultMap = new HashMap<>();
        ExerciseDAO exerciseDAO = new ExerciseDAO(database.getConnection());
        try (PreparedStatement statement =
                     database.getConnection().prepareStatement(SELECT_ALL_EXERCISES_IN_RESULT_BY_CLIENT)) {
            statement.setLong(1, client.getId());
            statement.execute();

            ResultSet result = statement.getResultSet();

            while (result.next()) {
                resultMap.put(exerciseDAO.get(result.getLong(1)), result.getInt(2));
            }

            result.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultMap;
    }

    public static Map<String, Integer> getExerciseByDate(Database database, Client client, Exercise exercise) {
        Map<String, Integer> resultMap = new HashMap<>();
        String queryBuilder = "select date, real_training_weight from result where client_id = " +
                client.getId() +
                " and exercise_id = " +
                exercise.getId() +
                " order by 1";


        return Executor.executeQuery(database.getConnection(), queryBuilder,
                result -> {
                    while (result.next()) {
                        resultMap.put(result.getDate(1).toString(), result.getInt(2));
                    }

                    return resultMap;
                });
    }

    public static List<Exercise> getResultExercises(Database database, Client client) {
        List<Exercise> exercises = new ArrayList<>();
        ExerciseDAO exerciseDAO = new ExerciseDAO(database.getConnection());
        String query = "select distinct exercise_id from result where client_id = " +
                client.getId();

        return Executor.executeQuery(database.getConnection(), query,
                result -> {
                    while (result.next()) {
                        exercises.add(exerciseDAO.get(result.getLong(1)));
                    }

                    return exercises;
                });

    }

    public static Map<String, Integer> trainingsResults(Database database, Client client,
                                                        Exercise exercise) {
        Map<String, Integer> results = new HashMap<>();

        StringBuilder query = new StringBuilder();
        query.append("select date, real_training_weight from result " +
                "where client_id = ")
                .append(client.getId())
                .append(" and exercise_id = ")
                .append(exercise.getId())
                .append(" order by 2");

        return Executor.executeQuery(database.getConnection(), query.toString(),
                result -> {
                    while (result.next()) {
                        results.put(result.getDate(1).toString(), result.getInt(2));
                    }

                    return results;
                });
    }

}
