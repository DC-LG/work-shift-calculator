import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import persistence.config.PersistenceConfig;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

  public static void main(String[] args) {
    PersistenceConfig.getInstance();
    Application.launch(args);
  }

  public void start(Stage primaryStage) throws IOException {
    primaryStage.setTitle("Work shifts calculator");

    AnchorPane root = FXMLLoader.load(getClass().getResource("/views/login.fxml"),
        ResourceBundle.getBundle("i18n/app", Locale.ENGLISH));

    primaryStage.setScene(new Scene(root, 800, 600));
    primaryStage.show();
  }
}
