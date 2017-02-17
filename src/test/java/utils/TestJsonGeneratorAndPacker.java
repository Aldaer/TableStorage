package utils;

import com.fasterxml.jackson.core.JsonFactory;
import model.SampleRecord;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestJsonGeneratorAndPacker {
    private static JsonFactory JF = new JsonFactory();

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final JsonNullableGenerator gen = new JsonNullableGenerator(JF, out);
    private final JsonPacker jPacker = new JsonPacker(gen);

    @Test
    public void testJsonGeneratorWithInt() throws Exception {
        gen.writeStartObject();
        gen.writeFieldName("INT");
        gen.writeNumber(1);
        gen.writeEndObject();
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

