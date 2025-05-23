package com.atbm.dao;

import com.atbm.models.Voucher;
import com.atbm.utils.ExecuteSQLUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class VoucherDao implements IDao<Voucher, Long> {

    @Override
    public boolean insert(Voucher entity) {
        String query = "INSERT INTO Voucher (code, expirationTime, percentDecrease, name, quantity, maxUsagePerUser, isActive) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return ExecuteSQLUtil.executeUpdate(query,
                entity.getCode(),
                entity.getExpirationTime(),
                entity.getPercentDecrease(),
                entity.getName(),
                entity.getQuantity());
    }

    @Override
    public Voucher getById(Long id) {
        String query = "SELECT * FROM Voucher WHERE voucherId = ?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, id);
        try {
            if (rs.next()) {
                return extractVoucher(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Voucher> getAll() {
        String query = "SELECT * FROM Voucher";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        List<Voucher> vouchers = new LinkedList<>();
        try {
            while (rs.next()) {
                vouchers.add(extractVoucher(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vouchers;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM Voucher WHERE voucherId = ?";
        return ExecuteSQLUtil.executeUpdate(query, id);
    }

    @Override
    public boolean update(Voucher entity) {
        String query = "UPDATE Voucher SET code = ?, expirationTime = ?, percentDecrease = ?, " +
                "name = ?, quantity = ?" +
                "WHERE voucherId = ?";
        return ExecuteSQLUtil.executeUpdate(query,
                entity.getCode(),
                entity.getExpirationTime(),
                entity.getPercentDecrease(),
                entity.getName(),
                entity.getQuantity(),
                entity.getVoucherId());
    }

    public Voucher getByCode(String code) throws SQLException {
        String query = "SELECT * FROM Voucher WHERE code = ?";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query, code);
        if (rs != null && rs.next())
            return extractVoucher(rs);
        return null;
    }

    public List<Voucher> getActiveVouchers() throws SQLException {
        String query = "SELECT * FROM Voucher WHERE isActive = true AND expirationTime > CURRENT_DATE";
        ResultSet rs = ExecuteSQLUtil.ExecuteQuery(query);
        List<Voucher> vouchers = new LinkedList<>();
        if (rs != null)
            while (rs.next()) {
                vouchers.add(extractVoucher(rs));
            }
        return vouchers;
    }

    public boolean decreaseQuantity(long voucherId) {
        String query = "UPDATE Voucher SET quantity = quantity - 1 WHERE voucherId = ? AND quantity > 0";
        return ExecuteSQLUtil.executeUpdate(query, voucherId);
    }

    private Voucher extractVoucher(ResultSet rs) throws SQLException {
        Voucher voucher = new Voucher();
        voucher.setVoucherId(rs.getLong("voucherId"));
        voucher.setCode(rs.getString("code"));
        voucher.setExpirationTime(rs.getDate("expirationTime"));
        voucher.setPercentDecrease(rs.getDouble("percentDecrease"));
        voucher.setName(rs.getString("name"));
        voucher.setQuantity(rs.getInt("quantity"));
        return voucher;
    }

    public boolean voucherIsUsed(long accountId, String code) throws SQLException {
        String query = "select * from UsedVouchers where accountId=? and code=?";
        ResultSet resultSet = ExecuteSQLUtil.ExecuteQuery(query, accountId, code);
        if (resultSet != null)
            return resultSet.next();
        else return false;
    }

    public boolean usingVoucher(long accountId, String code, Date currentDate) {
        String query = "insert into UsedVouchers (accountId,code,usedAt) values (?,?,?)";
        return ExecuteSQLUtil.executeUpdate(query, accountId, code, currentDate);
    }

}



