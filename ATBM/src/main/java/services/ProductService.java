package services;

import dao.ProductDao;
import models.Product;
import java.util.List;

public class ProductService implements IService<Product, Long> {
	private ProductDao productDao = new ProductDao();

	@Override
	public boolean insert(Product product) {
		return productDao.insert(product);
	}

	@Override
	public Product getById(Long productId) {
		return productDao.getById(productId);
	}

	@Override
	public List<Product> getAll() {
		return productDao.getAll();
	}

	@Override
	public boolean delete(Long productId) {
		return productDao.delete(productId);
	}

	@Override
	public boolean update(Product product) {
		return productDao.update(product);
	}
	public static void main(String[] args) {
		ProductService service = new ProductService();
		System.out.println(service.getAll());
	}
}