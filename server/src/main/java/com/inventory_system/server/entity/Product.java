package com.inventory_system.server.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_products_sku", columnList = "sku"),
    @Index(name = "idx_products_category", columnList = "category_id"),
    @Index(name = "idx_products_supplier", columnList = "supplier_id"),
})
public class Product {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  @Column(nullable=false, length=200)
  private String name;
  @Column(nullable=false, unique=true, length=100)
  private String sku;
  @Column(columnDefinition="TEXT")
  private String description;
  @Column(nullable=false, precision=12, scale=2)
  private BigDecimal price = BigDecimal.ZERO;
  @Column(nullable=false)
  private Integer quantity = 0;
  @Column(name="minimum_stock", nullable=false)
  private Integer minimumStock = 0;
  // @ManyToOne(fetch=FetchType.LAZY)
  // @JoinColumn(name="category_id")
  // private Category category;

}
