package utils;

import model.SampleRecord;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestObjectParser {
    private final MockHttpServletRequest req = new MockHttpServletRequest();
    private final RequestObjectParser parser = new RequestObjectParser(req);

    @Test
    public void testUnpackRecordFromRequest() throws Exception {
        req.setParameter("id", "12");
        req.setParameter("NAME", "test");

        SampleRecord rec = new SampleRecord();
        parser.reconstruct(rec);

        SampleRecord expected = new SampleRecord(12, "test");
        assertThat(rec, is(expected));
    }

    @Test
    public void testReconstructFromPrototype() throws Exception {
        req.setParameter("id", "12");
        SampleRecord returnedRecord = parser.reconstructFromPrototype(SampleRecord.class, this::mockDb);
        assertThat(returnedRecord.getName(), is("twelve"));

        req.setParameter("id", "11");
        returnedRecord = parser.reconstructFromPrototype(SampleRecord.class, this::mockDb);
        assertThat(returnedRecord.getName(), is("default"));
    }

    private SampleRecord mockDb(Object key) {
        return (Long) key == 12 ? new SampleRecord("twelve") : new SampleRecord("default");
    }
}
