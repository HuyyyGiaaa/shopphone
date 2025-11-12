package com.example.shopphone.repository;

import com.example.shopphone.model.HangHoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HangHoaRepository extends JpaRepository<HangHoa, Long> {

    HangHoa findByTenhh(String tenhh);

    // ✅ Lấy sản phẩm mới nhất
    @Query(value = "SELECT * FROM hanghoa ORDER BY mahh DESC LIMIT ?1", nativeQuery = true)
    List<HangHoa> findLatestProducts(int limit);

    // ✅ Lấy sản phẩm đang giảm giá
    @Query("SELECT h FROM HangHoa h WHERE h.giamgia > 0 ORDER BY h.giamgia DESC")
    List<HangHoa> findSaleProducts();

    // ✅ Tìm theo id dòng máy
    // HangHoaRepository.java
    List<HangHoa> findByDongMay_Idmay(Long idmay);


    // ✅ Tìm theo series
    @Query("SELECT h FROM HangHoa h WHERE h.dongMay.tenmay = :seriesName")
    List<HangHoa> findBySeries(@Param("seriesName") String seriesName);

    // ✅ Tìm theo series + loại hàng
    @Query("SELECT h FROM HangHoa h WHERE h.dongMay.tenmay = :seriesName AND h.dongMay.loaiHang.maloai = :loaiId")
    List<HangHoa> findBySeriesAndLoai(@Param("seriesName") String seriesName,
                                      @Param("loaiId") int loaiId);

    // ✅ Nâng cấp: load đầy đủ chi tiết sản phẩm (dongMay + ctHangHoa + boNho)
    @Query("SELECT h FROM HangHoa h " +
            "LEFT JOIN FETCH h.ctHangHoaList c " +
            "LEFT JOIN FETCH c.mauSac " +
            "LEFT JOIN FETCH c.boNho " +
            "LEFT JOIN FETCH h.dongMay " +
            "WHERE h.mahh = :id")
    HangHoa findByIdWithDetails(@Param("id") int id);

    // ✅ Nâng cấp: load toàn bộ sản phẩm có chi tiết
    // Load toàn bộ sản phẩm + chi tiết (ctHangHoa, BoNho, MauSac, DongMay)
    @Query("""
        SELECT DISTINCT h
        FROM HangHoa h
        JOIN FETCH h.ctHangHoaList cth
        LEFT JOIN FETCH cth.boNho
        LEFT JOIN FETCH cth.mauSac
        JOIN FETCH h.dongMay
    """)
    List<HangHoa> findAllWithDetails();

    // ✅ Nâng cấp: tìm tất cả sản phẩm cùng series (load chi tiết)
    @Query("SELECT h FROM HangHoa h " +
            "LEFT JOIN FETCH h.ctHangHoaList c " +
            "LEFT JOIN FETCH c.mauSac " +
            "LEFT JOIN FETCH c.boNho " +
            "LEFT JOIN FETCH h.dongMay " +
            "WHERE h.dongMay.tenmay = :seriesName")
    List<HangHoa> findBySeriesWithDetails(@Param("seriesName") String seriesName);
}
