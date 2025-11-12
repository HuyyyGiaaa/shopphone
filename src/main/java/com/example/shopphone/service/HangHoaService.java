package com.example.shopphone.service;

import com.example.shopphone.model.CTHangHoaId;
import com.example.shopphone.model.CTHangHoa;
import com.example.shopphone.model.HangHoa;
import com.example.shopphone.model.DongMay;
import com.example.shopphone.repository.CTHangHoaRepository;
import com.example.shopphone.repository.HangHoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HangHoaService {

    @Autowired
    private CTHangHoaRepository ctRepo;

    @Autowired
    private HangHoaRepository hangHoaRepository;

    @Autowired
    private DongMayService dongMayService;

    @Autowired
    private BoNhoService boNhoService;

    // --- Các phương thức cũ giữ nguyên ---
        public CTHangHoa getCTHangHoa(int idhanghoa, int idmau, int idmay, int idbonho) {
        CTHangHoaId id = new CTHangHoaId(
                Long.valueOf(idhanghoa),
                Long.valueOf(idmau),
                Long.valueOf(idmay),
                Long.valueOf(idbonho)
        );
        return ctRepo.findById(id).orElse(null);
    }
    // --- Method mới: load tất cả sản phẩm kèm chi tiết ---
    public List<HangHoa> findAllWithDetails() {
        return hangHoaRepository.findAllWithDetails();
    }

    public List<CTHangHoa> getVariantsByProductId(int mahh) {
        return ctRepo.findByHangHoaWithDetails(mahh);
    }

    public List<HangHoa> getLatestProducts(int limit) {
        return hangHoaRepository.findLatestProducts(limit);
    }

    public List<HangHoa> getSaleProducts(int limit) {
        List<HangHoa> allSale = hangHoaRepository.findSaleProducts();
        return allSale.size() > limit ? allSale.subList(0, limit) : allSale;
    }

    // --- Thêm đoạn này để tìm theo DongMay ---
    public List<HangHoa> findByDongMay(Long idmay) {
        return hangHoaRepository.findByDongMay_Idmay(idmay);
    }

    public List<HangHoa> findAll() {
        return hangHoaRepository.findAll();
    }

    public HangHoa findByTenhh(String tenhh) {
        return hangHoaRepository.findByTenhh(tenhh);
    }

    public HangHoa findById(int id) {
        return hangHoaRepository.findById((long) id).orElse(null);
    }

    public boolean existsById(int id) {
        return hangHoaRepository.existsById((long) id);
    }

    public List<HangHoa> findBySeries(String seriesName) {
        return hangHoaRepository.findBySeries(seriesName);
    }

    public HangHoa save(HangHoa hangHoa) {
        DongMay dm = hangHoa.getDongMay();
        if (dm != null) {
            if (dm.getIdmay() != null) {
                DongMay existing = dongMayService.findById(dm.getIdmay());
                if (existing != null) hangHoa.setDongMay(existing);
                else dongMayService.save(dm);
            } else dongMayService.save(dm);
        }
        return hangHoaRepository.save(hangHoa);
    }

    // --- Nâng cấp: load đầy đủ chi tiết + tránh N+1 ---
    public HangHoa findByIdWithDetails(int id) {
        return hangHoaRepository.findByIdWithDetails(id);
    }

    public List<HangHoa> findBySeriesWithDetails(String seriesName) {
        return hangHoaRepository.findBySeriesWithDetails(seriesName);
    }

    // --- Nếu muốn vẫn giữ getCTHangHoa nâng cấp ---
    public CTHangHoa findDetail(int idhanghoa, int idmau, int idmay, int idbonho) {
        CTHangHoaId id = new CTHangHoaId(
                Long.valueOf(idhanghoa),
                Long.valueOf(idmau),
                Long.valueOf(idmay),
                Long.valueOf(idbonho)
        );
        return ctRepo.findById(id).orElse(null);
    }
}
