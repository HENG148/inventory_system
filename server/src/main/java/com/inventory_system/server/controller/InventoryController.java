package com.inventory_system.server.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory_system.server.dto.req.InventoryTransactionReq;
import com.inventory_system.server.dto.res.ApiResponse;
import com.inventory_system.server.dto.res.PageResponse;
import com.inventory_system.server.dto.res.TransactionResponse;
import com.inventory_system.server.entity.TransactionType;
import com.inventory_system.server.entity.User;
import com.inventory_system.server.service.impl.InventoryServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory", description = "Stock in, stock out, adjustments, and transaction history")
public class InventoryController {
  private final InventoryServiceImpl inventoryService;

  public InventoryController(InventoryServiceImpl inventoryService) {
    this.inventoryService = inventoryService;
  }

  @PostMapping("/transactions")
  @Operation(summary = "Record a stock-in, stock-out, or adjustment transaction")
  public ResponseEntity<ApiResponse<TransactionResponse>> processTransaction(
    @Valid
    @RequestBody InventoryTransactionReq request,
    @AuthenticationPrincipal User currentUser
  ) {
    TransactionResponse response = inventoryService.processTransaction(request, currentUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Transaction recorded", response));
  }

  @GetMapping("/transactions")
  @Operation(summary = "List transactions with optional filters and pagination")
  public ResponseEntity<ApiResponse<PageResponse<TransactionResponse>>> getTransactions(
    @RequestParam(required = false) Long productId,
        @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                    @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                            @RequestParam(defaultValue = "desc") String sortDir
  ) {
    return ResponseEntity.ok(ApiResponse.success(
      inventoryService.getTransactions(productId, type, from, to, page, size, sortDir)
    ));
  }
}
