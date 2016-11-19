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

    private SampleRecord rec = new SampleRecord("testservlet");
    private MockHttpServletRequest req;
    private MockHttpServletResponse res;

    @Before
    public void createHttpMocks() throws Exception {
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();

        recordDao.save(rec);
    }

    @Test
    public void testDoGetReturnsCorrectJson() throws Exception {
        String recAsJson = "{\"id\":\"" + rec.getId() + "\",\"NAME\":\"" + rec.getName() + "\"}";

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
        final SampleRecord oldRec = recordDao.getRecordById(rec.getId());

        req.setParameter("id", String.valueOf(rec.getId()));
        req.setParameter("NAME", "new value");

        testServlet.doPut(req, res);

        final SampleRecord newRec = recordDao.getRecordById(rec.getId());
        assertThat(res.getStatus(), is(HttpServletResponse.SC_OK));

        assertThat(oldRec.getName(), is("testservlet"));
        assertThat(newRec.getName(), is("new value"));
    }
}