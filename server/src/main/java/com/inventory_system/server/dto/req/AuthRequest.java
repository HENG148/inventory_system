package com.inventory_system.server.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AuthRequest {
  public static class Register {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can ony contain letters, number and underscores")
    private String username;

    @NotBlank(message="Email is required")
    @Email(message="Invalid email format")
    private String email;

    @NotBlank(message="Password is required")
    @Size(min=8, message="Password must be at least 8 characters")
    @Pattern(
      regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
          message="Password must contain at least one digit, one lowercase, one uppercase, and one special character"
    )
    private String password;

    private String role;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getRole() {
      return role;
    }

    public void setRole(String role) {
      this.role = role;
    }

    public static class Login {
      @NotBlank(message = "Username or email is required")
      private String usernameOrEmail;

      @NotBlank(message = "Password is required")
      private String password;

      public String getUsernameOrEmail() {
        return usernameOrEmail;
      }

      public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
      }

      public String getPassword() {
        return password;
      }

      public void setPassword(String password) {
        this.password = password;
      }
    }
    
    public static class RefreshToken {
      @NotBlank(message="Refresh token is required")
      private String refreshToken;

      public String getRefreshToken() {
        return refreshToken;
      }

      public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
      }
    }
  }
}
