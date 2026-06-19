package com.inventory_system.server.service.impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory_system.server.entity.RefreshToken;
import com.inventory_system.server.entity.User;
import com.inventory_system.server.exception.TokenRefreshException;
import com.inventory_system.server.repository.RefreshTokenRepository;

@Service
public class RefreshTokenServiceImpl {
  @Value("${application.security.jwt.refresh-token.expiration}")
  private Long refreshTokenDurationMs;
  private final RefreshTokenRepository refreshTokenRepository;

  public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
  }
  @Transactional
  public RefreshToken createRefreshToken(User user) {
    refreshTokenRepository.deleteByUser(user);
    RefreshToken token = RefreshToken.builder()
        .user(user)
        .token(UUID.randomUUID().toString())
        .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
        .build();
    return refreshTokenRepository.save(token);
  }
  
  @Transactional(readOnly = true)
  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.isExpired()) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(token.getToken(), "Refresh token is expired. Please log in again.");
    }
    return token;
  }

  @Transactional(readOnly = true)
  public RefreshToken findByToken(String token) {
    return refreshTokenRepository.findByToken(token)
        .orElseThrow(() -> new TokenRefreshException(token, "Refresh token out found"));
  }

  @Transactional
  public void deleteByUser(User user) {
    refreshTokenRepository.deleteByUser(user);
  }
}
