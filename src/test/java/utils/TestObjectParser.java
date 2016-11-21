package utils;

import model.SampleRecord;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestObjectParser {
    private final MockHttpServletRequest req = new MockHttpServletRequest();

    @Test
    public void testUnpackRecordFromRequest() throws Exception {
        req.setParameter("id", "12");
        req.setParameter("NAME", "test");

        SampleRecord rec = new SampleRecord();
        final RequestObjectParser parser = new RequestObjectParser(req.getParameterMap());
        parser.reconstruct(rec);

        SampleRecord expected = new SampleRecord(12, "test");
        assertThat(rec, is(expected));
    }

    @Test
    public void testReconstructFromPrototype() throws Exception {
        req.setParameter("id", "12");
        RequestObjectParser parser = new RequestObjectParser(req.getParameterMap());
        SampleRecord returnedRecord = parser.reconstructFromPrototype(SampleRecord.class, this::mockDb);
        assertThat(returnedRecord.getName(), is("twelve"));

        req.setParameter("id", "11");
        parser = new RequestObjectParser(req.getParameterMap());
        returnedRecord = parser.reconstructFromPrototype(SampleRecord.class, this::mockDb);
        assertThat(returnedRecord.getName(), is("default"));
    }

    @Test
    public void testFlattenMap() throws Exception {
        Map<String, String[]> input = new HashMap<>();
        input.put("a", new String[]{"1","2"});
        input.put("b", new String[]{"3"});

        Map<String, String> expected = new HashMap<>();
        expected.put("a", "1,2");
        expected.put("b", "3");
        assertThat(RequestObjectParser.flattenMap(input), is(expected));
    }

    private SampleRecord mockDb(Object key) {
        return (Long) key == 12 ? new SampleRecord("twelve") : new SampleRecord("default");
    }
}
