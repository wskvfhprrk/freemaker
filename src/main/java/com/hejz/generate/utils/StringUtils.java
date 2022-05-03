package com.hejz.generate.utils;

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
}
