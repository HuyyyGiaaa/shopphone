package com.example.shopphone.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // đổi tên table tránh trùng với từ khóa
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // bắt buộc, duy nhất

    @Column(nullable = false)
    private String password;

    @Column(nullable = true, unique = true)
    private String email; // có thể null, duy nhất nếu có

    @Column(nullable = true, unique = true)
    private String phone; // có thể null, duy nhất nếu có

    @Column(nullable = true)
    private String role; // admin / user

    @Column(nullable = true)
    private String avatar; // ảnh đại diện

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String fullname; // họ tên người dùng (không bắt buộc)

    @Transient
    private String confirmPassword; // chỉ dùng trong form đăng ký, không lưu DB

    // ===== Getters & Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
