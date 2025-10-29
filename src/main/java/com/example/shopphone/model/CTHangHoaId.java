package com.example.shopphone.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite id class for CTHangHoa (idhanghoa, idmau, idsize)
 * implements Serializable → cho phép Hibernate tuần tự hóa khóa chính phức hợp.
 *
 * private static final long serialVersionUID = 1L;
 * → là mã phiên bản để đảm bảo tính tương thích khi class thay đổi giữa các phiên bản (rất quan trọng khi lưu cache hoặc truyền qua mạng).
 */
public class CTHangHoaId implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idhanghoa;
    private int idmau;
    private int idmay;

    public CTHangHoaId() {}

    public CTHangHoaId(int idhanghoa, int idmau, int idmay) {
        this.idhanghoa = idhanghoa;
        this.idmau = idmau;
        this.idmay = idmay;
    }

    public int getIdhanghoa() { return idhanghoa; }
    public void setIdhanghoa(int idhanghoa) { this.idhanghoa = idhanghoa; }
    public int getIdmau() { return idmau; }
    public void setIdmau(int idmau) { this.idmau = idmau; }
    public int getIdmay() { return idmay; }
    public void setIdmay(int idmay) { this.idmay = this.idmay; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CTHangHoaId that = (CTHangHoaId) o;
        return idhanghoa == that.idhanghoa && idmau == that.idmau
                && idmay == that.idmay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idhanghoa, idmau, idmay);
    }
}
