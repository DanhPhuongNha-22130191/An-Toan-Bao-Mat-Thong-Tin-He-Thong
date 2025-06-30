package com.atbm.utils;


import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {
    private static final Logger logger = Logger.getLogger(LogUtils.class.getName());

    public static <T> void info(Class<T> clazz, String message) {
        logger.info(clazz.getName() + ":  " + message);
    }

    public static <T> void debug(Class<T> clazz, String message) {
        logger.severe(clazz.getName() + ":  " + message);
    }

    /**
     * Ghi log ở mức độ ERROR kèm stack trace của ngoại lệ
     * @param clazz Lớp gọi log
     * @param message Thông điệp log
     * @param throwable Ngoại lệ cần ghi log
     */
    public static <T> void error(Class<T> clazz, String message, Throwable throwable) {
        logger.log(Level.SEVERE, clazz.getName() + ": " + message, throwable);
    }
}
