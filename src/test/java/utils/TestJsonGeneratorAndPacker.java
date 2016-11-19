package utils;

import model.SampleRecord;
import org.junit.Test;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import java.io.ByteArrayOutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestJsonGeneratorAndPacker {
    private static JsonGeneratorFactory JF = Json.createGeneratorFactory(null);

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final JsonGenerator gen = new JsonNullableGenerator(JF, out);
    private final JsonPacker jPacker = new JsonPacker(gen);

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
        SampleRecord rec = new SampleRecord(111, "some name");

        jPacker.packIntoJson(rec);
        jPacker.flushGenerator();

        final String expectedResult = "{\"id\":\"111\",\"NAME\":\"some name\"}";
        assertThat(out.toString(), is(expectedResult));
    }

    @Test
    public void testPackMultilevelObject() throws Exception {
        SampleRecord rec = new SampleRecord(5, "test");

        RecordContainer recC = new RecordContainer();
        recC.id = 10;
        recC.rec = rec;

        jPacker.packIntoJson(recC);
        jPacker.flushGenerator();

        final String expectedResult = "{\"id\":\"10\",\"rec\":{\"id\":\"5\",\"NAME\":\"test\"}}";
        assertThat(out.toString(), is(expectedResult));
    }

    @Test
    public void testPackArray() throws Exception {
        SampleRecord[] recArray = {new SampleRecord(1, "one"), new SampleRecord(2, "two")};

        jPacker.packIntoJson(recArray);
        jPacker.flushGenerator();

        final String expectedResult = "[{\"id\":\"1\",\"NAME\":\"one\"},{\"id\":\"2\",\"NAME\":\"two\"}]";
        assertThat(out.toString(), is(expectedResult));
    }
}

