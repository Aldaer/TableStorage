package utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestObjectParser {
    private final Map<String, String> flatParameterMap;

    /**
     * Reconstructs object from request into supplied blank, returning reference to it.
     * Do not use with Spring managed objects. Use detached objects instead.
     */
    public <T> T reconstruct(T blank) {
        for (Field field : blank.getClass().getDeclaredFields())
            reconstructField(blank, field);

        return blank;
    }

    private <T> void reconstructField(T blank, Field field) {
        String fieldName = JsonPacker.getFieldName(field);

        final String fieldValue = flatParameterMap.get(fieldName);
        if (fieldValue != null) {
            Object fieldValueAsObject = convertValueIntoObject(fieldValue, field.getType());
            setObjectFieldValue(blank, field, fieldValueAsObject);
        }
    }

    /**
     * Creates managed entity by finding/creating it in the database using primary key,
     * then fills its fields from request. Primary key is the field annotated with @javax.persistence.Id.
     */
    public <T extends JsonPackable> T reconstructFromPrototype(Class<T> entityClass, Function<Object, T> getPrototypeByKey) {
        final Object pKeyValue = getPrimaryKeyValue(entityClass);

        final T prototype = getPrototypeByKey.apply(pKeyValue);

        if (prototype != null) {
            Arrays.stream(entityClass.getDeclaredFields())
                    .filter(RequestObjectParser::isFillableField)
                    .forEach(field -> reconstructField(prototype, field));
        }

        return prototype;
    }

    public <T extends JsonPackable> Object getPrimaryKeyValue(Class<T> entityClass) {
        final Field pKeyField = Arrays.stream(entityClass.getDeclaredFields())
                .filter(RequestObjectParser::isPrimaryKeyField)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);

        final String pKeyName = JsonPacker.getFieldName(pKeyField);

        return convertValueIntoObject(flatParameterMap.get(pKeyName), pKeyField.getType());
    }

    private static boolean isPrimaryKeyField(Field f) {
        return f.isAnnotationPresent(javax.persistence.Id.class);
    }

    private static boolean isFillableField(Field f) {
        return !isPrimaryKeyField(f);
    }

    private static Object convertValueIntoObject(String fieldValue, Class<?> fieldType) { // TODO: add parsing methods as required
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

    public RequestObjectParser(Map<String, String[]> parameterMap) {
        flatParameterMap = flattenMap(parameterMap);
    }

    static Map<String, String> flattenMap(Map<String, String[]> parameterMap) {
        return parameterMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> String.join(",", (CharSequence[]) entry.getValue())));
    }

    public RequestObjectParser(String putLine) {
        flatParameterMap = new HashMap<>();
        Stream.of(putLine.split(",", 2))
                .map(s ->  s.split("=", 2))
                .forEach(s -> flatParameterMap.put(s[0], s[1]));
    }



}