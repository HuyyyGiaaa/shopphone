package com.example.shopphone.repository;

import com.example.shopphone.model.DongMay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DongMayRepository extends JpaRepository<DongMay, Integer> {
    // tên phương thức phải đúng tên trường trong entity: 'tenmay'
    DongMay findByTenmay(String tenmay);
}