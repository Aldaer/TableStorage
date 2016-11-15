package dao;

import model.Record;

import java.util.Collection;

public interface RecordDao {

    void save(Record record);

    Collection<Record> getAllRecords();

    Record getRecordById(long id);

}