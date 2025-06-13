package com.atbm.utils;


import java.util.logging.Logger;

public class LogUtils {
    private static final Logger logger = Logger.getLogger(LogUtils.class.getName());

    public static <T> void info(Class<T> clazz, String message) {
        logger.info(clazz.getName() + ":  " + message);
    }

    public static <T> void debug(Class<T> clazz, String message) {
        logger.severe(clazz.getName() + ":  " + message);
    }
}
