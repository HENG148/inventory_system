package com.inventory_system.server.dto.res;

import java.time.LocalDateTime;

import com.inventory_system.server.entity.Category;

public class CategoryResponse {
  private Long id;
  private String name;
  private String description;
  private int productCount;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public CategoryResponse() {
  }

  public CategoryResponse(Long id, String name, String description, int productCount, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.productCount = productCount;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static CategoryResponse from(Category c) {
    return new CategoryResponse(
        c.getId(),
        c.getName(),
        c.getDescription(),
        c.getProducts() != null ? c.getProducts().size() : 0,
        c.getCreatedAt(),
        c.getUpdatedAt());
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getProductCount() {
    return productCount;
  }

  public void setProductCount(int productCount) {
    this.productCount = productCount;
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
}
