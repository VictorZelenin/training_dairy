package controllers.scenes;

import data.DataInfo;
import data.data_sets.Training;
import database_service.database.Database;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static controllers.windows.main_windows.StartSceneController.CURRENT_CLIENT;

/**
 * Created by victor on 19.05.16.
 */
public class TrainingSceneController implements Initializable {

    private static final Database DATABASE = Database.getInstance();

    private static final MainSceneController CONTROLLER = new MainSceneController();
    @FXML
    private ListView<String> recommendTrainings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> trainingsNames =
                DataInfo.getRecommendedTrainings(DATABASE, CURRENT_CLIENT, 5, 10.0)
                        .stream()
                        .map(Training::getTrainingName)
                        .collect(Collectors.toList());

        recommendTrainings.setItems(FXCollections.observableList(trainingsNames));
    }

    @FXML
    public void showPersonalData(ActionEvent event) {
        CONTROLLER.showPersonalData(event);

    }

    @FXML
    public void createNewTraining(ActionEvent event) {
        Stage stage = new Stage();

        String fxmlFile = "/windows/create_new_training.fxml";
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
        stage.setTitle("Додайте власне тренування.");

        stage.showAndWait();

    }

    @FXML
    public void showResults(ActionEvent event) {
        CONTROLLER.showResults(event);
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
