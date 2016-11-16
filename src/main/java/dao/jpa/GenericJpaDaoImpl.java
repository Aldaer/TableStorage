package dao.jpa;

import dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

public abstract class GenericJpaDaoImpl<T> extends AbstractJpaDao implements GenericDao<T> {
    protected abstract Class<T> workingClass();

    @Override
    public T save(T record) {
        final EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(record);
        em.getTransaction().commit();

        em.close();

        return record;
    }

    @Override
    public Collection<T> getAllRecords() {
        final EntityManager em = emf.createEntityManager();

        final TypedQuery<T> query = em.createQuery("from " + workingClass().getName(), workingClass());

        final Collection<T> resultList = query.getResultList();
        em.close();
        return resultList;
    }

    @Override
    public T getRecordById(long id) {
        final EntityManager em = emf.createEntityManager();

        final TypedQuery<T> query = em.createQuery("select c from " + workingClass().getName() + " c where c.id = :id", workingClass());
        query.setParameter("id", id);

        final T singleResult = query.getSingleResult();
        em.close();
        return singleResult;
    }
}
