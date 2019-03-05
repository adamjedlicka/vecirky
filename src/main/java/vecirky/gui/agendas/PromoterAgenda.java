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
import vecirky.events.PromoterCreated;
import vecirky.events.PromoterDeleted;
import vecirky.events.PromoterUpdated;
import vecirky.models.Promoter;
import vecirky.repositories.PromoterRepository;

import java.io.IOException;

public class PromoterAgenda {

    private PromoterRepository repository = C.get(PromoterRepository.class);

    @FXML
    private TableView<Promoter> tableView;

    @FXML
    private TableColumn<String, TableView<Promoter>> titleColumn;

    @FXML
    private TableColumn<String, TableView<Promoter>> firstNameColumn;

    @FXML
    private TableColumn<String, TableView<Promoter>> lastNameColumn;

    @FXML
    private TableColumn<String, TableView<Promoter>> emailColumn;

    @FXML
    private void onMousePressed(MouseEvent e) throws IOException {
        if (!e.isPrimaryButtonDown() || e.getClickCount() != 2) return;

        Promoter promoter = tableView.getSelectionModel().getSelectedItem();

        if (promoter == null) return;

        FXMLLoader loader = new FXMLLoader(G.getResource("vecirky/gui/agendas/PromoterDetail.fxml"));
        loader.setController(new PromoterDetail(promoter));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        scene.setUserData(parent);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(promoter.toString());
        stage.show();
    }

    @FXML
    private void onNewPromoterAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(G.getResource("vecirky/gui/agendas/PromoterDetail.fxml"));
        loader.setController(new PromoterDetail(new Promoter()));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        scene.setUserData(parent);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Nový pořadatel");
        stage.show();
    }

    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        refresh();

        C.eventBus().subscribe(PromoterCreated.class, e -> refresh());
        C.eventBus().subscribe(PromoterUpdated.class, e -> refresh());
        C.eventBus().subscribe(PromoterDeleted.class, e -> refresh());
    }

    private void refresh() {
        tableView.getItems().clear();

        for (Promoter promoter : repository.findAll()) {
            tableView.getItems().add(promoter);
        }
    }

}
