package com.atbm.services;


import com.atbm.dao.ProductDao;
import com.atbm.dto.Brand;
import com.atbm.dto.Strap;
import com.atbm.models.Product;

import java.util.List;

public class ProductService implements services.IService<Product, Long> {
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
	public List<Product> filterProducts(List<Integer> brandIds, List<Integer> strapIds, Double minPrice, Double maxPrice) {
		return productDao.filterProducts(brandIds, strapIds, minPrice, maxPrice);
	}
	public Double getMinProductPrice() {
		return productDao.getMinPrice();
	}

	public Double getMaxProductPrice() {
		return productDao.getMaxPrice();
	}



}