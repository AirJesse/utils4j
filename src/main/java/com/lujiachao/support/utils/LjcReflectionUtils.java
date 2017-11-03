package com.lujiachao.support.utils;

import com.lujiachao.support.common.LjcUtilsException;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by cyril on 17-2-14.
 */
public class LjcReflectionUtils {

    //类属性列表缓存
    private final static Map<Class<?>, Map<String, Field>> fieldsOfClassCacheMap = new HashMap<>();


    /**
     * 获取该类及其全部父类所声明的全部属性
     *
     * @param targetClass 目标类
     * @return fieldMap 属性名与属性对象的键值对
     */
    public static Map<String, Field> getAllFieldOfClass(Class<?> targetClass) {
        return getAllFieldOfClass(targetClass, true);
    }

    public static Map<String, Field> getAllFieldOfClass(Class<?> targetClass, boolean cached) {
        if (cached && fieldsOfClassCacheMap.containsKey(targetClass)) {
            return fieldsOfClassCacheMap.get(targetClass);
        } else {
            Map<String, Field> fieldMap = new HashMap<>();
            if (targetClass.equals(Object.class)) {
                return fieldMap;
            }

            Field[] fields = targetClass.getDeclaredFields();
            for (Field fld : fields) {
                fieldMap.put(fld.getName(), fld);
            }

            Map<String, Field> superFields = getAllFieldOfClass(targetClass.getSuperclass(), false);

            for (Map.Entry<String, Field> superField : superFields.entrySet()) {
                if (fieldMap.containsKey(superField.getKey()) == false) {
                    fieldMap.put(superField.getKey(), superField.getValue());
                }
            }

            fieldsOfClassCacheMap.put(targetClass, fieldMap);
            return fieldMap;
        }
    }


    /**
     * 用于复制两个类中的同名同类型属性（浅拷贝）。
     *
     * @param <ToType>
     * @param <FromType>
     * @param to         目标对象
     * @param from       源对象
     * @param toClass    目标对象类
     * @param fromClass  源对象类
     * @param notNull    True则不会将源对象中的NULL值属性覆盖目标对象的属性
     * @param except     不参与copy的属性名
     */
    public static <ToType, FromType> ToType copy(ToType to, FromType from, Class<? super ToType> toClass, Class<?
            super FromType> fromClass, boolean notNull, String... except) throws LjcUtilsException {

        Map<String, Field> fromClassFlds = getAllFieldOfClass(from.getClass());
        Map<String, Field> toClassFlds = getAllFieldOfClass(to.getClass());

        Set<String> lex = null;
        if (except != null && except.length != 0) {
            lex = new HashSet<>(Arrays.asList(except));
        } else {
            lex = new HashSet<>();
        }

        for (Map.Entry<String, Field> fldEntry : fromClassFlds.entrySet()) {

            if (lex.isEmpty() || lex.contains(fldEntry.getKey()) == false) {

                if (toClassFlds.containsKey(fldEntry.getKey())) {

                    Field fldFrom = fldEntry.getValue();

                    Field fldTo = toClassFlds.get(fldEntry.getKey());

                    boolean accessableTo = fldTo.isAccessible();
                    boolean accessableFrom = fldFrom.isAccessible();

                    fldFrom.setAccessible(true);
                    fldTo.setAccessible(true);

                    try {
                        if (fldTo
                                .getType()
                                .equals(fldFrom.getType())) {
                            Object fromValue = fldFrom.get(from);
                            if (notNull) {
                                if (fromValue != null) {
                                    fldTo.set(to, fldFrom.get(from));
                                }
                            } else {
                                fldTo.set(to, fldFrom.get(from));
                            }
                        }
                    } catch (Exception e) {
                        throw new LjcUtilsException(e.getMessage());
                    }
//多线程情况下再次修改会产生问题
//                    fldFrom.setAccessible(accessableFrom);
//                    fldTo.setAccessible(accessableTo);

                }
            }
        }
        return to;
    }

    public static <ToType, FromType> ToType copy(ToType to, FromType from, boolean notNull, String... except) throws LjcUtilsException {
        return copy(to, from, (Class<ToType>) to.getClass(), (Class<FromType>) from.getClass(), notNull, except);
    }

    public static <ToType, FromType> ToType copy(ToType to, FromType from, String... except) throws LjcUtilsException {
        return copy(to, from, false, except);
    }

    /**
     * 获取目标类的所有父类
     *
     * @param clazz
     * @return 父类的集合
     */
    public static List<Class> getAllSuperClasses(Class<?> clazz) {
        List<Class> lclz = new ArrayList<Class>();
        if (clazz != null && clazz.equals(Object.class) == false) {
            lclz.add(clazz.getSuperclass());
            lclz.addAll(getAllSuperClasses(clazz.getSuperclass()));
        }
        return lclz;
    }

    /**
     * 创建类的实例
     *
     * @param clazz
     * @param <Tp>
     * @return
     */
    public static <Tp> Tp getInstance(Class<Tp> clazz) throws LjcUtilsException {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

}
