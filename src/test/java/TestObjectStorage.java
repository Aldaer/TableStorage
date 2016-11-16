
import dao.GenericDao;
import model.SampleRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")
public class TestObjectStorage {

    @Autowired
    GenericDao<SampleRecord> recordDao;

    @Test
    public void testSaveObjectToDatabase() {
        SampleRecord rec = new SampleRecord("test");

        Collection<SampleRecord> allRecords = recordDao.getAllRecords();
        int sizeBefore = allRecords.size();
        recordDao.save(rec);

        int sizeAfter = recordDao.getAllRecords().size();
        assertThat(sizeAfter, is(sizeBefore + 1));
    }

    @Test
    public void testSaveRecordAndGetId() {
        SampleRecord rec = new SampleRecord("test1");

        SampleRecord returnedRecord = recordDao.save(rec);

        SampleRecord recordById = recordDao.getRecordById(returnedRecord.getId());

        assertThat(rec.getName(), is(recordById.getName()));

    }


}
