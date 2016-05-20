package controllers.scenes;

import static controllers.windows.main_windows.StartSceneController.CURRENT_CLIENT;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by victor on 19.05.16.
 */
// TODO to add right-click on training list
public class PersonalDataController implements Initializable {

    private static final Database DATABASE = Database.getInstance();
    private static final MainSceneController MAIN_CONTROLLER = new MainSceneController();

    @FXML
    private Label userName;

    @FXML
    private Label name;

    @FXML
    private Label userAge;

    @FXML
    private Label userWeight;

    @FXML
    private ListView<String> trainingList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userName.setText(userName.getText() + CURRENT_CLIENT.getLogin());

        name.setText(CURRENT_CLIENT.getName());
        userAge.setText(String.valueOf(CURRENT_CLIENT.getAge()));
        userWeight.setText(String.valueOf(CURRENT_CLIENT.getWeight()));

        List<String> trainingsNames =
                DataInfo.getClientTrainings(DATABASE, CURRENT_CLIENT)
                        .stream()
                        .map(Training::getTrainingName)
                        .collect(Collectors.toList());

        trainingList.setItems(FXCollections.observableList(trainingsNames));
    }

    @FXML
    private void exit(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void update(ActionEvent event) {
        Stage stage = new Stage();

        String fxmlFile = "/windows/update_data.fxml";
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
    public void showTrainings(ActionEvent event) {
        MAIN_CONTROLLER.showTrainings(event);
    }

    @FXML
    public void showResults(ActionEvent event) {
        MAIN_CONTROLLER.showResults(event);
    }

    @FXML
    public void showStatistics(ActionEvent event) {
        MAIN_CONTROLLER.showStatistics(event);
    }
}
