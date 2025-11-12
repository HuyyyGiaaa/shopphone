package com.example.shopphone.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "loaihang")
public class LoaiHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maloai")
    private Integer maloai;

    @Column(name = "tenloai", nullable = false)
    private String tenloai;

    // 1 loại có nhiều dòng máy
    @OneToMany(mappedBy = "loaiHang", cascade = CascadeType.ALL)
    private List<DongMay> dsDongMay;

    // Getter & Setter
    public Integer getMaloai() { return maloai; }
    public void setMaloai(Integer maloai) { this.maloai = maloai; }
    public String getTenloai() { return tenloai; }
    public void setTenloai(String tenloai) { this.tenloai = tenloai; }
    public List<DongMay> getDsDongMay() { return dsDongMay; }
    public void setDsDongMay(List<DongMay> dsDongMay) { this.dsDongMay = dsDongMay; }
}
