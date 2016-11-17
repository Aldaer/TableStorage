package controller;

import model.SampleRecord;
import org.junit.Test;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import java.io.ByteArrayOutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestJsonGenerator {
    private static final JsonGeneratorFactory JF = Json.createGeneratorFactory(null);

    private final ByteArrayOutputStream out = new ByteArrayOutputStream(100);
    private final JsonGenerator gen = new JsonNullableGenerator(JF, out);

    @Test
    public void testJsonGeneratorWithInt() throws Exception {
        gen.writeStartObject();
        gen.write("INT", 1);
        gen.writeEnd();
        gen.flush();

        final String expectedResult = "{\"INT\":1}";
        assertThat(out.toString(), is(expectedResult));

    }

    @Test
    public void testPackSampleRecord() throws Exception {
        SampleRecord rec = new SampleRecord("some name");
        rec.setId(111);

        rec.packIntoJson(gen);

        final String expectedResult = "{\"id\":\"111\",\"NAME\":\"some name\"}";
        assertThat(out.toString(), is(expectedResult));
    }
}
