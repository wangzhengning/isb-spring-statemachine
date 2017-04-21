package com.statemachine.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zn.wang on 17/4/21.
 * 使用org.springframework.util.ReflectionUtils做一些事情
 */
public class IsbReflectionUtils {

    private static final Logger logger = LoggerFactory.getLogger(IsbReflectionUtils.class);

    public static <T> T readField(String name, Object target) throws Exception {
        Field field = null;
        Class<?> clazz = target.getClass();
        do {
            try {
                field = clazz.getDeclaredField(name);
            } catch (Exception ex) {
                logger.error("[ERRORS-READ-FIELD-FUNCTION]" , ex);
            }

            clazz = clazz.getSuperclass();
        } while (field == null && !clazz.equals(Object.class));

        if (field == null)
            throw new IllegalArgumentException("Cannot find field '" + name + "' in the class hierarchy of "
                    + target.getClass());
        field.setAccessible(true);
        return (T) field.get(target);
    }

    public static void setField(String name ,Object target, Object value) throws Exception{
        Field field = null;
        Class<?> clazz = target.getClass();
        do {
            try {
                field = clazz.getDeclaredField(name);
            } catch (Exception ex) {
                logger.error("[ERRORS-SET-FIELD-FUNCTION]" , ex);
            }

            clazz = clazz.getSuperclass();
        } while (field == null && !clazz.equals(Object.class));

        if (field == null)
            throw new IllegalArgumentException("Cannot find field '" + name + "' in the class hierarchy of "
                    + target.getClass());
        field.setAccessible(true);
        field.set(target, value);
    }


    public static <T> T callMethod(String name, Object target) throws Exception {
        Class<?> clazz = target.getClass();
        Method method = ReflectionUtils.findMethod(clazz , name);
        if(null == method)
            throw new IllegalArgumentException("Cannot find method '" + method +"' in the class hierarchy of "
                    + target.getClass());

        //必须设置访问权限true
        method.setAccessible(true);
        return (T)ReflectionUtils.invokeMethod(method , target);
    }


    public static <T> T callMethod(String name , Object target , Object[] args ,Class<?> argsTypes)
        throws Exception{
        Class<?> clazz = target.getClass();
        Method method = ReflectionUtils.findMethod(clazz, name, argsTypes);

        if (null == method)
            throw new IllegalArgumentException("Cannot find method '" + method + "' in the class hierarchy of "
                    + target.getClass());
        method.setAccessible(true);
        return (T) ReflectionUtils.invokeMethod(method, target, args);
    }

    public static <T> T callMethod(String name ,Object target , Object[] args , Class<?>... argsTypes)
        throws Exception{
        Class<?> clazz = target.getClass();
        Method method = ReflectionUtils.findMethod(clazz , name , argsTypes );

        if(null == method)
            throw new IllegalArgumentException("Cannot find method '" + method +"' in the class hierarchy of "
                + target.getClass());
        method.setAccessible(true);
        return (T) ReflectionUtils.invokeMethod(method , target , args);
    }


}

































