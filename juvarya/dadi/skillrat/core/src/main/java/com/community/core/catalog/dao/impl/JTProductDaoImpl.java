package com.community.core.catalog.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.MathUtils;
import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.hibernate.jpa.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.community.core.catalog.dao.JTProductDao;
import com.community.core.catalog.domain.JTProduct;
import com.community.core.catalog.domain.impl.JTProductImpl;
import com.community.core.config.JTCoreConstants;
import com.mchange.lang.DoubleUtils;

@Repository("jtProductDao")
public class JTProductDaoImpl implements JTProductDao {

	@PersistenceContext(unitName = "blPU")
	protected EntityManager em;

	@Override
	@Transactional("blTransactionManager")
	public JTProduct saveProduct(JTProduct product) {
		return em.merge(product);
	}

	@SuppressWarnings("unchecked")
	public Page<JTProduct> productsList(Pageable pageable) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(JTCoreConstants.FROM);
		stringBuilder.append(" JTProductImpl product");
		stringBuilder.append(" WHERE product.popular=true");
		Query queryForCourse = em.createQuery(stringBuilder.toString());

		queryForCourse.setFirstResult((int) pageable.getOffset());
		queryForCourse.setFirstResult(pageable.getPageNumber());
		queryForCourse.setMaxResults(pageable.getPageSize());
		queryForCourse.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(queryForCourse.getResultList())) {
			List<JTProduct> resultList = queryForCourse.getResultList();
			Query countQuery = em.createQuery("SELECT COUNT(id) FROM JTProductImpl WHERE popular=true");
			long totalCount = (long) countQuery.getSingleResult();
			return new PageImpl<>(resultList, pageable, totalCount);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<JTProduct> getProductsByItem(int page, int pageSize, Long itemId) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTProductImpl");
		sb.append(" WHERE jtItem.id =:itemId");
		Query query = em.createQuery(sb.toString());
		query.setFirstResult(page);
		query.setMaxResults(pageSize);
		query.setParameter("itemId", itemId);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		return query.getResultList();
	}

	@Override
	public JTProduct removeCourse(JTProduct product) {

		return em.merge(product);
	}

	@Override
	public JTProduct findById(Long id) {
		return em.find(JTProductImpl.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JTProduct> getCustomerByCourses(int pageNo, int pageSize) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" JTProductImpl product");
		sb.append(" WHERE product.createdBy.id =:customerId");
		Query query = em.createQuery(sb.toString());
		query.setParameter("customerId", CustomerState.getCustomer().getId());
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		query.setFirstResult((pageNo) * pageSize);
		query.setMaxResults(pageSize);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return query.getResultList();
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional("blTransactionManager")
	public JTProduct deleteProduct(Long productId) {
		JTProduct jtProduct = findById(productId);
		if (jtProduct != null) {
			em.remove(jtProduct);
		}
		return jtProduct;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JTProduct> getProductsList(String query, int pageNo, int pageSize) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(JTCoreConstants.FROM);
		stringBuilder.append(" ");
		stringBuilder.append(" JTProductImpl product");
		Query queryForCourse = em.createQuery(stringBuilder.toString());

		if (StringUtils.isNotEmpty(query)) {
			stringBuilder.append(" WHERE product.defaultSku.name LIKE :query ");
			queryForCourse = em.createQuery(stringBuilder.toString());
			queryForCourse.setParameter("query", query);
		}
		queryForCourse.setFirstResult((pageNo) * pageSize);
		queryForCourse.setMaxResults(pageSize);
		queryForCourse.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(queryForCourse.getResultList())) {
			return queryForCourse.getResultList();
		}
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JTProduct> getPopularProducts(Boolean popular, int pageNo, int pageSize) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(JTCoreConstants.FROM);
		stringBuilder.append(" ");
		stringBuilder.append(" JTProductImpl product");
		Query queryForCourse = em.createQuery(stringBuilder.toString());

		if (BooleanUtils.isTrue(popular)) {
			stringBuilder.append(" WHERE product.popular = :popular AND product.active=true");
			queryForCourse = em.createQuery(stringBuilder.toString());
			queryForCourse.setParameter("popular", popular);
		}
		queryForCourse.setFirstResult((pageNo) * pageSize);
		queryForCourse.setMaxResults(pageSize);
		queryForCourse.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(queryForCourse.getResultList())) {
			return queryForCourse.getResultList();
		}
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<JTProduct> getByStore(Long storeId, Pageable pageable) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(JTCoreConstants.FROM);
		stringBuilder.append(" JTProductImpl");
		stringBuilder.append(" WHERE jtStore.id=:storeId");
		Query queryForCourse = em.createQuery(stringBuilder.toString());

		queryForCourse.setParameter("storeId", storeId);
		queryForCourse.setFirstResult((int) pageable.getOffset());
		queryForCourse.setMaxResults(pageable.getPageSize());
		queryForCourse.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(queryForCourse.getResultList())) {
			List<JTProduct> resultList = queryForCourse.getResultList();
			Query countQuery = em.createQuery("SELECT COUNT(id) FROM JTProductImpl WHERE jtStore.id=:storeId");
			countQuery.setParameter("storeId", storeId);
			long totalCount = (long) countQuery.getSingleResult();
			return new PageImpl<>(resultList, pageable, totalCount);
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteProduct(JTProduct jtProduct) {
		em.remove(jtProduct);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<CategoryProductXref> getProductsByCategory(Long categoryId, Pageable pageable) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(JTCoreConstants.FROM);
		stringBuilder.append(" CategoryProductXrefImpl");
		stringBuilder.append(" WHERE category.id=:categoryId");
		Query queryForCourse = em.createQuery(stringBuilder.toString());

		queryForCourse.setParameter("categoryId", categoryId);
		queryForCourse.setFirstResult((int) pageable.getOffset());
		queryForCourse.setMaxResults(pageable.getPageSize());
		queryForCourse.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(queryForCourse.getResultList())) {
			List<JTProduct> resultList = queryForCourse.getResultList();
			Query countQuery = em
					.createQuery("SELECT COUNT(id) FROM CategoryProductXrefImpl WHERE category.id=:categoryId");
			countQuery.setParameter("categoryId", categoryId);
			long totalCount = (long) countQuery.getSingleResult();
			return new PageImpl(resultList, pageable, totalCount);
		}

		return null;
	}

	@Override
	public CategoryProductXref findByProduct(Long productId) {
		StringBuilder sb = new StringBuilder();
		sb.append(JTCoreConstants.FROM);
		sb.append(" CategoryProductXrefImpl");
		sb.append(" WHERE product.id=:productId");
		Query query = em.createQuery(sb.toString());
		query.setParameter("productId", productId);
		query.setHint(QueryHints.HINT_CACHEABLE, true);

		if (CollectionUtils.isNotEmpty(query.getResultList())) {
			return (CategoryProductXref) query.getResultList().get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public CategoryProductXref saveCategoryProductXref(CategoryProductXref categoryProductXref) {
		return em.merge(categoryProductXref);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page<JTProduct> findSortedByNameANDPriceANDPopular(String name, BigDecimal salePrice, Boolean popular, Pageable pageable) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(JTCoreConstants.FROM);
		stringBuilder.append(" JTProductImpl product");
		Query queryForCourse = em.createQuery(stringBuilder.toString());

		if (StringUtils.isNotEmpty(name)) {
			stringBuilder.append(" WHERE product.defaultSku.name=:name");
			queryForCourse = em.createQuery(stringBuilder.toString());
			queryForCourse.setParameter("name", name);
		}

		if (BooleanUtils.isTrue(popular)) {
			stringBuilder.append(" WHERE product.popular=:popular");
			queryForCourse = em.createQuery(stringBuilder.toString());
			queryForCourse.setParameter("popular", popular);
		}
		
		if (Objects.nonNull(salePrice)) {
			stringBuilder.append(" WHERE product.defaultSku.salePrice=:salePrice");
			queryForCourse = em.createQuery(stringBuilder.toString());
			queryForCourse.setParameter("salePrice", salePrice);
		}

		queryForCourse.setMaxResults(pageable.getPageSize());
		queryForCourse.setHint(QueryHints.HINT_CACHEABLE, true);

		if (!CollectionUtils.isEmpty(queryForCourse.getResultList())) {
			List<JTProduct> resultList = queryForCourse.getResultList();
			Query countQuery = em.createQuery(
					"SELECT COUNT(id) FROM JTProductImpl WHERE defaultSku.name=:name OR popular = :popular OR defaultSku.salePrice=:salePrice");
			countQuery.setParameter("name", name);
			countQuery.setParameter("popular", popular);
			countQuery.setParameter("salePrice", salePrice);
			long totalCount = (long) countQuery.getSingleResult();
			return new PageImpl(resultList, pageable, totalCount);
		}

		return null;
	}

}
