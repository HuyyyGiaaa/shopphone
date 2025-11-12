package com.example.shopphone.config;

import com.example.shopphone.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Cấu hình WebConfig tổng hợp
 * - Đăng ký Interceptor (RequestInterceptor, GlobalInterceptor)
 * - Khai báo Bean PasswordEncoder để mã hóa mật khẩu người dùng
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Inject interceptor riêng của bạn (dùng để xử lý logic như kiểm tra đăng nhập, session, role,...)
    @Autowired
    private RequestInterceptor requestInterceptor;

    /**
     * Đăng ký các interceptor vào Spring MVC
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // Interceptor 1: RequestInterceptor — xử lý các logic nghiệp vụ như kiểm tra đăng nhập
        registry.addInterceptor(requestInterceptor)
                .addPathPatterns("/**"); // áp dụng cho tất cả đường dẫn

        // Interceptor 2: GlobalInterceptor — thêm biến currentPath cho tất cả view (để highlight menu, v.v.)
        registry.addInterceptor(new GlobalInterceptor())
                .addPathPatterns("/**"); // cũng áp dụng cho mọi đường dẫn
    }

    /**
     * Khai báo Bean mã hóa mật khẩu dùng cho Spring Security hoặc login custom.
     * BCryptPasswordEncoder là thuật toán hash an toàn, phổ biến hiện nay.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
