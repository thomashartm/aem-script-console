package biz.netcentric.aem.scriptconsole.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author thomas.hartmann@netcentric.biz
 * @since 11/2014
 */
public class ReflectionUtil {

    public static void setFieldValue(final Object object, final String fieldName, final Object value) {
        @SuppressWarnings("rawtypes")
        Class clazz = object.getClass();
        try {
            getAccessibleField(clazz, fieldName).set(object, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getFieldValue(final Object object, final String fieldName) {
        @SuppressWarnings("rawtypes")
        Class clazz = object.getClass();
        try {
            return getAccessibleField(clazz, fieldName).get(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Field getAccessibleField(@SuppressWarnings("rawtypes") final Class clazz, final String fieldName)
            throws NoSuchFieldException {
        Class<?> tmpClass = clazz;
        do {
            for (final Field field : tmpClass.getDeclaredFields()) {
                String candidateName = field.getName();
                if (!candidateName.equals(fieldName)) {
                    continue;
                }
                field.setAccessible(true);
                return field;
            }
            tmpClass = tmpClass.getSuperclass();
        } while (clazz != null);

        throw new RuntimeException("Field '" + fieldName + "' not found on class " + clazz);
    }

    @SuppressWarnings("rawtypes")
    public static Object invoke
            (final Object instance, final String methodName, final Class[] methodParams, final Object[] args)
                    throws Exception {
        Class<? extends Object> cl = instance.getClass();
        Method method = cl.getDeclaredMethod(methodName, methodParams);
        method.setAccessible(true);
        Object result = method.invoke(instance, args);
        return result;
    }

    public static Object invoke
            (final Object instance, final String methodName)
                    throws Exception {
        Class<? extends Object> cl = instance.getClass();
        Method method = cl.getDeclaredMethod(methodName, new Class[0]);
        method.setAccessible(true);
        Object result = method.invoke(instance, new Object[0]);
        return result;
    }
}
