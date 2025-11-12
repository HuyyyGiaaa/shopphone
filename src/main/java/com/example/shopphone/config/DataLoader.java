package com.example.shopphone.config;

import com.example.shopphone.repository.MauSacRepository;
import com.example.shopphone.model.*;
import com.example.shopphone.repository.UserRepository;
import com.example.shopphone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CTHangHoaService ctHangHoaService;
    @Autowired
    private HangHoaService hangHoaService;
    @Autowired
    private DongMayService dongMayService;
    @Autowired
    private MauSacService mauSacService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BoNhoService boNhoService;
    @Autowired
    private LoaiHangService loaiHangService;

    @Override
    public void run(String... args) {
        loadUsers();
        loadBoNho();
        loadLoaiHangAndDongMay();
        loadProducts();
    }

    // ------------------ USERS ------------------
    private void loadUsers() {
        createUserIfNotExists("admin", "admin@gmail.com", "123456", RoleConstants.ADMIN);
        createUserIfNotExists("staff", "staff@gmail.com", "123456", RoleConstants.STAFF);
        createUserIfNotExists("seller", "seller@gmail.com", "123456", RoleConstants.SELLER);
        createUserIfNotExists("testuser", "testuser@gmail.com", "123", RoleConstants.USER);
        System.out.println("✅ Users loaded");
    }

    private void createUserIfNotExists(String username, String email, String rawPassword, String role) {
        if (userRepository.findByUsername(username) == null) {
            User u = new User();
            u.setUsername(username);
            u.setEmail(email);
            u.setPassword(passwordEncoder.encode(rawPassword));
            u.setRole(role);
            userRepository.save(u);
        }
    }

    // ------------------ BO NHO ------------------
    private void loadBoNho() {
        List<String> sizes = Arrays.asList("16GB", "32GB", "64GB", "128GB", "256GB", "512GB", "1TB", "2TB");
        for (String s : sizes) {
            if (boNhoService.findByDungluong(s) == null) {
                BoNho bn = new BoNho();
                bn.setDungluong(s);
                boNhoService.save(bn);
            }
        }
        System.out.println("✅ BoNho loaded");
    }

    // ------------------ LOAI HANG & DONG MAY ------------------
    private void loadLoaiHangAndDongMay() {
        LoaiHang iphone = addLoaiHangIfNotExists("iPhone");
        LoaiHang ipad = addLoaiHangIfNotExists("iPad");

        addDongMayIfNotExists("iPhone 6 Series", iphone);
        addDongMayIfNotExists("iPhone 7 Series", iphone);
        addDongMayIfNotExists("iPhone 8 Series", iphone);
        addDongMayIfNotExists("iPhone X Series", iphone);
        addDongMayIfNotExists("iPhone 11 Series", iphone);
        addDongMayIfNotExists("iPhone 12 Series", iphone);
        addDongMayIfNotExists("iPhone 13 Series", iphone);
        addDongMayIfNotExists("iPhone 14 Series", iphone);
        addDongMayIfNotExists("iPhone 15 Series", iphone);
        addDongMayIfNotExists("iPhone 16 Series", iphone);
        addDongMayIfNotExists("iPhone 17 Series", iphone);

        System.out.println("✅ LoaiHang & DongMay loaded");
    }

    private LoaiHang addLoaiHangIfNotExists(String name) {
        LoaiHang lh = loaiHangService.findByTenloai(name);
        if (lh == null) {
            lh = new LoaiHang();
            lh.setTenloai(name);
            lh = loaiHangService.save(lh);
        }
        return lh;
    }

    private DongMay addDongMayIfNotExists(String tenmay, LoaiHang loai) {
        DongMay dm = dongMayService.findByTenmay(tenmay);
        if (dm == null) {
            dm = new DongMay();
            dm.setTenmay(tenmay);
            dm.setLoaiHang(loai);
            dm = dongMayService.save(dm);
        }
        return dm;
    }

    // ------------------ PRODUCTS ------------------
    private void loadProducts() {
        // Lấy dòng máy từ DB
        DongMay dm6 = dongMayService.findByTenmay("iPhone 6 Series");
        DongMay dm7 = dongMayService.findByTenmay("iPhone 7 Series");
        DongMay dm8 = dongMayService.findByTenmay("iPhone 8 Series");
        DongMay dmX = dongMayService.findByTenmay("iPhone X Series");
        DongMay dm11 = dongMayService.findByTenmay("iPhone 11 Series");
        DongMay dm12 = dongMayService.findByTenmay("iPhone 12 Series");
        DongMay dm13 = dongMayService.findByTenmay("iPhone 13 Series");
        DongMay dm14 = dongMayService.findByTenmay("iPhone 14 Series");
        DongMay dm15 = dongMayService.findByTenmay("iPhone 15 Series");
        DongMay dm16 = dongMayService.findByTenmay("iPhone 16 Series");
        DongMay dm17 = dongMayService.findByTenmay("iPhone 17 Series");

        HangHoa[] products = new HangHoa[]{
                // ---------------- iPhone 6 ----------------
                createOrUpdateProduct("iPhone 6", 5000000f, "ip6.png", Arrays.asList(16L, 32L, 64L), dm6,
                        "Apple A8", "PowerVR GX6450", "1GB", "iOS 8",
                        "4G LTE, Wi-Fi 802.11 a/b/g/n/ac, Bluetooth 4.0", "1810 mAh"),
                createOrUpdateProduct("iPhone 6s", 6000000f, "ip6s.png", Arrays.asList(16L, 32L, 64L), dm6,
                        "Apple A9", "PowerVR GT7600", "2GB", "iOS 9",
                        "4G LTE, Wi-Fi 802.11 a/b/g/n/ac, Bluetooth 4.2", "1715 mAh"),
                // ---------------- iPhone 7 ----------------
                createOrUpdateProduct("iPhone 7", 8000000f, "ip7.png", Arrays.asList(32L, 64L, 128L), dm7,
                        "Apple A10 Fusion", "GPU 6 nhân", "2GB", "iOS 10",
                        "4G LTE, Wi-Fi 802.11 a/b/g/n/ac, Bluetooth 4.2", "1960 mAh"),
                createOrUpdateProduct("iPhone 7 Plus", 9000000f, "ip7plus.png", Arrays.asList(32L, 64L, 128L), dm7,
                        "Apple A10 Fusion", "GPU 6 nhân", "3GB", "iOS 10",
                        "4G LTE, Wi-Fi 802.11 a/b/g/n/ac, Bluetooth 4.2", "2900 mAh"),
                // ---------------- iPhone 8 ----------------
                createOrUpdateProduct("iPhone 8", 11000000f, "ip8.png", Arrays.asList(64L, 128L, 256L), dm8,
                        "Apple A11 Bionic", "Apple GPU 3 nhân", "2GB", "iOS 11",
                        "4G LTE, Wi-Fi 802.11 a/b/g/n/ac, Bluetooth 5.0", "1821 mAh"),
                createOrUpdateProduct("iPhone 8 Plus", 12000000f, "ip8plus.png", Arrays.asList(64L, 128L, 256L), dm8,
                        "Apple A11 Bionic", "Apple GPU 3 nhân", "3GB", "iOS 11",
                        "4G LTE, Wi-Fi 802.11 a/b/g/n/ac, Bluetooth 5.0", "2691 mAh"),
                // ---------------- iPhone X ----------------
                createOrUpdateProduct("iPhone X", 15000000f, "ipx.png", Arrays.asList(64L, 128L, 256L), dmX,
                        "Apple A11 Bionic", "Apple GPU 3 nhân", "3GB", "iOS 11",
                        "4G LTE, Wi-Fi 802.11 a/b/g/n/ac, Bluetooth 5.0", "2716 mAh"),
                createOrUpdateProduct("iPhone XR", 16000000f, "ipxr.png", Arrays.asList(64L, 128L, 256L), dmX,
                        "Apple A12 Bionic", "Apple GPU 4 nhân", "3GB", "iOS 12",
                        "4G LTE, Wi-Fi 802.11 a/b/g/n/ac, Bluetooth 5.0", "2942 mAh"),
                createOrUpdateProduct("iPhone XS Max", 16000000f, "ipxsmax.png", Arrays.asList(64L, 128L, 256L), dmX,
                        "Apple A12 Bionic", "Apple GPU 4 nhân", "4GB", "iOS 12",
                        "4G LTE, Wi-Fi 802.11 a/b/g/n/ac, Bluetooth 5.0", "3174 mAh"),
                    // ---------------- iPhone 11 Series ----------------
                    createOrUpdateProduct("iPhone 11", 17000000f, "ip11.png", Arrays.asList(64L, 128L, 256L), dm11,
                            "Apple A13 Bionic", "Apple GPU 4 nhân", "4GB", "iOS 13",
                            "4G LTE, Wi-Fi 6, Bluetooth 5.0", "3110 mAh"),
                    createOrUpdateProduct("iPhone 11 Pro", 19000000f, "ip11pro.png", Arrays.asList(64L, 128L, 256L), dm11,
                            "Apple A13 Bionic", "Apple GPU 4 nhân", "4GB", "iOS 13",
                            "4G LTE, Wi-Fi 6, Bluetooth 5.0", "3046 mAh"),
                    createOrUpdateProduct("iPhone 11 Pro Max", 21000000f, "ip11promax.png", Arrays.asList(64L, 128L, 256L), dm11,
                            "Apple A13 Bionic", "Apple GPU 4 nhân", "4GB", "iOS 13",
                            "4G LTE, Wi-Fi 6, Bluetooth 5.0", "3969 mAh"),
                    // ---------------- iPhone 12 Series ----------------
                    createOrUpdateProduct("iPhone 12", 22000000f, "ip12.png", Arrays.asList(64L, 128L, 256L), dm12,
                            "Apple A14 Bionic", "Apple GPU 4 nhân", "4GB", "iOS 14",
                            "5G, Wi-Fi 6, Bluetooth 5.0", "2815 mAh"),
                    createOrUpdateProduct("iPhone 12 Pro", 24000000f, "ip12pro.png", Arrays.asList(64L, 128L, 256L), dm12,
                            "Apple A14 Bionic", "Apple GPU 4 nhân", "6GB", "iOS 14",
                            "5G, Wi-Fi 6, Bluetooth 5.0", "2815 mAh"),
                    createOrUpdateProduct("iPhone 12 Pro Max", 26000000f, "ip12promax.png", Arrays.asList(64L, 128L, 256L), dm12,
                            "Apple A14 Bionic", "Apple GPU 4 nhân", "6GB", "iOS 14",
                            "5G, Wi-Fi 6, Bluetooth 5.0", "3687 mAh"),

    // ---------------- iPhone 13 Series ----------------
                    createOrUpdateProduct("iPhone 13", 27000000f, "ip13.png", Arrays.asList(128L, 256L, 512L), dm13,
                            "Apple A15 Bionic", "Apple GPU 4 nhân", "4GB", "iOS 15",
                            "5G, Wi-Fi 6, Bluetooth 5.0", "3240 mAh"),
                    createOrUpdateProduct("iPhone 13 Pro", 29000000f, "ip13pro.png", Arrays.asList(128L, 256L, 512L), dm13,
                            "Apple A15 Bionic", "Apple GPU 5 nhân", "6GB", "iOS 15",
                            "5G, Wi-Fi 6, Bluetooth 5.0", "3095 mAh"),
                    createOrUpdateProduct("iPhone 13 Pro Max", 31000000f, "ip13promax.png", Arrays.asList(128L, 256L, 512L), dm13,
                            "Apple A15 Bionic", "Apple GPU 5 nhân", "6GB", "iOS 15",
                            "5G, Wi-Fi 6, Bluetooth 5.0", "4352 mAh"),

    // ---------------- iPhone 14 Series ----------------
                    createOrUpdateProduct("iPhone 14", 32000000f, "ip14.png", Arrays.asList(128L, 256L, 512L), dm14,
                            "Apple A15 Bionic", "Apple GPU 4 nhân", "6GB", "iOS 16",
                            "5G, Wi-Fi 6, Bluetooth 5.3", "3279 mAh"),
                    createOrUpdateProduct("iPhone 14 Plus", 33000000f, "ip14plus.png", Arrays.asList(128L, 256L, 512L), dm14,
                            "Apple A15 Bionic", "Apple GPU 4 nhân", "6GB", "iOS 16",
                            "5G, Wi-Fi 6, Bluetooth 5.3", "4325 mAh"),
                    createOrUpdateProduct("iPhone 14 Pro", 34000000f, "ip14pro.png", Arrays.asList(128L, 256L, 512L, 1024L), dm14,
                            "Apple A16 Bionic", "Apple GPU 5 nhân", "6GB", "iOS 16",
                            "5G, Wi-Fi 6, Bluetooth 5.3", "3200 mAh"),
                    createOrUpdateProduct("iPhone 14 Pro Max", 36000000f, "ip14promax.png", Arrays.asList(128L, 256L, 512L, 1024L, 2048L), dm14,
                            "Apple A16 Bionic", "Apple GPU 5 nhân", "6GB", "iOS 16",
                            "5G, Wi-Fi 6, Bluetooth 5.3", "4323 mAh"),

    // ---------------- iPhone 15 Series ----------------
                    createOrUpdateProduct("iPhone 15", 37000000f, "ip15.png", Arrays.asList(128L, 256L, 512L), dm15,
                            "Apple A16 Bionic", "Apple GPU 5 nhân", "6GB", "iOS 17",
                            "5G, Wi-Fi 6, Bluetooth 5.3", "3279 mAh"),
                    createOrUpdateProduct("iPhone 15 Plus", 38000000f, "ip15plus.png", Arrays.asList(128L, 256L, 512L), dm15,
                            "Apple A16 Bionic", "Apple GPU 5 nhân", "6GB", "iOS 17",
                            "5G, Wi-Fi 6, Bluetooth 5.3", "4323 mAh"),
                    createOrUpdateProduct("iPhone 15 Pro", 39000000f, "ip15pro.png", Arrays.asList(128L, 256L, 512L, 1024L), dm15,
                            "Apple A17 Bionic", "Apple GPU 6 nhân", "8GB", "iOS 17",
                            "5G, Wi-Fi 6E, Bluetooth 5.3", "3203 mAh"),
                    createOrUpdateProduct("iPhone 15 Pro Max", 41000000f, "ip15promax.png", Arrays.asList(128L, 256L, 512L, 1024L, 2048L), dm15,
                            "Apple A17 Bionic", "Apple GPU 6 nhân", "8GB", "iOS 17",
                            "5G, Wi-Fi 6E, Bluetooth 5.3", "4422 mAh"),

    // ---------------- iPhone 16 Series ----------------
                    createOrUpdateProduct("iPhone 16", 42000000f, "ip16.png", Arrays.asList(128L, 256L, 512L), dm16,
                            "Apple A17 Bionic", "Apple GPU 6 nhân", "8GB", "iOS 18",
                            "5G, Wi-Fi 7, Bluetooth 5.4", "3300 mAh"),
                    createOrUpdateProduct("iPhone 16 Plus", 43000000f, "ip16plus.png", Arrays.asList(128L, 256L, 512L), dm16,
                            "Apple A17 Bionic", "Apple GPU 6 nhân", "8GB", "iOS 18",
                            "5G, Wi-Fi 7, Bluetooth 5.4", "4400 mAh"),
                    createOrUpdateProduct("iPhone 16 Pro", 44000000f, "ip16pro.png", Arrays.asList(128L, 256L, 512L, 1024L), dm16,
                            "Apple A18 Bionic", "Apple GPU 6 nhân", "8GB", "iOS 18",
                            "5G, Wi-Fi 7, Bluetooth 5.4", "3300 mAh"),
                    createOrUpdateProduct("iPhone 16 Pro Max", 46000000f, "ip16promax.png", Arrays.asList(128L, 256L, 512L, 1024L, 2048L), dm16,
                            "Apple A18 Bionic", "Apple GPU 6 nhân", "8GB", "iOS 18",
                            "5G, Wi-Fi 7, Bluetooth 5.4", "4500 mAh"),

    // ---------------- iPhone 17 Series ----------------
                    createOrUpdateProduct("iPhone 17", 47000000f, "ip17.png", Arrays.asList(128L, 256L, 512L), dm17,
                            "Apple A18 Bionic", "Apple GPU 6 nhân", "8GB", "iOS 19",
                            "5G, Wi-Fi 7, Bluetooth 5.4", "3400 mAh"),
                    createOrUpdateProduct("iPhone 17 Air", 48000000f, "ip17air.png", Arrays.asList(128L, 256L, 512L), dm17,
                            "Apple A18 Bionic", "Apple GPU 6 nhân", "8GB", "iOS 19",
                            "5G, Wi-Fi 7, Bluetooth 5.4", "3200 mAh"),
                    createOrUpdateProduct("iPhone 17 Pro", 49000000f, "ip17pro.png", Arrays.asList(128L, 256L, 512L, 1024L), dm17,
                            "Apple A19 Bionic", "Apple GPU 6 nhân", "12GB", "iOS 19",
                            "5G, Wi-Fi 7, Bluetooth 5.4", "3500 mAh"),
                    createOrUpdateProduct("iPhone 17 Pro Max", 51000000f, "ip17promax.png", Arrays.asList(128L, 256L, 512L, 1024L, 2048L), dm17,
                            "Apple A19 Bionic", "Apple GPU 6 nhân", "12GB", "iOS 19",
                            "5G, Wi-Fi 7, Bluetooth 5.4", "4600 mAh"),

            };
    }
        private HangHoa createOrUpdateProduct(String tenhh, float dongia, String hinh, List<Long> bonhoIds, DongMay dm,
                String cpu, String gpu, String ram, String hedieuhanh, String ketnoi, String pin) {


            HangHoa existing = hangHoaService.findByTenhh(tenhh);
            HangHoa p;

            if (existing == null) {
                p = new HangHoa();
                p.setTenhh(tenhh);
                p.setDongia(dongia);
                p.setHinh(hinh);
                p.setDongMay(dm);
                p = hangHoaService.save(p); // Save để có ID
            } else {
                p = existing;
                p.setDongia(dongia);
                p.setHinh(hinh);
                p.setDongMay(dm);
                hangHoaService.save(p);
            }


            // Tạo CTHangHoa
            List<MauSac> colors = mauSacService.findByDongMay(dm);
            for (Long bId : bonhoIds) {
                BoNho bn = boNhoService.findById(bId).orElse(null);
                if (bn == null) continue;

                for (MauSac ms : colors) {
                    CTHangHoa ct = new CTHangHoa();
                    ct.setHangHoa(p);
                    ct.setDongMay(dm);
                    ct.setMauSac(ms);
                    ct.setBoNho(bn);
                    ct.setCpu(cpu);
                    ct.setGpu(gpu);
                    ct.setRam(ram);
                    ct.setHedieuhanh(hedieuhanh);
                    ct.setKetnoi(ketnoi);
                    ct.setPin(pin);
                    ct.setDongia(dongia);
                    ct.setSoluongton(50);

                    ctHangHoaService.save(ct);
                }
            }

            return p;
        }
    }






