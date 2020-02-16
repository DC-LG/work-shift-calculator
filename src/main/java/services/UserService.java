package services;

import persistence.User;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class UserService implements GenericDao<Long, User> {

  public final static String FIND_ALL = "USER.FIND_ALL";
  public final static String FIND_BY_USERNAME_OR_EMAIL = "USER.FIND_BY_USERNAME_OR_EMAIL";

  public Optional<User> findOneById(Long id) {
    User user = em.find(User.class, id);

    return Optional.ofNullable(user);
  }

  public List<User> findAll() {
    TypedQuery<User> namedQuery = em.createNamedQuery(FIND_ALL, User.class);

    return namedQuery.getResultList();
  }

  public User findByUsernameOrEmail(String usernameOrEmail) {
    TypedQuery<User> namedQuery = em.createNamedQuery(FIND_BY_USERNAME_OR_EMAIL, User.class)
        .setParameter(1, usernameOrEmail);

    return namedQuery.getResultStream()
        .findFirst()
        .orElse(null);
  }

}
