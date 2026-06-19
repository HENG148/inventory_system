package com.inventory_system.server.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "supplier_id")
  private Supplier supplier;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
  private List<InventoryTransaction> transactions = new ArrayList<>();

  public Product() {
  }

  public Product(Long id, String name, String sku, String description, BigDecimal price, Integer quantity,
      Integer minimunStock, Category category, Supplier supplier, LocalDateTime createdAt, LocalDateTime updatedAt,
      List<InventoryTransaction> transactions) {
    this.id = id;
    this.name = name;
    this.sku = sku;
    this.description = description;
    this.price = price != null ? price : BigDecimal.ZERO;
    this.quantity = quantity != null ? quantity : 0;
    this.minimumStock = minimunStock != null ? minimunStock : 0;
    this.category = category;
    this.supplier = supplier;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.transactions = transactions != null ? transactions : new ArrayList<>();
  }

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public boolean isLowStock() {
    return quantity <= minimumStock;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
 
  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
 
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
 
  public Integer getMinimumStock() {
    return minimumStock;
  }

  public void setMinimumStock(Integer minimumStock) {
    this.minimumStock = minimumStock;
  }
 
  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }
 
  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }
 
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
 
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
 
  public List<InventoryTransaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<InventoryTransaction> transactions) {
    this.transactions = transactions;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Long id;
    private String name;
    private String sku;
    private String description;
    private BigDecimal price = BigDecimal.ZERO;
    private Integer quantity = 0;
    private Integer minimumStock = 0;
    private Category category;
    private Supplier supplier;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<InventoryTransaction> transactions = new ArrayList<>();

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder sku(String sku) {
      this.sku = sku;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    public Builder quantity(Integer quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder miminumStock(Integer minimumStock) {
      this.minimumStock = minimumStock;
      return this;
    }

    public Builder category(Category category) {
      this.category = category;
      return this;
    }

    public Builder supplier(Supplier supplier) {
      this.supplier = supplier;
      return this;
    }

    public Builder createdAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder updatedAt(LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public Builder transactions(List<InventoryTransaction> transactions) {
      this.transactions = transactions;
      return this;
    }

    public Product build() {
      return new Product(id, name, sku, description, price, quantity, minimumStock, category, supplier, createdAt,
          updatedAt, transactions);
    }
  }
}
