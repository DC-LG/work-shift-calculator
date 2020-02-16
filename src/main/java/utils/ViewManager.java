package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ViewManager {

  private static final Map<View, Parent> views = new HashMap<>();

  private static Stage mainStage;

  public static void setMainStage(Stage stage) {
    ViewManager.mainStage = stage;
  }

  public static void switchView(View view) {
    Parent parent = views.get(view);
    if (parent != null) {
      mainStage.getScene().setRoot(parent);
      return;
    }

    try {
      Locale locale = UserContext.getCurrentUser()
          .map(user -> Locale.forLanguageTag(user.getConfig().getLocale()))
          .orElse(Locale.ENGLISH);

      FXMLLoader fxmlLoader = new FXMLLoader(ViewManager.class.getResource(view.getUrl()),
          ResourceBundle.getBundle("i18n/app", locale));

      parent = fxmlLoader.load();

      views.put(view, parent);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    mainStage.getScene().setRoot(parent);
  }
}
