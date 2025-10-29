package com.example.shopphone.model;

import jakarta.persistence.*;

// explicit imports (in same package but clarify for analyzers)


@Entity
@Table(name = "cthanghoa")
@IdClass(CTHangHoaId.class)
public class CTHangHoa {
    @Id
    @Column(name = "idhanghoa")
    private int idhanghoa;
    @Id
    @Column(name = "idmau")
    private int idmau;
    @Id
    @Column(name = "idmay")
    private int idmay;
    @Column(name = "dongia")
    private Float dongia;
    @Column(name = "soluongton")
    private Integer soluongton;

    // Map relationships to other entities (read-only here using insertable=false, updatable=false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idhanghoa", insertable = false, updatable = false)
    private HangHoa hangHoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmau", insertable = false, updatable = false)
    private MauSac mausac;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmay", insertable = false, updatable = false)
    private DongMay dongmay;

    // Getters and setters
    public int getIdhanghoa() { return idhanghoa; }
    public void setIdhanghoa(int idhanghoa) { this.idhanghoa = idhanghoa; }
    public int getIdmau() { return idmau; }
    public void setIdmau(int idmau) { this.idmau = idmau; }
    public int getIdmay() { return idmay; }
    public void setIdmay(int idmay) { this.idmay = this.idmay; }
    public Float getDongia() { return dongia; }
    public void setDongia(Float dongia) { this.dongia = dongia; }
    public Integer getSoluongton() { return soluongton; }
    public void setSoluongton(Integer soluongton) { this.soluongton = soluongton; }

    // Relationship getters (read-only)
    public HangHoa getHangHoa() { return hangHoa; }
    public MauSac getMausac() { return mausac; }
    public DongMay getSizegiay() { return dongmay; }
}
