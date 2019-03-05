package vecirky.gui.agendas;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vecirky.C;
import vecirky.G;
import vecirky.controllers.EventController;
import vecirky.exceptions.ModelCannotBeDeleted;
import vecirky.exports.ExportToFile;
import vecirky.models.*;
import vecirky.repositories.ClientRepository;
import vecirky.repositories.EventTypeRepository;
import vecirky.repositories.PromoterRepository;

import java.io.File;
import java.util.Date;
import java.util.Optional;

public class EventDetail {

    private Event event;

    private EventController controller = new EventController();

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextArea tasksArea;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private DatePicker cancellationDatePicker;

    @FXML
    private TextField priceField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField numberOfDescriptiveField;

    @FXML
    private TextField zipCodeField;

    @FXML
    private ComboBox<Promoter> promoterComboBox;

    @FXML
    private ComboBox<EventType> eventTypeComboBox;

    @FXML
    private ComboBox<Client> clientComboBox;

    @FXML
    private ComboBox<Promoter> addMinorPromoterComboBox;

    @FXML
    private ListView<Promoter> minorPromotersListView;

    @FXML
    private Button cancelButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button deleteButton;

    public EventDetail(Event event) {
        this.event = event;
    }

    public void initialize() {
        onlyNumbersIn(priceField);
        onlyNumbersIn(numberOfDescriptiveField);
        onlyNumbersIn(zipCodeField);

        descriptionArea.setText(event.getDescription());
        tasksArea.setText(event.getTasks());
        eventDatePicker.setValue(G.toLocalDate(event.getEventDate()));
        cancellationDatePicker.setValue(G.toLocalDate(event.getCancellationDate().orElse(null)));
        priceField.setText("" + event.getPrice());

        Address address = event.getAddress();
        streetField.setText(address.getStreet());
        cityField.setText(address.getCity());
        numberOfDescriptiveField.setText("" + address.getNumberOfDescriptive());
        zipCodeField.setText("" + address.getZipCode());

        promoterComboBox.getItems().addAll(C.get(PromoterRepository.class).findAll());
        promoterComboBox.getSelectionModel().select(event.getMainPromoter());

        eventTypeComboBox.getItems().addAll(C.get(EventTypeRepository.class).findAll());
        eventTypeComboBox.getSelectionModel().select(event.getEventType());

        clientComboBox.getItems().addAll(C.get(ClientRepository.class).findAll());
        clientComboBox.getSelectionModel().select(event.getClient());

        exportButton.setDisable(event.getId() == null);
        deleteButton.setDisable(event.getId() == null);

        refreshMinorPromoters();
    }

    @FXML
    private void onCancelAction() {
        Date curDate = new Date();
        event.setCancellationDate(Optional.of(curDate));
        cancellationDatePicker.setValue(G.toLocalDate(curDate));
    }


    @FXML
    private void onSaveAction() {
        if (!validate()) return;

        event.setDescription(descriptionArea.getText());
        event.setTasks(tasksArea.getText());
        event.setEventDate(G.toDate(eventDatePicker.getValue()));
        event.setCancellationDate(Optional.ofNullable(G.toDate(cancellationDatePicker.getValue())));
        event.setPrice(G.toInteger(priceField.getText()));

        event.getAddress().setStreet(streetField.getText());
        event.getAddress().setCity(cityField.getText());
        event.getAddress().setNumberOfDescriptive(G.toInteger(numberOfDescriptiveField.getText()));
        event.getAddress().setZipCode(G.toInteger(zipCodeField.getText()));

        event.setMainPromoter(promoterComboBox.getSelectionModel().getSelectedItem());

        event.setEventType(eventTypeComboBox.getSelectionModel().getSelectedItem());

        event.setClient(clientComboBox.getSelectionModel().getSelectedItem());

        try {
            if (event.getId() == null) {
                controller.create(event);
                ((Stage) descriptionArea.getScene().getWindow()).close();
            } else {
                controller.update(event);
            }
        } catch (Exception e) {
            G.error("Event se nepovedlo uložit.");
        }
    }

    @FXML
    private void onExportAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportovat do");
        fileChooser.setInitialFileName("export.pdf");

        File file = fileChooser.showSaveDialog(descriptionArea.getScene().getWindow());

        new ExportToFile().export(event, file.getPath());
    }

    @FXML
    private void onDeleteAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Odstranit");
        alert.setHeaderText("Odstranit tento event?");
        if (alert.showAndWait().get() == ButtonType.CANCEL) return;

        try {
            controller.delete(event);

            ((Stage) descriptionArea.getScene().getWindow()).close();
        } catch (ModelCannotBeDeleted modelCannotBeDeleted) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba");
            alert.setHeaderText("Event nelze odstranit.");
            alert.show();
        }
    }

    @FXML
    private void onAddMinorPromoterAction() {
        Promoter promoter = addMinorPromoterComboBox.getValue();

        if (promoter == null) return;

        event.getMinorPromoters().add(promoter);

        Platform.runLater(() -> {
            refreshMinorPromoters();
        });
    }

    private void refreshMinorPromoters() {
        addMinorPromoterComboBox.getItems().clear();
        minorPromotersListView.getItems().clear();

        for (Promoter promoter : C.get(PromoterRepository.class).findAll()) {
            if (!event.getMinorPromoters().contains(promoter) && !promoter.equals(event.getMainPromoter())) {
                addMinorPromoterComboBox.getItems().add(promoter);
            }
        }

        minorPromotersListView.getItems().addAll(event.getMinorPromoters());
    }

    @FXML
    private void onMinorPromotersMousePressed(MouseEvent e) {
        if (!e.isPrimaryButtonDown() || e.getClickCount() != 2) return;

        for (Promoter promoter : minorPromotersListView.getSelectionModel().getSelectedItems()) {
            event.getMinorPromoters().remove(promoter);
        }

        refreshMinorPromoters();
    }

    private void onlyNumbersIn(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(oldValue);
            }
        });
    }

    private boolean validate() {
        if (streetField.getText() == null || streetField.getText().equals("")) {
            G.error("Ulice je povinná.");
            return false;
        }

        if (numberOfDescriptiveField.getText() == null || numberOfDescriptiveField.getText().equals("")) {
            G.error("Číslo popisné je povinné.");
            return false;
        }

        if (cityField.getText() == null || cityField.getText().equals("")) {
            G.error("Město je povinné.");
            return false;
        }

        if (zipCodeField.getText() == null || zipCodeField.getText().equals("")) {
            G.error("PSČ je povinná.");
            return false;
        }

        if (promoterComboBox.getSelectionModel().getSelectedItem() == null) {
            G.error("Hlavní pořadatel je povinný.");
            return false;
        }

        if (clientComboBox.getSelectionModel().getSelectedItem() == null) {
            G.error("Klient je povinný.");
            return false;
        }

        if (eventTypeComboBox.getSelectionModel().getSelectedItem() == null) {
            G.error("Typ event je povinný.");
            return false;
        }

        return true;
    }

}
