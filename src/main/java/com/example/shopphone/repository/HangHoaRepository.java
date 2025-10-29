package com.example.shopphone.repository;

import com.example.shopphone.model.HangHoa;
import com.example.shopphone.model.HangHoaWithPrice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HangHoaRepository extends JpaRepository<HangHoa, Integer> {
    HangHoa findByTenhh(String tenhh); // Tìm sản phẩm theo tên
    // Lấy sản phẩm mới nhất (sắp xếp theo mã giảm dần)
    @Query("SELECT h FROM HangHoa h ORDER BY h.mahh DESC")
    List<HangHoa> findLatest(Pageable pageable);

    // Lấy sản phẩm khuyến mãi (giamgia > 0)
    @Query("SELECT h FROM HangHoa h WHERE h.giamgia > 0 ORDER BY h.giamgia DESC")
    List<HangHoa> findSale(Pageable pageable);

    // Lấy sản phẩm không khuyến mãi, kèm giá từ bảng chi tiết (CTHangHoa)
    @Query("SELECT new com.example.shopphone.model.HangHoaWithPrice(h.mahh, h.tenhh, h.hinh, c.dongia) " +
            "FROM CTHangHoa c JOIN c.hangHoa h " +
            "WHERE (h.giamgia IS NULL OR h.giamgia = 0) " +
            "ORDER BY h.ngaylap DESC")
    List<HangHoaWithPrice> findLatestWithPrice(Pageable pageable);
}
