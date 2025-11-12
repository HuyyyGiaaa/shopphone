package com.example.shopphone.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cthanghoa")
@IdClass(CTHangHoaId.class)
public class CTHangHoa {

    // -------------------- PRIMARY KEYS --------------------
    @Id
    @Column(name="mahh")
    private Long mahh;

    @Id
    @Column(name="idmau")
    private Long idmau;

    @Id
    @Column(name="idmay")
    private Long idmay;

    @Id
    @Column(name="idbonho")
    private Long idbonho;

    // -------------------- RELATIONSHIPS --------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mahh", insertable=false, updatable=false)
    private HangHoa hangHoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmau", insertable=false, updatable=false)
    private MauSac mauSac;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmay", insertable=false, updatable=false)
    private DongMay dongMay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idbonho", insertable=false, updatable=false)
    private BoNho boNho;

    // -------------------- ATTRIBUTES --------------------
    @Column(name = "dongia")
    private Float dongia;

    @Column(name = "soluongton")
    private Integer soluongton;

    @Column(name = "hedieuhanh")
    private String hedieuhanh;

    @Column(name = "cpu")
    private String cpu;

    @Column(name = "gpu")
    private String gpu;

    @Column(name = "ram")
    private String ram;

    @Column(name = "pin")
    private String pin;

    @Column(name = "ketnoi")
    private String ketnoi;

    // -------------------- CONSTRUCTORS --------------------
    public CTHangHoa() {}

    public CTHangHoa(Long mahh, Long idmau, Long idmay, Long idbonho) {
        this.mahh = mahh;
        this.idmau = idmau;
        this.idmay = idmay;
        this.idbonho = idbonho;
    }

    // -------------------- GETTERS & SETTERS --------------------
    public Long getMahh() { return mahh; }
    public void setMahh(Long mahh) { this.mahh = mahh; }

    public Long getIdmau() { return idmau; }
    public void setIdmau(Long idmau) { this.idmau = idmau; }

    public Long getIdmay() { return idmay; }
    public void setIdmay(Long idmay) { this.idmay = idmay; }

    public Long getIdbonho() { return idbonho; }
    public void setIdbonho(Long idbonho) { this.idbonho = idbonho; }

    public HangHoa getHangHoa() { return hangHoa; }
    public void setHangHoa(HangHoa hangHoa) { this.hangHoa = hangHoa; }

    public MauSac getMauSac() { return mauSac; }
    public void setMauSac(MauSac mauSac) { this.mauSac = mauSac; }

    public DongMay getDongMay() { return dongMay; }
    public void setDongMay(DongMay dongMay) { this.dongMay = dongMay; }

    public BoNho getBoNho() { return boNho; }
    public void setBoNho(BoNho boNho) { this.boNho = boNho; }

    public Float getDongia() { return dongia; }
    public void setDongia(Float dongia) { this.dongia = dongia; }

    public Integer getSoluongton() { return soluongton; }
    public void setSoluongton(Integer soluongton) { this.soluongton = soluongton; }

    public String getHedieuhanh() { return hedieuhanh; }
    public void setHedieuhanh(String hedieuhanh) { this.hedieuhanh = hedieuhanh; }

    public String getCpu() { return cpu; }
    public void setCpu(String cpu) { this.cpu = cpu; }

    public String getGpu() { return gpu; }
    public void setGpu(String gpu) { this.gpu = gpu; }

    public String getRam() { return ram; }
    public void setRam(String ram) { this.ram = ram; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }

    public String getKetnoi() { return ketnoi; }
    public void setKetnoi(String ketnoi) { this.ketnoi = ketnoi; }

    // -------------------- EQUALS & HASHCODE --------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CTHangHoa)) return false;
        CTHangHoa that = (CTHangHoa) o;
        return Objects.equals(mahh, that.mahh) &&
                Objects.equals(idmau, that.idmau) &&
                Objects.equals(idmay, that.idmay) &&
                Objects.equals(idbonho, that.idbonho);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mahh, idmau, idmay, idbonho);
    }

    // -------------------- TO STRING --------------------
    @Override
    public String toString() {
        return "CTHangHoa{" +
                "mahh=" + mahh +
                ", idmau=" + idmau +
                ", idmay=" + idmay +
                ", idbonho=" + idbonho +
                ", dongia=" + dongia +
                ", soluongton=" + soluongton +
                '}';
    }
}
