package controller;

import dao.GenericDao;
import model.SampleRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class TableServletTest {
    private final TableServlet servlet = new TableServlet();

    private SampleRecord rec = new SampleRecord("test");

    @Autowired
    GenericDao<SampleRecord> recordDao;

    @Test
    public void testDoGetReturnsCorrectJson() throws Exception {
        recordDao.save(rec);
        String recAsJson = "{\"id\":\"" + rec.getId() + "\",\"NAME\":\"" + rec.getName() + "\"}";

        HttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        servlet.doGet(req, res);
        assertTrue(res.getContentAsString().contains(recAsJson));
    }

    @Test
    public void testDoGetContentType() throws Exception {
        recordDao.save(rec);

        HttpServletRequest req = new MockHttpServletRequest();
        HttpServletResponse res = new MockHttpServletResponse();
        servlet.doGet(req, res);
        assertTrue(res.getContentType().contains("application/json"));
    }


}