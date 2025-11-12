package com.example.shopphone.repository;

import com.example.shopphone.model.DongMay;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DongMayRepository extends JpaRepository<DongMay, Long> {
    // ✅ Tìm DongMay theo LoaiHang.maloai (đã dùng Long)
    List<DongMay> findByLoaiHang_Maloai(Long maloai);

    // ✅ Tìm theo tên dòng máy
    DongMay findByTenmay(String tenmay);
}
