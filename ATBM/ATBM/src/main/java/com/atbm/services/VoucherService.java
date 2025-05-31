package com.atbm.services;

import com.atbm.dao.VoucherDao;
import com.atbm.models.Voucher;

import java.sql.SQLException;
import java.util.List;

public class VoucherService implements IService<Voucher, Long> {
    private final VoucherDao dao;

    public VoucherService() {
        dao = new VoucherDao();
    }

    @Override
    public boolean insert(Voucher entity) {
        if (entity == null) return false;
        return dao.insert(entity);
    }

    @Override
    public Voucher getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<Voucher> getAll() {
        return dao.getAll();
    }

    @Override
    public boolean delete(Long id) {
        return dao.delete(id);
    }

    @Override
    public boolean update(Voucher entity) {
        return dao.update(entity);
    }

    public Voucher getByCode(String code) throws SQLException {
        return dao.getByCode(code);
    }

    public Voucher canApplyVoucher(String code) throws SQLException {
        Voucher voucher = getByCode(code);
        if (voucher == null || voucher.isValid())
            return null;
        return voucher;
    }

    public boolean isVoucherValid(String code, long accountId) throws SQLException {
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        Voucher voucher = getByCode(code);
        return voucher == null || !currentDate.before(voucher.getExpirationTime())
                || dao.voucherIsUsed(accountId, voucher.getCode());
    }

}

