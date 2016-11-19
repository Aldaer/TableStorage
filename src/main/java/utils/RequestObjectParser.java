package utils;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Function;

@RequiredArgsConstructor
class RequestObjectParser {
    private final HttpServletRequest req;

    /**
     * Reconstructs object from request into supplied blank, returning reference to it.
     * Do not use with Spring managed objects.
     */
    <T> T reconstruct(T blank) {
        for (Field field : blank.getClass().getDeclaredFields())
            reconstructField(blank, field);

        return blank;
    }

    private <T> void reconstructField(T blank, Field field) {
        String fieldName = JsonPacker.getFieldName(field);

        final String fieldValue = req.getParameter(fieldName);
        if (fieldValue != null) {
            Object fieldValueAsObject = convertValueIntoObject(fieldValue, field.getType());
            setObjectFieldValue(blank, field, fieldValueAsObject);
        }
    }

    /**
     * Creates managed entity by finding/creating it in the database using primary key,
     * then fills its fields from request.
     */
    public <T extends JsonPackable> T reconstructFromPrototype(Class<T> entityClass, Function<Object, T> getPrototypeByKey) {
        final Field[] declaredFields = entityClass.getDeclaredFields();

        final Field pKeyField = Arrays.stream(declaredFields)
                .filter(RequestObjectParser::isPrimaryKeyField)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);

        final String pKeyName = JsonPacker.getFieldName(pKeyField);

        final Object pKeyValue = convertValueIntoObject(req.getParameter(pKeyName), pKeyField.getType());

        final T prototype = getPrototypeByKey.apply(pKeyValue);

        Arrays.stream(declaredFields)
                .filter(RequestObjectParser::isFillableField)
                .forEach(field -> reconstructField(prototype, field));

        return prototype;
    }

    private static boolean isPrimaryKeyField(Field f) {
        return f.isAnnotationPresent(javax.persistence.Id.class);
    }

    private static boolean isFillableField(Field f) {
        return ! isPrimaryKeyField(f);
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