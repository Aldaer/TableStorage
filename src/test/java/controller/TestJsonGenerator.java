package controller;

import org.junit.Test;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import java.io.ByteArrayOutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestJsonGenerator {
    private static final JsonGeneratorFactory JF = Json.createGeneratorFactory(null);

    @Test
    public void testJsonGeneratorWithInt() throws Exception {
        final ByteArrayOutputStream out = new ByteArrayOutputStream(100);

        final JsonGenerator gen = new JsonNullableGenerator(JF, out);

        gen.writeStartObject();
        gen.write("INT", 1);
        gen.writeEnd();
        gen.flush();

        assertThat(out.toString(), is("{\"INT\":1}"));

    }


}
