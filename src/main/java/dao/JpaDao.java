package dao;

import java.util.Collection;

public interface JpaDao<T> {
    T save(T record);

    void update(T record);

    Collection<T> getAllRecords();

    T getRecordById(long id);

    T getDetachedReference(Object primaryKey);

    void removeRecord(Object primaryKey);
}
