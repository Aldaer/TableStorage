package dao.jpa;

import dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Collection;

public class JpaDaoImpl<T extends Serializable> extends AbstractJpaDao implements GenericDao<T> {
    private final Class<T> entityClass;

    private JpaDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public static<T1 extends Serializable> JpaDaoImpl<T1> createInstance(T1 sampleEntity) {
        return new JpaDaoImpl<>((Class<T1>) sampleEntity.getClass());
    }

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

        final T singleResult = query.getSingleResult();
        em.close();
        return singleResult;
    }
}
