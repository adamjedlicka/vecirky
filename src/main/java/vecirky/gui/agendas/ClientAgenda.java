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
import vecirky.controllers.ClientController;
import vecirky.events.ClientCreated;
import vecirky.events.ClientDeleted;
import vecirky.events.ClientUpdated;
import vecirky.models.Client;

import java.io.IOException;

public class ClientAgenda {

    private ClientController controller = new ClientController();

    @FXML
    private TableView<Client> tableView;

    @FXML
    private TableColumn<String, TableView<Client>> titleColumn;

    @FXML
    private TableColumn<String, TableView<Client>> firstNameColumn;

    @FXML
    private TableColumn<String, TableView<Client>> lastNameColumn;

    @FXML
    private TableColumn<String, TableView<Client>> emailColumn;

    @FXML
    private void onMousePressed(MouseEvent e) throws IOException {
        if (!e.isPrimaryButtonDown() || e.getClickCount() != 2) return;

        Client client = tableView.getSelectionModel().getSelectedItem();

        if (client == null) return;

        FXMLLoader loader = new FXMLLoader(G.getResource("vecirky/gui/agendas/ClientDetail.fxml"));
        loader.setController(new ClientDetail(client));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        scene.setUserData(parent);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(client.toString());
        stage.show();
    }

    @FXML
    private void onNewClientAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(G.getResource("vecirky/gui/agendas/ClientDetail.fxml"));
        loader.setController(new ClientDetail(new Client()));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        scene.setUserData(parent);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Nový klient");
        stage.show();
    }

    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        refresh();

        C.eventBus().subscribe(ClientCreated.class, e -> refresh());
        C.eventBus().subscribe(ClientUpdated.class, e -> refresh());
        C.eventBus().subscribe(ClientDeleted.class, e -> refresh());
    }

    private void refresh() {
        tableView.getItems().clear();

        for (Client client : controller.index()) {
            tableView.getItems().add(client);
        }
    }

}
