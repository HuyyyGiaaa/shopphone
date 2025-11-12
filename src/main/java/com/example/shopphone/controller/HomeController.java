package com.example.shopphone.controller;

import com.example.shopphone.model.HangHoa;
import com.example.shopphone.service.HangHoaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private HangHoaService hangHoaService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        // Lấy 10 sản phẩm mới nhất
        List<HangHoa> latestProducts = hangHoaService.getLatestProducts(10);
        // Lấy 10 sản phẩm khuyến mãi
        List<HangHoa> saleProducts = hangHoaService.getSaleProducts(10);

        // Lấy username từ session
        String username = (String) session.getAttribute("username");

        // Gửi dữ liệu sang view
        model.addAttribute("latestProducts", latestProducts);
        model.addAttribute("saleProducts", saleProducts);
        model.addAttribute("username", username); // ✅ thêm dòng này

        return "home"; // templates/home.html
    }

    @GetMapping("/dienthoai")
    public String dienthoai() {
        return "dienthoai";
    }

    @GetMapping("/phukien")
    public String phukien() {
        return "phukien";
    }

    @GetMapping("/ipad")
    public String ipad() {
        return "ipad";
    }

    @GetMapping("/lienhe")
    public String lienhe() {
        return "lienhe";
    }
}
