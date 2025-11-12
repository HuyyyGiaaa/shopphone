package com.example.shopphone.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "idbonho")
    private int idbonho;

    @Column(name = "boNho")
    private String boNho; // ví dụ "256GB"


    private String username; // user nào thêm
    private int idhanghoa;
    private int idmau;
    private int idmay;
    private int soluong;
    private float dongia;
    private String tenHangHoa;
    private String mauSac;
    private String tenMay;

    public CartItem() {}

    public CartItem(String username, int idhanghoa, int idmau, int idmay,
                    String tenHangHoa, String mauSac, String tenMay,
                    int soluong, float dongia) {
        this.username = username;
        this.idhanghoa = idhanghoa;
        this.idmau = idmau;
        this.idmay = idmay;
        this.tenHangHoa = tenHangHoa;
        this.mauSac = mauSac;
        this.tenMay = tenMay;
        this.soluong = soluong;
        this.dongia = dongia;
    }


    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getIdhanghoa() { return idhanghoa; }
    public void setIdhanghoa(int idhanghoa) { this.idhanghoa = idhanghoa; }

    public int getIdmau() { return idmau; }
    public void setIdmau(int idmau) { this.idmau = idmau; }

    public int getIdmay() { return idmay; }
    public void setIdmay(int idmay) { this.idmay = idmay; }

    public int getSoluong() { return soluong; }
    public void setSoluong(int soluong) { this.soluong = soluong; }

    public float getDongia() { return dongia; }
    public void setDongia(float dongia) { this.dongia = dongia; }

    public String getTenHangHoa() { return tenHangHoa; }
    public void setTenHangHoa(String tenHangHoa) { this.tenHangHoa = tenHangHoa; }

    public String getMauSac() { return mauSac; }
    public void setMauSac(String mauSac) { this.mauSac = mauSac; }

    public String getTenMay() { return tenMay; }
    public void setTenMay(String tenMay) { this.tenMay = tenMay; }

    public int getIdbonho() { return idbonho; }
    public void setIdbonho(int idbonho) { this.idbonho = idbonho; }


    public String getBoNho() { return boNho; }
    public void setBoNho(String boNho) { this.boNho = boNho; }
}
