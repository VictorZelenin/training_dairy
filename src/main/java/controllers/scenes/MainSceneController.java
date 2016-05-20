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
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by victor on 17.05.16.
 */
public class MainSceneController implements Initializable {

    private static final Database database = Database.getInstance();
    private static final int NUMBER_OF_CLIENTS = 2;

    @FXML
    private AnchorPane root;

    @FXML
    private SplitPane splitPane;

    @FXML
    private AnchorPane dynamicPane;

    @FXML
    private ListView<String> listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> favouritesTrainingsNames =
                DataInfo.getFavouritesTrainings(database, NUMBER_OF_CLIENTS).stream()
                        .map(Training::getTrainingName)
                        .collect(Collectors.toList());

        listView.setItems(FXCollections.observableList(favouritesTrainingsNames));
    }

    @FXML
    public void showPersonalData(ActionEvent event) {
        Scene scene;
        Stage stage;

        String fxmlFile = "/scenes/personal_data.fxml";
        FXMLLoader loader = new FXMLLoader();

        Parent parent = null;
        try {
            parent = loader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(parent);
        scene = new Scene(parent);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }

    @FXML
    public void showTrainings(ActionEvent event) {
        Scene scene;
        Stage stage;

        String fxmlFile = "/scenes/training_scene.fxml";
        FXMLLoader loader = new FXMLLoader();

        Parent parent = null;
        try {
            parent = loader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(parent);
        scene = new Scene(parent);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

    }

    @FXML
    public void showResults(ActionEvent event) {
        Scene scene;
        Stage stage;

        String fxmlFile = "/scenes/results_scene.fxml";
        FXMLLoader loader = new FXMLLoader();

        Parent parent = null;
        try {
            parent = loader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(parent);
        scene = new Scene(parent);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }

    @FXML
    public void showStatistics(ActionEvent event) {
        Scene scene;
        Stage stage;

        String fxmlFile = "/scenes/statistic_scene.fxml";
        FXMLLoader loader = new FXMLLoader();

        Parent parent = null;
        try {
            parent = loader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(parent);
        scene = new Scene(parent);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
    }

    @FXML
    private void exit(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }


}
