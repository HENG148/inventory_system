package com.inventory_system.server.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SupplierReq {
  @NotBlank(message="Supplier name is required")
  @Size(max=150, message="Name must not exceed 150 characters")
  private String name;

  @Email(message="Invalid email format")
  @Size(max=100)
  private String email;

  @Size(max=20, message="Phone must not exceed 20 characters")
  private String phone;

  private String address;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
