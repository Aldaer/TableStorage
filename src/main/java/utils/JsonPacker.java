package utils;

import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Packs object into JSON. Works only with declared fields (as opposed to inherited).
 * Uses @Column annotations to produce JSON field names, defaults to field name for non-annotated fields.
 * Due to autoboxing, primitive types are written as strings, i.e. "111" instead of 111.
 * Object fields that do not implement JsonPackable interface are written as strings via toString().
 * Arrays fields are not supported.
 * Does not flush the generator.
 */
@RequiredArgsConstructor
public class JsonPacker {
    private final JsonNullableGenerator generator;

    public void packIntoJson(JsonPackable object) {
        generator.writeStartObject();
        packObjectBody(object);
        generator.writeEndObject();
    }

    public void packIntoJson(JsonPackable[] objects) {
        generator.writeStartArray();
        Arrays.stream(objects).forEachOrdered(this::packIntoJson);
        generator.writeEndArray();
    }

    public void flushGenerator() {
        generator.flush();
    }

    private void packObjectBody(Object obj) {
        final Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String fieldName = getFieldName(field);

            Object fieldValue;
            try {
                fieldValue = field.get(obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot get field value", e);
            }

            if (JsonPackable.class.isAssignableFrom(fieldValue.getClass())) {
                generator.writeFieldName(fieldName);
                generator.writeStartObject();
                packObjectBody(fieldValue);
                generator.writeEndObject();
            } else {
                generator.writeFieldName(fieldName);
                generator.writeString(fieldValue.toString());
            }
        }
    }

    static String getFieldName(Field field) {
        final Column annotation = field.getAnnotation(Column.class);

        return annotation == null ? field.getName() : annotation.name();
    }

}

