package com.example.shopphone.repository;

import com.example.shopphone.model.LoaiHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoaiHangRepository extends JpaRepository<LoaiHang, Integer> {
    LoaiHang findByTenloai(String tenloai);
}
