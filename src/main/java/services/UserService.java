package services;

import persistence.User;
import persistence.config.PersistenceConfig;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class UserService implements GenericDao<Long, User> {

  public final static String FIND_ALL = "USER.FIND_ALL";
  public final static String FIND_BY_USERNAME_OR_EMAIL = "USER.FIND_BY_USERNAME_OR_EMAIL";

  private EntityManager em = PersistenceConfig.getInstance().getEntityManager();

  public void delete(User user) {
    em.remove(user);
  }

  public User update(User entity) {
    return em.merge(entity);
  }

  public void save(User entity) {
    em.persist(entity);
  }

  public Optional<User> findOne(Long id) {
    User user = em.find(User.class, id);

    return Optional.ofNullable(user);
  }

  public List<User> findAll() {
    TypedQuery<User> namedQuery = em.createNamedQuery(FIND_ALL, User.class);

    return namedQuery.getResultList();
  }

  public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
    TypedQuery<User> namedQuery = em.createNamedQuery(FIND_BY_USERNAME_OR_EMAIL, User.class)
        .setParameter(1, usernameOrEmail);

    return namedQuery.getResultStream().findFirst();
  }

}
