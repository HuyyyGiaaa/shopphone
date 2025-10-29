package com.example.shopphone.controller;

import com.example.shopphone.model.HangHoa;
import com.example.shopphone.service.HangHoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HangHoaController {

    @Autowired
    private HangHoaService hangHoaService;

    // Trang hiển thị tất cả sản phẩm
    @GetMapping("/products")
    public String products(Model model) {
        // Lấy tất cả sản phẩm hiện có trong CSDL
        List<HangHoa> allProducts = hangHoaService.findAll();

        // Thêm vào model để Thymeleaf hiển thị
        model.addAttribute("allProducts", allProducts);

        return "products"; // trả về trang products.html
    }

    // Trang chi tiết sản phẩm theo id
    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable("id") int id, Model model) {
        HangHoa product = hangHoaService.getById(id);
        model.addAttribute("product", product);
        return "chitietsanpham";
    }
}
