package com.example.shopphone.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite id class for CTHangHoa (mahh, idmau, idmay, idbonho)
 * implements Serializable → cho phép Hibernate tuần tự hóa khóa chính phức hợp.
 */
public class CTHangHoaId implements Serializable {

    private static final long serialVersionUID = 1L;

    // === Các trường Long tương ứng với @MapsId trong entity ===
    private Long mahh;
    private Long idmau;
    private Long idmay;
    private Long idbonho;

    // === Constructor mặc định ===
    public CTHangHoaId() {
    }

    // === Constructor đầy đủ ===
    public CTHangHoaId(Long mahh, Long idmau, Long idmay, Long idbonho) {
        this.mahh = mahh;
        this.idmau = idmau;
        this.idmay = idmay;
        this.idbonho = idbonho;
    }

    // === Getter & Setter ===
    public Long getMahh() { return mahh; }
    public void setMahh(Long mahh) { this.mahh = mahh; }

    public Long getIdmau() { return idmau; }
    public void setIdmau(Long idmau) { this.idmau = idmau; }

    public Long getIdmay() { return idmay; }
    public void setIdmay(Long idmay) { this.idmay = idmay; }

    public Long getIdbonho() { return idbonho; }
    public void setIdbonho(Long idbonho) { this.idbonho = idbonho; }

    // === equals & hashCode chuẩn Hibernate ===
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CTHangHoaId)) return false;
        CTHangHoaId that = (CTHangHoaId) o;
        return Objects.equals(mahh, that.mahh) &&
                Objects.equals(idmau, that.idmau) &&
                Objects.equals(idmay, that.idmay) &&
                Objects.equals(idbonho, that.idbonho);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mahh, idmau, idmay, idbonho);
    }

    // === toString tiện debug ===
    @Override
    public String toString() {
        return "CTHangHoaId{" +
                "mahh=" + mahh +
                ", idmau=" + idmau +
                ", idmay=" + idmay +
                ", idbonho=" + idbonho +
                '}';
    }
}
