// package com.inventory_system.server.entity;

// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.Table;

// @Entity
// @Table(name="categories")
// public class Category {
//   @Id
//   @GeneratedValue(strategy=GenerationType.IDENTITY)
//   private Long id;

//   @Column(nullable=false, unique=true, length=100)
//   private String name;
//   @Column(columnDefinition="TEXT")
//   private String description;
//   @Column(name="created_at", nullable=false, updatable=false)
//   private LocalDateTime createdAt;

//   @Column(name="updated_at", nullable=false)
//   private LocalDateTime updatedAt;

//   @OneToMany(mappedBy="category", fetch=FetchType.LAZY)
//   private List<Product> products = new ArrayList<>();

//   public Category() {
//   }
  
//   public Category(Long id, String name, String description, LocalDateTime createdAt, LocalDateTime updatedAt,
//       List<Product> product) {
    
//   }
// }
