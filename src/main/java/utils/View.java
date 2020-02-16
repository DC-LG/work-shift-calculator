package utils;

import lombok.Getter;

/**
 * Created on: 2/16/20
 *
 * @author Denis Citaku
 **/
public enum View {

  LOGIN("/views/login.fxml"),

  REGISTER("/views/register.fxml");

  @Getter
  private String url;

  View(String url) {
    this.url = url;
  }

}
