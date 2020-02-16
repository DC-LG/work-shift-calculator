package utils;

import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Supplier;

/**
 * Created on: 2/16/20
 *
 * @author Denis Citaku
 **/
public class ValidatorFactory {

  public static RequiredFieldValidator createRequiredFieldValidator() {
    Locale locale = UserContext.getCurrentUser()
        .map(user -> Locale.forLanguageTag(user.getConfig().getLocale()))
        .orElse(Locale.ENGLISH);

    ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n/app", locale);

    return new RequiredFieldValidator(resourceBundle.getString("validator.required-field"));
  }

  public static RegexValidator createRegexFieldValidator(String regex, String message) {
    RegexValidator regexValidator = new RegexValidator(message);
    regexValidator.setRegexPattern(regex);

    return regexValidator;
  }

  public static ValidatorBase createCustomValidator(Supplier<Boolean> hasErrorSupplier) {
    return new ValidatorBase() {
      @Override
      protected void eval() {
        hasErrors.set(hasErrorSupplier.get());
      }
    };
  }

  public static ValidatorBase createCustomValidator(String message, Supplier<Boolean> hasErrorSupplier) {
    return new ValidatorBase(message) {
      @Override
      protected void eval() {
        hasErrors.set(hasErrorSupplier.get());
      }
    };
  }

}
