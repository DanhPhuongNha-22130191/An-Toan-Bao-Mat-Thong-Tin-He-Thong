package services;

import java.util.List;

import dao.VoucherDao;
import models.Voucher;

public class VoucherService implements IService<Voucher, Long> {
	private VoucherDao dao;

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

	public Voucher getByCode(String code) {
		return dao.getByCode(code);
	}

	public boolean applyVoucher(String code, long accountId) {
		java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
		Voucher voucher = getByCode(code);
		if (voucher != null && currentDate.before(voucher.getExpirationTime())
				&& !dao.voucherIsUsed(accountId, voucher.getCode())) {
			return dao.usingVoucher(accountId, voucher.getCode(), currentDate);
		}
		return false;

	}

}
