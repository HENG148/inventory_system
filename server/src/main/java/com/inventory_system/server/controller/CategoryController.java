package com.inventory_system.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory_system.server.dto.req.CategoryReq;
import com.inventory_system.server.dto.res.ApiResponse;
import com.inventory_system.server.dto.res.CategoryResponse;
import com.inventory_system.server.service.impl.CategoryServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Manage product categories")
public class CategoryController {
  private final CategoryServiceImpl categoryService;

  public CategoryController(CategoryServiceImpl categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Createt a new category (ADMIN only)")
  public ResponseEntity<ApiResponse<CategoryResponse>> create(
    @Valid
    @RequestBody CategoryReq request
  ) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success("Category created", categoryService.create(request)));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('Admin')")
  @Operation(summary = "Update a category (ADMIN only)")
  public ResponseEntity<ApiResponse<CategoryResponse>> update(
    @PathVariable Long id,
    @Valid
    @RequestBody CategoryReq request
  ) {
    return ResponseEntity.ok(ApiResponse.success("Category updated", categoryService.update(id, request)));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Delete a category (ADMIN only)")
  public ResponseEntity<ApiResponse<Void>> delete(
    @PathVariable Long id
  ) {
    categoryService.delete(id);
    return ResponseEntity.ok(ApiResponse.success("Category deleted"));
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get category by ID")
  public ResponseEntity<ApiResponse<CategoryResponse>> getById(
    @PathVariable Long id
  ) {
    return ResponseEntity.ok(ApiResponse.success(categoryService.getById(id)));
  }

  @GetMapping
  @Operation(summary = "List all categories")
  public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll() {
    return ResponseEntity.ok(ApiResponse.success(categoryService.getAll()));
  }
}
