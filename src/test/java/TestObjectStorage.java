import dao.GenericDao;
import model.SampleRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")
public class TestObjectStorage {

    @Autowired
    GenericDao<SampleRecord> recordDao;

    private SampleRecord rec = new SampleRecord("test");

    @Before
    public void setUpRecord() throws Exception {
        recordDao.save(rec);
    }

    @Test
    public void testSaveObjectToDatabase() {
        SampleRecord rec = new SampleRecord("test1");

        int sizeBefore = recordDao.getAllRecords().size();
        recordDao.save(rec);

        int sizeAfter = recordDao.getAllRecords().size();
        assertThat(sizeAfter, is(sizeBefore + 1));
    }

    @Test
    public void testSaveRecordAndGetId() {
        SampleRecord recordById = recordDao.getRecordById(rec.getId());

        assertThat(recordById.getName(), is("test"));
    }

    @Test
    public void testUpdateObjectInDatabase() {
        long storedId = rec.getId();
        rec.setName("updated");

        recordDao.update(rec);

        SampleRecord updatedRecord = recordDao.getRecordById(storedId);

        assertThat(updatedRecord.getName(), is("updated"));
    }


}
