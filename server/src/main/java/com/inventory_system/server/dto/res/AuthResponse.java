package com.inventory_system.server.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
  private String accessToken;
  private String refreshToken;
  private String tokenType;
  private Long expiresIn;
  private UserResponse user;

  public AuthResponse() {
  }

  public AuthResponse(String accessToken, String refreshToken, String tokenType, Long expiresIn, UserResponse user) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
    this.user = user;
  }

  public static AuthResponse of(String accessToken, String refreshToken, Long expiredIn,
      UserResponse user) {
    return new AuthResponse(accessToken, refreshToken, "Bearer", expiredIn, user);
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
 
  public String getRefreshToken() {
    return refreshToken;
  };

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  };
 
  public String getTokenType() {
    return tokenType;
  };

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  };
 
  public Long getExpiresIn() {
    return expiresIn;
  };

  public void setExpiresIn(Long expiresIn) {
    this.expiresIn = expiresIn;
  }
 
  public UserResponse getUser() {
    return user;
  }

  public void setUser(UserResponse user) {
    this.user = user;
  }
}
