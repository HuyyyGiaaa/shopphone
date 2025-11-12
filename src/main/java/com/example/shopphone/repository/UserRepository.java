package com.example.shopphone.repository;

import com.example.shopphone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Long> {
// Khai báo interface UserRepository, kế thừa (extends) JpaRepository.
// <User, Long> nghĩa là: Entity làm việc là User, và khóa chính (id) của User có kiểu dữ liệu Long.

    User findByUsername(String username);
    // Phương thức tìm người dùng theo tên đăng nhập (username).


    User findByEmail(String email);
    // Phương thức tìm người dùng theo email.


    User findByPhone(String phone);
    // Phương thức tìm người dùng theo số điện thoại.

    // Tìm user theo username hoặc email hoặc phone
    @Query("SELECT u FROM User u WHERE u.username = :account OR u.email = :account OR u.phone = :account")
    User findByUsernameOrEmailOrPhone(@Param("account") String account);

}
