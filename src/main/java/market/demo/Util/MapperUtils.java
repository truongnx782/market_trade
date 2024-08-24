package market.demo.Util;

import java.lang.reflect.Field;

public class MapperUtils {
    public static <T, U> U mapCommonFields(T source, Class<U> targetClass) {
        if (source == null) {
            return null;
        }

        U target = null;
        try {
            // Tạo một instance mới của lớp target
            target = targetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // Lấy các trường từ lớp nguồn và lớp đích, bao gồm các lớp cha
        Field[] sourceFields = getAllDeclaredFields(source.getClass());
        Field[] targetFields = getAllDeclaredFields(targetClass);

        for (Field sourceField : sourceFields) {
            sourceField.setAccessible(true);
            for (Field targetField : targetFields) {
                targetField.setAccessible(true);
                if (sourceField.getName().equals(targetField.getName()) &&
                        sourceField.getType().equals(targetField.getType())) {
                    try {
                        Object value = sourceField.get(source);
                        targetField.set(target, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return target;
    }

    private static Field[] getAllDeclaredFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            Field[] superFields = getAllDeclaredFields(superClass);
            Field[] combined = new Field[fields.length + superFields.length];
            System.arraycopy(fields, 0, combined, 0, fields.length);
            System.arraycopy(superFields, 0, combined, fields.length, superFields.length);
            return combined;
        }
        return fields;
    }
}

