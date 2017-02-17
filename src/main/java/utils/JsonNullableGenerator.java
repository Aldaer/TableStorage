package utils;

import com.fasterxml.jackson.core.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Wrapper for JsonGenerator from Jackson. Instead of generating exception
 * on null objects when writing JSON, just skips them. Turns checked exceptions to unchecked
 */
public class JsonNullableGenerator extends JsonGenerator {
    private final JsonGenerator jg;

    public JsonNullableGenerator(JsonFactory factory, OutputStream out) {
        try {
            jg = factory.createGenerator(out, JsonEncoding.UTF8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonNullableGenerator setCodec(ObjectCodec oc) {
        jg.setCodec(oc);
        return this;
    }

    @Override
    public ObjectCodec getCodec() {
        return jg.getCodec();
    }

    @Override
    public Version version() {
        return jg.version();
    }

    @Override
    public JsonNullableGenerator enable(Feature f) {
        jg.enable(f);
        return this;
    }

    @Override
    public JsonNullableGenerator disable(Feature f) {
        jg.disable(f);
        return this;
    }

    @Override
    public boolean isEnabled(Feature f) {
        return false;
    }

    @Override
    public int getFeatureMask() {
        return 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    @Deprecated
    public JsonNullableGenerator setFeatureMask(int values) {
        jg.setFeatureMask(values);
        return this;
    }

    @Override
    public JsonNullableGenerator useDefaultPrettyPrinter() {
        jg.useDefaultPrettyPrinter();
        return this;
    }

    @Override
    public void writeStartArray() {
        try {
            jg.writeStartArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeEndArray() {
        try {
            jg.writeEndArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeStartObject() {
        try {
            jg.writeStartObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeEndObject() {
        try {
            jg.writeEndObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeFieldName(String name) {
        try {
            jg.writeFieldName(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeFieldName(SerializableString name) {
        try {
            jg.writeFieldName(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeString(String text) {
        try {
            jg.writeString(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeString(char[] text, int offset, int len) {
        try {
            jg.writeString(text, offset, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeString(SerializableString text) {
        try {
            jg.writeString(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeRawUTF8String(byte[] text, int offset, int length) {
        try {
            jg.writeRawUTF8String(text, offset, length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeUTF8String(byte[] text, int offset, int length) {
        try {
            jg.writeUTF8String(text, offset, length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeRaw(String text) {
        try {
            jg.writeRaw(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeRaw(String text, int offset, int len) {
        try {
            jg.writeRaw(text, offset, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeRaw(char[] text, int offset, int len) {
        try {
            jg.writeRaw(text, offset, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeRaw(char c) {
        try {
            jg.writeRaw(c);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeRawValue(String text) {
        try {
            jg.writeRawValue(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeRawValue(String text, int offset, int len) {
        try {
            jg.writeRawValue(text, offset, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeRawValue(char[] text, int offset, int len) {
        try {
            jg.writeRawValue(text, offset, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeBinary(Base64Variant bv, byte[] data, int offset, int len) {
        try {
            jg.writeBinary(bv, data, offset, len);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int writeBinary(Base64Variant bv, InputStream data, int dataLength) {
        try {
            jg.writeBinary(bv, data, dataLength);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public void writeNumber(int v) {
        try {
            jg.writeNumber(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeNumber(long v) {
        try {
            jg.writeNumber(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeNumber(BigInteger v) {
        try {
            jg.writeNumber(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeNumber(double v) {
        try {
            jg.writeNumber(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeNumber(float v) {
        try {
            jg.writeNumber(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeNumber(BigDecimal v) {
        try {
            jg.writeNumber(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeNumber(String encodedValue) {
        try {
            jg.writeNumber(encodedValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeBoolean(boolean state) {
        try {
            jg.writeBoolean(state);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeNull() {
        try {
            jg.writeNull();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeObject(Object pojo) {
        try {
            jg.writeObject(pojo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeTree(TreeNode rootNode) {
        try {
            jg.writeTree(rootNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonStreamContext getOutputContext() {
        return jg.getOutputContext();
    }

    @Override
    public void flush() {
        try {
            jg.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void close() {
        try {
            jg.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
