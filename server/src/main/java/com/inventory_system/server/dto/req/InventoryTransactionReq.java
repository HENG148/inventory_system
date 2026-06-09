package com.inventory_system.server.dto.req;

import com.inventory_system.server.entity.TransactionType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class InventoryTransactionReq {
  @NotNull(message="Product ID is required")
  private Long productId;

  @NotNull(message="Transaction type is required")
  private TransactionType type;

  @NotNull(message="Quantity is required")
  @Min(value=1,message="Quantity must be at least 1")
  private Integer quantity;

  @Size(max=100, message="Reference number must not exceed 100 characters")
  private String referenceNumber;

  private String notes;

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
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
}
