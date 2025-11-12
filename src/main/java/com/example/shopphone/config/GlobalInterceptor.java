package com.example.shopphone.config;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Tóm lại dùng để sử dụng được các nút trên thanh header ở mọi trang cho tiện
 * Lớp GlobalInterceptor dùng để can thiệp vào luồng xử lý request của Spring MVC.
 * Mục đích: thêm biến "currentPath" vào Model của tất cả các view (HTML),
 * giúp Thymeleaf trong header biết trang hiện tại đang là gì (để highlight menu, điều hướng, v.v.).
 * Ngoài ra cũng truyền cartCount từ Session vào Model để hiển thị badge giỏ hàng.
 */
public class GlobalInterceptor implements HandlerInterceptor {

    /**
     * Phương thức postHandle() được gọi sau khi Controller xử lý xong,
     * nhưng trước khi view (HTML) được render ra cho người dùng.
     *
     * @param request      Đối tượng chứa thông tin request (URL, method, header, v.v.)
     * @param response     Đối tượng phản hồi
     * @param handler      Là phương thức Controller được gọi
     * @param modelAndView Dùng để thêm dữ liệu vào model trước khi render view
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {

        // Kiểm tra modelAndView khác null (chỉ áp dụng cho các request trả về view HTML)
        if (modelAndView != null) {

            // Lấy đường dẫn hiện tại (ví dụ: /login, /register, /cart, /)
            String currentPath = request.getRequestURI();

            // Thêm biến currentPath vào Model để Thymeleaf sử dụng trong view
            modelAndView.addObject("currentPath", currentPath);

            // 🛒 Lấy cartCount từ Session và thêm vào Model
            HttpSession session = request.getSession(false);
            if (session != null) {
                Object cartCount = session.getAttribute("cartCount");
                if (cartCount != null) {
                    modelAndView.addObject("cartCount", cartCount);
                } else {
                    modelAndView.addObject("cartCount", 0);
                }
            } else {
                modelAndView.addObject("cartCount", 0);
            }
        }
    }
}
