package com.inventory_system.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientStockException extends RuntimeException {
  public InsufficientStockException(String productName, int available, int req) {
    super(String.format("Insufficient stock for '%s'. Available: %d, Request: %d", productName, available, req));
  }
}
