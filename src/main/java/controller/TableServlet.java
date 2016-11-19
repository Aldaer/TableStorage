package controller;


import dao.JpaDao;
import model.SampleRecord;
import org.springframework.beans.factory.annotation.Autowired;
import utils.JsonNullableGenerator;
import utils.JsonPacker;

import javax.json.stream.JsonGeneratorFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "TableServlet", urlPatterns = {"/data"})
public class TableServlet extends HttpServlet {
    @Autowired
    private JpaDao<SampleRecord> recordDao;

    @Autowired
    private JsonGeneratorFactory JF;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        final Collection<SampleRecord> allRecords = recordDao.getAllRecords();
        JsonPacker packer = new JsonPacker(new JsonNullableGenerator(JF, resp.getOutputStream()));
        packer.packIntoJson(allRecords.toArray(new SampleRecord[0]));
        packer.flushGenerator();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
