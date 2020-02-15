package persistence.config;

import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceConfig {

  private static final PersistenceConfig instance = new PersistenceConfig();

  @Getter
  private final EntityManager entityManager;

  public static PersistenceConfig getInstance() {
    return instance;
  }

  private PersistenceConfig() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-work-shift-calculator");
    this.entityManager = emf.createEntityManager();
  }

}
