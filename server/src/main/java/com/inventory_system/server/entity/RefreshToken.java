package com.inventory_system.server.entity;

import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;

public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false, unique = true, length = 500)
  private String token;

  @Column(name = "expiry_date", nullable = false)
  private Instant expiryDate;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  public RefreshToken() {
  }

  public RefreshToken(Long id, User user, String token, Instant expiryDate, LocalDateTime createdAt) {
    this.id = id;
    this.user = user;
    this.token = token;
    this.expiryDate = expiryDate;
    this.createdAt = createdAt;
  }

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  public boolean isExpired() {
    return expiryDate.isBefore(Instant.now());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Instant getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Instant expiryDate) {
    this.expiryDate = expiryDate;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Long id;
    private User user;
    private String token;
    private Instant expiryDate;
    private LocalDateTime createdAt;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder user(User user) {
      this.user = user;
      return this;
    }

    public Builder token(String token) {
      this.token = token;
      return this;
    }

    public Builder expiryDate(Instant expiryDate) {
      this.expiryDate = expiryDate;
      return this;
    }

    public Builder createdAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public RefreshToken build() {
      return new RefreshToken(id, user, token, expiryDate, createdAt);
    }
  }
}
