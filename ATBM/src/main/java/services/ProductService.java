package services;

import dao.ProductDao;
import dto.Brand;
import dto.Strap;
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

	public List<Product> getAllTrendingProduct() {
		return productDao.getTrendingProducts();
	}

	public List<Brand> getAllBrand() {
		return productDao.getBrands();
	}

	public List<Product> getProductsByStrap(String strapId) {
		return productDao.getProductByStrap(strapId);
	}

	public List<Strap> getAllStrap() {
		return productDao.getStraps();
	}

	public List<Product> getByBrandId(int brandId) {
		return productDao.getProductsByBrandId(brandId);
	}

	public static void main(String[] args) {
		ProductService service = new ProductService();
		System.out.println(service.getByBrandId(1));
	}

}