package vecirky.gui.agendas;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vecirky.G;
import vecirky.controllers.PromoterController;
import vecirky.exceptions.ModelCannotBeDeleted;
import vecirky.exceptions.ModelIsInvalid;
import vecirky.models.Promoter;

public class PromoterDetail {

    private Promoter promoter;

    private PromoterController controller = new PromoterController();

    @FXML
    private TextField titleField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField bankAccountField;

    @FXML
    private Button deleteButton;

    public PromoterDetail(Promoter promoter) {
        this.promoter = promoter;
    }

    public void initialize() {
        titleField.textProperty().set(promoter.getTitle());
        firstNameField.textProperty().set(promoter.getFirstName());
        lastNameField.textProperty().set(promoter.getLastName());
        emailField.textProperty().set(promoter.getEmail());
        bankAccountField.textProperty().set(promoter.getBankAccount());

        deleteButton.setDisable(promoter.getId() == null);
    }

    @FXML
    private void onSaveAction() {
        if (!validate()) return;

        promoter.setTitle(titleField.getText());
        promoter.setFirstName(firstNameField.getText());
        promoter.setLastName(lastNameField.getText());
        promoter.setEmail(emailField.getText());
        promoter.setBankAccount(bankAccountField.getText());

        try {
            if (promoter.getId() == null) {
                controller.create(promoter);
                ((Stage) titleField.getScene().getWindow()).close();
            } else {
                controller.update(promoter);
            }
        } catch (ModelIsInvalid modelIsInvalid) {
            G.error("Pořadatele se nepovedlo uložit.");
        }
    }

    @FXML
    private void onDeleteAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Odstranit");
        alert.setHeaderText("Odstranit tohoto pořadatele?");
        if (alert.showAndWait().get() == ButtonType.CANCEL) return;

        try {
            controller.delete(promoter);

            ((Stage) titleField.getScene().getWindow()).close();
        } catch (ModelCannotBeDeleted modelCannotBeDeleted) {
            G.error("Pořadatele nelze odstranit.");
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
