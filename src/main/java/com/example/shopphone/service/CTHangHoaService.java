package com.example.shopphone.service;

import com.example.shopphone.model.CTHangHoa;
import com.example.shopphone.repository.CTHangHoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CTHangHoaService {

    @Autowired
    private CTHangHoaRepository cthangHoaRepository;

    // --- Lưu 1 CTHangHoa ---
    public CTHangHoa save(CTHangHoa ct) {
        return cthangHoaRepository.save(ct);
    }

    // Lấy danh sách biến thể theo id hanghoa (không fetch chi tiết)
    public List<CTHangHoa> getVariantsByProductId(int mahh) {
        return cthangHoaRepository.findByHangHoa_Mahh(mahh);
    }

    // Lấy danh sách biến thể theo id hanghoa, fetch chi tiết (màu + dòng máy + bộ nhớ)
    public List<CTHangHoa> findByHangHoaId(int idHangHoa) {
        return cthangHoaRepository.findByHangHoaWithDetails(idHangHoa);
    }
}
