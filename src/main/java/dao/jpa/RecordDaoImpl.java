package dao.jpa;

import model.Record;
import org.springframework.stereotype.Repository;

@Repository
public class RecordDaoImpl extends GenericJpaDaoImpl {

    @Override
    protected Class workingClass() {
        return Record.class;
    }
}
