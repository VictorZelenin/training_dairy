package controllers.windows.additional_windows;

import static controllers.windows.main_windows.StartSceneController.CURRENT_CLIENT;

import data.dao.ClientDAO;
import data.data_sets.Client;
import database_service.database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Created by victor on 19.05.16.
 */
public class UpdateSceneController {

    private static final Database DATABASE = Database.getInstance();

    @FXML
    private TextField name;

    @FXML
    private TextField login;

    @FXML
    private TextField age;

    @FXML
    private TextField weight;

    @FXML
    private void updateInfo(ActionEvent event) {
        validate();

        Client client = new Client(CURRENT_CLIENT.getId(), name.getText(), login.getText(),
                CURRENT_CLIENT.getPassword(), CURRENT_CLIENT.getGender(), Integer.parseInt(age.getText()),
                Double.parseDouble(weight.getText()));

        ClientDAO clientDAO = new ClientDAO(DATABASE.getConnection());

        clientDAO.updateClientInfo(client);

        //TODO ask permission
        close(event);
    }

    //TODO to write validation
    private void validate() {
    }

    @FXML
    private void close(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

}
