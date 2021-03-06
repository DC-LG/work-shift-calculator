package views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import persistence.User;
import services.UserService;
import utils.UserContext;
import utils.ValidatorFactory;
import utils.View;
import utils.ViewManager;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

  @FXML
  private JFXTextField usernameOrEmailTextField;

  @FXML
  private JFXPasswordField passwordField;

  @FXML
  private JFXButton loginButton;

  @FXML
  private JFXButton registerButton;

  private UserService userService;

  private ResourceBundle resources;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.resources = resources;
    this.userService = new UserService();

    prepareValidators();
  }

  @FXML
  void loginButtonOnAction(ActionEvent event) {
    validateFields();
    UserContext.getCurrentUser()
        .ifPresent(user -> {
          System.out.println("--> Logged in");
          // TODO show dashboard
        });
  }

  @FXML
  void registerButtonOnAction(ActionEvent event) {
    ViewManager.switchView(View.REGISTER);
  }

  private void validateFields() {
    if (usernameOrEmailTextField.validate()) {
      passwordField.validate();
    }
  }

  private void prepareValidators() {
    String errorMessage = resources.getString("login.validation.username-or-email.not-found");

    ValidatorBase usernameOrEmailValidator = ValidatorFactory.createCustomValidator(errorMessage, () -> {
      User currentUser = userService.findByUsernameOrEmail(usernameOrEmailTextField.getText());
      UserContext.setCurrentUser(currentUser);
      return !UserContext.getCurrentUser().isPresent();
    });

    ValidatorBase requiredFieldValidator = ValidatorFactory.createRequiredFieldValidator();

    ValidatorBase passwordValidator = ValidatorFactory.createCustomValidator("Password incorrect.",
        () -> UserContext.getCurrentUser()
        .map(user -> !user.getPassword().equals(passwordField.getText()))
        .orElse(true));

    usernameOrEmailTextField.getValidators().addAll(requiredFieldValidator, usernameOrEmailValidator);
    passwordField.getValidators().addAll(requiredFieldValidator, passwordValidator);
  }

}
