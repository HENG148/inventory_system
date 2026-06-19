package com.inventory_system.server.repository;

import java.util.List;
import java.util.Optional;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inventory_system.server.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
  Optional<Product> findBySku(String sku);

  boolean existsBySku(String sku);

  boolean existsBySkuAndIdNot(String sku, Long id);
  @Query("""
        SELECT p FROM Product p
        LEFT JOIN FETCH p.category
        LEFT JOIN FETCH p.supplier
        WHERE (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
                               OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :search, '%')))
          AND (:categoryId IS NULL OR p.category.id = :categoryId)
          AND (:supplierId IS NULL OR p.supplier.id = :supplierId)
    """)
    Page<Product> searchProducts(
      @Param("search") String search,
          @Param("categoryId") Long categoryId,
              @Param("supplierId") Long supplierId,
                  Pageable pageable 
    );

    @Query("SELECT p FROM Product p WHERE p.quantity <= p.minimumStock ORDER BY p.quantity ASC")
    List<Product> findLowStockProducts();

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long category);
}
