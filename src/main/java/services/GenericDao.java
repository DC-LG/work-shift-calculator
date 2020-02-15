package services;

import java.util.List;
import java.util.Optional;

public interface GenericDao<ID, E> {

  void delete(E entity);

  E update(E entity);

  void save(E entity);

  Optional<E> findOne(ID id);

  List<E> findAll();

}
