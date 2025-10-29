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

    // Lấy tất cả dòng máy
    public List<DongMay> findAll() {
        return dongMayRepository.findAll();
    }

    //tìm theo tên dòng máy
    public DongMay findByTenmay(String tenmay) {
        return dongMayRepository.findByTenmay(tenmay);
    }

    // Lấy theo id
    public DongMay findById(Integer id) {
        return dongMayRepository.findById(id).orElse(null);
    }

    // Lưu hoặc cập nhật
    public DongMay save(DongMay dongMay) {
        return dongMayRepository.save(dongMay);
    }

    // Xóa nếu cần
    public void deleteById(Integer id) {
        dongMayRepository.deleteById(id);
    }
}
