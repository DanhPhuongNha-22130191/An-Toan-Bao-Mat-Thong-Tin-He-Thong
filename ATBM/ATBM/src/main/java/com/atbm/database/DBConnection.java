package com.atbm.database;


import com.atbm.utils.LogUtils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    static Connection connection;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=WatchShop;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "1";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return connection;
        } catch (Exception e) {
            LogUtils.debug(DBConnection.class, "Lỗi khi kết nối với cơ sở dữ liệu");
            throw new RuntimeException("Lỗi khi kết nối với cơ sở dữ liệu");
        }
    }
}

