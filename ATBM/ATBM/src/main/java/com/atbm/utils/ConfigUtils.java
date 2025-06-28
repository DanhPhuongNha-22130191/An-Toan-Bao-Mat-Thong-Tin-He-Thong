package com.atbm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigUtils.class.getClassLoader().getResourceAsStream("./config/config.properties")) {
            if (input == null) {
                throw new RuntimeException("Không tìm thấy file config.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Lỗi khi đọc file cấu hình", ex);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
