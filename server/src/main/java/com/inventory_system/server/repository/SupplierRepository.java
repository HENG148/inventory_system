package com.inventory_system.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory_system.server.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
  Optional<Supplier> findByEmail(String email);

  boolean existsByEmail(String email);
}
