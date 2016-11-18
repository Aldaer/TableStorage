package dao;

import java.util.Collection;

public interface GenericDao<T> {

    T save(T record);
    void update(T record);

    Collection<T> getAllRecords();

    T getRecordById(long id);

}