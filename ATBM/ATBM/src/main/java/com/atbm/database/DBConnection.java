package com.atbm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    static Connection connection;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=WatchShop;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "123456789";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("SQL Server JDBC Driver not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static void main(String[] args) {
        System.out.println(123);
        if(DBConnection.getConnection()!=null) {
            System.out.println("S");
        }else {
            System.out.println("F");
        }
    }

}

