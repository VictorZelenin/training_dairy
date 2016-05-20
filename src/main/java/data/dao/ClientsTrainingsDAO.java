package data.dao;

import data.data_sets.Client;
import data.data_sets.Training;
import database_service.executor.Executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 19.05.16.
 */
public class ClientsTrainingsDAO {

    private static final String INSERT = "insert into clients_trainings values (?, ?)";
    private static final String SELECT_BY_CLIENT = "select `training_id` from clients_trainings" +
            " where client_id = ";

    private Connection connection;

    public ClientsTrainingsDAO(Connection connection) {
        this.connection = connection;
    }

    public int addTrainingToClient(Client client, Training training) {
        int updated = 0;

        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setLong(1, client.getId());
            statement.setLong(2, training.getId());

            statement.execute();

            updated = statement.getUpdateCount();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updated;
    }

    public List<Training> getClientTrainings(Client client) {
        List<Training> clientTrainings = new ArrayList<>();
        TrainingDAO trainingDAO = new TrainingDAO(connection);

        Executor.executeQuery(connection, SELECT_BY_CLIENT + client.getId(), result -> {

            while (result.next()) {
                Training training = trainingDAO.get(result.getLong(1));
                clientTrainings.add(training);
            }
            return clientTrainings;
        });

        return clientTrainings;
    }

    // TODO to test delete method
    public int deleteClientTraining(Client client, Training training) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("delete from clients_trainings where client_id = ");
        queryBuilder.append(client.getId());
        queryBuilder.append(" and training_id = ");
        queryBuilder.append(training.getId());


        return Executor.executeUpdate(connection, queryBuilder.toString());
    }
}


