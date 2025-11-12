package com.example.shopphone.service;

import com.example.shopphone.model.DongMay;
import com.example.shopphone.model.MauSac;
import com.example.shopphone.repository.MauSacRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MauSacService {

    private final MauSacRepository mauSacRepository;

    public MauSacService(MauSacRepository mauSacRepository) {
        this.mauSacRepository = mauSacRepository;
    }

    public MauSac findByTenMau(String tenMau) {
        return mauSacRepository.findByTenMau(tenMau);
    }

    public List<MauSac> findByDongMay(DongMay dm) {
        return mauSacRepository.findByDongMay(dm);
    }

    public MauSac save(MauSac ms) {
        return mauSacRepository.save(ms);
    }
}
