package controller;

import dao.JpaDao;
import model.SampleRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class TableServletTest {
    @Autowired
    private TableServlet testServlet;

    @Autowired
    private JpaDao<SampleRecord> recordDao;

    private SampleRecord record = new SampleRecord("testservlet");
    private MockHttpServletRequest req;
    private MockHttpServletResponse res;

    @Before
    public void createHttpMocks() throws Exception {
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();

        recordDao.save(record);
    }

    @Test
    public void testDoGetReturnsCorrectJson() throws Exception {
        String recAsJson = "{\"id\":\"" + record.getId() + "\",\"NAME\":\"" + record.getName() + "\"}";

        testServlet.doGet(req, res);
        assertTrue(res.getContentAsString().contains(recAsJson));
    }

    @Test
    public void testDoGetContentTypeAndStatus() throws Exception {
        testServlet.doGet(req, res);
        assertThat(res.getStatus(), is(HttpServletResponse.SC_OK));
        assertTrue(res.getContentType().contains("application/json"));
    }

    @Test
    public void testDoPutUpdatesExistingObjects() throws Exception {
        final SampleRecord oldRec = recordDao.getRecordById(record.getId());

        req.setParameter("id", String.valueOf(record.getId()));
        req.setParameter("NAME", "new value");

        testServlet.doPut(req, res);

        final SampleRecord newRec = recordDao.getRecordById(record.getId());
        assertThat(res.getStatus(), is(HttpServletResponse.SC_OK));

        assertThat(oldRec.getName(), is("testservlet"));
        assertThat(newRec.getName(), is("new value"));
    }

    @Test
    public void testDoPutFailsOnNonExistingObjects() throws Exception {
        req.setParameter("id", "-1");
        req.setParameter("NAME", "some value");

        testServlet.doPut(req, res);
        assertThat(res.getStatus(), is(HttpServletResponse.SC_BAD_REQUEST));
    }
}