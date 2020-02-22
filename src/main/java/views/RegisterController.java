package views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import persistence.User;
import persistence.UserConfig;
import services.UserService;
import utils.UserContext;
import utils.ValidatorFactory;
import utils.View;
import utils.ViewManager;

import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

  @FXML
  private JFXTextField firstNameTextField;

  @FXML
  private JFXTextField usernameTextField;

  @FXML
  private JFXTextField emailTextField;

  @FXML
  private JFXCheckBox autoIncludeFromEmailCheckBox;

  @FXML
  private JFXComboBox<String> languageComboBox;

  @FXML
  private JFXTextField lastNameTextField;

  @FXML
  private JFXPasswordField passwordField;

  @FXML
  private JFXDatePicker birthDatePicker;

  @FXML
  private JFXPasswordField emailPasswordField;

  @FXML
  private JFXComboBox<String> currencyComboBox;

  @FXML
  private JFXButton cancelButton;

  @FXML
  private JFXButton saveButton;

  private ResourceBundle resources;

  private UserService userService;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.resources = resources;
    this.userService = new UserService();

    prepareValidators();

    prepareLanguages();

    prepareCurrency();

    prepareEnableIncludeFromEmailCheckBox();
  }

  private void prepareEnableIncludeFromEmailCheckBox() {
    autoIncludeFromEmailCheckBox.selectedProperty()
        .addListener((observable, oldValue, newValue) -> emailPasswordField.setDisable(!newValue));
  }

  private void prepareCurrency() {
    currencyComboBox.getItems().addAll("$", "€", "LEK");
  }

  private void prepareLanguages() {
    String[] locales = Arrays.stream(Locale.getAvailableLocales())
        .map(Locale::getLanguage)
        .filter(language -> language.equals("en") || language.equals("sq"))
        .distinct()
        .map(language -> Locale.forLanguageTag(language).getDisplayLanguage())
        .toArray(String[]::new);

    languageComboBox.getItems().addAll(locales);
  }

  private void prepareValidators() {
    RequiredFieldValidator requiredFieldValidator = ValidatorFactory.createRequiredFieldValidator();

    RegexValidator emailRegexValidator = ValidatorFactory
        .createRegexFieldValidator("^(.+)@(.+)$", "Text should be email");

    ValidatorBase emailPasswordValidator = ValidatorFactory.createCustomValidator(() ->
        autoIncludeFromEmailCheckBox.isSelected() &&
            (emailPasswordField.getText() == null || emailPasswordField.getText().isEmpty()));

    ValidatorBase currencyValidator = ValidatorFactory.createCustomValidator(() -> {
      String selectedItem = currencyComboBox.getSelectionModel().getSelectedItem();
      return selectedItem.length() > 3;
    });

    String errorMessage = resources.getString("login.validation.username.exists");
    ValidatorBase usernameValidator = ValidatorFactory.createCustomValidator(errorMessage, () -> {
      User currentUser = userService.findByUsernameOrEmail(usernameTextField.getText());
      UserContext.setCurrentUser(currentUser);
      return UserContext.getCurrentUser().isPresent();
    });

    String error = resources.getString("login.validation.email.exists");
    ValidatorBase emailValidator = ValidatorFactory.createCustomValidator(error, () -> {
      User currentUser = userService.findByUsernameOrEmail(emailTextField.getText());
      UserContext.setCurrentUser(currentUser);
      return UserContext.getCurrentUser().isPresent();
    });

    firstNameTextField.getValidators().add(requiredFieldValidator);
    lastNameTextField.getValidators().add(requiredFieldValidator);
    usernameTextField.getValidators().addAll(requiredFieldValidator, usernameValidator);
    passwordField.getValidators().add(requiredFieldValidator);
    emailTextField.getValidators().addAll(requiredFieldValidator, emailRegexValidator, emailValidator);
    birthDatePicker.getValidators().add(requiredFieldValidator);
    emailPasswordField.getValidators().add(emailPasswordValidator);
    currencyComboBox.getValidators().add(currencyValidator);
  }

  @FXML
  void cancelButtonOnAction(ActionEvent event) {
    ViewManager.switchView(View.LOGIN);
  }

  @FXML
  void saveButtonOnAction(ActionEvent event) {
    if (areFieldsValid()) {
      createUser();
      clearFields();
      ViewManager.switchView(View.LOGIN);
    }
  }

  private void clearFields() {
    firstNameTextField.clear();
    lastNameTextField.clear();
    usernameTextField.clear();
    passwordField.clear();
    emailTextField.clear();
    birthDatePicker.setValue(null);
    emailPasswordField.clear();
    currencyComboBox.getSelectionModel().clearSelection();
    languageComboBox.getSelectionModel().clearSelection();
  }

  private void createUser() {
    User user = new User();
    user.setFirstName(firstNameTextField.getText());
    user.setLastName(lastNameTextField.getText());
    user.setUsername(usernameTextField.getText());
    user.setPassword(passwordField.getText());
    user.setEmail(emailTextField.getText());
    user.setBirthday(birthDatePicker.getValue());

    UserConfig userConfig = new UserConfig();
    userConfig.setEmailPassword(emailPasswordField.getText());
    userConfig.setAutoIncludeShiftsFromEmail(autoIncludeFromEmailCheckBox.isSelected());
    userConfig.setCurrency(currencyComboBox.getSelectionModel().getSelectedItem());
    String languageTag = getLanguageTag(languageComboBox.getSelectionModel().getSelectedItem());
    userConfig.setLocale(languageTag);

    userConfig.setUser(user);
    user.setConfig(userConfig);

    userService.save(user);
  }

  private boolean areFieldsValid() {
    return firstNameTextField.validate() &&
        lastNameTextField.validate() &&
        usernameTextField.validate() &&
        passwordField.validate() &&
        emailTextField.validate() &&
        birthDatePicker.validate() &&
        emailPasswordField.validate() &&
        currencyComboBox.validate();
  }

  private String getLanguageTag(String displayName) {
    return Arrays.stream(Locale.getAvailableLocales())
        .filter(locale -> locale.getDisplayName().equals(displayName))
        .map(Locale::getLanguage)
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }

}
