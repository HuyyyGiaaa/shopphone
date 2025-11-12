package com.example.shopphone.repository;

import com.example.shopphone.model.DongMay;
import com.example.shopphone.model.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Long> {
    MauSac findByTenMau(String tenMau);      // tìm màu theo tên

    List<MauSac> findByDongMay(DongMay dm);
}


