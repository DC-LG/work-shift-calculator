package views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import persistence.User;
import services.UserService;

import javax.swing.text.html.Option;
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

  private URL location;

  private ResourceBundle resources;

  private Optional<User> currentUser;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
    this.userService = new UserService();
    this.currentUser = Optional.empty();
    prepareValidators();
  }

  @FXML
  void loginButtonOnAction(ActionEvent event) {
    validateFields();
    currentUser.ifPresent(user -> {
      // TODO show dashboard
    });
  }

  @FXML
  void registerButtonOnAction(ActionEvent event) {

  }

  private void validateFields() {
    if (usernameOrEmailTextField.validate()) {
      passwordField.validate();
    }
  }

  private void prepareValidators() {
    String errorMessage = resources.getString("login.validation.username-or-email.not-found");

    ValidatorBase usernameOrEmailValidator = new ValidatorBase(errorMessage) {
      @Override
      protected void eval() {
        currentUser = userService.findByUsernameOrEmail(usernameOrEmailTextField.getText());
        hasErrors.set(!currentUser.isPresent());
      }
    };

    ValidatorBase requiredFieldValidator = new RequiredFieldValidator();

    usernameOrEmailTextField.getValidators().addAll(requiredFieldValidator, usernameOrEmailValidator);
    passwordField.getValidators().addAll(requiredFieldValidator);
  }

}
