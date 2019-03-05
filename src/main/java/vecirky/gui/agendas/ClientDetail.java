package vecirky.gui.agendas;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import vecirky.G;
import vecirky.controllers.ClientController;
import vecirky.exceptions.ModelCannotBeDeleted;
import vecirky.exceptions.ModelIsInvalid;
import vecirky.models.Client;

public class ClientDetail {

    private Client client;

    private ClientController controller = new ClientController();

    @FXML
    private TextField titleField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField varSymbolField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button deleteButton;

    public ClientDetail(Client client) {
        this.client = client;
    }

    public void initialize() {
        titleField.textProperty().set(client.getTitle());
        firstNameField.textProperty().set(client.getFirstName());
        lastNameField.textProperty().set(client.getLastName());
        emailField.textProperty().set(client.getEmail());
        varSymbolField.textProperty().set(client.getVarSymbol());
        descriptionArea.textProperty().set(client.getDescription());

        deleteButton.setDisable(client.getId() == null);
    }

    @FXML
    private void onSaveAction() {
        if (!validate()) return;

        client.setTitle(titleField.getText());
        client.setFirstName(firstNameField.getText());
        client.setLastName(lastNameField.getText());
        client.setEmail(emailField.getText());
        client.setVarSymbol(varSymbolField.getText());
        client.setDescription(descriptionArea.getText());

        try {
            if (client.getId() == null) {
                controller.create(client);
                ((Stage) titleField.getScene().getWindow()).close();
            } else {
                controller.update(client);
            }
        } catch (ModelIsInvalid modelIsInvalid) {
            G.error("Klienta se nepodařilo uložit.");
        }
    }

    @FXML
    private void onDeleteAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Odstranit");
        alert.setHeaderText("Odstranit tohoto klienta?");
        if (alert.showAndWait().get() == ButtonType.CANCEL) return;

        try {
            controller.delete(client);

            ((Stage) titleField.getScene().getWindow()).close();
        } catch (ModelCannotBeDeleted modelCannotBeDeleted) {
            G.error("Klienta se nepodařilo odstranit.");
        }
    }

    private boolean validate() {
        if (firstNameField.getText() == null || firstNameField.getText().equals("")) {
            G.error("Křestní jméno je povinné.");
            return false;
        }

        if (lastNameField.getText() == null || lastNameField.getText().equals("")) {
            G.error("Příjmení je povinné.");
            return false;
        }

        if (emailField.getText() == null || emailField.getText().equals("")) {
            G.error("Email je povinný.");
            return false;
        }

        if (!emailField.getText().matches("\\w+@\\w+\\.\\w+")) {
            G.error("Email je v chybném tvaru.");
            return false;
        }

        return true;
    }

}
