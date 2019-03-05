package vecirky;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class App extends Application {

    public static void main(String[] args) {
        Init.init();
        Init.runMigrations();

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent parent = FXMLLoader.load(G.getResource("vecirky/gui/App.fxml"));
        Scene scene = new Scene(parent, 720, 540);

        stage.setOnCloseRequest(e -> Platform.exit());

        stage.setScene(scene);
        stage.setTitle(L.get("strings.title"));
        stage.show();
    }
}
