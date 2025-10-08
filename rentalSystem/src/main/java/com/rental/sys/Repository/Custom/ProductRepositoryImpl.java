package com.rental.sys.Repository.Custom;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rental.sys.entities.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
@Transactional
public class ProductRepositoryImpl implements ProductCustomRepository {
  
        private static final Logger log = LoggerFactory.getLogger(ProductRepositoryImpl.class);

        @PersistenceContext
        private EntityManager entityManager;

        @Override
        public List<Product> filterBy(Integer page, Integer size, String type, String categoryName, String productName,
                                      String description, String color, BigDecimal minPrice, BigDecimal maxPrice,
                                      BigDecimal minSalePrice, BigDecimal maxSalePrice, String keyword) {
            log.info("Executing filterBy with page={}, size={}, type={}, categoryName={}, productName={}, description={}, color={}, minPrice={}, maxPrice={}, minSalePrice={}, maxSalePrice={}, keyword={}",
                    page, size, type, categoryName, productName, description, color, minPrice, maxPrice, minSalePrice, maxSalePrice, keyword);

            String queryStr = createQuery("p", type, categoryName, productName, description, color,
                                          minPrice, maxPrice, minSalePrice, maxSalePrice, keyword);
            log.debug("Generated JPQL for filterBy: {}", queryStr);

            TypedQuery<Product> typedQuery = entityManager.createQuery(queryStr, Product.class);
            setParam(typedQuery, page, size, type, categoryName, productName, description, color,
                     minPrice, maxPrice, minSalePrice, maxSalePrice, keyword);

            List<Product> result = typedQuery.getResultList();
            log.info("filterBy returned {} products", result.size());
            return result;
        }

        @Override
        public long countBy(String type, String categoryName, String productName, String description, String color,
                            BigDecimal minPrice, BigDecimal maxPrice, BigDecimal minSalePrice, BigDecimal maxSalePrice,
                            String keyword) {
            log.info("Executing countBy with type={}, categoryName={}, productName={}, description={}, color={}, minPrice={}, maxPrice={}, minSalePrice={}, maxSalePrice={}, keyword={}",
                    type, categoryName, productName, description, color, minPrice, maxPrice, minSalePrice, maxSalePrice, keyword);

            String queryStr = createQuery("COUNT(p.id)", type, categoryName, productName, description, color,
                                          minPrice, maxPrice, minSalePrice, maxSalePrice, keyword);
            log.debug("Generated JPQL for countBy: {}", queryStr);

            TypedQuery<Long> typedQuery = entityManager.createQuery(queryStr, Long.class);
            setParam(typedQuery, null, null, type, categoryName, productName, description, color,
                     minPrice, maxPrice, minSalePrice, maxSalePrice, keyword);

            long count = typedQuery.getSingleResult();
            log.info("countBy returned total: {}", count);
            return count;
        }

        private String createQuery(String selectField, String type, String categoryName, String productName,
                                   String description, String color, BigDecimal minPrice, BigDecimal maxPrice,
                                   BigDecimal minSalePrice, BigDecimal maxSalePrice, String keyword) {

            StringBuilder query = new StringBuilder("SELECT " + selectField + " FROM Product p ");
            boolean isWhereAdded = false;

            // Type filter (partial match on category.name; adjust if Product has 'type' field)
            if (type != null && !type.trim().isEmpty()) {
                query.append(isWhereAdded ? " AND " : " WHERE ");
                isWhereAdded = true;
                query.append(" LOWER(p.category.name) LIKE LOWER(:type) ");
            }

            // Category name filter (partial match)
            if (categoryName != null && !categoryName.trim().isEmpty()) {
                query.append(isWhereAdded ? " AND " : " WHERE ");
                isWhereAdded = true;
                query.append(" LOWER(p.category.name) LIKE LOWER(:categoryName) ");
            }

            // Product name filter (partial match)
            if (productName != null && !productName.trim().isEmpty()) {
                query.append(isWhereAdded ? " AND " : " WHERE ");
                isWhereAdded = true;
                query.append(" LOWER(p.name) LIKE LOWER(:productName) ");
            }

            // Description filter (partial match)
            if (description != null && !description.trim().isEmpty()) {
                query.append(isWhereAdded ? " AND " : " WHERE ");
                isWhereAdded = true;
                query.append(" LOWER(p.description) LIKE LOWER(:description) ");
            }

            // Color filter (partial match)
            if (color != null && !color.trim().isEmpty()) {
                query.append(isWhereAdded ? " AND " : " WHERE ");
                isWhereAdded = true;
                query.append(" LOWER(p.color) LIKE LOWER(:color) ");
            }

            // Rental price filters (pricePerDay: min and max separately)
            if (minPrice != null) {
                query.append(isWhereAdded ? " AND " : " WHERE ");
                isWhereAdded = true;
                query.append(" p.pricePerDay >= :minPrice ");
            }
            if (maxPrice != null) {
                query.append(isWhereAdded ? " AND " : " WHERE ");
                isWhereAdded = true;
                query.append(" p.pricePerDay <= :maxPrice ");
            }

            // Sale price filters (priceForSale: min and max separately)
            if (minSalePrice != null) {
                query.append(isWhereAdded ? " AND " : " WHERE ");
                isWhereAdded = true;
                query.append(" p.priceForSale >= :minSalePrice ");
            }
            if (maxSalePrice != null) {
                query.append(isWhereAdded ? " AND " : " WHERE ");
                isWhereAdded = true;
                query.append(" p.priceForSale <= :maxSalePrice ");
            }

            // Keyword search (OR across name and description, partial match)
            if (keyword != null && !keyword.trim().isEmpty()) {
                query.append(isWhereAdded ? " AND " : " WHERE ");
                isWhereAdded = true;
                query.append(" (LOWER(p.name) LIKE LOWER(:keyword) OR LOWER(p.description) LIKE LOWER(:keyword)) ");
            }

            // Default ORDER BY (only for non-count queries)
            if (!"COUNT(p.id)".equals(selectField)) {
                query.append(" ORDER BY p.createdAt DESC ");
            }

            return query.toString();
        }

        private void setParam(Query query, Integer page, Integer size, String type, String categoryName,
                              String productName, String description, String color,
                              BigDecimal minPrice, BigDecimal maxPrice, BigDecimal minSalePrice,
                              BigDecimal maxSalePrice, String keyword) {

            // String params: Wrap with % for LIKE (only if non-null/non-empty)
            if (type != null && !type.trim().isEmpty()) {
                query.setParameter("type", "%" + type.trim() + "%");
            }
            if (categoryName != null && !categoryName.trim().isEmpty()) {
                query.setParameter("categoryName", "%" + categoryName.trim() + "%");
            }
            if (productName != null && !productName.trim().isEmpty()) {
                query.setParameter("productName", "%" + productName.trim() + "%");
            }
            if (description != null && !description.trim().isEmpty()) {
                query.setParameter("description", "%" + description.trim() + "%");
            }
            if (color != null && !color.trim().isEmpty()) {
                query.setParameter("color", "%" + color.trim() + "%");
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                query.setParameter("keyword", "%" + keyword.trim() + "%");
            }

            // Price params: Set only if non-null
            if (minPrice != null) {
                query.setParameter("minPrice", minPrice);
            }
            if (maxPrice != null) {
                query.setParameter("maxPrice", maxPrice);
            }
            if (minSalePrice != null) {
                query.setParameter("minSalePrice", minSalePrice);
            }
            if (maxSalePrice != null) {
                query.setParameter("maxSalePrice", maxSalePrice);
            }

            // Pagination: Only for filterBy
            if (page != null && size != null && page >= 0 && size > 0) {
                query.setFirstResult(page * size);
                query.setMaxResults(size);
            }
        }
    }

    
        

    

