package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=DB_Test;encrypt=false";
    private static final String USER = "minhhien"; // Thay bằng user SQL Server của bạn
    private static final String PASSWORD = "minhhien"; // Thay bằng mật khẩu thật
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                connection = DriverManager.getConnection(URL, USER, PASSWORD);

            } catch (ClassNotFoundException e) {
                System.err.println("Lỗi: Không tìm thấy driver JDBC.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Lỗi kết nối đến database.");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
=======
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	static Connection connection;
	public static Connection getConnection() {
		try {
			connection= DriverManager.getConnection("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
		
	}

}

