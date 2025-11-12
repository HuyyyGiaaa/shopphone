package com.example.shopphone.service;

import com.example.shopphone.model.DongMay;
import com.example.shopphone.repository.DongMayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DongMayService {

    @Autowired
    private DongMayRepository dongMayRepository;

    // ------------------ CRUD cơ bản ------------------

    // Lấy tất cả dòng máy
    public List<DongMay> findAll() {
        return dongMayRepository.findAll();
    }

    // Tìm theo tên dòng máy
    public DongMay findByTenmay(String tenmay) {
        if (tenmay == null || tenmay.isEmpty()) return null;
        return dongMayRepository.findByTenmay(tenmay);
    }

    // Tìm theo ID (Long)
    public DongMay findById(Long id) {
        if (id == null) return null;
        return dongMayRepository.findById(id).orElse(null);
    }

    // Lưu hoặc cập nhật dòng máy
    public DongMay save(DongMay dongMay) {
        if (dongMay == null) return null;
        return dongMayRepository.save(dongMay);
    }

    // Xóa theo ID
    public void deleteById(Long id) {
        if (id != null && dongMayRepository.existsById(id)) {
            dongMayRepository.deleteById(id);
        }
    }

    // ------------------ Tìm kiếm theo loại/series ------------------

    // Lấy tất cả dòng máy thuộc cùng loại (series)
    public List<DongMay> findByMaloai(Long maloai) {
        if (maloai == null) return List.of();
        return dongMayRepository.findByLoaiHang_Maloai(maloai);
    }

    // ------------------ Tiện ích nâng cao ------------------

    // Kiểm tra tồn tại theo ID
    public boolean existsById(Long id) {
        return id != null && dongMayRepository.existsById(id);
    }

    // Lấy danh sách ID của tất cả dòng máy
    public List<Long> findAllIds() {
        return dongMayRepository.findAll().stream().map(DongMay::getIdmay).toList();
    }
}
