package com.atbm.services;

import com.atbm.dao.VoucherDao;
import com.atbm.models.Voucher;

import java.sql.SQLException;
import java.util.List;

public class VoucherService implements services.IService<Voucher, Long> {
    private final VoucherDao dao;

    public VoucherService() {
        dao = new VoucherDao();
    }

    @Override
    public boolean insert(Voucher entity) {
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


    public boolean deactivateVoucher(long voucherId) {
        Voucher voucher = dao.getById(voucherId);
        if (voucher != null) {
            return dao.update(voucher);
        }
        return false;
    }


    public int getRemainingQuantity(long voucherId) {
        Voucher voucher = dao.getById(voucherId);
        return voucher != null ? voucher.getQuantity() : 0;
    }

    public boolean canApplyToOrder(Voucher voucher, double orderTotal) {
        if (voucher == null || !voucher.isValid()) {
            return false;
        }
        return orderTotal >= voucher.getMinOrderValue() &&
                voucher.getQuantity() > 0;
    }

    public double calculateDiscount(Voucher voucher, double orderTotal) {
        if (!canApplyToOrder(voucher, orderTotal)) {
            return 0;
        }
        return (orderTotal * voucher.getPercentDecrease()) / 100;
    }


}

