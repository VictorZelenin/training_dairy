package controllers.windows.additional_windows;

import static controllers.windows.main_windows.StartSceneController.CURRENT_CLIENT;

import data.dao.ExerciseDAO;
import data.dao.TrainingResultsDAO;
import data.data_sets.Exercise;
import data.data_sets.TrainingsResult;
import database_service.database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * Created by victor on 20.05.16.
 */
//TODO to show notification when clicked on add button
public class ResultController implements Initializable {

    private static final Database DATABASE = Database.getInstance();

    private Exercise exercise;

    @FXML
    private DatePicker date;

    @FXML
    private ChoiceBox<String> exercises;

    @FXML
    private TextField approaches;

    @FXML
    private TextField repetitions;

    @FXML
    private TextField expectedWeight;

    @FXML
    private TextField realWeight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExerciseDAO exerciseDAO = new ExerciseDAO(DATABASE.getConnection());

        for (Exercise exercise : exerciseDAO.getAll()) {
            exercises.getItems().add(exercise.getExerciseName());
        }
    }

    @FXML
    public void addNewResult(ActionEvent event) {
        validate();

        TrainingsResult result;
        TrainingResultsDAO resultsDAO = new TrainingResultsDAO(DATABASE.getConnection());
        ExerciseDAO exerciseDAO = new ExerciseDAO(DATABASE.getConnection());


        exerciseDAO.getAll().stream()
                .filter(ex -> ex.getExerciseName()
                        .equals(exercises.getValue())).forEach(ex -> exercise = ex);

        System.out.println(date.getEditor().getText());

        result = new TrainingsResult(parseSQLDate(), CURRENT_CLIENT, exercise,
                Integer.parseInt(approaches.getText()), Integer.parseInt(repetitions.getText()),
                Integer.parseInt(expectedWeight.getText()), Integer.parseInt(realWeight.getText()));

        resultsDAO.save(result);

        showNotification();
    }

    //TODO to write validation!
    private void validate() {
    }

    private void showNotification() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат додано");
        alert.setHeaderText("Додайте ще один результат.");
        alert.setContentText("Або тисніть кнопку \"Закрити\".");

        alert.showAndWait();

    }

    private Date parseSQLDate() {
        Date sqlDate = null;

        //dd.mm.yyyy -> yyyy-mm-dd
        String dateAsString = date.getEditor().getText();
        StringBuilder builder = new StringBuilder();

        builder.append(dateAsString.charAt(6))
                .append(dateAsString.charAt(7))
                .append(dateAsString.charAt(8))
                .append(dateAsString.charAt(9))
                .append("-")
                .append(dateAsString.charAt(3))
                .append(4)
                .append("-")
                .append(dateAsString.charAt(0))
                .append(dateAsString.charAt(1));

        sqlDate = Date.valueOf(builder.toString());
        System.out.println(sqlDate);


        return sqlDate;
    }

    @FXML
    public void close(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    public static void main(String[] args) {
        //\///? System.out.println(day);
    }

}
