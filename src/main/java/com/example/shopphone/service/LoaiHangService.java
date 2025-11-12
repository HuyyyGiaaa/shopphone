package com.example.shopphone.service;

import com.example.shopphone.model.LoaiHang;
import com.example.shopphone.repository.LoaiHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// <-- Lỗi nếu bạn có dòng code ở đây ngoài class

@Service
public class LoaiHangService {

    @Autowired
    private LoaiHangRepository loaiHangRepository;

    public List<LoaiHang> findAll() {
        return loaiHangRepository.findAll();
    }

    public LoaiHang findByTenloai(String tenloai) {
        return loaiHangRepository.findByTenloai(tenloai);
    }

    public LoaiHang save(LoaiHang loaiHang) {
        return loaiHangRepository.save(loaiHang);
    }

    public LoaiHang findById(Integer id) {
        return loaiHangRepository.findById(id).orElse(null);
    }

}
