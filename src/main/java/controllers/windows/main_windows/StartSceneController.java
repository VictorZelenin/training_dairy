package controllers.windows.main_windows;

import data.DataInfo;
import data.data_sets.Client;
import database_service.database.Database;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by victor on 16.05.16.
 */
public class StartSceneController {

    // TODO think over about this static variable
    public static Client CURRENT_CLIENT;

    private static final Database DATABASE = Database.getInstance();

    @FXML
    private TextField userLogin;

    @FXML
    private TextField userPassword;

    @FXML
    private void handleRegistrationButton(ActionEvent event) {
        Stage stage = new Stage();

        String fxmlFile = "/registration_scene.fxml";
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
        stage.setTitle("Реєстраційна форма");

        stage.showAndWait();
    }

    @FXML
    private void handleEnterButton(ActionEvent event) {

        Stage stage;
        Scene scene;

        Button button = (Button) event.getSource();

        stage = (Stage) button.getScene().getWindow();

        String fxmlFile = "/scenes/main_scene.fxml";
        FXMLLoader loader = new FXMLLoader();

        Parent root = null;

        try {
            root = loader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (DataInfo.isThisClientInDatabase(DATABASE, userLogin.getText(), userPassword.getText())) {
            assert root != null;
            scene = new Scene(root);



            stage.setScene(scene);
            stage.setTitle("Main menu");
            stage.show();

        } else {
            showLoginError();
        }
    }

    private void showLoginError() {
        Alert loginAlert = new Alert(Alert.AlertType.WARNING);

        loginAlert.setTitle("Помилка");
        loginAlert.setHeaderText("Вказані дані невірні!");
        loginAlert.setContentText("Перевірте ще раз ваші логін та пароль.");

        loginAlert.showAndWait();
    }
}