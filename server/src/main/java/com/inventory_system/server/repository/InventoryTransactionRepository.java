package com.inventory_system.server.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inventory_system.server.entity.InventoryTransaction;
import com.inventory_system.server.entity.TransactionType;

@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
  @Query("""
        SELECT t FROM InventoryTransaction t
        LEFT JOIN FETCH t.product
        LEFT JOIN FETCH t.createdBy
        WHERE (:productId IS NULL OR t.product.id = :productId)
          AND (:type     IS NULL OR t.type = :type)
          AND (:from     IS NULL OR t.createdAt >= :from)
          AND (:to       IS NULL OR t.createdAt <= :to)
    """)
    Page<InventoryTransaction> findTransactions(
      @Param("productId") Long productId,
      @Param("type") TransactionType type,
          @Param("from") LocalDateTime from,
              @Param("to") LocalDateTime to,
                  Pageable pageable
    );

  @Query("""
      SELECT t FROM InventoryTransaction t WHERE t.createdAt >= :from AND t.createdAt <= :to ORDER BY t.createdAt DESC
      """)
      List<InventoryTransaction> findByDateRange(
        @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
      );

  @Query("""
        SELECT t.product.id, t.type, SUM(ABS(t.quantity))
        FROM InventoryTransaction t
        WHERE YEAR(t.createdAt)  = :year
          AND MONTH(t.createdAt) = :month
        GROUP BY t.product.id, t.type
    """)
    List<Object[]> findMonthlyMovements(@Param("year") int year, @Param("month") int month);

    @Query("""
        SELECT t.product.id, SUM(ABS(t.quantity)) AS total
        FROM InventoryTransaction t
        WHERE t.createdAt >= :from AND t.createdAt <= :to
        GROUP BY t.product.id
        ORDER BY total DESC
    """)
    List<Object[]> findTopMovedProducts(
        @Param("from") LocalDateTime from,
        @Param("to")   LocalDateTime to,
        Pageable pageable
    );

    List<InventoryTransaction> findByProductIdOrderByCreatedAtDesc(Long productId);
}
