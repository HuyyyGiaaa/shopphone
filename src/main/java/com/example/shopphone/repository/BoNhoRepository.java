package com.example.shopphone.repository;

import com.example.shopphone.model.BoNho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoNhoRepository extends JpaRepository<BoNho, Long> {

    @Query("SELECT b FROM BoNho b WHERE UPPER(REPLACE(b.dungluong,' ','')) = ?1")
    BoNho findByDungluong(String dungluong);
}

