package dao.jpa;

import model.SampleRecord;
import org.springframework.stereotype.Repository;

@Repository
public class RecordDaoImpl extends GenericJpaDaoImpl<SampleRecord> {

    @Override
    protected Class workingClass() {
        return SampleRecord.class;
    }
}
