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

}
