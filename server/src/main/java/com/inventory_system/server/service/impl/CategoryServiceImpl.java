package com.inventory_system.server.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory_system.server.dto.req.CategoryReq;
import com.inventory_system.server.dto.res.CategoryResponse;
import com.inventory_system.server.entity.Category;
import com.inventory_system.server.exception.DuplicateResourceException;
import com.inventory_system.server.exception.ResourceNotFoundException;
import com.inventory_system.server.repository.CategoryRepository;

@Service
public class CategoryServiceImpl {
  private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
  private final CategoryRepository categoryRepository;

  public CategoryServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Transactional
  public CategoryResponse create(CategoryReq request) {
    if (categoryRepository.existByNameIgnoreCase(request.getName())) {
      throw new DuplicateResourceException("Category '" + request.getName() + "' already exists");
    }
    Category category = Category.builder()
        .name(request.getName())
        .description(request.getDescription())
        .build();
    return CategoryResponse.from(categoryRepository.save(category));
  }

  @Transactional
  public CategoryResponse update(Long id, CategoryReq req) {
    Category category = findById(id);
    if (!category.getName().equalsIgnoreCase(req.getName())
        && categoryRepository.existByNameIgnoreCase(req.getName())) {
      throw new DuplicateResourceException("Category '" + req.getName() + "' already exists");
    }
    category.setName(req.getName());
    category.setDecription(req.getDescription());
    return CategoryResponse.from(categoryRepository.save(category));
  }
  
  @Transactional
  public void delete(Long id) {
    Category category = findById(id);
    categoryRepository.delete(category);
    log.info("Deleted category id={}", id);
  }

  @Transactional(readOnly = true)
  public CategoryResponse getById(Long id) {
    return CategoryResponse.from(findById(id));
  }

  @Transactional(readOnly = true)
  public List<CategoryResponse> getAll() {
    return categoryRepository.findAll(Sort.by("name"))
        .stream().map(CategoryResponse::from).toList();
  }

  private Category findById(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category", id));
  }
}
