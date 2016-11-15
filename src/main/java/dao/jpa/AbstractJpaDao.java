package dao.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class AbstractJpaDao {
    @PersistenceUnit
    protected EntityManagerFactory emf;
}