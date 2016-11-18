package utils;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

@RequiredArgsConstructor
class RequestObjectParser {
    private final HttpServletRequest req;

    /**
     * Reconstructs object from request into supplied blank, returning reference to it.
     * Do not use with Spring managed objects.
     */
    <T> T reconstruct(T blank) {
        for (Field field : blank.getClass().getDeclaredFields()) {
            String fieldName = JsonPacker.getFieldName(field);

            final String fieldValue = req.getParameter(fieldName);
            if (fieldValue != null) {
                Object fieldValueAsObject = convertValueIntoObject(fieldValue, field.getType());
                setObjectFieldValue(blank, field, fieldValueAsObject);
            }
        }
        return blank;
    }

    private static Object convertValueIntoObject(String fieldValue, Class<?> fieldType) {
        if (fieldType.isAssignableFrom(String.class))
            return fieldValue;

        if (fieldType.isAssignableFrom(double.class))
            return Double.valueOf(fieldValue);

        if (fieldType.isAssignableFrom(long.class))
            return Long.valueOf(fieldValue);

        if (fieldType.isAssignableFrom(boolean.class))
            return (fieldValue.equalsIgnoreCase("true"));

        throw new IllegalArgumentException("Incompatible field value: " + fieldValue);
    }

    private static void setObjectFieldValue(Object blank, Field field, Object fieldValueAsObject) {
        try {
            field.setAccessible(true);
            field.set(blank, fieldValueAsObject);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot set field value", e);
        }
    }
}