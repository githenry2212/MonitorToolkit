package com.panther.toolkit;

/**
 * @author yangfan
 * @since 2018/3/21 17:30
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
