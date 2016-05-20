package controllers.windows.additional_windows;

import controllers.windows.main_windows.StartSceneController;
import data.dao.ClientsTrainingsDAO;
import data.dao.ExerciseDAO;
import data.dao.ExercisesInTrainingDAO;
import data.dao.TrainingDAO;
import data.data_sets.Exercise;
import data.data_sets.Training;
import database_service.database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by victor on 19.05.16.
 */
public class CreateNewTrainingController implements Initializable {

    private static final Database DATABASE = Database.getInstance();

    private List<Exercise> exercisesInTraining;

    @FXML
    private TextField trainingName;

    @FXML
    private TextField exerciseName;

    @FXML
    private ChoiceBox<String> exercises;

    private ExerciseDAO exerciseDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exerciseDAO = new ExerciseDAO(DATABASE.getConnection());
        exercisesInTraining = new ArrayList<>();

        for (Exercise exercise : exerciseDAO.getAll()) {
            exercises.getItems().add(exercise.getExerciseName());
        }
    }

    @FXML
    public void addExerciseToTraining(ActionEvent event) {
        exercisesInTraining.addAll(exerciseDAO.getAll().stream()
                .filter(exercise -> exercise.getExerciseName().equals(exercises.getValue()))
                .collect(Collectors.toList()));
    }

    @FXML
    public void createAndAddExercise(ActionEvent event) {
        validate();
        Exercise exercise = new Exercise(exerciseName.getText());
        exerciseName.clear();
        exerciseDAO.save(exercise);
        exercisesInTraining.add(exercise);

    }

    // TODO to write validation
    private void validate() {
    }


    static long id;

    @FXML
    public void createNewTraining(ActionEvent event) {
        validate();
        TrainingDAO trainingDAO = new TrainingDAO(DATABASE.getConnection());
        ExercisesInTrainingDAO exercisesInTrainingDAO =
                new ExercisesInTrainingDAO(DATABASE.getConnection());
        ClientsTrainingsDAO clientsTrainingsDAO =
                new ClientsTrainingsDAO(DATABASE.getConnection());

        Training training = new Training(trainingName.getText());


        trainingDAO.save(training);
        trainingDAO.getAll().stream().
                filter(tr -> tr.getTrainingName().equals(trainingName.getText()))
                .forEach(tr -> id = tr.getId());


        exercisesInTrainingDAO.addExercisesInTraining(trainingDAO.get(id), exercisesInTraining);

        clientsTrainingsDAO.addTrainingToClient(StartSceneController.CURRENT_CLIENT, trainingDAO.get(id));

        close(event);

    }


    @FXML
    public void close(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }
}
