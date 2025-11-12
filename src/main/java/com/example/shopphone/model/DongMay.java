    package com.example.shopphone.model;

    import jakarta.persistence.*;

    import java.util.List;

    @Entity
    @Table(name="dongmay")
    public class DongMay {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) // nếu muốn tự tăng
        private Long idmay; // đổi từ Integer -> Long

        private String tenmay;

        @ManyToOne
        @JoinColumn(name = "maloai")
        private LoaiHang loaiHang;

        @OneToMany(mappedBy = "dongMay")
        private List<HangHoa> hangHoas;

        public DongMay() {}

        public DongMay(Long idmay, String tenmay) {
            this.idmay = idmay;
            this.tenmay = tenmay;
        }

        public Long getIdmay() {
            return idmay;
        }

        public void setIdmay(Long idmay) {
            this.idmay = idmay;
        }

        public String getTenmay() {
            return tenmay;
        }

        public void setTenmay(String tenmay) {
            this.tenmay = tenmay;
        }

        public LoaiHang getLoaiHang() {
            return loaiHang;
        }

        public void setLoaiHang(LoaiHang loaiHang) {
            this.loaiHang = loaiHang;
        }

        public List<HangHoa> getHangHoas() {
            return hangHoas;
        }

        public void setHangHoas(List<HangHoa> hangHoas) {
            this.hangHoas = hangHoas;
        }
    }