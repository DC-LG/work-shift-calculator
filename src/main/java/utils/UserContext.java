package utils;

import persistence.User;

import java.util.Optional;

public class UserContext {

  private static User user;

  public static void setCurrentUser(User user) {
    UserContext.user = user;
  }

  public static Optional<User> getCurrentUser() {
    return Optional.ofNullable(UserContext.user);
  }

}
