import data.dao.ClientDAO;
import data.dao.DAO;
import data.dao.TrainingDAO;
import data.dao.TrainingResultsDAO;
import data.data_sets.Client;
import data.data_sets.Training;
import data.data_sets.TrainingsResult;
import data.enums.Gender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by victor on 15.05.16.
 */
public class Main {

    static final String URL = "jdbc:mysql://localhost:3306/training_dairy";
    static final String USERNAME = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args) {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);


        } catch (SQLException e) {
            System.err.println("Немає з’єднання із базою");
            e.printStackTrace();
        }

//        DAO<Client> clientDAO = new ClientDAO(connection);

        DAO<Training> trainingDAO = new TrainingDAO(connection);
        DAO<TrainingsResult> resultDAO = new TrainingResultsDAO(connection);
        Client client = new Client("Mikola", "bandera22@ukr.net", "slavaukraini", Gender.MAN, 19, 78.4);
        Client masha = new Client(7, "Маша", "mariya@mail.ru", "12345qwerty", Gender.WOMAN, 16, 55.3);

        try {
            System.out.println(trainingDAO.getAll());
            System.out.println(resultDAO.getAll());
//            System.out.println(trainingDAO.save(new Training(3, "Вправи для дівчат", masha)));
//            System.out.println(trainingDAO.delete(3));
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
