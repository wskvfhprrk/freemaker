package com.hejz.generate.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class StringUtils {
    // isEmpty
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    // isBlank
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    // isNotEmpty
    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    // isNotBlank
    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    /**
     * 下划线转驼峰
     * @param tableName
     * @return
     */
    public static String makeA11WordFirstLetterUpperCase(String tableName) {
        String[] s = tableName.split("_");
        StringBuffer sb=new StringBuffer();
        for (String s1 : s) {
            sb.append(s1.substring(0, 1).toUpperCase() + s1.substring(1));
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰首字线小写
     * @param columnName
     * @return
     */
    public static String javaBeanName(String columnName) {
        String s = makeA11WordFirstLetterUpperCase(columnName);
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    /**
     * 将一个类用属性名为Key，值为value的方式传入map
     * @param o
     * @return
     */
    public static Map<String,Object> conver2Map(Object o){
        Map<String,Object> map=new HashMap<>();
        List<Method> list=getAllMethods(o);
        for (Method m : list) {
            String name = m.getName();
            if(name.startsWith("get")){
                //获取属性名
                name.substring(3);
                try {
                    map.put(name,m.invoke(o));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;

    }

    /**
     * 获取ojb中的所有方法
     * @param o
     * @return
     */
    private static List<Method> getAllMethods(Object o) {
        List<Method> methods=new ArrayList<>();
        Class<?> aClass = o.getClass();
        while (!aClass.getName().equals("java.lang.Object")){
            methods.addAll(Arrays.asList(aClass.getDeclaredMethods()));
        }
        return methods;
    }
}
