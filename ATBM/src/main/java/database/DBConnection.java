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

