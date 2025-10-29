package com.example.shopphone.controller;

import com.example.shopphone.service.HangHoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private HangHoaService hangHoaService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("latestProducts", hangHoaService.getLatest(8)); // Lấy sản phẩm mới
        model.addAttribute("saleProducts", hangHoaService.getSale(8)); // Lấy 8 sản phẩm khuyến mãi
        return "home";
    }
}
