package controllers.windows.main_windows;

import data.dao.ClientDAO;
import data.data_sets.Client;
import data.enums.Gender;
import database_service.database.Database;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Created by victor on 16.05.16.
 */
public class RegistrationSceneController {

    private static final Database DATABASE = Database.getInstance();

    @FXML
    private TextField userName;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private TextField age;

    @FXML
    private TextField weight;

    @FXML
    private RadioButton man;

    @FXML
    public void register(ActionEvent event) {
        validate();

        Client client = new Client(userName.getText(), login.getText(), password.getText(),
                getGender(), Integer.parseInt(age.getText()), Double.parseDouble(weight.getText()));

        ClientDAO clientDAO = new ClientDAO(DATABASE.getConnection());

        clientDAO.save(client);

        exit(event);
    }

    @FXML
    public void exit(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }


    private Gender getGender() {
        return man.isSelected() ? Gender.MAN : Gender.WOMAN;
    }

    // TODO validation on registration frame!
    private void validate() {
    }


}
