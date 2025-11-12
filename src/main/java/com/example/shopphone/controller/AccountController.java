package com.example.shopphone.controller;

import com.example.shopphone.model.User;
import com.example.shopphone.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public String showAccountPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }

        // Lấy user mới nhất từ DB (phòng khi đã cập nhật)
        User freshUser = userService.getById(user.getId());
        model.addAttribute("user", freshUser);
        return "account";
    }

    @PostMapping("/account/update")
    public String updateAccount(@ModelAttribute("user") User updatedUser, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Cập nhật thông tin
        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setPhone(updatedUser.getPhone());
        currentUser.setAddress(updatedUser.getAddress());

        // Lưu vào DB
        userService.save(currentUser);

        // Cập nhật lại session
        session.setAttribute("loggedUser", currentUser);

        return "redirect:/account";
    }
}
