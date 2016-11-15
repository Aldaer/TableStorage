package dao;

import model.Record;

import java.util.Collection;

public interface RecordDao {

    Record save(Record record);

    Collection<Record> getAllRecords();

    Record getRecordById(long id);

}