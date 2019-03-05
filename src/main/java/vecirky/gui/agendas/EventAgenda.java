package vecirky.gui.agendas;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import vecirky.C;
import vecirky.G;
import vecirky.events.EventCreated;
import vecirky.events.EventDeleted;
import vecirky.events.EventUpdated;
import vecirky.models.Event;
import vecirky.repositories.EventRepository;

import java.io.IOException;
import java.util.Date;

public class EventAgenda {

    private EventRepository repository = C.get(EventRepository.class);

    @FXML
    private TableView<Event> tableView;

    @FXML
    private TableColumn<Integer, TableView<Event>> idColumn;

    @FXML
    private TableColumn<Date, TableView<Event>> dateColumn;

    @FXML
    private TableColumn<Integer, TableView<Event>> priceColumn;

    @FXML
    private TableColumn<String, TableView<Event>> typeColumn;

    @FXML
    private TableColumn<String, TableView<Event>> promoterColumn;

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        promoterColumn.setCellValueFactory(new PropertyValueFactory<>("mainPromoter"));

        refresh();

        C.eventBus().subscribe(EventCreated.class, e -> refresh());
        C.eventBus().subscribe(EventUpdated.class, e -> refresh());
        C.eventBus().subscribe(EventDeleted.class, e -> refresh());
    }

    @FXML
    private void onMousePressed(MouseEvent e) throws IOException {
        if (!e.isPrimaryButtonDown() || e.getClickCount() != 2) return;

        Event event = tableView.getSelectionModel().getSelectedItem();

        if (event == null) return;

        FXMLLoader loader = new FXMLLoader(G.getResource("vecirky/gui/agendas/EventDetail.fxml"));
        loader.setController(new EventDetail(event));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        scene.setUserData(parent);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(event.toString());
        stage.show();
    }

    @FXML
    private void onNewEventAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(G.getResource("vecirky/gui/agendas/EventDetail.fxml"));
        loader.setController(new EventDetail(new Event()));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        scene.setUserData(parent);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Nový event");
        stage.show();
    }

    private void refresh() {
        tableView.getItems().clear();

        for (Event event : repository.findAll()) {
            tableView.getItems().add(event);
        }
    }

}
