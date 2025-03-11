package services;

import dao.ProductDao;
import dto.TrendingProductDTO;
import models.Product;

import java.util.List;
import java.util.ArrayList;

public class HomeService {

	private ProductDao productDao;

	public HomeService() {
		productDao = new ProductDao();
	}

	// Lấy tất cả sản phẩm trending
	public List<TrendingProductDTO> getAllTrendingProducts() {
		return productDao.getTrendingProducts();
	}

	// Lấy sản phẩm trending theo ID
	public TrendingProductDTO getTrendingProductById(Long productId) {
		List<TrendingProductDTO> trendingProducts = productDao.getTrendingProducts();
		for (TrendingProductDTO product : trendingProducts) {
			if (product.getProductId() == productId) {
				return product;
			}
		}
		return null;
	}

	// Lọc sản phẩm trending theo tên
	public List<TrendingProductDTO> getTrendingProductsByName(String name) {
		List<TrendingProductDTO> trendingProducts = productDao.getTrendingProducts();
		List<TrendingProductDTO> result = new ArrayList<>();

		for (TrendingProductDTO product : trendingProducts) {
			if (product.getName().toLowerCase().contains(name.toLowerCase())) {
				result.add(product);
			}
		}
		return result;
	}

	// Kiểm tra sản phẩm có phải là trending không
	public boolean isTrendingProduct(Long productId) {
		TrendingProductDTO product = getTrendingProductById(productId);
		return product != null;
	}
//public static void main(String[] args) {
//	HomeService service = new HomeService();
//	System.out.println(service.isTrendingProduct(1L));
//}
	// Lấy tất cả sản phẩm bán chạy nhất
	public List<Product> getAllBestSeller() {
		return null;
		
	}
}
