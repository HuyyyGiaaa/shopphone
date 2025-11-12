package com.example.shopphone.controller;

import com.example.shopphone.model.BoNho;
import com.example.shopphone.model.CTHangHoa;
import com.example.shopphone.model.DongMay;
import com.example.shopphone.model.HangHoa;
import com.example.shopphone.model.MauSac;
import com.example.shopphone.service.CTHangHoaService;
import com.example.shopphone.service.DongMayService;
import com.example.shopphone.service.MauSacService;
import com.example.shopphone.service.HangHoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;



@Controller
public class HangHoaController {

    @Autowired
    private DongMayService dongMayService;
    @Autowired
    private HangHoaService hangHoaService;
    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private CTHangHoaService cthangHoaService;

    // ✅ Trang hiển thị tất cả sản phẩm
    @GetMapping("/products")
    public String products(Model model) {
        List<HangHoa> allProducts = hangHoaService.findAll();
        model.addAttribute("allProducts", allProducts);
        return "products";
    }


    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable("id") int id, Model model) {

        // ✅ Dùng method JOIN FETCH từ repository/service
        HangHoa product = hangHoaService.findByIdWithDetails(id);
        if (product == null) return "redirect:/";

        DongMay dongMay = product.getDongMay();

        // Lấy tất cả sản phẩm cùng series (cũng nên dùng fetch nếu muốn tránh N+1)
        List<HangHoa> seriesProducts = hangHoaService.findBySeriesWithDetails(dongMay.getTenmay());


        // Lấy tất cả biến thể
        List<CTHangHoa> variants = product.getCtHangHoaList();
        CTHangHoa defaultVariant = variants.isEmpty() ? null : variants.get(0);

        // Tạo map thông số kỹ thuật
        Map<String, String> specs = Map.of(
                "Hệ điều hành", defaultVariant != null ? defaultVariant.getHedieuhanh() : "-",
                "CPU", defaultVariant != null ? defaultVariant.getCpu() : "-",
                "GPU", defaultVariant != null ? defaultVariant.getGpu() : "-",
                "RAM", defaultVariant != null ? defaultVariant.getRam() + " GB" : "-",
                "Bộ nhớ", defaultVariant != null && defaultVariant.getBoNho() != null
                        ? defaultVariant.getBoNho().getDungluong()
                        : "-",
                "Pin", defaultVariant != null ? defaultVariant.getPin() : "-",
                "Kết nối", defaultVariant != null ? defaultVariant.getKetnoi() : "-"
        );

            model.addAttribute("product", product);
            model.addAttribute("cthanghoaList", variants);
            model.addAttribute("dongMays", seriesProducts);
            model.addAttribute("colors", variants);
            model.addAttribute("specs", specs);
            model.addAttribute("defaultVariant", defaultVariant);
            model.addAttribute("memories", variants.stream()
                    .map(CTHangHoa::getBoNho)
                    .filter(Objects::nonNull)
                    .distinct()
                    .toList());
            return "productDetail"; // giữ nguyên

        }

}










