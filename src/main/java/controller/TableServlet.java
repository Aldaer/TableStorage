package controller;


import dao.JpaDao;
import model.SampleRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import utils.JsonNullableGenerator;
import utils.JsonPacker;
import utils.RequestObjectParser;

import javax.json.stream.JsonGeneratorFactory;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@WebServlet(name = "TableServlet", urlPatterns = {"/data"})
public class TableServlet extends HttpServlet {
    @Autowired
    private JpaDao<SampleRecord> recordDao;

    @Autowired
    private JsonGeneratorFactory JF;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Performing GET");
        setResponseParametersToJson(resp);

        final Collection<SampleRecord> allRecords = recordDao.getAllRecords();
        JsonPacker packer = new JsonPacker(new JsonNullableGenerator(JF, resp.getOutputStream()));
        packer.packIntoJson(allRecords.toArray(new SampleRecord[0]));
        packer.flushGenerator();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Performing PUT");
        final RequestObjectParser parser = new RequestObjectParser(req);

        SampleRecord updatedRecord = parser.reconstructFromPrototype(SampleRecord.class, recordDao::getDetachedReference);

        if (updatedRecord == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Cannot update: record not exists");
            return;
        }

        recordDao.update(updatedRecord);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Performing DELETE");
        final RequestObjectParser parser = new RequestObjectParser(req);

        final Object deletionKey = parser.getPrimaryKeyValue(SampleRecord.class);

        recordDao.removeRecord(deletionKey);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Performing POST");

        final RequestObjectParser parser = new RequestObjectParser(req);

        final SampleRecord newRecord = parser.reconstruct(new SampleRecord());    // Primary key ignored, will be auto-generated

        recordDao.save(newRecord);

        JsonPacker packer = new JsonPacker(new JsonNullableGenerator(JF, resp.getOutputStream()));
        packer.packIntoJson(newRecord);
        packer.flushGenerator();
    }


    private void setResponseParametersToJson(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("Retrieving Spring context");
        final WebApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        System.out.println("Retrieving bean factory");
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        System.out.println("Autowiring beans");
        beanFactory.autowireBean(this);
    }
}
