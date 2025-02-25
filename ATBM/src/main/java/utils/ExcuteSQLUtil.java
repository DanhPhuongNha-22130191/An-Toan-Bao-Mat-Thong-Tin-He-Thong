package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnection;
/**
 * @author minhhien
 * Class dùng để thực hiện query
 */
public class ExcuteSQLUtil {
	/**
	 * Phương thức  thực hiện lệnh update, insert, delete
	 * @param query lệnh query
	 * @param data tham số
	 * @return true nếu thành công, ngược lại false
	 */
	public static boolean excuteUpdate(String query, Object... data) {
		Connection connect = DBConnection.getConnection();
		try {
			PreparedStatement preStatement = connect.prepareStatement(query);
			for (int i = 0; i < data.length; i++)
				preStatement.setObject(i + 1, data[i]);
			return preStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
