package com.community.core.catalog.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.community.core.catalog.domain.JTProduct;
import com.community.core.catalog.domain.JTProductToMedia;

public interface JTProductService {

	JTProduct saveProduct(JTProduct product);

	Page<JTProduct> productsList(Pageable pageable);

	List<JTProduct> getProductsByItem(int page, int pageSize, Long itemId);

	JTProduct removeCourse(JTProduct product);

	JTProduct findById(Long id);

	public List<JTProduct> getCustomerByCourses(int pageNo, int pageSize);

	JTProduct deleteProduct(Long productId);

	List<JTProduct> getProductsList(String query, int pageNo, int pageSize);

	List<JTProduct> getPopularProducts(Boolean popular, int pageNo, int pageSize);

	JTProductToMedia upload(Long productId, List<MultipartFile> files) throws FileNotFoundException, IOException;

	Page<JTProduct> getAllProductsByStore(Long storeId, Pageable pageable);

	void deleteProduct(JTProduct jtProduct);

	Page<CategoryProductXref> getProductsByCategory(Long categoryId, Pageable pageable);
	
	CategoryProductXref findByProduct(Long productId);
	
	public CategoryProductXref saveCategoryProductXref(CategoryProductXref categoryProductXref);

	Page<JTProduct> findSortedByNameANDPriceANDPopular(String name, BigDecimal salePrice, Boolean popular, Pageable pageable);

}
