package com.inventory_system.server.dto.req;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductReq {
  @NotBlank(message="Product name is required")
  @Size(max=200, message="Product name must not exceed 200 characters")
  private String name;

  @NotBlank(message="SKU is required")
  @Size(max=100, message="SKU must not exceed 100 characters")
  private String sku;
  private String description;

  @NotNull(message="Price is required")
  @DecimalMin(value="0.0", inclusive=true, message="Price must be non-negative")
  @Digits(integer=10, fraction=2, message="Invalid price format")
  private BigDecimal price;

  @NotNull(message="Quantity is required")
  @Min(value=0, message="Quantity must be non-negative")
  private Integer quantity;

  @NotNull(message="Minimum stock is required")
  @Min(value=0, message="Minimum stock must be non-negative")
  private Integer minimumStock;

  private Long categoryId;
  private Long supplierId;

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

  public void SetQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Integer getMinimumStock() {
    return minimumStock;
  }

  public void SetMinimumStock(Integer minimunStock) {
    this.minimumStock = minimunStock;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void SetCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }
}
