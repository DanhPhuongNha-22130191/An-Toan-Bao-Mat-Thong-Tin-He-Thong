package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import models.Voucher;
import utils.ExecuteSQLUtil;

public class VoucherDao implements IDao<Voucher, Long> {

	@Override
	public boolean insert(Voucher entity) {
		String query = "insert into Voucher (code,expirationTime,percentDescrease,name,quantity) values (?,?,?,?,?)";
		return ExecuteSQLUtil.executeUpdate(query, entity.getCode(), entity.getExpirationTime(),
				entity.getPercentDescrease(), entity.getName(), entity.getQuantity());
	}

	@Override
	public Voucher getById(Long id) {
		String query = "select * from Voucher where voucherId=?";
		ResultSet resultSet = ExecuteSQLUtil.executeQuery(query, id);
		Voucher voucher = null;
		try {
			if (resultSet.next()) {
				voucher = new Voucher(resultSet.getLong(1), resultSet.getString(2), resultSet.getDate(3),
						resultSet.getDouble(4), resultSet.getString(5), resultSet.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return voucher;
	}

	@Override
	public List<Voucher> getAll() {
		String query = "select * from Voucher";
		ResultSet resultSet = ExecuteSQLUtil.executeQuery(query, new Object[0]);
		List<Voucher> listVoucher = new LinkedList<Voucher>();
		try {
			while (resultSet.next()) {
				Voucher voucher = new Voucher(resultSet.getLong(1), resultSet.getString(2), resultSet.getDate(3),
						resultSet.getDouble(4), resultSet.getString(5), resultSet.getInt(6));
				listVoucher.add(voucher);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			listVoucher = null;
		}
		return listVoucher;
	}

	@Override
	public boolean delete(Long id) {
		String query = "delete from Voucher where voucherId=?";
		return ExecuteSQLUtil.executeUpdate(query, id);
	}

	@Override
	public boolean update(Voucher entity) {
		return false;
	}

}
