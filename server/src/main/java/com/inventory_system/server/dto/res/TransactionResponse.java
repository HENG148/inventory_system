package com.inventory_system.server.dto.res;

import java.time.LocalDateTime;

import com.inventory_system.server.entity.InventoryTransaction;
import com.inventory_system.server.entity.TransactionType;

public class TransactionResponse {
  private Long id;
  private Long productId;
  private String productName;
  private String productSku;
  private TransactionType type;
  private Integer quantity;
  private String referenceNumber;
  private String notes;
  private String createdBy;
  private LocalDateTime createdAt;

  public TransactionResponse() {
  }

  public TransactionResponse(Long id, Long productId, String productName, String productSku, TransactionType type,
      Integer quantity, String referenceNumber, String notes, String createdBy, LocalDateTime createdAt) {
    this.id = id;
    this.productId = productId;
    this.productName = productName;
    this.type = type;
    this.quantity = quantity;
    this.referenceNumber = referenceNumber;
    this.notes = notes;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
  }

  public static TransactionResponse from(InventoryTransaction t) {
    return new TransactionResponse(
        t.getId(),
        t.getProduct().getId(),
        t.getProduct().getName(),
        t.getProduct().getSku(),
        t.getType(),
        t.getQuantity(),
        t.getReferenceNumber(),
        t.getNotes(),
        t.getCreatedBy() != null ? t.getCreatedBy().getUsername() : null,
        t.getCreatedAt());
  }
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
 
  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }
 
  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }
 
  public String getProductSku() {
    return productSku;
  }

  public void setProductSku(String productSku) {
    this.productSku = productSku;
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
 
  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }
 
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
