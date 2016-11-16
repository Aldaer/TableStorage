package dao.jpa;

import model.Record;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class RecordDaoImpl extends GenericJpaDaoImpl {

    @Override
    public Collection<Record> getAllRecords() {
        final EntityManager em = emf.createEntityManager();

        final TypedQuery<Record> query = em.createQuery("from Record", Record.class);

        final Collection<Record> resultList = query.getResultList();
        em.close();
        return resultList;
    }

    @Override
    public Record getRecordById(long id) {
        final EntityManager em = emf.createEntityManager();

        final TypedQuery<Record> query = em.createQuery("select c from Record c where c.id = :id", Record.class);
        query.setParameter("id", id);

        final Record singleResult = query.getSingleResult();
        em.close();
        return singleResult;
    }

}
