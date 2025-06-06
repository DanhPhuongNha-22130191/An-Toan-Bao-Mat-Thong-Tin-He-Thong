package com.atbm.services;

import com.atbm.dao.ProductDao;
import com.atbm.models.Brand;
import com.atbm.models.ProductState;
import com.atbm.models.Strap;
import com.atbm.models.Brand;
import com.atbm.models.Product;

import java.util.List;
import java.util.Map;

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

//	public List<Product> getProductsByStrapId(long strapId) {
//		return productDao.getProductByStrapId(strapId);
//	}

	public List<Strap> getAllStrap() {
		return productDao.getStraps();
	}

    public List<ProductState> getAllState() {
        return productDao.getStates();
    }

    public List<ProductState> getAllState() {
        return productDao.getStates();
    }

    public List<Product> getByBrandId(long brandId) {
        return productDao.getProductsByBrandId(brandId);
    }

    // Đồng bộ kiểu List<Long> với DAO
    public List<Product> filterProducts(List<Long> brandIds, List<Long> strapIds, Double minPrice, Double maxPrice) {
        return productDao.filterProducts(brandIds, strapIds, minPrice, maxPrice);
    }

    public Double getMinProductPrice() {
        return productDao.getMinPrice();
    }

	public Double getMaxProductPrice() {
		return productDao.getMaxPrice();
	}

    // Tổng số lượng sản phẩm trong kho
    public int getTotalProductStock() {
        return productDao.getTotalProductStock();
    }

    // Đếm số sản phẩm sắp hết hàng (stock <= 2)
    public int countLowStockProducts() {
        return productDao.countLowStockProducts();
    }


    // Tổng số thương hiệu (Brand)
    public int getTotalBrandCount() {
        return productDao.getTotalBrandCount();
    }

    public String getImageByProductId(long id) {
        return productDao.getImageByProductId(id);
    }

    // Tổng số lượng sản phẩm trong kho
    public int getTotalProductStock() {
        return productDao.getTotalProductStock();
    }

    // Đếm số sản phẩm sắp hết hàng (stock <= 2)
    public int countLowStockProducts() {
        return productDao.countLowStockProducts();
    }


    // Tổng số thương hiệu (Brand)
    public int getTotalBrandCount() {
        return productDao.getTotalBrandCount();
    }

    public String getImageByProductId(long id) {
        return productDao.getImageByProductId(id);
    }

}
