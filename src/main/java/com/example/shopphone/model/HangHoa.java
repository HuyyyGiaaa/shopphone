package com.example.shopphone.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

@Entity
@Table(name = "hanghoa")
public class HangHoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mahh;

    private String tenhh;
    private Float giamgia;
    private Float dongia;
    private String hinh;
    private Integer soluongxem;
    private String mota;
    private LocalDate ngaylap;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idmay")
    private DongMay dongMay;

    // ✅ Cascade ALL + orphanRemoval để tự động thêm/xóa CTHangHoa
    @OneToMany(mappedBy = "hangHoa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CTHangHoa> ctHangHoaList = new ArrayList<>();



    // --- Phương thức tiện ích ---
    @Transient
    public List<BoNho> getBonhoList() {
        if (ctHangHoaList == null) return List.of();
        return ctHangHoaList.stream()
                .map(CTHangHoa::getBoNho)
                .distinct()
                .toList();
    }

    @Transient
    public String getDongiaVn() {
        if (dongia == null) return "0₫";
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(dongia).replace(",", ".") + "₫";
    }

    // --- Getters & Setters ---
    public Long getMahh() { return mahh; }
    public void setMahh(Long mahh) { this.mahh = mahh; }

    public String getTenhh() { return tenhh; }
    public void setTenhh(String tenhh) { this.tenhh = tenhh; }

    public Float getDongia() { return dongia; }
    public void setDongia(Float dongia) { this.dongia = dongia; }

    public Float getGiamgia() { return giamgia; }
    public void setGiamgia(Float giamgia) { this.giamgia = giamgia; }

    public String getHinh() { return hinh; }
    public void setHinh(String hinh) { this.hinh = hinh; }

    public Integer getSoluongxem() { return soluongxem; }
    public void setSoluongxem(Integer soluongxem) { this.soluongxem = soluongxem; }

    public String getMota() { return mota; }
    public void setMota(String mota) { this.mota = mota; }

    public LocalDate getNgaylap() { return ngaylap; }
    public void setNgaylap(LocalDate ngaylap) { this.ngaylap = ngaylap; }

    public DongMay getDongMay() { return dongMay; }
    public void setDongMay(DongMay dongMay) { this.dongMay = dongMay; }

    public List<CTHangHoa> getCtHangHoaList() { return ctHangHoaList; }
    public void setCtHangHoaList(List<CTHangHoa> ctHangHoaList) { this.ctHangHoaList = ctHangHoaList; }
}
