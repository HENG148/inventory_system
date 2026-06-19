package com.inventory_system.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inventory_system.server.dto.req.AuthRequest;
import com.inventory_system.server.dto.res.AuthResponse;
import com.inventory_system.server.dto.res.UserResponse;
import com.inventory_system.server.entity.RefreshToken;
import com.inventory_system.server.entity.Role;
import com.inventory_system.server.entity.User;
import com.inventory_system.server.exception.DuplicateResourceException;
import com.inventory_system.server.repository.UserRepository;
import com.inventory_system.server.security.jwt.JwtService;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl {
  private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;
  private final RefreshTokenServiceImpl refreshTokenService;

  public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
      AuthenticationManager authManager, RefreshTokenServiceImpl refreshTokenService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authManager = authManager;
    this.refreshTokenService = refreshTokenService;
  }

  @Transactional
  public AuthResponse register(AuthRequest.Register request) {
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new DuplicateResourceException("Username '" + request.getUsername() + "' is already taken");
    }
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new DuplicateResourceException("Email '" + request.getEmail() + "' is already registered");
    }
    Role role = Role.STAFF;
    if (request.getRole() != null && !request.getRole().isBlank()) {
      try {
        role = Role.valueOf(request.getRole().toUpperCase());
      } catch (IllegalArgumentException ignore) {
      }
    }

    User user = User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(role)
        .build();

    userRepository.save(user);
    log.info("New user registered: {}", user.getUsername());

    String accessToken = jwtService.generateToken(user);
    String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

    return AuthResponse.of(accessToken, refreshToken, jwtService.getExpirationTime(), UserResponse.from(user));
  }
  
  @Transactional
  public AuthResponse login(AuthRequest.Login request) {
    authManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword()));
    User user = userRepository.findByUsername(request.getUsernameOrEmail())
        .or(() -> userRepository.findByUsername(request.getUsernameOrEmail()))
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    String accessToken = jwtService.generateToken(user);
    String refreshToken = refreshTokenService.createRefreshToken(user).getToken();
    log.info("User logged in: {}", user.getUsername());
    return AuthResponse.of(accessToken, refreshToken, jwtService.getExpirationTime(), UserResponse.from(user));
  }
  
  @Transactional
  public AuthResponse refreshToken(String refreshtokenStr) {
    RefreshToken refreshToken = refreshTokenService.findByToken(refreshtokenStr);
    refreshTokenService.verifyExpiration(refreshToken);

    User user = refreshToken.getUser();
    String accessToken = jwtService.generateToken(user);
    String newRefresh = refreshTokenService.createRefreshToken(user).getToken();
    return AuthResponse.of(accessToken, newRefresh, jwtService.getExpirationTime(), UserResponse.from(user));
  }

  @Transactional
  public void logout(User user) {
    refreshTokenService.deleteByUser(user);
    log.info("User logged out: {}", user.getUsername());
  }
}
