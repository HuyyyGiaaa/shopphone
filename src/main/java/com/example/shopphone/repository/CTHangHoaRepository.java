package com.example.shopphone.repository;

import com.example.shopphone.model.CTHangHoa;
import com.example.shopphone.model.CTHangHoaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CTHangHoaRepository extends JpaRepository<CTHangHoa, CTHangHoaId> {

    // Lấy tất cả biến thể theo sản phẩm, không join fetch
    List<CTHangHoa> findByHangHoa_Mahh(int mahh);

    // Lấy biến thể theo sản phẩm và fetch sẵn MauSac + DongMay + BoNho
    @Query("SELECT c FROM CTHangHoa c " +
            "JOIN FETCH c.mauSac " +
            "JOIN FETCH c.dongMay " +
            "JOIN FETCH c.boNho " +
            "WHERE c.hangHoa.mahh = :mahh")
    List<CTHangHoa> findByHangHoaWithDetails(@Param("mahh") int mahh);

}
