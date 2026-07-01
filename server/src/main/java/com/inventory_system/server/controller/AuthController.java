package com.inventory_system.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory_system.server.dto.req.AuthRequest;
import com.inventory_system.server.dto.res.ApiResponse;
import com.inventory_system.server.dto.res.AuthResponse;
import com.inventory_system.server.dto.res.UserResponse;
import com.inventory_system.server.entity.User;
import com.inventory_system.server.service.impl.AuthServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Register, login, token refresh, and logout")
public class AuthController {
  private final AuthServiceImpl authService;

  public AuthController(AuthServiceImpl authService) {
    this.authService = authService;
  }
  @PostMapping("/register")
  @Operation(summary = "Register a new user")
  public ResponseEntity<ApiResponse<AuthResponse>> register(
    @Valid @RequestBody AuthRequest.Register request
  ) {
    AuthResponse response = authService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success("User registered successfully", response));
  }

  @PostMapping("/login")
  @Operation(summary = "Login and receive JWT tokens")
  public ResponseEntity<ApiResponse<AuthResponse>> login(
    @Valid
    @RequestBody AuthRequest.Login request
  ) {
    AuthResponse response = authService.login(request);
    return ResponseEntity.ok(ApiResponse.success("Login successful", response));
  }

  @PostMapping("/refresh-token")
  @Operation(summary = "Refresh access token using refresh token")
  public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(
    @Valid
    @RequestBody AuthRequest.RefreshToken request
  ) {
    AuthResponse response = authService.refreshToken(request.getRefreshToken());
    return ResponseEntity.ok(ApiResponse.success("Token refreshed", response));
  }

  @PostMapping("/logout")
  @Operation(summary = "Logout and invalidate refresh token")
  public ResponseEntity<ApiResponse<Void>> logout(
    @AuthenticationPrincipal User user
  ) {
    authService.logout(user);
    return ResponseEntity.ok(ApiResponse.success("Logged out successfully"));
  }

  @GetMapping("/me")
  @Operation(summary = "Get current authenticated user profile")
  public ResponseEntity<ApiResponse<UserResponse>> me(
    @AuthenticationPrincipal User user
  ) {
    return ResponseEntity.ok(ApiResponse.success(UserResponse.from(user)));
  }
}
