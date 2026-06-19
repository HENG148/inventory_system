package com.inventory_system.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory_system.server.dto.req.InventoryTransactionReq;
import com.inventory_system.server.dto.res.PageResponse;
import com.inventory_system.server.dto.res.TransactionResponse;
import com.inventory_system.server.entity.InventoryTransaction;
import com.inventory_system.server.entity.Product;
import com.inventory_system.server.entity.TransactionType;
import com.inventory_system.server.entity.User;
import com.inventory_system.server.exception.InsufficientStockException;
import com.inventory_system.server.exception.ResourceNotFoundException;
import com.inventory_system.server.repository.InventoryTransactionRepository;
import com.inventory_system.server.repository.ProductRepository;

@Service
public class InventoryServiceImpl {
  private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);
  private final InventoryTransactionRepository transactionRepository;
  private final ProductRepository productRepository;

  public InventoryServiceImpl(InventoryTransactionRepository transactionRepository,
      ProductRepository productRepository) {
    this.transactionRepository = transactionRepository;
    this.productRepository = productRepository;
  }

  @Transactional
  public TransactionResponse processTransaction(InventoryTransactionReq req, User performedBy) {
    Product product = productRepository.findById(req.getProductId())
        .orElseThrow(() -> new ResourceNotFoundException("Product", req.getProductId()));

    int qty = req.getQuantity();
    switch (req.getType()) {
      case STOCK_IN -> product.setQuantity(product.getQuantity() + qty);
      case STOCK_OUT -> {
        if (product.getQuantity() < qty) {
          throw new InsufficientStockException(product.getName(), product.getQuantity(), qty);
        }
        product.setQuantity(product.getQuantity() - qty);
      }
      case ADJUSTMENT -> {
        int newQty = product.getQuantity() + qty;
        if (newQty < 0) {
          throw new InsufficientStockException(product.getName(), product.getQuantity(), Math.abs(qty));
        }
        product.setQuantity(newQty);
      }
    }

    productRepository.save(product);

    InventoryTransaction txn = InventoryTransaction.builder()
        .product(product)
        .type(req.getType())
        .quantity(req.getType() == TransactionType.STOCK_OUT ? -qty : qty)
        .referenceNumber(req.getReferenceNumber())
        .notes(req.getNotes())
        .createdBy(performedBy)
        .build();

    InventoryTransaction saved = transactionRepository.save(txn);
    log.info("Inventory transaction: type={} product={} qty={} by={}", req.getType(), product.getSku(), qty,
        performedBy.getUsername());
    return TransactionResponse.from(saved);
  }
  
  @Transactional(readOnly = true)
  public PageResponse<TransactionResponse> getTransactions(
    Long productId, TransactionType type, LocalDateTime from, LocalDateTime to, int page, int size, String sortDir 
  ) {
    Sort sort = sortDir.equalsIgnoreCase("asc")
        ? Sort.by("createdAt").ascending()
        : Sort.by("createdAt").descending();
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<TransactionResponse> result = transactionRepository
        .findTransactions(productId, type, from, to, pageable)
        .map(TransactionResponse::from);
    return PageResponse.from(result);
  }
  
  public List<TransactionResponse> getProductHistory(Long productId) {
    if (!productRepository.existsById(productId)) {
      throw new ResourceNotFoundException("Product", productId);
    }
    return transactionRepository.findByProductIdOrderByCreatedAtDesc(productId)
        .stream().map(TransactionResponse::from).toList();
  }
}