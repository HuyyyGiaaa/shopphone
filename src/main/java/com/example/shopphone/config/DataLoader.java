package com.example.shopphone.config;

import com.example.shopphone.model.DongMay;
import com.example.shopphone.model.HangHoa;
import com.example.shopphone.service.DongMayService;
import com.example.shopphone.service.HangHoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private HangHoaService hangHoaService;

    @Autowired
    private DongMayService dongMayService;

    @Override
    public void run(String... args) throws Exception {
        loadProducts();
    }

    private void loadProducts() {
        // Nếu đã có sản phẩm thì không làm gì
        if (!hangHoaService.findAll().isEmpty()) return;

        // --- Tạo dòng máy ---
        DongMay dm6 = addDongMayIfNotExists("iPhone 6 Series");
        DongMay dm7 = addDongMayIfNotExists("iPhone 7 Series");
        DongMay dm8 = addDongMayIfNotExists("iPhone 8 Series");
        DongMay dmX = addDongMayIfNotExists("iPhone X Series");
        DongMay dm11 = addDongMayIfNotExists("iPhone 11 Series");
        DongMay dm12 = addDongMayIfNotExists("iPhone 12 Series");
        DongMay dm13 = addDongMayIfNotExists("iPhone 13 Series");
        DongMay dm14 = addDongMayIfNotExists("iPhone 14 Series");
        DongMay dm15 = addDongMayIfNotExists("iPhone 15 Series");
        DongMay dm16 = addDongMayIfNotExists("iPhone 16 Series");
        DongMay dm17 = addDongMayIfNotExists("iPhone 17 Series");

        int id = 1;

        // --- Tạo sản phẩm ---
        HangHoa[] products = new HangHoa[] {
                createProduct(id++, "iPhone 6", 5000000f, "ip6.png", Arrays.asList(16,32,64), dm6),
                createProduct(id++, "iPhone 6s", 6000000f, "ip6s.png", Arrays.asList(16,32,64), dm6),
                createProduct(id++, "iPhone 7", 8000000f, "ip7.png", Arrays.asList(32,64,128), dm7),
                createProduct(id++, "iPhone 7 Plus", 9000000f, "ip7plus.png", Arrays.asList(32,64,128), dm7),
                createProduct(id++, "iPhone 8", 11000000f, "ip8.png", Arrays.asList(64,128,256), dm8),
                createProduct(id++, "iPhone 8 Plus", 12000000f, "ip8plus.png", Arrays.asList(64,128,256), dm8),
                createProduct(id++, "iPhone X", 15000000f, "ipx.png", Arrays.asList(64,128,256), dmX),
                createProduct(id++, "iPhone XR", 16000000f, "ipxr.png", Arrays.asList(64,128,256), dmX)
                // ... tiếp tục thêm các sản phẩm còn lại theo cùng cách
        };

        // Lưu sản phẩm, tránh trùng tên
        for(HangHoa p : products) {
            if (hangHoaService.findByTenhh(p.getTenhh()) == null) {
                hangHoaService.save(p);
            }
        }
    }

    private DongMay addDongMayIfNotExists(String tenmay) {
        DongMay dm = dongMayService.findByTenmay(tenmay);
        if(dm == null) {
            dm = new DongMay();
            dm.setTenmay(tenmay);
            dm = dongMayService.save(dm);
        }
        return dm;
    }

    private HangHoa createProduct(int id, String tenhh, float dongia, String hinh, List<Integer> bonhoOptions, DongMay dm) {
        HangHoa p = new HangHoa();
        p.setMahh(id);
        p.setTenhh(tenhh);
        p.setDongia(dongia);
        p.setHinh(hinh);
        p.setBonhoOptions(bonhoOptions);
        p.setDongMay(dm);
        return p;
    }
}
