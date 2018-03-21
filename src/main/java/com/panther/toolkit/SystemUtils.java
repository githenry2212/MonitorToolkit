package com.panther.toolkit;

/**
 * @author yangfan
 * @since 2018/3/21 17:08
 */
public class SystemUtils {

    private static final String OS_NAME = System.getProperty("os.name").toUpperCase();

    public static boolean isWindows() {
        return OS_NAME.startsWith("WINDOWS");
    }

    public static String getOsName() {
        return OS_NAME;
    }

    public static boolean isLinux() {
        return OS_NAME.startsWith("LINUX");
    }
}
