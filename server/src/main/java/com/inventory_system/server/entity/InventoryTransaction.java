package com.inventory_system.server.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_transactions", indexes = {
    @Index(name = "idx_txn_product_id", columnList = "product_id"),
    @Index(name = "idx_txn_type", columnList = "type"),
    @Index(name = "idx_tun_created_at", columnList = "created_at"),
    @Index(name = "idx_txn_created_by", columnList = "created_by")
})
public class InventoryTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TransactionType type;

  @Column(nullable = false)
  private Integer quantity;

  @Column(name = "reference_number", length = 100)
  private String referenceNumber;

  @Column(columnDefinition = "TEXT")
  private String notes;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by")
  private User createdBy;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  public InventoryTransaction() {
  }

  public InventoryTransaction(Long id, Product product, TransactionType type, Integer quantity, String referenceNumber,
      String notes, User createdBy, LocalDateTime createdAt) {
    this.id = id;
    this.product = product;
    this.type = type;
    this.quantity = quantity;
    this.referenceNumber = referenceNumber;
    this.notes = notes;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
  }

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public String getReferenceNumber() {
    return referenceNumber;
  }

  public void setReferenceNumber(String referenceNumber) {
    this.referenceNumber = referenceNumber;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Long id;
    private Product product;
    private TransactionType type;
    private Integer quantity;
    private String referenceNumber;
    private String notes;
    private User createdBy;
    private LocalDateTime createdAt;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder product(Product product) {
      this.product = product;
      return this;
    }

    public Builder type(TransactionType type) {
      this.type = type;
      return this;
    }

    public Builder quantity(Integer quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder referenceNumber(String referenceNumber) {
      this.referenceNumber = referenceNumber;
      return this;
    }

    public Builder notes(String notes) {
      this.notes = notes;
      return this;
    }

    public Builder createdBy(User createdBy) {
      this.createdBy = createdBy;
      return this;
    }

    public Builder createdAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public InventoryTransaction build() {
      return new InventoryTransaction(id, product, type, quantity, referenceNumber, notes, createdBy, createdAt);
    }
  }
}
