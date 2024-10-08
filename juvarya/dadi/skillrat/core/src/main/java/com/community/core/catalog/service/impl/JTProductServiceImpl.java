package com.community.core.catalog.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.community.core.azure.service.JTAwsBlobService;
import com.community.core.catalog.dao.JTProductDao;
import com.community.core.catalog.dao.JTProductToMediaDao;
import com.community.core.catalog.domain.JTProduct;
import com.community.core.catalog.domain.JTProductToMedia;
import com.community.core.catalog.domain.Media;
import com.community.core.catalog.domain.impl.JTProductToMediaImpl;
import com.community.core.catalog.service.JTProductService;

@Service("jtProductService")
public class JTProductServiceImpl implements JTProductService {

	@Resource(name = "jtProductDao")
	private JTProductDao jtProductDao;

	@Resource(name = "jtAwsBlobService")
	private JTAwsBlobService jtAwsBlobService;

	@Resource(name = "jtProductToMediaDao")
	private JTProductToMediaDao productToMediaDao;

	@Override
	@Transactional
	public JTProduct saveProduct(JTProduct product) {
		return jtProductDao.saveProduct(product);
	}

	public JTProductToMedia upload(Long productId, List<MultipartFile> files)
			throws FileNotFoundException, IOException {
		JTProductToMedia product = new JTProductToMediaImpl();
		if (!CollectionUtils.isEmpty(files)) {
			List<Media> uploadedFiles = jtAwsBlobService.uploadFiles(files);
			for (Media media : uploadedFiles) {
				JTProductToMedia feedMedia = new JTProductToMediaImpl();
				feedMedia.setProduct(productId);
				feedMedia.setMedia(media);
				product = productToMediaDao.save(feedMedia);
			}
		}
		return product;
	}

	@Override
	public Page<JTProduct> productsList(Pageable pageable) {
		return jtProductDao.productsList(pageable);
	}

	@Override
	public List<JTProduct> getProductsByItem(int page, int pageSize, Long itemId) {
		return jtProductDao.getProductsByItem(page, pageSize, itemId);
	}

	@Override
	public JTProduct removeCourse(JTProduct product) {
		return jtProductDao.removeCourse(product);
	}

	@Override
	public JTProduct findById(Long id) {
		return jtProductDao.findById(id);
	}

	@Override
	public List<JTProduct> getCustomerByCourses(int pageNo, int pageSize) {
		return jtProductDao.getCustomerByCourses(pageNo, pageSize);
	}

	@Override
	public JTProduct deleteProduct(Long productId) {
		return jtProductDao.deleteProduct(productId);
	}

	@Override
	public List<JTProduct> getProductsList(String query, int pageNo, int pageSize) {
		return jtProductDao.getProductsList(query, pageNo, pageSize);
	}

	@Override
	public List<JTProduct> getPopularProducts(Boolean popular, int pageNo, int pageSize) {
		return jtProductDao.getPopularProducts(popular, pageNo, pageSize);
	}

	@Override
	public Page<JTProduct> getAllProductsByStore(Long storeId, Pageable pageable) {
		return jtProductDao.getByStore(storeId, pageable);
	}

	@Override
	public void deleteProduct(JTProduct jtProduct) {
		jtProductDao.deleteProduct(jtProduct);
	}

	@Override
	public Page<CategoryProductXref> getProductsByCategory(Long categoryId, Pageable pageable) {
		return jtProductDao.getProductsByCategory(categoryId, pageable);
	}

	@Override
	public CategoryProductXref findByProduct(Long productId) {
		return jtProductDao.findByProduct(productId);
	}

	@Override
	public CategoryProductXref saveCategoryProductXref(CategoryProductXref categoryProductXref) {
		return jtProductDao.saveCategoryProductXref(categoryProductXref);
	}

	@Override
	public Page<JTProduct> findSortedByNameANDPriceANDPopular(String name, BigDecimal salePrice, Boolean popular, Pageable pageable) {
		return jtProductDao.findSortedByNameANDPriceANDPopular(name, salePrice, popular, pageable);
	}
}
