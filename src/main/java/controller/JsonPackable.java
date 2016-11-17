package controller;

import javax.json.stream.JsonGenerator;
import javax.persistence.Column;
import java.lang.reflect.Field;

/**
 * Packs object into JSON. Works only with declared fields (as opposed to inherited).
 * Uses @Column annotations to produce JSON field names, defaults to field name for non-annotated fields.
 * Due to autoboxing, primitive types are written as strings, i.e. "111" instead of 111.
 * Objects fields that do not implement JsonPackable interface are written as strings via toString().
 * Arrays are not supported.
 */
public interface JsonPackable {

    default void packIntoJson(JsonGenerator generator) throws IllegalAccessException {
        generator.writeStartObject();
        PackJsonUtil.packObjectBody(this, generator);
        generator.writeEnd();
        generator.flush();
    }

    default void packIntoJson(String name, JsonGenerator generator) throws IllegalAccessException {
        generator.writeStartObject(name);
        PackJsonUtil.packObjectBody(this, generator);
        generator.writeEnd();
        generator.flush();
    }

}

class PackJsonUtil {
    static void packObjectBody(Object obj, JsonGenerator generator) throws IllegalAccessException {
        final Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String fieldName = getFieldName(field);

            Object fieldValue = field.get(obj);

            if (JsonPackable.class.isAssignableFrom(fieldValue.getClass())) {
                generator.writeStartObject(fieldName);
                packObjectBody(fieldValue, generator);
                generator.writeEnd();
            } else {
                generator.write(fieldName, fieldValue.toString());
            }
        }
    }

    private static String getFieldName(Field field) {
        final Column annotation = field.getAnnotation(Column.class);

        return annotation == null? field.getName() : annotation.name();
    }
}
