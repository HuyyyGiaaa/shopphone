package com.example.shopphone.service;

import com.example.shopphone.model.BoNho;
import com.example.shopphone.repository.BoNhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoNhoService {

    @Autowired
    private BoNhoRepository boNhoRepository;

    // ✅ Lấy tất cả
    public List<BoNho> findAll() {
        return boNhoRepository.findAll();
    }

    // ✅ Lưu hoặc cập nhật
    public BoNho save(BoNho bn) {
        return boNhoRepository.save(bn);
    }

    // ✅ Tìm theo dung lượng, bỏ khoảng trắng và ignore case
    public BoNho findByDungluong(String dungluong) {
        if (dungluong == null) return null;
        String cleanInput = dungluong.replaceAll("\\s+","").toUpperCase();
        BoNho bn = boNhoRepository.findByDungluong(cleanInput);
        if(bn == null){
            throw new RuntimeException("BoNho not found: " + dungluong);
        }
        return bn;
    }

    // ✅ Tìm theo ID
    public Optional<BoNho> findById(Long id) {
        return boNhoRepository.findById(id);
    }
}
