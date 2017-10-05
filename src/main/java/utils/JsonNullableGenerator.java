package utils;

import javax.json.JsonValue;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Wrapper for JsonGenerator by glassfish. Instead of generating exception
 * on null objects when writing JSON, just skips them.
 */
public class JsonNullableGenerator implements JsonGenerator {
    private final JsonGenerator jg;

    public JsonNullableGenerator(JsonGeneratorFactory factory, OutputStream out) {
        jg = factory.createGenerator(out, StandardCharsets.UTF_8);
    }

    @Override
    public JsonNullableGenerator writeStartObject() {
        jg.writeStartObject();
        return this;
    }

    @Override
    public JsonNullableGenerator writeStartObject(String name) {
        jg.writeStartObject(name);
        return this;
    }

    @Override
    public JsonNullableGenerator writeStartArray() {
        jg.writeStartArray();
        return this;
    }

    @Override
    public JsonNullableGenerator writeStartArray(String name) {
        jg.writeStartArray(name);
        return this;
    }

    @Override
    public JsonNullableGenerator write(String name, JsonValue value) {
        if (value != null) jg.write(name, value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(String name, String value) {
        if (value != null) jg.write(name, value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(String name, BigInteger value) {
        if (value != null) jg.write(name, value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(String name, BigDecimal value) {
        if (value != null) jg.write(name, value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(String name, int value) {
        jg.write(name, value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(String name, long value) {
        jg.write(name, value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(String name, double value) {
        jg.write(name, value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(String name, boolean value) {
        jg.write(name, value);
        return this;
    }

    @Override
    public JsonNullableGenerator writeNull(String name) {
        jg.writeNull(name);
        return this;
    }

    @Override
    public JsonNullableGenerator writeEnd() {
        jg.writeEnd();
        return this;
    }

    @Override
    public JsonNullableGenerator write(JsonValue value) {
        if (value != null) jg.write(value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(String value) {
        if (value != null) jg.write(value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(BigDecimal value) {
        if (value != null) jg.write(value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(BigInteger value) {
        if (value != null) jg.write(value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(int value) {
        jg.write(value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(long value) {
        jg.write(value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(double value) {
        jg.write(value);
        return this;
    }

    @Override
    public JsonNullableGenerator write(boolean value) {
        jg.write(value);
        return this;
    }

    @Override
    public JsonNullableGenerator writeNull() {
        jg.writeNull();
        return this;
    }

    @Override
    public void close() {
        jg.close();
    }

    @Override
    public void flush() {
        jg.flush();
    }

    @Override
    public JsonNullableGenerator writeKey(String name) {
        jg.writeKey(name);
        return this;
    }
}
