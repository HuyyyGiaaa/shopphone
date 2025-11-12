package com.example.shopphone.controller;

import com.example.shopphone.model.User;
import com.example.shopphone.service.CartService;
import com.example.shopphone.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /** Hiển thị form login */
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "redirect", required = false) String redirectUrl,
                                HttpSession session) {
        if (redirectUrl != null) {
            session.setAttribute("redirectAfterLogin", redirectUrl);
        }
        return "login";
    }

    /** Xử lý login */
    @PostMapping("/login")
    public String login(@RequestParam("account") String account,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {

        // Lấy user theo username/email/phone
        User user = userService.getByUsername(account);
        if (user == null) user = userService.getByEmail(account);
        if (user == null) user = userService.getByPhone(account);

        if (user == null) {
            model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng!");
            return "login";
        }

        //  Kiểm tra password hashed
        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng!");
            return "login";
        }

        // Lưu thông tin đăng nhập vào session
        session.setAttribute("username", user.getUsername());
        session.setAttribute("loggedUser", user); // user là entity vừa login
        session.setAttribute("avatar", user.getAvatar() != null ? user.getAvatar() : "avatardefault.png");
        session.setAttribute("role", user.getRole());
        session.setAttribute("userId", user.getId());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("phone", user.getPhone());

        // 📥 Load giỏ hàng từ database vào session
        cartService.loadCartFromDatabase(user.getUsername());

        // Cập nhật số lượng giỏ hàng
        int cartCount = cartService.getCartCount(user.getUsername());
        session.setAttribute("cartCount", cartCount);

        // Redirect về trang trước nếu có
        String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
        if (redirectUrl != null) {
            session.removeAttribute("redirectAfterLogin");
            return "redirect:" + redirectUrl;
        }

        return "redirect:/"; // về trang chủ nếu không có redirect trước
    }

    /** Logout */
    @GetMapping("/logout")
    public String logout(HttpSession session,
                         @RequestHeader(value = "Referer", required = false) String referer) {
        // 💾 Lưu giỏ hàng từ session vào database trước khi logout
        String username = (String) session.getAttribute("username");
        if (username != null) {
            cartService.saveCartToDatabase(username);
        }

        // Gọi service logout (hàm này có session.invalidate())
        userService.logout(session);

        // Xóa thêm dữ liệu giỏ hàng và thông tin user khỏi session (nếu còn)
        session.removeAttribute("cartCount");
        session.removeAttribute("loggedUser");
        session.removeAttribute("username");
        session.removeAttribute("avatar");
        session.removeAttribute("role");
        session.removeAttribute("email");
        session.removeAttribute("phone");
        session.removeAttribute("userId");

        // Sau khi logout → quay về trang chủ mặc định
        return "redirect:/";
    }

}
