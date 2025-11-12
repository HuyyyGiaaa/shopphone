package com.example.shopphone.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mausac")
public class MauSac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idmau;

    @Column(name = "ten_mau") // map đúng tên cột trong DB
    private String tenMau;

    // Bỏ Long idmay cũ, dùng ManyToOne
    @ManyToOne
    @JoinColumn(name = "idmay") // trùng với cột idmay trong DB
    private DongMay dongMay;

    // Các getter/setter
    public Long getIdmau() { return idmau; }
    public void setIdmau(Long idmau) { this.idmau = idmau; }

    public String getTenMau() { return tenMau; }
    public void setTenMau(String tenMau) { this.tenMau = tenMau; }

    public DongMay getDongMay() { return dongMay; }
    public void setDongMay(DongMay dongMay) { this.dongMay = dongMay; }
}
