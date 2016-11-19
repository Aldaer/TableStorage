package controller;

import dao.GenericDao;
import model.SampleRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
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
    private GenericDao<SampleRecord> recordDao;

    private SampleRecord rec = new SampleRecord("testservlet");
    private HttpServletRequest req;
    private MockHttpServletResponse res;

    @Before
    public void createHttpMocks() throws Exception {
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
    }

    @Test
    public void testDoGetReturnsCorrectJson() throws Exception {
        recordDao.save(rec);
        String recAsJson = "{\"id\":\"" + rec.getId() + "\",\"NAME\":\"" + rec.getName() + "\"}";

        testServlet.doGet(req, res);
        assertTrue(res.getContentAsString().contains(recAsJson));
    }

    @Test
    public void testDoGetContentTypeAndStatus() throws Exception {
        recordDao.save(rec);

        testServlet.doGet(req, res);
        assertTrue(res.getContentType().contains("application/json"));
        assertThat(res.getStatus(), is(HttpServletResponse.SC_OK));
    }
}