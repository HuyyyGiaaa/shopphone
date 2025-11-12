package com.example.shopphone.controller;

import com.example.shopphone.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user")
    public String userPage(HttpSession session, Model model) {
        Object loggedUser = session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // nếu chưa đăng nhập thì chuyển về trang đăng nhập
        }
        model.addAttribute("user", loggedUser);
        return "user";
    }

    // ===== Dashboard =====
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "dashboard"; // dashboard.html
    }


}
