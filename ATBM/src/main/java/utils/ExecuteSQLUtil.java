
package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnection;

/**
 * @author minhhien
 */
public class ExecuteSQLUtil {
	/**
	 * Phương thức thực hiện lệnh update, insert, delete
	 * 
	 * @param query lệnh query
	 * @param data  tham số
	 * @return true nếu thành công, ngược lại false
	 */
	public static boolean executeUpdate(String query, Object... data) {
		Connection connect = DBConnection.getConnection();
		try {
			PreparedStatement preStatement = connect.prepareStatement(query);
			for (int i = 0; i < data.length; i++)
				preStatement.setObject(i + 1, data[i]);
			return preStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Phương thức thực hiện lệnh excuteQuery
	 * 
	 * @param query lệnh query
	 * @param data  tham số
	 * @return ResultSet đại diện cho từng dòng giá trị
	 */
	public static ResultSet ExecuteQuery(String query, Object... data) {
		Connection connect = DBConnection.getConnection();
		try {
			PreparedStatement preStatement = connect.prepareStatement(query);
			for (int i = 0; i < data.length; i++)
				preStatement.setObject(i + 1, data[i]);
			return preStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
