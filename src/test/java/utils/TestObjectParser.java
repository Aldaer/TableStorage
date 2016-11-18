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
}
