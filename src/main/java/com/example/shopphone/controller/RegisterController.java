package com.example.shopphone.controller;

import com.example.shopphone.model.RoleConstants;
import com.example.shopphone.model.User;
import com.example.shopphone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // lấy từ WebConfig

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User()); // tạo object rỗng để bind form
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute User user,
            Model model
    ) {
        // username bắt buộc
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            model.addAttribute("error", "Tên đăng nhập là bắt buộc!");
            return "register";
        }

        // ít nhất phải có 1 trong 2: email hoặc phone
        if ((user.getEmail() == null || user.getEmail().isEmpty()) &&
                (user.getPhone() == null || user.getPhone().isEmpty())) {
            model.addAttribute("error", "Vui lòng nhập email hoặc số điện thoại!");
            return "register";
        }

        // Mật khẩu khớp
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
            return "register";
        }

        // Kiểm tra trùng username/email/phone
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "register";
        }

        if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email đã được sử dụng!");
            return "register";
        }

        if (user.getPhone() != null && userRepository.findByPhone(user.getPhone()) != null) {
            model.addAttribute("error", "Số điện thoại đã được sử dụng!");
            return "register";
        }

        // Khi lưu user:

        // --- Mã hóa password ---
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Gán role mặc định
        user.setRole(RoleConstants.USER);

        // Lưu vào database
        userRepository.save(user);

        // Reset form + thông báo success
        model.addAttribute("success", "Đăng ký thành công! Bạn có thể đăng nhập ngay.");
        model.addAttribute("user", new User()); // reset form

        return "register";
    }
}
