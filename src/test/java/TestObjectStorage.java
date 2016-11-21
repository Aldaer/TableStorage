import dao.JpaDao;
import model.SampleRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utils.RequestObjectParser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class TestObjectStorage {

    @Autowired
    JpaDao<SampleRecord> recordDao;

    private SampleRecord rec = new SampleRecord("test");

    @Before
    public void setUpRecord() throws Exception {
        recordDao.save(rec);
    }

    @Test
    public void testSaveObjectToDatabase() {
        SampleRecord rec1 = new SampleRecord("test1");

        int sizeBefore = recordDao.getAllRecords().size();
        recordDao.save(rec1);

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

    @Test
    public void testRemoveObject() {
        SampleRecord rec = new SampleRecord("doomed");
        recordDao.save(rec);
        long storedId = rec.getId();

        assertNotNull(recordDao.getRecordById(storedId));

        recordDao.removeRecord(storedId);

        assertNull(recordDao.getRecordById(storedId));
    }

    @Test
    public void testUpdateObjectInDatabaseByReconstruction() {
        final MockHttpServletRequest req = new MockHttpServletRequest();

        Long storedId = rec.getId();

        req.setParameter("id", storedId.toString());
        req.setParameter("NAME", "reconstructed");

        final RequestObjectParser parser = new RequestObjectParser(req.getParameterMap());
        final SampleRecord newRecord = parser.reconstructFromPrototype(SampleRecord.class, recordDao::getDetachedReference);

        assertThat(newRecord.getName(), is("reconstructed"));

        recordDao.update(newRecord);

        final SampleRecord recordInDb = recordDao.getRecordById(storedId);
        assertThat(recordInDb.getName(), is("reconstructed"));
    }

}
