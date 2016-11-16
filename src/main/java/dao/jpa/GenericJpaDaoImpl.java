package dao.jpa;

import dao.GenericDao;

import javax.persistence.EntityManager;

public abstract class GenericJpaDaoImpl<T> extends AbstractJpaDao implements GenericDao<T> {


    @Override
    public T save(T record) {
        final EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(record);
        em.getTransaction().commit();

        em.close();

        return record;
    }
}
