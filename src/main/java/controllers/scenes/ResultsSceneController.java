package controllers.scenes;

import static controllers.windows.main_windows.StartSceneController.CURRENT_CLIENT;

import data.dao.TrainingResultsDAO;
import data.data_sets.TrainingsResult;
import database_service.database.Database;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * Created by victor on 20.05.16.
 */
public class ResultsSceneController implements Initializable {

    private static final MainSceneController CONTROLLER = new MainSceneController();
    private static final Database DATABASE = Database.getInstance();

    @FXML
    private TableView<TrainingsResult> tableView;

    @FXML
    private TableColumn<TrainingsResult, Date> dateColumn;

    @FXML
    private TableColumn<TrainingsResult, String> exerciseColumn;

    @FXML
    private TableColumn<TrainingsResult, Integer> approachesColumn;

    @FXML
    private TableColumn<TrainingsResult, Integer> repetitionsColumn;

    @FXML
    private TableColumn<TrainingsResult, Integer> realWeightColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TrainingResultsDAO resultsDAO = new TrainingResultsDAO(DATABASE.getConnection());

        System.out.println(CURRENT_CLIENT.getId());


        tableView.setItems(
                FXCollections.observableArrayList(resultsDAO.getList(CURRENT_CLIENT.getId())));

        dateColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getDate()));
        exerciseColumn.setCellValueFactory(
                p -> new SimpleObjectProperty<>(p.getValue().getExercise().getExerciseName()));
        approachesColumn.setCellValueFactory(
                p -> new SimpleObjectProperty<>(p.getValue().getApproaches()));
        repetitionsColumn.setCellValueFactory(
                p -> new SimpleObjectProperty<>(p.getValue().getRepetitions()));
        realWeightColumn.setCellValueFactory(
                p -> new SimpleObjectProperty<>(p.getValue().getRealTrainingWeight()));

    }

    @FXML
    public void showPersonalData(ActionEvent event) {
        CONTROLLER.showPersonalData(event);
    }

    @FXML
    public void showTrainings(ActionEvent event) {
        CONTROLLER.showTrainings(event);
    }

    public void addNewResult(ActionEvent event) {
        Stage stage = new Stage();

        String fxmlFile = "/windows/add_result.fxml";
        FXMLLoader loader = new FXMLLoader();

        Parent root = null;
        try {
            root = loader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Оновлення персональної інформації");

        stage.showAndWait();

    }

    @FXML
    public void showStatistics(ActionEvent event) {
        CONTROLLER.showStatistics(event);
    }

    @FXML
    private void exit(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }
}
