import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.View;
import utils.ViewManager;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  public void start(Stage primaryStage) throws IOException {
    ViewManager.setMainStage(primaryStage);

    primaryStage.setTitle("Work shifts calculator");

    Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"),
        ResourceBundle.getBundle("i18n/app", Locale.ENGLISH));
    primaryStage.setScene(new Scene(root, 800, 600));

    primaryStage.show();
  }
}
