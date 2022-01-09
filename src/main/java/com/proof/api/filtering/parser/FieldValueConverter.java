package com.proof.api.filtering.parser;

import com.proof.exceptions.FilterParseException;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper class to cast values of fields of classes to the right types.
 */
@Component
public class FieldValueConverter {

    /**
     * Seeks a field in the given class properties and casts the given value to the right type.
     * <br><br>
     * For instance, given this class:
     * <pre>
     * public class Foo {
     *     int bar;
     * }
     * </pre>
     * If we call this method with <code>getValueWithRightType("bar", "25", Foo.class)</code>, this method will return the
     * integer value 25.
     *
     * @param field The class field to check type
     * @param value The value to be converted
     * @param clazz The class
     *
     * @return The value converted to the correct type
     */
    public Object getValueWithRightType(String field, String value, Class<?> clazz) {
        try {
            Class<?> fieldType = getField(clazz, field).getType();
            if (fieldType.equals(String.class)) {
                return value;
            } else if (fieldType.equals(Long.class) || fieldType.equals(Long.TYPE)) {
                return Long.valueOf(value);
            } else if (fieldType.equals(Integer.class) || fieldType.equals(Integer.TYPE)) {
                return Integer.valueOf(value);
            } else if (fieldType.equals(Boolean.class) || fieldType.equals(Boolean.TYPE)) {
                return Boolean.valueOf(value);
            } else if (fieldType.isEnum()) {
                Method valueOf = fieldType.getMethod("valueOf", String.class);
                return valueOf.invoke(null, value);
            } else {
                throw new NoSuchFieldException();
            }
        } catch (NoSuchFieldException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new FilterParseException("Can't find field " + field);
        }
    }

    private static Field getField(Class<?> clazz, String fieldName) {
        List<Field> properties = getAllFields(clazz);
        for (Field field : properties) {
            if (field.getName().equalsIgnoreCase(fieldName)) {
                return field;
            }
        }
        throw new FilterParseException("Can't find field " + fieldName);
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        getAllFieldsRecursive(fields, clazz);
        return fields;
    }

    private static List<Field> getAllFieldsRecursive(List<Field> fields, Class<?> type) {
        Collections.addAll(fields, type.getDeclaredFields());

        if (type.getSuperclass() != null) {
            fields = getAllFieldsRecursive(fields, type.getSuperclass());
        }

        return fields;
    }
}
