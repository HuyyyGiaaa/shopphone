package com.example.shopphone.service;

import com.example.shopphone.model.DongMay;
import com.example.shopphone.repository.HangHoaRepository;
import com.example.shopphone.model.HangHoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class HangHoaService {

    @Autowired
    private HangHoaRepository hangHoaRepository;

    @Autowired
    private DongMayService dongMayService; // tích hợp DongMayService

    public List<HangHoa> getLatest(int limit) {
        return hangHoaRepository.findLatest(Pageable.ofSize(limit));
    }

    public List<HangHoa> getSale(int limit) {
        return hangHoaRepository.findSale(Pageable.ofSize(limit));
    }

    public List<HangHoa> findAll() {
        return hangHoaRepository.findAll();
    }

    public HangHoa getById(int id) {
        return hangHoaRepository.findById(id).orElse(null);
    }

    public HangHoa findByTenhh(String tenhh) {
        return hangHoaRepository.findByTenhh(tenhh);
    }

    // --- gộp save(HangHoa) duy nhất ---
    public HangHoa save(HangHoa hangHoa) {
        DongMay dm = hangHoa.getDongMay();
        if (dm != null && dm.getIdmay() != null) {
            DongMay existing = dongMayService.findById(dm.getIdmay());
            if (existing != null) {
                hangHoa.setDongMay(existing); // liên kết dòng máy đã có
            } else {
                dongMayService.save(dm); // lưu mới nếu chưa có
            }
        }
        return hangHoaRepository.save(hangHoa); // lưu sản phẩm
    }
}
