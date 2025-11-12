package com.example.shopphone.model;

/**
 * 🔹 Class: cachvanhanhRequest
 * 🔹 Mục đích: Ghi chú cách vận hành hệ thống ShopPhone
 *
 * =========================================
 * 🌐 Luồng vận hành hệ thống
 *
 * 1. Trình duyệt gửi request tới server Spring Boot:
 *       [Browser] --> HTTP Request --> [Spring Boot Server @8080]
 *
 * 2. Interceptor xử lý trước:
 *       [Interceptor]
 *          ├─ Gắn danh sách menu (menuList) vào model
 *          ├─ Xác định menu active dựa URL (activeMenu)
 *          ├─ Lấy username từ session, gán vào model
 *          └─ Lấy số lượng giỏ hàng (cartCount), gán vào model
 *
 * 3. Controller xử lý request:
 *       [Controller]
 *          ├─ HomeController: trả danh sách sản phẩm, banner…
 *          ├─ DienThoaiController: trả danh sách điện thoại
 *          └─ PhuKienController: trả danh sách phụ kiện
 *
 * 4. Thymeleaf render view:
 *       [Thymeleaf Template]
 *          ├─ Header: dùng menuList + activeMenu + username + cartCount
 *          └─ Body: dữ liệu từ controller
 *
 * 5. Browser hiển thị trang:
 *       [Rendered HTML/CSS/JS] --> [User]
 *
 * =========================================
 * ⚡ Các điểm mở rộng dễ bảo trì:
 *
 * - Thêm menu mới: chỉ cần chỉnh menuList trong Interceptor
 * - Hiển thị username thật: session user đã login
 * - Hiển thị giỏ hàng: lấy từ session hoặc database
 */

public class cachvanhanhRequest {
}
