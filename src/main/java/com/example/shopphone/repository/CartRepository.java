package com.example.shopphone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopphone.model.CartItem;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUsername(String username);

    CartItem findByUsernameAndIdhanghoaAndIdmauAndIdmayAndIdbonho(
            String username, int idhanghoa, int idmau, int idmay, int idbonho
    );

    void deleteByUsernameAndIdhanghoaAndIdmauAndIdmayAndIdbonho(
            String username, int idhanghoa, int idmau, int idmay, int idbonho
    );

    void deleteByUsername(String username);
}
