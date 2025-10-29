package com.example.shopphone.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.text.DecimalFormat;

@Entity
@Table(name = "hanghoa")
public class HangHoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mahh;

    private String tenhh;
    private Float giamgia;
    private String hinh;
    private Integer maloai;
    private Boolean dacbiet;
    private Integer soluongxem;
    private String mota;
    private LocalDate ngaylap;
    private Float dongia;

    @ElementCollection
    private List<Integer> bonhoOptions;

    @ManyToOne
    @JoinColumn(name = "idmay")
    private DongMay dongMay;

    // --- Getters & Setters ---
    public Integer getMahh() { return mahh; }
    public void setMahh(Integer mahh) { this.mahh = mahh; }

    public String getTenhh() { return tenhh; }
    public void setTenhh(String tenhh) { this.tenhh = tenhh; }

    public Float getDongia() { return dongia; }
    public void setDongia(Float dongia) { this.dongia = dongia; }

    public List<Integer> getBonhoOptions() { return bonhoOptions; }
    public void setBonhoOptions(List<Integer> bonhoOptions) { this.bonhoOptions = bonhoOptions; }

    public Float getGiamgia() { return giamgia; }
    public void setGiamgia(Float giamgia) { this.giamgia = giamgia; }

    public String getHinh() { return hinh; }
    public void setHinh(String hinh) { this.hinh = hinh; }

    public Integer getMaloai() { return maloai; }
    public void setMaloai(Integer maloai) { this.maloai = maloai; }

    public Boolean getDacbiet() { return dacbiet; }
    public void setDacbiet(Boolean dacbiet) { this.dacbiet = dacbiet; }

    public Integer getSoluongxem() { return soluongxem; }
    public void setSoluongxem(Integer soluongxem) { this.soluongxem = soluongxem; }

    public String getMota() { return mota; }
    public void setMota(String mota) { this.mota = mota; }

    public LocalDate getNgaylap() { return ngaylap; }
    public void setNgaylap(LocalDate ngaylap) { this.ngaylap = ngaylap; }

    public DongMay getDongMay() { return dongMay; }
    public void setDongMay(DongMay dongMay) { this.dongMay = dongMay; }

    // --- Khởi tạo danh sách bộ nhớ ---
    public void initBonhoOptions() {
        bonhoOptions = Arrays.asList(64, 128, 256, 512, 1024);
    }

    // --- PHƯƠNG THỨC MỚI: Trả về giá VNĐ ---
    @Transient
    public String getDongiaVn() {
        if (dongia == null) return "0₫";
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(dongia).replace(",", ".") + "₫";
    }
}
