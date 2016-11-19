package dao.jpa;

import dao.JpaDao;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * This class supports saving and retrieving objects via JPA.
 *
 * @param <T> Type of the object stored in the database. Class of returned objects (T.getClass())
 *            must be passed as a constructor parameter.
 */
@RequiredArgsConstructor
public class JpaDaoImpl<T extends Serializable> implements JpaDao<T> {
    private final Class<T> entityClass;

    @PersistenceUnit
    private EntityManagerFactory emf;       // Will be autowired

    @Override
    public T save(T entity) {
        final EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        em.close();

        return entity;
    }

    @Override
    public void update(T entity) {
        final EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

        em.close();
    }

    @Override
    public Collection<T> getAllRecords() {
        final EntityManager em = emf.createEntityManager();

        final TypedQuery<T> query = em.createQuery("from " + entityClass.getName(), entityClass);

        final Collection<T> resultList = query.getResultList();

        em.close();
        return resultList;
    }

    @Override
    public T getRecordById(long id) {
        final EntityManager em = emf.createEntityManager();

        final TypedQuery<T> query = em.createQuery("select c from " + entityClass.getName() + " c where c.id = :id", entityClass);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public T getDetachedReference(Object primaryKey) {
        final EntityManager em = emf.createEntityManager();

        final T ref = em.find(entityClass, primaryKey);

        em.close();

        return ref;
    }

    public void removeRecord(Object primaryKey) {
        final EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        final T ref = em.find(entityClass, primaryKey);
        if (ref != null) em.remove(ref);
        em.getTransaction().commit();

        em.close();
    }
}
