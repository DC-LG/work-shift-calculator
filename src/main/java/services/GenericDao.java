package services;

import persistence.User;
import persistence.config.PersistenceConfig;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface GenericDao<ID, E> {

  EntityManager em = PersistenceConfig.getInstance().getEntityManager();

  default void delete(E entity) {
    em.getTransaction().begin();
    em.remove(entity);
    em.getTransaction().commit();
  }

  default E update(E entity) {
    em.getTransaction().begin();
    E mergedEntity = em.merge(entity);
    em.getTransaction().commit();

    return mergedEntity;
  }

  default void save(E entity) {
    em.getTransaction().begin();
    em.persist(entity);
    em.getTransaction().commit();
  }

  Optional<E> findOneById(Long id);

  List<User> findAll();

}
